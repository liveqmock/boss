package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.OpGroup;
import com.dtv.oss.domain.OpGroupHome;
import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigOpGroupService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigOpGroupService.class;
	
	public ConfigOpGroupService(ServiceContext s){
		this.context=s;
	}
	
	public void OpGroupModify(OpGroupDTO dto)throws ServiceException{
		if(dto==null)
			throw new ServiceException("�������󣬲���Ա�鲻��Ϊ�գ�");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸Ĳ���Ա����Ϣ��ʼ");
				
		try{
			OpGroupHome og2pHome = HomeLocater.getOpGroupHome();
		 
			OpGroup opGroup=og2pHome.findByPrimaryKey(new Integer(dto.getOpGroupID()));
			
			if(opGroup.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"���²���Ա�����");
	    		throw new ServiceException("���²���Ա����Ϣ����");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ĳ���Ա��", 
					"�޸Ĳ���Ա����Ϣ,�޸ĵĲ���Ա��IDΪ:" +dto.getOpGroupID(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "���²���Ա�����");
	}
	
	public void addOpGroup(OpGroupDTO  dto)throws ServiceException{
		 if (dto == null) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		
		 try{
		   
		 	OpGroupHome og2pHome = HomeLocater.getOpGroupHome();
		 	OpGroup  opGroup = og2pHome.create(dto);
	           
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "��������Ա��",
						"��������Ա�� OPGROUPIDΪ:" + opGroup.getOpGroupID(), 
						
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
