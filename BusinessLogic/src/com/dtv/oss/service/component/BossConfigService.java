package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.BossConfiguration;
import com.dtv.oss.domain.BossConfigurationHome;
import com.dtv.oss.dto.BossConfigurationDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class BossConfigService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=BossConfigService.class;
	
	public BossConfigService(ServiceContext s){
		this.context=s;
	}
	
	public void bossConfigCreate(BossConfigurationDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建系统配置!");
			return;
		}
		
	 
		BossConfigurationHome bHome=null;
		BossConfiguration bc=null;
		try{
			bHome=HomeLocater.getBossConfigurationHome();
			bc=bHome.create(dto);
			 
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "增加", 
					"增加系统配置信息：软件名称为 :"+bc.getSoftwareName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		 
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
		
	}
	
	public void bossConfigModify(BossConfigurationDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改系统配置!");
			return;
		}
		
		 
		
	 
		BossConfigurationHome bHome=null;
		BossConfiguration bc=null;
		try{
			bHome=HomeLocater.getBossConfigurationHome();
			bc=bHome.findByPrimaryKey(dto.getSoftwareName());
			if(bc.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改系统配置出错!");
				throw new ServiceException("更新系统配置出错");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改", 
					"修改系统配置,软件名称为：" + bc.getSoftwareName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "查找出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("查找出错");
		}
	}
	
	 
	 
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
