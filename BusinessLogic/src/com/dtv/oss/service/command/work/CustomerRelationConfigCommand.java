/*
 * Created on 2005-11-5
 * 
 * @author whq
 * 
 * ��Ҫ����Ϳͻ���ϵ�йص�һЩ���ã�������
 * ������Ϣ,���ûط���Ϣ,������ϲ���
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
				case EJBEvent.CREATE_MESSAGE : //������Ϣ
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
					throw new IllegalArgumentException("CustomerRelationConfigEJBEvent��actionType�����ò���ȷ��");
			}
		} catch (CommandException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, ce);
			throw ce;
		} catch (Exception e) {
		    LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new CommandException("BOSSϵͳ�쳣��");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "Leave execute() now.");
		return response;
	}
	
	//������ϲ���
	private void createDiagnosisParameter(DiagnosisInfoDTO dpDto) throws CommandException {
	    if (dpDto == null)
	        throw new IllegalArgumentException("dpDtoΪnull.");
	    try {
            int id = CustomerRelationConfigService.createDiagnosisParameter(dpDto);
            String description = "������ϲ�����ID��"+id;
			SystemLogRecorder.keyLog(description, "������ϲ���", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
			response.setPayload(new Integer(id));
		} catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//������ϲ���
	private void updateDiagnosisParameter(DiagnosisInfoDTO dpDto) throws CommandException {
	    if ((dpDto == null) || (dpDto.getId() == 0))
	        throw new IllegalArgumentException("dpDtoΪnull����IDΪ0.");
	    try {
            CustomerRelationConfigService.updateDiagnosisParameter(dpDto);
			//������ص���־��¼��
			String description = "������ϲ�����ID��"+dpDto.getId();
			SystemLogRecorder.keyLog(description, "������ϲ���", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//ɾ����ϲ���
	private void deleteDiagnosisParameter(Collection dpIDs) throws CommandException {
	    if ((dpIDs == null) || dpIDs.isEmpty())
	        throw new IllegalArgumentException("dpIDsΪnull����Ϊ��.");
	    try {
	        Iterator it = dpIDs.iterator();
	        while (it.hasNext()) {
	            int dpId = ((Integer)it.next()).intValue();
	            CustomerRelationConfigService.deleteDiagnosisParameter(dpId);
				//������ص���־��¼��
				String description = "ɾ����ϲ�����ID��"+dpId;
				SystemLogRecorder.keyLog(description, "ɾ����ϲ���", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
	        }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//����CallBackInfoValue
	private void createCallBackInfoValue(BidimConfigSettingValueDTO cbivDto) throws CommandException {
	    if (cbivDto == null)
	        throw new IllegalArgumentException("cbivDtoΪnull.");
	    try {
            int id = CustomerRelationConfigService.createCallBackInfoValue(cbivDto);
            String description = "����CallBackInfoValue��ID��"+id;
			SystemLogRecorder.keyLog(description, "����CallBackInfoValue", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
			response.setPayload(new Integer(id));
		} catch (ServiceException e) {
            throw new CommandException(e);
        }
	}

	//����CallBackInfoValue
	private void updateCallBackInfoValue(BidimConfigSettingValueDTO cbivDto) throws CommandException {
	    if ((cbivDto == null) || (cbivDto.getId() == 0))
	        throw new IllegalArgumentException("cbivDtoΪnull����IDΪ0.");
	    try {
            CustomerRelationConfigService.updateCallBackInfoValue(cbivDto);
			//������ص���־��¼��
			String description = "����CallBackInfoValue��ID��"+cbivDto.getId();
			SystemLogRecorder.keyLog(description, "����CallBackInfoValue", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//ɾ��CallBackInfoValue
	private void deleteCallBackInfoValue(Collection cbivIDs) throws CommandException {
	    if ((cbivIDs == null) || cbivIDs.isEmpty())
	        throw new IllegalArgumentException("cbivIDsΪnull����Ϊ��.");
	    try {
	        Iterator it = cbivIDs.iterator();
	        while (it.hasNext()) {
	            int cbivId = ((Integer)it.next()).intValue();
	            CustomerRelationConfigService.deleteCallBackInfoValue(cbivId);
				//������ص���־��¼��
				String description = "ɾ��CallBackInfoValue��ID��"+cbivId;
				SystemLogRecorder.keyLog(description, "ɾ��CallBackInfoValue", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
	        }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//�����ط���Ϣ����
	private void createCallBackInfoSetting(BidimConfigSettingDTO cbiDto) throws CommandException {
	    if (cbiDto == null)
	        throw new IllegalArgumentException("cbiDtoΪnull.");
	    try {
            int id = CustomerRelationConfigService.createCallBackInfoSetting(cbiDto);
            String description = "�����ط���Ϣ���ã�ID��"+id;
			SystemLogRecorder.keyLog(description, "�����ط���Ϣ����", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
			response.setPayload(new Integer(id));
		} catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//���»ط���Ϣ����
	private void updateCallBackInfoSetting(BidimConfigSettingDTO cbiDto) throws CommandException {
	    if ((cbiDto == null) || (cbiDto.getId() == 0))
	        throw new IllegalArgumentException("cbiDtoΪnull����IDΪ0.");
	    try {
            CustomerRelationConfigService.updateCallBackInfoSetting(cbiDto);
			//������ص���־��¼��
			String description = "���»ط���Ϣ���ã�ID��"+cbiDto.getId();
			SystemLogRecorder.keyLog(description, "���»ط���Ϣ����", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}	
	
	//ɾ���ط���Ϣ����
	private void deleteCallBackInfoSetting(Collection cbisIDs) throws CommandException {
	    if ((cbisIDs == null) || cbisIDs.isEmpty())
	        throw new IllegalArgumentException("cbisIDsΪnull����Ϊ��.");
	    try {
	        Iterator it = cbisIDs.iterator();
	        while (it.hasNext()) {
	            int cbisId = ((Integer)it.next()).intValue();
	            CustomerRelationConfigService.deleteCallBackInfoSetting(cbisId);
				//������ص���־��¼��
				String description = "ɾ���ط���Ϣ���ã�ID��"+cbisId;
				SystemLogRecorder.keyLog(description, "ɾ���ط���Ϣ����", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);	            
	        }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
	}
	
	//	������Ϣ
	private void createMessage(Collection sendMessageDtos) throws CommandException {
	    if ((sendMessageDtos == null) || sendMessageDtos.isEmpty())
	        throw new IllegalArgumentException("sendMessageDtosΪnull����Ϊ��.");
	    Iterator it = sendMessageDtos.iterator();
	    
	    while (it.hasNext()) {
	        SendMessageDTO smDto = (SendMessageDTO)it.next();
	        createMessageForEach(smDto);
	    }	    
	    
	}
	
	//������Ϣ����������
	private void createMessageForEach(SendMessageDTO smDto) throws CommandException {
		if ((smDto == null) || (smDto.getCustomerId() == 0))
		    throw new IllegalArgumentException("smDto is not set properly");
		
		try {
		    int messageID = CustomerRelationConfigService.createMessageForEachCustomer(smDto, operatorID);
			
			//������ص���־��¼��
			String description = "������Ϣ����ϢID��"+messageID;
			SystemLogRecorder.keyLog(description, "������Ϣ", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, smDto.getCustomerId());
			
        } catch (ServiceException e) {
            throw new CommandException(e);
        }		

	}
	
	/**
	 * ��ʼ��ServiceContext
	 * ��һЩͨ�õ���Ϣ����ServiceContext
	 */
	private ServiceContext initServiceContext() {
	    ServiceContext serviceContext = new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    
	    return serviceContext;
	}
	
	public static void main(String[] args) {

	}
}



