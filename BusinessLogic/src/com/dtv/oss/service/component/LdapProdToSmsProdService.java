package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.LdapProdToSmsProd;
import com.dtv.oss.domain.LdapProdToSmsProdHome;
import com.dtv.oss.dto.LdapProdToSmsProdDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class LdapProdToSmsProdService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=LdapProdToSmsProdService.class;
	
	public LdapProdToSmsProdService(ServiceContext s){
		this.context=s;
	}
	
	public void ldapProdToSmsProdCreate(LdapProdToSmsProdDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建系统配置!");
			return;
		}
		
	 
		LdapProdToSmsProdHome home=null;
		LdapProdToSmsProd remote=null;
		try{
			
			LdapProdToSmsProdDTO dto1=BusinessUtility.getProductDtoBySmsProductID(dto.getSmsProductId());
			if (dto1!=null)
				throw new ServiceException("该SMS产品已经存在,不能重复创建");	
			home=HomeLocater.getLdapProdToSmsProdHome(); 
			remote=home.create(dto);
			 
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "新增LDAP产品/SMS产品对应关系", 
					"LDAP产品/SMS产品对应关系：smsProductID 为:"+remote.getSmsProductId(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		 
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		
	}
	
	public void ldapProdToSmsProdModify(LdapProdToSmsProdDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改系统配置!");
			return;
		}
		
		 
		
	 
		LdapProdToSmsProdHome home=null;
		LdapProdToSmsProd remote=null;
		try{
			home=HomeLocater.getLdapProdToSmsProdHome();
			remote=home.findByPrimaryKey(new Integer(dto.getSmsProductId()));
			remote.setLdapProductName(dto.getLdapProductName());
			remote.setPriority(dto.getPriority());
			remote.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "修改LDAP产品/SMS产品对应关系", 
					"修改LDAP产品/SMS产品对应关系,smsProductID：" + dto.getSmsProductId(), 
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
