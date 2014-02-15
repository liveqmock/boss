package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.LdapHost;
import com.dtv.oss.domain.LdapHostHome;
import com.dtv.oss.dto.LdapHostDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class LDAPHostConfigService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=LDAPHostConfigService.class;
	
	public LDAPHostConfigService(ServiceContext s){
		this.context=s;
	}
	
	public void LdapHostCreate(LdapHostDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建系统配置!");
			return;
		}
		
	 
		LdapHostHome ldapHostHome=null;
		LdapHost ldapHost=null;
		try{
			ldapHostHome=HomeLocater.getLdapHostHome();
			ldapHost=ldapHostHome.create(dto);
			 
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "新增LDAP主机", 
					"创建LDAP主机信息：HostID 为:"+ldapHost.getHostID(), 
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
	
	public void ldapHostModify(LdapHostDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改系统配置!");
			return;
		}
		
		 
		
	 
		LdapHostHome ldapHostHome=null;
		LdapHost ldapHost=null;
		try{
			ldapHostHome=HomeLocater.getLdapHostHome();
			ldapHost=ldapHostHome.findByPrimaryKey(new Integer(dto.getHostID()));
			if(ldapHost.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改LDAP主机出错!");
				throw new ServiceException("更新LDAP主机出错");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "修改LDAP主机", 
					"修改LDAP主机配置,hostID为：" + dto.getHostID(), 
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
