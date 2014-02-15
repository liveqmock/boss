package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.BossConfiguration;
import com.dtv.oss.domain.BossConfigurationHome;
import com.dtv.oss.dto.BossConfigurationDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class BossConfigService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=BossConfigService.class;
	
	public BossConfigService(ServiceContext s){
		this.context=s;
	}
	
	public void bossConfigCreate(BossConfigurationDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ���ϵͳ����!");
			return;
		}
		
	 
		BossConfigurationHome bHome=null;
		BossConfiguration bc=null;
		try{
			bHome=HomeLocater.getBossConfigurationHome();
			bc=bHome.create(dto);
			 
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "����", 
					"����ϵͳ������Ϣ���������Ϊ :"+bc.getSoftwareName(), 
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
	
	public void bossConfigModify(BossConfigurationDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�ϵͳ����!");
			return;
		}
		
		 
		
	 
		BossConfigurationHome bHome=null;
		BossConfiguration bc=null;
		try{
			bHome=HomeLocater.getBossConfigurationHome();
			bc=bHome.findByPrimaryKey(dto.getSoftwareName());
			if(bc.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�޸�ϵͳ���ó���!");
				throw new ServiceException("����ϵͳ���ó���");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸�", 
					"�޸�ϵͳ����,�������Ϊ��" + bc.getSoftwareName(), 
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
