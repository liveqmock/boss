package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.LdapProduct;
import com.dtv.oss.domain.LdapProductHome;
import com.dtv.oss.dto.LdapProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class LDAPProductConfigService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=LDAPProductConfigService.class;
	
	public LDAPProductConfigService(ServiceContext s){
		this.context=s;
	}
	
	public void ldapProductCreate(LdapProductDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建系统配置!");
			return;
		}
		
	 
		LdapProductHome ldapProductHome=null;
		LdapProduct ldapProduct=null;
		try{
			ldapProductHome=HomeLocater.getLdapProductHome();
			checkExist(dto.getProductName());
			
			ldapProduct=ldapProductHome.create(dto);
			 
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "新增LDAP产品", 
					"创建LDAP产品信息：productname为:"+ldapProduct.getProductName(), 
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
	
	/**
	 * @param productName
	 * @throws ServiceException
	 */
	private void checkExist(String productName) throws ServiceException { 
		  LdapProductDTO dto=null;
		  dto = BusinessUtility.getDtoByProductName(productName);
		 
			if(dto != null) 
				throw new ServiceException("该LDAP产品已经存在,不能重复创建");
	}

	public void ldapProductModify(LdapProductDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改系统配置!");
			return;
		}
		
		 
		
	 
		LdapProductHome ldapProductHome=null;
		LdapProduct ldapProduct=null;
		try{
			ldapProductHome=HomeLocater.getLdapProductHome();
			ldapProduct=ldapProductHome.findByPrimaryKey(dto.getProductName());
			ldapProduct.setDescription(dto.getDescription());
			ldapProduct.setStatus(dto.getStatus());
			 
			ldapProduct.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "修改LDAP产品", 
					"修改LDAP产品信息,productName：" + dto.getProductName(), 
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
