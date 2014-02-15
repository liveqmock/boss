package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.ManualTransferSetting;
import com.dtv.oss.domain.ManualTransferSettingHome;
import com.dtv.oss.domain.OpGroup;
import com.dtv.oss.domain.OpGroupHome;
import com.dtv.oss.dto.ManualTransferSettingDTO;
import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigManualTransferService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigManualTransferService.class;
	
	public ConfigManualTransferService(ServiceContext s){
		this.context=s;
	}
	
	public void manualTransferModify(ManualTransferSettingDTO dto)throws ServiceException{
		if(dto==null)
			throw new ServiceException("����������ת���ò���Ϊ�գ�");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸���ת���ÿ�ʼ");
				
		try{
			ManualTransferSettingHome manualTranHome = HomeLocater.getManualTransferSettingHome();
		 
			ManualTransferSetting manuTrans=manualTranHome.findByPrimaryKey(new Integer(dto.getSeqNo()));
			
			if(manuTrans.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"��ת�����޸ĳ���");
	    		throw new ServiceException("��ת�����޸ĳ���");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸���ת����", 
					"�޸���ת������Ϣ,�޸ĵ�seqnoΪ:" +dto.getSeqNo(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "���Ҷ������");
    		throw new ServiceException("���Ҷ������");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "������ת���ý���");
	}
	
	public void manualTransferCreate(ManualTransferSettingDTO  dto)throws ServiceException{
		 if (dto == null) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		
		 try{
		   
			 ManualTransferSettingHome manualTranHome = HomeLocater.getManualTransferSettingHome();
			 
			 ManualTransferSetting  opGroup = manualTranHome.create(dto);
	           
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "������ת����",
						"������ת����seqnoΪ:" + opGroup.getSeqNo(), 
						
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	         
	        
	    }catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
	}
	 
	 
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
