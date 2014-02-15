package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.LdapAttrConfig;
import com.dtv.oss.domain.LdapAttrConfigHome;
import com.dtv.oss.dto.LdapAttrConfigDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class LDAPAttrConfigService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=LDAPAttrConfigService.class;
	
	public LDAPAttrConfigService(ServiceContext s){
		this.context=s;
	}
	
	public void ldapAttrCreate(LdapAttrConfigDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建系统配置!");
			return;
		}
		
		LdapAttrConfigDTO dto1 = BusinessUtility.getDtoByAttrName(dto.getAttrName());
		if(dto1!=null)
			throw new ServiceException("该属性已经名称已经存在,请重新命名");
		LdapAttrConfigHome ldapAttrHome=null;
		LdapAttrConfig remote=null;
		try{
			ldapAttrHome=HomeLocater.getLdapAttrConfigHome();
			remote=ldapAttrHome.create(dto);
			 
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "新增LDAP属性字段取值规则", 
					"创建LDAP属性字段取值规则：attrName 为:"+remote.getAttrName(), 
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
	
	public void ldapAttrModify(LdapAttrConfigDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改LDAP属性字段取值规则!");
			return;
		}
		
		 
		
	 
		LdapAttrConfigHome ldapAttrHome=null;
		LdapAttrConfig remote=null;
		try{
			ldapAttrHome=HomeLocater.getLdapAttrConfigHome();
			remote=ldapAttrHome.findByPrimaryKey(dto.getAttrName());
			if(remote.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改LDAP属性字段取值规则出错!");
				throw new ServiceException("更新LDAP属性字段取值规则出错");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "修改LDAP属性字段取值规则", 
					"修改LDAP属性字段取值规则,attrName：为" + dto.getAttrName(), 
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
