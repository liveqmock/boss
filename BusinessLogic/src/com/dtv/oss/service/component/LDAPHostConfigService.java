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
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ���ϵͳ����!");
			return;
		}
		
	 
		LdapHostHome ldapHostHome=null;
		LdapHost ldapHost=null;
		try{
			ldapHostHome=HomeLocater.getLdapHostHome();
			ldapHost=ldapHostHome.create(dto);
			 
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "����LDAP����", 
					"����LDAP������Ϣ��HostID Ϊ:"+ldapHost.getHostID(), 
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
	
	public void ldapHostModify(LdapHostDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�ϵͳ����!");
			return;
		}
		
		 
		
	 
		LdapHostHome ldapHostHome=null;
		LdapHost ldapHost=null;
		try{
			ldapHostHome=HomeLocater.getLdapHostHome();
			ldapHost=ldapHostHome.findByPrimaryKey(new Integer(dto.getHostID()));
			if(ldapHost.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�޸�LDAP��������!");
				throw new ServiceException("����LDAP��������");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�޸�LDAP����", 
					"�޸�LDAP��������,hostIDΪ��" + dto.getHostID(), 
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
