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
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ���ϵͳ����!");
			return;
		}
		
	 
		LdapEventCmdMapHome home=null;
		LdapEventCmdMap remote=null;
		try{
			home=HomeLocater.getLdapEventCmdMapHome();
			remote=home.create(dto);
			 
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�����¼�����ӳ��", 
					"�¼�����ӳ�䣺mapID Ϊ:"+remote.getMapID(), 
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
	
	public void ldapEventCmdMapModify(LdapEventCmdMapDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�ϵͳ����!");
			return;
		}
		
		 
		
	 
		LdapEventCmdMapHome home=null;
		LdapEventCmdMap remote=null;
		try{
			home=HomeLocater.getLdapEventCmdMapHome();
			remote=home.findByPrimaryKey(new Integer(dto.getMapID()));
			if(remote.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�޸�LDAP�¼�����ӳ�����!");
				throw new ServiceException("����LDAP�¼�����ӳ�����");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�޸��¼�����ӳ��", 
					"�޸�LDAP�¼�����ӳ��,mapIDΪ��" + dto.getMapID(), 
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
