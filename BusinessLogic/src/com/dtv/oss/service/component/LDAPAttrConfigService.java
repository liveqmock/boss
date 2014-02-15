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
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ���ϵͳ����!");
			return;
		}
		
		LdapAttrConfigDTO dto1 = BusinessUtility.getDtoByAttrName(dto.getAttrName());
		if(dto1!=null)
			throw new ServiceException("�������Ѿ������Ѿ�����,����������");
		LdapAttrConfigHome ldapAttrHome=null;
		LdapAttrConfig remote=null;
		try{
			ldapAttrHome=HomeLocater.getLdapAttrConfigHome();
			remote=ldapAttrHome.create(dto);
			 
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "����LDAP�����ֶ�ȡֵ����", 
					"����LDAP�����ֶ�ȡֵ����attrName Ϊ:"+remote.getAttrName(), 
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
	
	public void ldapAttrModify(LdapAttrConfigDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�LDAP�����ֶ�ȡֵ����!");
			return;
		}
		
		 
		
	 
		LdapAttrConfigHome ldapAttrHome=null;
		LdapAttrConfig remote=null;
		try{
			ldapAttrHome=HomeLocater.getLdapAttrConfigHome();
			remote=ldapAttrHome.findByPrimaryKey(dto.getAttrName());
			if(remote.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�޸�LDAP�����ֶ�ȡֵ�������!");
				throw new ServiceException("����LDAP�����ֶ�ȡֵ�������");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�޸�LDAP�����ֶ�ȡֵ����", 
					"�޸�LDAP�����ֶ�ȡֵ����,attrName��Ϊ" + dto.getAttrName(), 
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
