
package com.dtv.oss.service.command.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.OrganizationEJBEvent;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.domain.Organization;
import com.dtv.oss.domain.OrganizationHome;
import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * 
 * 
 * @author 260327h
 * 
 */
public class OrganizationCommand extends Command {
	private static final Class clazz = OrganizationCommand.class;
	private String loginIP="";
	private static final String commandName = "OrganizationCommand";
	HomeFactory homeFac = null;
	private int operatorID = 0;
	CommandResponseImp response = null;
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		OrganizationEJBEvent inEvent = (OrganizationEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		loginIP = inEvent.getRemoteHostAddress();
		
		// 接收返回值
		List res=new ArrayList();
		if (getVerbose()) {
			System.out.println("Enter " + commandName + " execute() now.");
		}
		try {
			homeFac = HomeFactory.getFactory();
			switch (inEvent.getActionType()) {
				case OrganizationEJBEvent.ORGANIZATION_ADD:				
					res=OrganizationDataAdd(inEvent.getDto());
				    break;
				case OrganizationEJBEvent.ORGANIZATION_UPDATE :
					res=OrganizationDataModify(inEvent.getDto());
					break;
				case OrganizationEJBEvent.ORGANIZATION_DELETE :				
					res=OrganizationDataDelete(inEvent.getCurrentDataList());
					break;
				default :
					throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
			// 设置结果
			response.setPayload(res);
			return response;
		} catch (CommandException ce) {
			LogUtility.log(clazz,LogLevel.ERROR,this,ce);
			throw ce;
		} catch (HomeFactoryException he) {
			LogUtility.log(clazz,LogLevel.ERROR,this,he);
			throw new CommandException(ErrorCode.HOMEFAC_CANNT_LOOKUP);
		} catch (CreateException ce) {
			LogUtility.log(clazz,LogLevel.ERROR,this,ce);
			throw new CommandException(ErrorCode.ENTITYBEAN_CANNT_CREATE);
		} catch (FinderException fe) {
			LogUtility.log(clazz,LogLevel.ERROR,this,fe);
			throw new CommandException(ErrorCode.ENTITYBEAN_HOMEINTERFACE_CANNTFIND);
		} catch (Exception e) {
			LogUtility.log(clazz,LogLevel.ERROR,this,e);
			throw new CommandException(ErrorCode.APP_GENERAL_ERROR);
		}
	}

/**
 * 添加组织机构
 * 
 * @param dto
 * @throws CommandException
 * @throws ServiceException
 */
	private List OrganizationDataAdd(OrganizationDTO dto)throws CommandException, ServiceException {
		List res=new ArrayList();
		if(!checkOrganization(dto)){
			throw new CommandException("设置的组织类型在上级组织机构中不存在");
		}
		String id=null;
		try {
			OrganizationHome home=HomeLocater.getOrganizationHome();
			id=home.create(dto).getOrgID().toString();
			res.add(id);
		} catch (HomeFactoryException e) {
			throw new CommandException("创建组织机构失败，服务出错");
		} catch (CreateException e) {
			throw new CommandException("创建组织机构失败");
		}
// 记录系统日志
		SystemLogRecorder.createSystemLog(loginIP, 
				operatorID, 0, 
				SystemLogRecorder.LOGMODULE_CONFIG, "增加", "增加组织机构，组织机构ID:"+id, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		return res;
	}
/**
 * 更新组织机构
 * 
 * @param dto
 * @throws CommandException
 * @throws ServiceException
 */
	private List OrganizationDataModify(OrganizationDTO dto)throws CommandException, ServiceException{
		List res=new ArrayList();
		if(!checkOrganization(dto)){
			throw new CommandException("设置的组织类型在上级组织机构中不存在");
		}

		try {
			OrganizationHome home=HomeLocater.getOrganizationHome();
			Organization ds=home.findByPrimaryKey(new Integer(dto.getOrgID()));
			if(ds.ejbUpdate(dto)==-1)
			    throw new CommandException("更新组织机构失败。");
			System.out.println("被更新的记录:"+dto);
			res.add(dto.getOrgID()+"");
		} catch (HomeFactoryException e) {
			throw new CommandException("更新组织机构失败，服务出错");
		} catch (FinderException e) {
			throw new CommandException("更新组织机构失败，组织机构"+dto.getOrgID()+"找不到");
		}
// 记录系统日志
		SystemLogRecorder.createSystemLog(loginIP, 
				operatorID, 0, 
				SystemLogRecorder.LOGMODULE_CONFIG, "修改", "修改组织机构，组织机构的ID：" +dto.getOrgID(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		return res;
	}
	/**
	 * 删除
	 * 
	 * @throws ServiceException
	 * 
	 */
	private List OrganizationDataDelete(List currentDataList) throws CommandException, CreateException, FinderException, HomeFactoryException, ServiceException {
		List res=new ArrayList();
		OrganizationHome home=HomeLocater.getOrganizationHome();
		Organization ds = null;			
			
		for(int i=0;i<currentDataList.size();i++)
		{
			Integer id=(Integer)currentDataList.get(i);
			int parentID=0;
			
			if (isUsedByOthers(id.intValue()))
				throw new CommandException("删除组织机构失败，组织机构"+id+"有下级组织机构不能删除");
			try{				
				ds = home.findByPrimaryKey(id);
				parentID=ds.getParentOrgID();
			}catch (FinderException e){
				e.printStackTrace();
				response.setPayload("删除组织机构失败，无法找到--"+id);
				throw new CommandException("删除组织机构失败，无法找到--"+id);
			}
			
			try{
				ds.remove();
				res.add(parentID+"");
			}catch (Exception e){
				e.printStackTrace();
				response.setPayload("删除组织机构失败，该组织机构的ID是"+id);
				throw new CommandException("删除组织机构失败，该组织机构的ID是"+id);
			}
			

// 记录系统日志
    		SystemLogRecorder.createSystemLog(loginIP, 
    				operatorID, 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除", "删除组织机构，组织机构ID：" +id, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		return res;
		
	}
	
	private boolean isUsedByOthers(int id) throws HomeFactoryException {
		OrganizationHome home = HomeLocater.getOrganizationHome();
		Collection lst = null;
		try {
			lst = home.findChilds(id);
			LogUtility.log(clazz,LogLevel.ERROR,"下级组织:"+lst);
		} catch (FinderException e) {
		}

		if ((lst != null) && (!lst.isEmpty())) {
			return true;
		}

		return false;
	}
	/**
	 * 检查当前节点的类型是否配置在父节点中.
	 * 得到父节点,取出父节点对应的orgAndSubOrgConfig配置,得到父节点下可以配置的节点类型集,
	 * 如果当前节点类型不在父节点允许的类型集中,返回false;
	 * @param org 要检查的节点
	 * @return
	 * @throws CommandException
	 */
	private boolean checkOrganization(OrganizationDTO org) throws CommandException{
		Organization parentOrg = null;
//		if(org.getParentOrgID()==0) return true;//当是总公司不进行该项检查.总公司没父节点,总公司的父节点ID可能非零,这个方法不适用
		
		try {
			OrganizationHome home = HomeLocater.getOrganizationHome();
			parentOrg = home.findByPrimaryKey(new Integer(org.getParentOrgID()));
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			//throw new CommandException("数据服务出错");
		} catch (FinderException e) {
			//throw new CommandException("验证出错,找不到"+org.getOrgName()+"的上级组织");
			e.printStackTrace();
			return true;//当前节点没有父节点时,认为当前节点是总公司,不进行检查;逻辑依据:除总公司外,没有父节点的不可能出现在树上.
		}
		Map orgConfig=BusinessUtility.getOrgAndSubOrgConfig();
		String subOrgType=(String) orgConfig.get(parentOrg.getOrgType());
		if(subOrgType!=null&&subOrgType.indexOf(org.getOrgType())>=0){
			return true;
		}
		return false;
	}
	
}
