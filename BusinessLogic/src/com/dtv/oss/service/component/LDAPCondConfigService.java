package com.dtv.oss.service.component;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.LdapCondition;
import com.dtv.oss.domain.LdapConditionHome;
import com.dtv.oss.dto.LdapConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class LDAPCondConfigService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=LDAPCondConfigService.class;
	
	public LDAPCondConfigService(ServiceContext s){
		this.context=s;
	}
	
	public void ldapCondCreate(LdapConditionDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建LDAP条件!");
			return;
		}
		LdapConditionDTO dto1 = BusinessUtility.getDtoByCondName(dto.getCondName());
		if(dto1!=null)
			throw new ServiceException("该LDAP条件名称已经存在,请重新命名");
		 
	 
		LdapConditionHome ldapCondHome=null;
		LdapCondition remote=null;
		try{
			ldapCondHome=HomeLocater.getLdapConditionHome();
			remote=ldapCondHome.create(dto);
			 
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "新增LDAP条件", 
					"创建LDAP条件：条件名称为:"+remote.getCondName(), 
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
	
	public void ldapCondModify(LdapConditionDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改LDAP条件!");
			return;
		}
		
		 
		
	 
		LdapConditionHome ldapCondHome=null;
		LdapCondition remote=null;
		try{
			ldapCondHome=HomeLocater.getLdapConditionHome();
			Collection condCol = ldapCondHome.findByCondName(dto.getCondName());
			remote=ldapCondHome.findByPrimaryKey(new Integer(dto.getCondId()));
			if(!remote.getCondName().equalsIgnoreCase(dto.getCondName()) && 
					condCol.size()>0)
				throw new ServiceException("该LDAP条件名称已经存在,请重新命名");
			remote.setCondName(dto.getCondName());
			remote.setCondString(dto.getCondString());
			remote.setHostId(dto.getHostId());
			remote.setDescription(dto.getDescription());
			remote.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			 
					
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "修改LDAP条件", 
					"修改LDAP条件,LDAP条件名称：为" + dto.getCondName(), 
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
