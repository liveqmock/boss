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
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ���ϵͳ����!");
			return;
		}
		
	 
		LdapProductHome ldapProductHome=null;
		LdapProduct ldapProduct=null;
		try{
			ldapProductHome=HomeLocater.getLdapProductHome();
			checkExist(dto.getProductName());
			
			ldapProduct=ldapProductHome.create(dto);
			 
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "����LDAP��Ʒ", 
					"����LDAP��Ʒ��Ϣ��productnameΪ:"+ldapProduct.getProductName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		 
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
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
				throw new ServiceException("��LDAP��Ʒ�Ѿ�����,�����ظ�����");
	}

	public void ldapProductModify(LdapProductDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�ϵͳ����!");
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
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�޸�LDAP��Ʒ", 
					"�޸�LDAP��Ʒ��Ϣ,productName��" + dto.getProductName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "���ҳ���");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("���ҳ���");
		}
	}
	
	 
	 
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
