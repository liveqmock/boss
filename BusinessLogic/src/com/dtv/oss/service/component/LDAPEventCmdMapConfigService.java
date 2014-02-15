package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.LdapEventCmdMap;
import com.dtv.oss.domain.LdapEventCmdMapHome;
import com.dtv.oss.dto.LdapEventCmdMapDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class LDAPEventCmdMapConfigService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=LDAPEventCmdMapConfigService.class;
	
	public LDAPEventCmdMapConfigService(ServiceContext s){
		this.context=s;
	}
	
	public void ldapEventCmdMapCreate(LdapEventCmdMapDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建系统配置!");
			return;
		}
		
	 
		LdapEventCmdMapHome home=null;
		LdapEventCmdMap remote=null;
		try{
			home=HomeLocater.getLdapEventCmdMapHome();
			remote=home.create(dto);
			 
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "新增事件命令映射", 
					"事件命令映射：mapID 为:"+remote.getMapID(), 
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
	
	public void ldapEventCmdMapModify(LdapEventCmdMapDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改系统配置!");
			return;
		}
		
		 
		
	 
		LdapEventCmdMapHome home=null;
		LdapEventCmdMap remote=null;
		try{
			home=HomeLocater.getLdapEventCmdMapHome();
			remote=home.findByPrimaryKey(new Integer(dto.getMapID()));
			if(remote.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改LDAP事件命令映射出错!");
				throw new ServiceException("更新LDAP事件命令映射出错");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "修改事件命令映射", 
					"修改LDAP事件命令映射,mapID为：" + dto.getMapID(), 
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
