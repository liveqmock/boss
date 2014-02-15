package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.CatvJobCardConfig;
import com.dtv.oss.domain.CatvJobCardConfigHome;
import com.dtv.oss.domain.CatvJobCardConfigPK;
import com.dtv.oss.domain.FiberNode;
import com.dtv.oss.domain.FiberNodeHome;
import com.dtv.oss.domain.FiberReceiver;
import com.dtv.oss.domain.FiberReceiverHome;
import com.dtv.oss.domain.FiberTransmitter;
import com.dtv.oss.domain.FiberTransmitterHome;
import com.dtv.oss.domain.MachineRoom;
import com.dtv.oss.domain.MachineRoomHome;
import com.dtv.oss.dto.CatvJobCardConfigDTO;
import com.dtv.oss.dto.FiberNodeDTO;
import com.dtv.oss.dto.FiberReceiverDTO;
import com.dtv.oss.dto.FiberTransmitterDTO;
import com.dtv.oss.dto.MachineRoomDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigResourceService extends AbstractService {

	private ServiceContext context=null;
	private static final Class clazz = ServiceConfigService.class;
	private MachineRoomHome mrHome=null;
	private FiberTransmitterHome ftHome=null;
	private FiberNodeHome fnHome=null;
	private FiberReceiverHome frHome=null;
	private CatvJobCardConfigHome catvJobCardHome=null;

	public ConfigResourceService(ServiceContext inContext){
		this.context=inContext;
	}
	//create the object of machineroom
	public void createMachineRoom(MachineRoomDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"��������");
		try{
			getHome(dto);
			int exist=BusinessUtility.getCountByCode(dto.getMachineRoomCode());
			if(exist>0)
				   throw new ServiceException("�û��������Ѿ�����,���������룡");
			MachineRoom mr=mrHome.create(dto);
			Integer id=mr.getId();
			context.put("ID",id);
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "������������");                    
            throw new ServiceException("������������");
		} 

	} 
	
	public void delMachineRoom(MachineRoomDTO dto) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"ɾ������");
		try{
			getHome(dto);
			mrHome.remove(new Integer(dto.getId()));
			 
		} 
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "ɾ����������");                    
            throw new ServiceException("ɾ����������");
		} 
		 
		
	}
	
	public void updateMachineRoom(MachineRoomDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"���»���");
	    try{
	    	getHome(dto);
	    	MachineRoom mr =mrHome.findByPrimaryKey(new Integer(dto.getId()));
	    	StringBuffer logDes=new StringBuffer();
	    	logDes.append("������ţ�" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";�������ƣ�", String.valueOf(mr.getMachineRoomName()),String.valueOf(dto.getMachineRoomName())));
			logDes.append(SystemLogRecorder.appendDescString(";��������", BusinessUtility.getDistrictNameById(mr.getDistrictId()),BusinessUtility.getDistrictNameById(dto.getDistrictId())));
			logDes.append(SystemLogRecorder.appendDescString(";��ϸ��ַ��", mr.getDetailAddress(),dto.getDetailAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";������Ϣ��", mr.getDescription(),dto.getDescription())); 
			 
	    	if(mr.getMachineRoomCode().equalsIgnoreCase(dto.getMachineRoomCode())){
	    		if (mr.ejbUpdate(dto)==-1){ 
	 			   throw new RuntimeException("MachineRoomDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of MachineRoomDTO is set.");
	 		    }	
	    	}
	    	else{
	    		int exist=BusinessUtility.getCountByCode(dto.getMachineRoomCode());
				if(exist>0)
					   throw new ServiceException("�û��������Ѿ�����,���������룡");
				if (mr.ejbUpdate(dto)==-1){ 
		 			   throw new ServiceException("MachineRoomDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of MachineRoomDTO is set.");
		 		    }	
	    	}
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "���»���", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	    	 
	    		 
	    	
	    } 
          catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("�������ҳ�������ID��"+dto.getId());
        }
	}
	
	public void createFiberTransmitter(FiberTransmitterDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ⷢ���");
		try{
			getHome(dto);
			int exist=BusinessUtility.getCountByFiberCode(dto.getFiberTransmitterCode());
			if(exist>0)
				   throw new ServiceException("�ùⷢ��������Ѿ�����,���������룡");
			FiberTransmitter mr=ftHome.create(dto);
			Integer id=mr.getId();
			context.put("FIberTransID",id);
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "�����ⷢ�������");                    
            throw new ServiceException("�����ⷢ�������");
		} 

	}  
	public void updateFiberTransmitter(FiberTransmitterDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"���¹ⷢ���");
	    try{
	    	getHome(dto);
	    	FiberTransmitter ft =ftHome.findByPrimaryKey(new Integer(dto.getId()));
	    	StringBuffer logDes=new StringBuffer();
	    	logDes.append("�ⷢ�����ţ�" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";����ID��", String.valueOf(ft.getMachineRoomId()),String.valueOf(dto.getMachineRoomId())));
			logDes.append(SystemLogRecorder.appendDescString(";��������", BusinessUtility.getDistrictNameById(ft.getDistrictId()),BusinessUtility.getDistrictNameById(dto.getDistrictId())));
			logDes.append(SystemLogRecorder.appendDescString(";��ϸ��ַ��", ft.getDetailAddress(),dto.getDetailAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";������Ϣ��", ft.getDescription(),dto.getDescription())); 
			 
	    	if(ft.getFiberTransmitterCode().equalsIgnoreCase(dto.getFiberTransmitterCode())){
	    		if (ft.ejbUpdate(dto)==-1){ 
	 			   throw new ServiceException("FiberTransmitterDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberTransmitterDTO is set.");
	 		    }	
	    	}
	    	else{
	    		int exist=BusinessUtility.getCountByFiberCode(dto.getFiberTransmitterCode());
				if(exist>0)
					   throw new ServiceException("�ùⷢ��������Ѿ�����,���������룡");
				if (ft.ejbUpdate(dto)==-1){ 
		 			   throw new ServiceException("FiberTransmitterDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberTransmitterDTO is set.");
		 		    }	
	    	}
	    		
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "���¹ⷢ���", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);	 
	    	
	    } 
          catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("�ⷢ������ҳ����ⷢ���ID��"+dto.getId());
        }
	}
	public void delFiberTransmitter(FiberTransmitterDTO dto) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"ɾ���ⷢ���");
		try{
			getHome(dto);
			ftHome.remove(new Integer(dto.getId()));
			 
		} 
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "ɾ���ⷢ�������");                    
            throw new ServiceException("ɾ���ⷢ�������");
		} 
		 
		
	}
	public void createFiberReceiver(FiberReceiverDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"�������ջ�");
		try{
			getHome(dto);
			int exist=BusinessUtility.getCountByFiberRecieverCode(dto.getFiberReceiverCode());
			if(exist>0)
				   throw new ServiceException("�ù��ջ������Ѿ�����,���������룡");
			FiberReceiver fr=frHome.create(dto);
			Integer id=fr.getId();
			context.put("FiberReceiverID",id);
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "�������ջ�����");                    
            throw new ServiceException("�������ջ�����");
		} 

	}  
	public void updateFiberReceiver(FiberReceiverDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"���¹���ջ�");
	    try{
	    	getHome(dto);
	    	StringBuffer logDes=new StringBuffer();
	    	FiberReceiver fr =frHome.findByPrimaryKey(new Integer(dto.getId()));
	    	logDes.append("����ջ���ţ�" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";�ⷢ����ţ�", String.valueOf(fr.getFiberTransmitterId()),String.valueOf(dto.getFiberTransmitterId())));
			logDes.append(SystemLogRecorder.appendDescString(";��������", BusinessUtility.getDistrictNameById(fr.getDistrictId()),BusinessUtility.getDistrictNameById(dto.getDistrictId())));
			logDes.append(SystemLogRecorder.appendDescString(";��ϸ��ַ��", fr.getDetailAddress(),dto.getDetailAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";������Ϣ��", fr.getDescription(),dto.getDescription())); 
	    	if(fr.getFiberReceiverCode().equalsIgnoreCase(dto.getFiberReceiverCode())){
	    		if (fr.ejbUpdate(dto)==-1){ 
	 			   throw new ServiceException("FiberReceiverDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberReceiverDTO is set.");
	 		    }	
	    	}
	    	else{
	    		int exist=BusinessUtility.getCountByFiberRecieverCode(dto.getFiberReceiverCode());
				if(exist>0)
					   throw new ServiceException("�ù��ջ������Ѿ�����,���������룡");
				if (fr.ejbUpdate(dto)==-1){ 
		 			   throw new ServiceException("FiberReceiverDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberTransmitterDTO is set.");
		 		    }	
	    	}
	    		
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "���¹���ջ�", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);	 	 
	    	
	    } 
          catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("���ջ����ҳ������ջ�ID��"+dto.getId());
        }
	}
	public void delFiberReceiver(FiberReceiverDTO dto) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"ɾ�����ջ�");
		try{
			getHome(dto);
			frHome.remove(new Integer(dto.getId()));
			 
		} 
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "ɾ�����ջ�����");                    
            throw new ServiceException("ɾ�����ջ�����");
		} 
		 
		
	}
	public void createFiberNode(FiberNodeDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"������ڵ�");
		try{
			getHome(dto);
			int exist=BusinessUtility.getCountByFiberNodeCode(dto.getFiberNodeCode());
			if(exist>0)
				   throw new ServiceException("�ù�ڵ�����Ѿ�����,���������룡");
			FiberNode fn=fnHome.create(dto);
			Integer id=fn.getId();
			context.put("FiberNodeID",id);
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "������ڵ����");                    
            throw new ServiceException("������ڵ����");
		} 

	}  
	public void updateFiberNode(FiberNodeDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"���¹�ڵ�");
	    try{
	    	getHome(dto);
	    	StringBuffer logDes=new StringBuffer();
	    	FiberNode fn =fnHome.findByPrimaryKey(new Integer(dto.getId()));
	    	logDes.append("��ڵ��ţ�" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";����ջ���ţ�", String.valueOf(fn.getFiberReceiverId()),String.valueOf(dto.getFiberReceiverId())));
			logDes.append(SystemLogRecorder.appendDescString(";��������", BusinessUtility.getDistrictNameById(fn.getDistrictId()),BusinessUtility.getDistrictNameById(dto.getDistrictId())));
			logDes.append(SystemLogRecorder.appendDescString(";��ϸ��ַ��", fn.getDetailAddress(),dto.getDetailAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";������Ϣ��", fn.getDescription(),dto.getDescription())); 
	    	if(fn.getFiberNodeCode().equalsIgnoreCase(dto.getFiberNodeCode())){
	    		if (fn.ejbUpdate(dto)==-1){ 
	 			   throw new ServiceException("FiberNodeDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberNodeDTO is set.");
	 		    }	
	    	}
	    	else{
	    		int exist=BusinessUtility.getCountByFiberNodeCode(dto.getFiberNodeCode());
				if(exist>0)
					   throw new ServiceException("�ù�ڵ�����Ѿ�����,���������룡");
				if (fn.ejbUpdate(dto)==-1){ 
		 			   throw new ServiceException("FiberNodeDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberNodeDTO is set.");
		 		    }	
	    	}
	    		
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "���¹�ڵ�", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);	 	 
	    	
	    } 
          catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("��ڵ���ҳ�����ڵ�ID��"+dto.getId());
        }
	}
	public void delFiberNode(FiberNodeDTO dto) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"ɾ����ڵ�");
		try{
			getHome(dto);
			fnHome.remove(new Integer(dto.getId()));
			 
		} 
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "ɾ����ڵ����");                    
            throw new ServiceException("ɾ����ڵ����");
		} 
		 
		
	}
	public void createCatvJobCardConfig(CatvJobCardConfigDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"����ʩ����");
		try{
			getHome(dto);
			 
			catvJobCardHome.create(dto);
			 
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "����ʩ��������");                    
            throw new ServiceException("����ʩ��������");
		} 

	}  
	public void updateCatvJobCardConfig(CatvJobCardConfigDTO dto)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"����ʩ����");
	    try{
	    	getHome(dto);
	    	CatvJobCardConfigPK pk=new CatvJobCardConfigPK(dto.getCsiType(),dto.getJobCardType(),dto.getJobCardSubType());
	    	CatvJobCardConfig catvConfig =catvJobCardHome.findByPrimaryKey(pk);
	    	catvConfig.setJobcardStatus(dto.getJobcardStatus()); 
	    	catvConfig.setStatus(dto.getStatus());
	    	catvConfig.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
	     } 
          catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("ʩ�������ҳ���");
        }
	}
	 
	
	private void getHome(Object obj) throws  ServiceException{
		   
		    try{
		    	 if(obj instanceof MachineRoomDTO)
			       mrHome =HomeLocater.getMachineRoomHome();
		    	 else if (obj instanceof FiberTransmitterDTO)
		    		 ftHome= HomeLocater.getFiberTransmitterHome();
		    	 else if (obj instanceof FiberNodeDTO)
		    		 fnHome= HomeLocater.getFiberNodeHome();
		    	 else if (obj instanceof FiberReceiverDTO)
		    		 frHome= HomeLocater.getFiberReceiverHome();
		    	 else if (obj instanceof CatvJobCardConfigDTO)
		    		 catvJobCardHome= HomeLocater.getCatvJobCardConfigHome();  
		    	 else 
		    		 throw new ServiceException("�����dto���Ͳ��ԣ�"); 
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "������Դ�ӿڶ�λ����");                    
            e1.printStackTrace();
		}
		
	}
	
	
}
