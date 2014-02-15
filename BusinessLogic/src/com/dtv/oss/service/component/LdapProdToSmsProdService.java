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
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ���ϵͳ����!");
			return;
		}
		
	 
		LdapProdToSmsProdHome home=null;
		LdapProdToSmsProd remote=null;
		try{
			
			LdapProdToSmsProdDTO dto1=BusinessUtility.getProductDtoBySmsProductID(dto.getSmsProductId());
			if (dto1!=null)
				throw new ServiceException("��SMS��Ʒ�Ѿ�����,�����ظ�����");	
			home=HomeLocater.getLdapProdToSmsProdHome(); 
			remote=home.create(dto);
			 
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "����LDAP��Ʒ/SMS��Ʒ��Ӧ��ϵ", 
					"LDAP��Ʒ/SMS��Ʒ��Ӧ��ϵ��smsProductID Ϊ:"+remote.getSmsProductId(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		 
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		
	}
	
	public void ldapProdToSmsProdModify(LdapProdToSmsProdDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�ϵͳ����!");
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
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�޸�LDAP��Ʒ/SMS��Ʒ��Ӧ��ϵ", 
					"�޸�LDAP��Ʒ/SMS��Ʒ��Ӧ��ϵ,smsProductID��" + dto.getSmsProductId(), 
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
