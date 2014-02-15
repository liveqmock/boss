package com.dtv.oss.service.command.config;

import com.dtv.oss.dto.FiberNodeDTO;
import com.dtv.oss.dto.FiberReceiverDTO;
import com.dtv.oss.dto.FiberTransmitterDTO;
import com.dtv.oss.dto.MachineRoomDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.ConfigResourceService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ResourceEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigResourceCommand extends Command {

	 private static final Class clazz = ConfigResourceCommand.class;
		private int operatorID = 0;
		private String machineName = "";	
		CommandResponseImp response = null;
		private ServiceContext context;

		public CommandResponse execute(EJBEvent ev) throws CommandException {
			response = new CommandResponseImp(null);
			ResourceEJBEvent inEvent = (ResourceEJBEvent) ev;
			operatorID = inEvent.getOperatorID();
			machineName = ev.getRemoteHostAddress();
			LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
			 
			try {
				switch (inEvent.getActionType()) {
				  
					case EJBEvent.CONFIG_MACHINEROOM_CREATE : //机房创建
						 createMachineRoom(inEvent);
						break;
					case EJBEvent.CONFIG_MACHINEROOM_REMOVE : //机房删除
						delMachineRoom(inEvent);
						break;
					case EJBEvent.CONFIG_MACHINEROOM_UPDATE : //机房修改
						updateMachineRoom(inEvent);
						break;
					case EJBEvent.CONFIG_FIBERTRANSMITTER_CREATE : //光发机创建
						createFiberTransmitter(inEvent);
						break;
					case EJBEvent.CONFIG_FIBERTRANSMITTER_REMOVE : //光发机删除
						delFiberTransmitter(inEvent);
						break;
					case EJBEvent.CONFIG_FIBERTRANSMITTER_UPDATE : //光发机修改
						updateFiberTransmitter(inEvent);
						break;
					case EJBEvent.CONFIG_FIBERNODE_CREATE : //光节点创建
						createFiberNode(inEvent);
						break;
					case EJBEvent.CONFIG_FIBERNODE_REMOVE : //光节点删除
						delFiberNode(inEvent);
						break;
					case EJBEvent.CONFIG_FIBERNODE_UPDATE : //光节点修改
						updateFiberNode(inEvent);
						break; 
					case EJBEvent.CONFIG_FIBERRECEIVER_CREATE : //光收机创建
						createFiberReceiver(inEvent);
						break;
					case EJBEvent.CONFIG_FIBERRECEIVER_REMOVE : //光收机删除
						delFiberReceiver(inEvent);
						break;
					case EJBEvent.CONFIG_FIBERRECEIVER_UPDATE : //光收机修改
						updateFiberReceiver(inEvent);
						break; 
                    default :
                    	break;
				}
			}catch(ServiceException ce){
		        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
		        throw new CommandException(ce.getMessage());
		    }catch(Throwable unkown) {
			    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
			    throw new CommandException("未知错误。");
			}
			return response;
		}
		 
		
		private void createMachineRoom(ResourceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			MachineRoomDTO dto =inEvent.getMrDto();
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			resourceService.createMachineRoom(dto);
			Integer id =(Integer)context.get("ID");
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CATV, "创建机房", "机房ID为:"+id.intValue()+",机房名称为"
						+dto.getMachineRoomName(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		private void delMachineRoom(ResourceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			MachineRoomDTO dto =inEvent.getMrDto();
			resourceService.delMachineRoom(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CATV, "删除机房", "机房ID为:"+dto.getId()+",机房名称为"
					+dto.getMachineRoomName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		private void updateMachineRoom(ResourceEJBEvent inEvent) throws ServiceException {
			//this.context =new ServiceContext();
			this.context =initServiceContext(inEvent); 
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			MachineRoomDTO dto =inEvent.getMrDto();
			resourceService.updateMachineRoom(dto);
			
		}
		
		 
		private void createFiberTransmitter(ResourceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			FiberTransmitterDTO dto =inEvent.getFtDto();
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			resourceService.createFiberTransmitter(dto);
			Integer id =(Integer)context.get("FIberTransID");
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CATV, "创建光发射机", "光发射机ID为:"+id.intValue()+",光发射机编码为"
						+dto.getFiberTransmitterCode(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		private void delFiberTransmitter(ResourceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			FiberTransmitterDTO dto =inEvent.getFtDto();
			resourceService.delFiberTransmitter(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CATV, "删除光发射机", "光发射机ID为:"+dto.getId()+",光发射机编码为"
					+dto.getFiberTransmitterCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		private void updateFiberTransmitter(ResourceEJBEvent inEvent) throws ServiceException {
			this.context =initServiceContext(inEvent);
			//ServiceContext serviceContext=initServiceContext(inEvent); 
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			FiberTransmitterDTO dto =inEvent.getFtDto();
			resourceService.updateFiberTransmitter(dto);
			 
		}
		
		private void createFiberReceiver(ResourceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			FiberReceiverDTO dto =inEvent.getFrDto();
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			resourceService.createFiberReceiver(dto);
			Integer id =(Integer)context.get("FiberReceiverID");
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CATV, "创建光收机", "光收机ID为:"+id.intValue()+",光收机编码为"
						+dto.getFiberReceiverCode(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		private void delFiberReceiver(ResourceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			FiberReceiverDTO dto =inEvent.getFrDto();
			resourceService.delFiberReceiver(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CATV, "删除光收机", "光收机ID为:"+dto.getId()+",光收机编码为"
					+dto.getFiberReceiverCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		private void updateFiberReceiver(ResourceEJBEvent inEvent) throws ServiceException {
		//	this.context =new ServiceContext();
			this.context =initServiceContext(inEvent); 
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			FiberReceiverDTO dto =inEvent.getFrDto();
			resourceService.updateFiberReceiver(dto);
			 
		}
		
		
		 
		private void createFiberNode(ResourceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			FiberNodeDTO dto =inEvent.getFnDto();
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			resourceService.createFiberNode(dto);
			Integer id =(Integer)context.get("FiberNodeID");
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CATV, "创建光节点", "光节点ID为:"+id.intValue()+",光节点编码为"
						+dto.getFiberNodeCode(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		private void delFiberNode(ResourceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			FiberNodeDTO dto =inEvent.getFnDto();
			resourceService.delFiberNode(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CATV, "删除光节点", "光节点ID为:"+dto.getId()+",光节点编码为"
					+dto.getFiberNodeCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		private void updateFiberNode(ResourceEJBEvent inEvent) throws ServiceException {
			 
			this.context =initServiceContext(inEvent);  
			ConfigResourceService resourceService = new ConfigResourceService(this.context);
			FiberNodeDTO dto =inEvent.getFnDto();
			resourceService.updateFiberNode(dto);
			 
		}
		
		  
		private ServiceContext initServiceContext(ResourceEJBEvent inEvent) {
			ServiceContext serviceContext = new ServiceContext();
			serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
			serviceContext.put(Service.REMOTE_HOST_ADDRESS,inEvent.getRemoteHostAddress());
			return serviceContext;
	}	 
		 
		
	 
		
		 
			
}
