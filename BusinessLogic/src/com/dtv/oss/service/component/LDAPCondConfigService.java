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
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ���LDAP����!");
			return;
		}
		LdapConditionDTO dto1 = BusinessUtility.getDtoByCondName(dto.getCondName());
		if(dto1!=null)
			throw new ServiceException("��LDAP���������Ѿ�����,����������");
		 
	 
		LdapConditionHome ldapCondHome=null;
		LdapCondition remote=null;
		try{
			ldapCondHome=HomeLocater.getLdapConditionHome();
			remote=ldapCondHome.create(dto);
			 
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "����LDAP����", 
					"����LDAP��������������Ϊ:"+remote.getCondName(), 
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
	
	public void ldapCondModify(LdapConditionDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�LDAP����!");
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
				throw new ServiceException("��LDAP���������Ѿ�����,����������");
			remote.setCondName(dto.getCondName());
			remote.setCondString(dto.getCondString());
			remote.setHostId(dto.getHostId());
			remote.setDescription(dto.getDescription());
			remote.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			 
					
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�޸�LDAP����", 
					"�޸�LDAP����,LDAP�������ƣ�Ϊ" + dto.getCondName(), 
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
