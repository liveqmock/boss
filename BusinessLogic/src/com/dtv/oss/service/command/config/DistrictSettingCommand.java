/*
 * Created on 2006-5-9 by Stone Liang
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.command.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ListEJBEvent;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.domain.DistrictSetting;
import com.dtv.oss.domain.DistrictSettingHome;
import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DistrictSettingCommand extends Command {
	private static final Class clazz = DistrictSettingCommand.class;
	private String loginIP="";
	private static final String commandName = "DistrictSettingCommand";
	HomeFactory homeFac = null;
	private int operatorID = 0;
	CommandResponseImp response = null;
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		ListEJBEvent inEvent = (ListEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		loginIP = inEvent.getRemoteHostAddress();
		
		//接收返回值
		List res=new ArrayList();
		if (getVerbose()) {
			System.out.println("Enter " + commandName + " execute() now.");
		}
		try {
			homeFac = HomeFactory.getFactory();
			switch (inEvent.getActionType()) {
				case ListEJBEvent.DISTRICT_SETTING_ADD:				
					res=districtSettingDataAdd(inEvent.getDto());
				    break;
				case ListEJBEvent.DISTRICT_SETTING_UPDATE :
					res=districtSettingDataModify(inEvent.getDto());
					break;
				case ListEJBEvent.DISTRICT_SETTING_DELETE :				
					res=districtSettingDataDelete(inEvent.getCurrentDataList());
					break;
				default :
					throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
			//设置结果
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
 * 添加行政区
 * @param dto
 * @throws CommandException
 * @throws ServiceException
 */
	private List districtSettingDataAdd(DistrictSettingDTO dto)throws CommandException, ServiceException {
		List res=new ArrayList();
		String id="";
		try {
			DistrictSettingHome home=HomeLocater.getDistrictSettingHome();
			id=home.create(dto).getId().toString();
			res.add(id);
		} catch (HomeFactoryException e) {
			throw new CommandException("创建行政区失败，服务出错");
		} catch (CreateException e) {
			throw new CommandException("创建行政区失败");
		}
//		记录系统日志
		SystemLogRecorder.createSystemLog(loginIP, 
				operatorID, 0, 
				SystemLogRecorder.LOGMODULE_CONFIG, "增加", "增加行政区，行政区ID:"+id, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		return res;
	}
/**
 * 更新行政区
 * @param dto
 * @throws CommandException
 * @throws ServiceException
 */
	private List districtSettingDataModify(DistrictSettingDTO dto)throws CommandException, ServiceException{
		List res=new ArrayList();
		try {
			DistrictSettingHome home=HomeLocater.getDistrictSettingHome();
			DistrictSetting ds=home.findByPrimaryKey(new Integer(dto.getId()));
			if(ds.ejbUpdate(dto)==-1)
				throw new CommandException("更新行政区失败");
			
			System.out.println("被更新的记录:"+dto);
			res.add(dto.getId()+"");
		} catch (HomeFactoryException e) {
			throw new CommandException("更新行政区失败，服务出错");
		} catch (FinderException e) {
			throw new CommandException("更新行政区失败，行政区"+dto.getId()+"找不到");
		}
//		记录系统日志
		SystemLogRecorder.createSystemLog(loginIP, 
				operatorID, 0, 
				SystemLogRecorder.LOGMODULE_CONFIG, "修改", "修改行政区，行政区的ID：" +dto.getId(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		return res;
	}
	/**
	 * 删除
	 * @throws ServiceException
	 * 	 
	 */
	private List districtSettingDataDelete(List currentDataList) throws CommandException, CreateException, FinderException, HomeFactoryException, ServiceException {
		List res=new ArrayList();
		DistrictSettingHome home=HomeLocater.getDistrictSettingHome();
		DistrictSetting ds = null;			
			
		for(int i=0;i<currentDataList.size();i++)
		{
			Integer id=(Integer)currentDataList.get(i);
			int belongID=0;
			
			if (isUsedByOthers(id.intValue()))
				throw new CommandException("删除行政区失败，行政区"+id+"有下级行政区不能删除");
			try{				
				ds = home.findByPrimaryKey(id);
				belongID=ds.getBelongTo();
			}catch (FinderException e){
				e.printStackTrace();
				response.setPayload("删除行政区失败，无法找到--"+id);
				throw new CommandException("删除行政区失败，无法找到--"+id);
			}
			
			try{
				ds.remove();
				res.add(belongID+"");
			}catch (Exception e){
				e.printStackTrace();
				response.setPayload("删除行政区失败，该行政区的ID是"+id);
				throw new CommandException("删除行政区失败，该行政区的ID是"+id);
			}
			

//			记录系统日志
    		SystemLogRecorder.createSystemLog(loginIP, 
    				operatorID, 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除", "删除行政区，行政区的ID是：" +id, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		return res;
		
	}
	private boolean isUsedByOthers(int id)
	throws CommandException, CreateException, FinderException, HomeFactoryException
	{
		DistrictSettingHome home=HomeLocater.getDistrictSettingHome();
		Collection lst = null;
		try
		{
			lst = home.findByBelongTo(id);
		}catch (FinderException e){
		}
		
		if ((lst!=null)&&(!lst.isEmpty()))
		{
			return true;
		}
		
		return false;
	}
}
