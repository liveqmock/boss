
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
		
		// ���շ���ֵ
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
			// ���ý��
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
 * �����֯����
 * 
 * @param dto
 * @throws CommandException
 * @throws ServiceException
 */
	private List OrganizationDataAdd(OrganizationDTO dto)throws CommandException, ServiceException {
		List res=new ArrayList();
		if(!checkOrganization(dto)){
			throw new CommandException("���õ���֯�������ϼ���֯�����в�����");
		}
		String id=null;
		try {
			OrganizationHome home=HomeLocater.getOrganizationHome();
			id=home.create(dto).getOrgID().toString();
			res.add(id);
		} catch (HomeFactoryException e) {
			throw new CommandException("������֯����ʧ�ܣ��������");
		} catch (CreateException e) {
			throw new CommandException("������֯����ʧ��");
		}
// ��¼ϵͳ��־
		SystemLogRecorder.createSystemLog(loginIP, 
				operatorID, 0, 
				SystemLogRecorder.LOGMODULE_CONFIG, "����", "������֯��������֯����ID:"+id, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		return res;
	}
/**
 * ������֯����
 * 
 * @param dto
 * @throws CommandException
 * @throws ServiceException
 */
	private List OrganizationDataModify(OrganizationDTO dto)throws CommandException, ServiceException{
		List res=new ArrayList();
		if(!checkOrganization(dto)){
			throw new CommandException("���õ���֯�������ϼ���֯�����в�����");
		}

		try {
			OrganizationHome home=HomeLocater.getOrganizationHome();
			Organization ds=home.findByPrimaryKey(new Integer(dto.getOrgID()));
			if(ds.ejbUpdate(dto)==-1)
			    throw new CommandException("������֯����ʧ�ܡ�");
			System.out.println("�����µļ�¼:"+dto);
			res.add(dto.getOrgID()+"");
		} catch (HomeFactoryException e) {
			throw new CommandException("������֯����ʧ�ܣ��������");
		} catch (FinderException e) {
			throw new CommandException("������֯����ʧ�ܣ���֯����"+dto.getOrgID()+"�Ҳ���");
		}
// ��¼ϵͳ��־
		SystemLogRecorder.createSystemLog(loginIP, 
				operatorID, 0, 
				SystemLogRecorder.LOGMODULE_CONFIG, "�޸�", "�޸���֯��������֯������ID��" +dto.getOrgID(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		return res;
	}
	/**
	 * ɾ��
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
				throw new CommandException("ɾ����֯����ʧ�ܣ���֯����"+id+"���¼���֯��������ɾ��");
			try{				
				ds = home.findByPrimaryKey(id);
				parentID=ds.getParentOrgID();
			}catch (FinderException e){
				e.printStackTrace();
				response.setPayload("ɾ����֯����ʧ�ܣ��޷��ҵ�--"+id);
				throw new CommandException("ɾ����֯����ʧ�ܣ��޷��ҵ�--"+id);
			}
			
			try{
				ds.remove();
				res.add(parentID+"");
			}catch (Exception e){
				e.printStackTrace();
				response.setPayload("ɾ����֯����ʧ�ܣ�����֯������ID��"+id);
				throw new CommandException("ɾ����֯����ʧ�ܣ�����֯������ID��"+id);
			}
			

// ��¼ϵͳ��־
    		SystemLogRecorder.createSystemLog(loginIP, 
    				operatorID, 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ��", "ɾ����֯��������֯����ID��" +id, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		return res;
		
	}
	
	private boolean isUsedByOthers(int id) throws HomeFactoryException {
		OrganizationHome home = HomeLocater.getOrganizationHome();
		Collection lst = null;
		try {
			lst = home.findChilds(id);
			LogUtility.log(clazz,LogLevel.ERROR,"�¼���֯:"+lst);
		} catch (FinderException e) {
		}

		if ((lst != null) && (!lst.isEmpty())) {
			return true;
		}

		return false;
	}
	/**
	 * ��鵱ǰ�ڵ�������Ƿ������ڸ��ڵ���.
	 * �õ����ڵ�,ȡ�����ڵ��Ӧ��orgAndSubOrgConfig����,�õ����ڵ��¿������õĽڵ����ͼ�,
	 * �����ǰ�ڵ����Ͳ��ڸ��ڵ���������ͼ���,����false;
	 * @param org Ҫ���Ľڵ�
	 * @return
	 * @throws CommandException
	 */
	private boolean checkOrganization(OrganizationDTO org) throws CommandException{
		Organization parentOrg = null;
//		if(org.getParentOrgID()==0) return true;//�����ܹ�˾�����и�����.�ܹ�˾û���ڵ�,�ܹ�˾�ĸ��ڵ�ID���ܷ���,�������������
		
		try {
			OrganizationHome home = HomeLocater.getOrganizationHome();
			parentOrg = home.findByPrimaryKey(new Integer(org.getParentOrgID()));
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			//throw new CommandException("���ݷ������");
		} catch (FinderException e) {
			//throw new CommandException("��֤����,�Ҳ���"+org.getOrgName()+"���ϼ���֯");
			e.printStackTrace();
			return true;//��ǰ�ڵ�û�и��ڵ�ʱ,��Ϊ��ǰ�ڵ����ܹ�˾,�����м��;�߼�����:���ܹ�˾��,û�и��ڵ�Ĳ����ܳ���������.
		}
		Map orgConfig=BusinessUtility.getOrgAndSubOrgConfig();
		String subOrgType=(String) orgConfig.get(parentOrg.getOrgType());
		if(subOrgType!=null&&subOrgType.indexOf(org.getOrgType())>=0){
			return true;
		}
		return false;
	}
	
}
