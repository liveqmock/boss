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
		LogUtility.log(clazz,LogLevel.DEBUG,"创建机房");
		try{
			getHome(dto);
			int exist=BusinessUtility.getCountByCode(dto.getMachineRoomCode());
			if(exist>0)
				   throw new ServiceException("该机房编码已经存在,请重新输入！");
			MachineRoom mr=mrHome.create(dto);
			Integer id=mr.getId();
			context.put("ID",id);
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "创建机房出错！");                    
            throw new ServiceException("创建机房出错！");
		} 

	} 
	
	public void delMachineRoom(MachineRoomDTO dto) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"删除机房");
		try{
			getHome(dto);
			mrHome.remove(new Integer(dto.getId()));
			 
		} 
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "删除机房出错！");                    
            throw new ServiceException("删除机房出错！");
		} 
		 
		
	}
	
	public void updateMachineRoom(MachineRoomDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"更新机房");
	    try{
	    	getHome(dto);
	    	MachineRoom mr =mrHome.findByPrimaryKey(new Integer(dto.getId()));
	    	StringBuffer logDes=new StringBuffer();
	    	logDes.append("机房编号：" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";机房名称：", String.valueOf(mr.getMachineRoomName()),String.valueOf(dto.getMachineRoomName())));
			logDes.append(SystemLogRecorder.appendDescString(";所属区域：", BusinessUtility.getDistrictNameById(mr.getDistrictId()),BusinessUtility.getDistrictNameById(dto.getDistrictId())));
			logDes.append(SystemLogRecorder.appendDescString(";详细地址：", mr.getDetailAddress(),dto.getDetailAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";描述信息：", mr.getDescription(),dto.getDescription())); 
			 
	    	if(mr.getMachineRoomCode().equalsIgnoreCase(dto.getMachineRoomCode())){
	    		if (mr.ejbUpdate(dto)==-1){ 
	 			   throw new RuntimeException("MachineRoomDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of MachineRoomDTO is set.");
	 		    }	
	    	}
	    	else{
	    		int exist=BusinessUtility.getCountByCode(dto.getMachineRoomCode());
				if(exist>0)
					   throw new ServiceException("该机房编码已经存在,请重新输入！");
				if (mr.ejbUpdate(dto)==-1){ 
		 			   throw new ServiceException("MachineRoomDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of MachineRoomDTO is set.");
		 		    }	
	    	}
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "更新机房", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	    	 
	    		 
	    	
	    } 
          catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("机房查找出错，机房ID："+dto.getId());
        }
	}
	
	public void createFiberTransmitter(FiberTransmitterDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"创建光发射机");
		try{
			getHome(dto);
			int exist=BusinessUtility.getCountByFiberCode(dto.getFiberTransmitterCode());
			if(exist>0)
				   throw new ServiceException("该光发射机编码已经存在,请重新输入！");
			FiberTransmitter mr=ftHome.create(dto);
			Integer id=mr.getId();
			context.put("FIberTransID",id);
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "创建光发射机出错！");                    
            throw new ServiceException("创建光发射机出错！");
		} 

	}  
	public void updateFiberTransmitter(FiberTransmitterDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"更新光发射机");
	    try{
	    	getHome(dto);
	    	FiberTransmitter ft =ftHome.findByPrimaryKey(new Integer(dto.getId()));
	    	StringBuffer logDes=new StringBuffer();
	    	logDes.append("光发射机编号：" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";机房ID：", String.valueOf(ft.getMachineRoomId()),String.valueOf(dto.getMachineRoomId())));
			logDes.append(SystemLogRecorder.appendDescString(";所属区域：", BusinessUtility.getDistrictNameById(ft.getDistrictId()),BusinessUtility.getDistrictNameById(dto.getDistrictId())));
			logDes.append(SystemLogRecorder.appendDescString(";详细地址：", ft.getDetailAddress(),dto.getDetailAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";描述信息：", ft.getDescription(),dto.getDescription())); 
			 
	    	if(ft.getFiberTransmitterCode().equalsIgnoreCase(dto.getFiberTransmitterCode())){
	    		if (ft.ejbUpdate(dto)==-1){ 
	 			   throw new ServiceException("FiberTransmitterDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberTransmitterDTO is set.");
	 		    }	
	    	}
	    	else{
	    		int exist=BusinessUtility.getCountByFiberCode(dto.getFiberTransmitterCode());
				if(exist>0)
					   throw new ServiceException("该光发射机编码已经存在,请重新输入！");
				if (ft.ejbUpdate(dto)==-1){ 
		 			   throw new ServiceException("FiberTransmitterDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberTransmitterDTO is set.");
		 		    }	
	    	}
	    		
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "更新光发射机", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);	 
	    	
	    } 
          catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("光发射机查找出错，光发射机ID："+dto.getId());
        }
	}
	public void delFiberTransmitter(FiberTransmitterDTO dto) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"删除光发射机");
		try{
			getHome(dto);
			ftHome.remove(new Integer(dto.getId()));
			 
		} 
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "删除光发射机出错！");                    
            throw new ServiceException("删除光发射机出错！");
		} 
		 
		
	}
	public void createFiberReceiver(FiberReceiverDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"创建光收机");
		try{
			getHome(dto);
			int exist=BusinessUtility.getCountByFiberRecieverCode(dto.getFiberReceiverCode());
			if(exist>0)
				   throw new ServiceException("该光收机编码已经存在,请重新输入！");
			FiberReceiver fr=frHome.create(dto);
			Integer id=fr.getId();
			context.put("FiberReceiverID",id);
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "创建光收机出错！");                    
            throw new ServiceException("创建光收机出错！");
		} 

	}  
	public void updateFiberReceiver(FiberReceiverDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"更新光接收机");
	    try{
	    	getHome(dto);
	    	StringBuffer logDes=new StringBuffer();
	    	FiberReceiver fr =frHome.findByPrimaryKey(new Integer(dto.getId()));
	    	logDes.append("光接收机编号：" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";光发机编号：", String.valueOf(fr.getFiberTransmitterId()),String.valueOf(dto.getFiberTransmitterId())));
			logDes.append(SystemLogRecorder.appendDescString(";所属区域：", BusinessUtility.getDistrictNameById(fr.getDistrictId()),BusinessUtility.getDistrictNameById(dto.getDistrictId())));
			logDes.append(SystemLogRecorder.appendDescString(";详细地址：", fr.getDetailAddress(),dto.getDetailAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";描述信息：", fr.getDescription(),dto.getDescription())); 
	    	if(fr.getFiberReceiverCode().equalsIgnoreCase(dto.getFiberReceiverCode())){
	    		if (fr.ejbUpdate(dto)==-1){ 
	 			   throw new ServiceException("FiberReceiverDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberReceiverDTO is set.");
	 		    }	
	    	}
	    	else{
	    		int exist=BusinessUtility.getCountByFiberRecieverCode(dto.getFiberReceiverCode());
				if(exist>0)
					   throw new ServiceException("该光收机编码已经存在,请重新输入！");
				if (fr.ejbUpdate(dto)==-1){ 
		 			   throw new ServiceException("FiberReceiverDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberTransmitterDTO is set.");
		 		    }	
	    	}
	    		
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "更新光接收机", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);	 	 
	    	
	    } 
          catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("光收机查找出错，光收机ID："+dto.getId());
        }
	}
	public void delFiberReceiver(FiberReceiverDTO dto) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"删除光收机");
		try{
			getHome(dto);
			frHome.remove(new Integer(dto.getId()));
			 
		} 
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "删除光收机出错！");                    
            throw new ServiceException("删除光收机出错！");
		} 
		 
		
	}
	public void createFiberNode(FiberNodeDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"创建光节点");
		try{
			getHome(dto);
			int exist=BusinessUtility.getCountByFiberNodeCode(dto.getFiberNodeCode());
			if(exist>0)
				   throw new ServiceException("该光节点编码已经存在,请重新输入！");
			FiberNode fn=fnHome.create(dto);
			Integer id=fn.getId();
			context.put("FiberNodeID",id);
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "创建光节点出错！");                    
            throw new ServiceException("创建光节点出错！");
		} 

	}  
	public void updateFiberNode(FiberNodeDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"更新光节点");
	    try{
	    	getHome(dto);
	    	StringBuffer logDes=new StringBuffer();
	    	FiberNode fn =fnHome.findByPrimaryKey(new Integer(dto.getId()));
	    	logDes.append("光节点编号：" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";光接收机编号：", String.valueOf(fn.getFiberReceiverId()),String.valueOf(dto.getFiberReceiverId())));
			logDes.append(SystemLogRecorder.appendDescString(";所属区域：", BusinessUtility.getDistrictNameById(fn.getDistrictId()),BusinessUtility.getDistrictNameById(dto.getDistrictId())));
			logDes.append(SystemLogRecorder.appendDescString(";详细地址：", fn.getDetailAddress(),dto.getDetailAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";描述信息：", fn.getDescription(),dto.getDescription())); 
	    	if(fn.getFiberNodeCode().equalsIgnoreCase(dto.getFiberNodeCode())){
	    		if (fn.ejbUpdate(dto)==-1){ 
	 			   throw new ServiceException("FiberNodeDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberNodeDTO is set.");
	 		    }	
	    	}
	    	else{
	    		int exist=BusinessUtility.getCountByFiberNodeCode(dto.getFiberNodeCode());
				if(exist>0)
					   throw new ServiceException("该光节点编码已经存在,请重新输入！");
				if (fn.ejbUpdate(dto)==-1){ 
		 			   throw new ServiceException("FiberNodeDTO update fail, ID:"+dto.getId()+". Please make sure that dt_lastmod of FiberNodeDTO is set.");
		 		    }	
	    	}
	    		
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "更新光节点", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);	 	 
	    	
	    } 
          catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("光节点查找出错，光节点ID："+dto.getId());
        }
	}
	public void delFiberNode(FiberNodeDTO dto) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"删除光节点");
		try{
			getHome(dto);
			fnHome.remove(new Integer(dto.getId()));
			 
		} 
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "删除光节点出错！");                    
            throw new ServiceException("删除光节点出错！");
		} 
		 
		
	}
	public void createCatvJobCardConfig(CatvJobCardConfigDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"创建施工单");
		try{
			getHome(dto);
			 
			catvJobCardHome.create(dto);
			 
		}
		 
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "创建施工单出错！");                    
            throw new ServiceException("创建施工单出错！");
		} 

	}  
	public void updateCatvJobCardConfig(CatvJobCardConfigDTO dto)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"更新施工单");
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
            throw new ServiceException("施工单查找出错");
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
		    		 throw new ServiceException("传入的dto类型不对！"); 
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "网络资源接口定位出错！");                    
            e1.printStackTrace();
		}
		
	}
	
	
}
