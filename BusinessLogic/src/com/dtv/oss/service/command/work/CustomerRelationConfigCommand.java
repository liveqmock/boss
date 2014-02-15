/*
 * Created on 2005-11-5
 * 
 * @author whq
 * 
 * 主要处理和客户关系有关的一些配置，包括：
 * 创建消息,设置回访信息,设置诊断参数
 */
package com.dtv.oss.service.command.work;

import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.*;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;

import com.dtv.oss.service.ejbevent.work.CustomerRelationConfigEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.dto.*;
import com.dtv.oss.service.component.CustomerRelationConfigService;
import java.util.*;

public class CustomerRelationConfigCommand extends Command {
    private static final Class clazz = CustomerRelationConfigCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		CustomerRelationConfigEJBEvent inEvent = (CustomerRelationConfigEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
		
		try {
			switch (inEvent.getActionType()) {
				case EJBEvent.CREATE_MESSAGE : //创建消息
					createMessage(inEvent.getSendMessageDtos());
					break;
			    case EJBEvent.CREATE_CALLBACKINFOSETTING:
			        createCallBackInfoSetting(inEvent.getCbisDto());
			        break;
			    case EJBEvent.UPDATE_CALLBACKINFOSETTING:
			        updateCallBackInfoSetting(inEvent.getCbisDto());
			    	break;
			    case EJBEvent.DELETE_CALLBACKINFOSETTING:
			        deleteCallBackInfoSetting(inEvent.getIDs());
			    	break;
			    case EJBEvent.CREATE__CALLBACKINFOVALUE:
			        createCallBackInfoValue(inEvent.getCbivDto());
			    	break;
			    case EJBEvent.UPDATE__CALLBACKINFOVALUE:
			        updateCallBackInfoValue(inEvent.getCbivDto());
			    	break;
			    case EJBEvent.DELETE__CALLBACKINFOVALUE:
			        deleteCallBackInfoValue(inEvent.getIDs());
			    	break;
			    case EJBEvent.CREATE_DIANOSISPARAMETER:
			        createDiagnosisParameter(inEvent.getDpDto());
			    	break;
			    case EJBEvent.UPDATE_DIANOSISPARAMETER:
			        updateDiagnosisParameter(inEvent.getDpDto());
			    	break;
			    case EJBEvent.DELETE_DIANOSISPARAMETER:
			        deleteDiagnosisParameter(inEvent.getIDs());
			    	break;
				default :
					throw new IllegalArgumentException("CustomerRelationConfigEJBEvent中actionType的设置不正确。");
			}
		} catch (CommandException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, ce);
			throw ce;
		} catch (Exception e) {
		    LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new CommandException("BOSS系统异常。");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "Leave execute() now.");
		return response;
	}
	
	//新增诊断参数
	private void createDiagnosisParameter(DiagnosisInfoDTO dpDto) throws CommandException {
	    if (dpDto == null)
	        throw new IllegalArgumentException("dpDto为null.");
	    try {
            int id = CustomerRelationConfigService.createDiagnosisParameter(dpDto);
            String description = "新增诊断参数，ID："+id;
			SystemLogRecorder.keyLog(description, "新增诊断参数", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
			response.setPayload(new Integer(id));
		} catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//更新诊断参数
	private void updateDiagnosisParameter(DiagnosisInfoDTO dpDto) throws CommandException {
	    if ((dpDto == null) || (dpDto.getId() == 0))
	        throw new IllegalArgumentException("dpDto为null或者ID为0.");
	    try {
            CustomerRelationConfigService.updateDiagnosisParameter(dpDto);
			//插入相关的日志记录。
			String description = "更新诊断参数，ID："+dpDto.getId();
			SystemLogRecorder.keyLog(description, "更新诊断参数", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//删除诊断参数
	private void deleteDiagnosisParameter(Collection dpIDs) throws CommandException {
	    if ((dpIDs == null) || dpIDs.isEmpty())
	        throw new IllegalArgumentException("dpIDs为null或者为空.");
	    try {
	        Iterator it = dpIDs.iterator();
	        while (it.hasNext()) {
	            int dpId = ((Integer)it.next()).intValue();
	            CustomerRelationConfigService.deleteDiagnosisParameter(dpId);
				//插入相关的日志记录。
				String description = "删除诊断参数，ID："+dpId;
				SystemLogRecorder.keyLog(description, "删除诊断参数", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
	        }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//新增CallBackInfoValue
	private void createCallBackInfoValue(BidimConfigSettingValueDTO cbivDto) throws CommandException {
	    if (cbivDto == null)
	        throw new IllegalArgumentException("cbivDto为null.");
	    try {
            int id = CustomerRelationConfigService.createCallBackInfoValue(cbivDto);
            String description = "新增CallBackInfoValue，ID："+id;
			SystemLogRecorder.keyLog(description, "新增CallBackInfoValue", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
			response.setPayload(new Integer(id));
		} catch (ServiceException e) {
            throw new CommandException(e);
        }
	}

	//更新CallBackInfoValue
	private void updateCallBackInfoValue(BidimConfigSettingValueDTO cbivDto) throws CommandException {
	    if ((cbivDto == null) || (cbivDto.getId() == 0))
	        throw new IllegalArgumentException("cbivDto为null或者ID为0.");
	    try {
            CustomerRelationConfigService.updateCallBackInfoValue(cbivDto);
			//插入相关的日志记录。
			String description = "更新CallBackInfoValue，ID："+cbivDto.getId();
			SystemLogRecorder.keyLog(description, "更新CallBackInfoValue", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//删除CallBackInfoValue
	private void deleteCallBackInfoValue(Collection cbivIDs) throws CommandException {
	    if ((cbivIDs == null) || cbivIDs.isEmpty())
	        throw new IllegalArgumentException("cbivIDs为null或者为空.");
	    try {
	        Iterator it = cbivIDs.iterator();
	        while (it.hasNext()) {
	            int cbivId = ((Integer)it.next()).intValue();
	            CustomerRelationConfigService.deleteCallBackInfoValue(cbivId);
				//插入相关的日志记录。
				String description = "删除CallBackInfoValue，ID："+cbivId;
				SystemLogRecorder.keyLog(description, "删除CallBackInfoValue", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
	        }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//新增回访信息设置
	private void createCallBackInfoSetting(BidimConfigSettingDTO cbiDto) throws CommandException {
	    if (cbiDto == null)
	        throw new IllegalArgumentException("cbiDto为null.");
	    try {
            int id = CustomerRelationConfigService.createCallBackInfoSetting(cbiDto);
            String description = "新增回访信息设置，ID："+id;
			SystemLogRecorder.keyLog(description, "新增回访信息设置", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
			response.setPayload(new Integer(id));
		} catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//更新回访信息设置
	private void updateCallBackInfoSetting(BidimConfigSettingDTO cbiDto) throws CommandException {
	    if ((cbiDto == null) || (cbiDto.getId() == 0))
	        throw new IllegalArgumentException("cbiDto为null或者ID为0.");
	    try {
            CustomerRelationConfigService.updateCallBackInfoSetting(cbiDto);
			//插入相关的日志记录。
			String description = "更新回访信息设置，ID："+cbiDto.getId();
			SystemLogRecorder.keyLog(description, "更新回访信息设置", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}	
	
	//删除回访信息设置
	private void deleteCallBackInfoSetting(Collection cbisIDs) throws CommandException {
	    if ((cbisIDs == null) || cbisIDs.isEmpty())
	        throw new IllegalArgumentException("cbisIDs为null或者为空.");
	    try {
	        Iterator it = cbisIDs.iterator();
	        while (it.hasNext()) {
	            int cbisId = ((Integer)it.next()).intValue();
	            CustomerRelationConfigService.deleteCallBackInfoSetting(cbisId);
				//插入相关的日志记录。
				String description = "删除回访信息设置，ID："+cbisId;
				SystemLogRecorder.keyLog(description, "删除回访信息设置", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
	        }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//	创建消息
	private void createMessage(Collection sendMessageDtos) throws CommandException {
	    if ((sendMessageDtos == null) || sendMessageDtos.isEmpty())
	        throw new IllegalArgumentException("sendMessageDtos为null或者为空.");
	    Iterator it = sendMessageDtos.iterator();
	    
	    while (it.hasNext()) {
	        SendMessageDTO smDto = (SendMessageDTO)it.next();
	        createMessageForEach(smDto);
	    }	    
	    
	}
	
	//创建消息，单个处理
	private void createMessageForEach(SendMessageDTO smDto) throws CommandException {
		if ((smDto == null) || (smDto.getCustomerId() == 0))
		    throw new IllegalArgumentException("smDto is not set properly");
		
		try {
		    int messageID = CustomerRelationConfigService.createMessageForEachCustomer(smDto, operatorID);
			
			//插入相关的日志记录。
			String description = "创建消息，消息ID："+messageID;
			SystemLogRecorder.keyLog(description, "创建消息", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, smDto.getCustomerId());
			
        } catch (ServiceException e) {
            throw new CommandException(e);
        }		

	}
	
	/**
	 * 初始化ServiceContext
	 * 将一些通用的信息放入ServiceContext
	 */
	private ServiceContext initServiceContext() {
	    ServiceContext serviceContext = new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    
	    return serviceContext;
	}
	
	public static void main(String[] args) {

	}
}



