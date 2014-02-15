/**
 * 
 */
package com.dtv.oss.service.command.voice;

import java.util.Collection;

import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.*;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.component.CustomerProductService;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.component.VoiceService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.*;
import com.dtv.oss.service.ejbevent.voice.VoiceEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.SystemLogRecorder;

/**
 * @author fiona he
 *
 */
public class VoiceCommand extends Command {
	private static final Class clazz = VoiceCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    VoiceEJBEvent inEvent = (VoiceEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.FRIEND_PHONENO_CREATE:
	    			addFriendPhoneNo(inEvent);
	    			break;
	    		case EJBEvent.FRIEND_PHONENO_DELETE:
	    			deleteFriendPhoneNo(inEvent);
	    			break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("����δ֪�Ĵ���");
		}
		return response;
	}
	/**
	 * �����������
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void addFriendPhoneNo(VoiceEJBEvent inEvent) throws ServiceException{
			//��ʼ��ServiceContext
			ServiceContext serviceContext=initServiceContext(inEvent);
			//���
			BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);		
			businessRuleService.checkVoice(inEvent);
			int customerID=inEvent.getVoiceFriendPhoneNoDTO().getCustomerID();
			int serviceAccountID=inEvent.getVoiceFriendPhoneNoDTO().getServiceAccountID();
			//�����µ��������
			VoiceService voiceService = new VoiceService(serviceContext);		
			voiceService.createFriendPhoneNo(inEvent.getVoiceFriendPhoneNoDTO());
			//����ϵͳ��־
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "�����������", "������룺"+inEvent.getVoiceFriendPhoneNoDTO().getPhoneNo()+"��ҵ���ʻ�:"+serviceAccountID+"���ͻ�ID:"+customerID, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);		
	}
	/**
	 * ɾ���������
	 * @param inEvent
	 * @throws ServiceException 
	 */
	public void deleteFriendPhoneNo(VoiceEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		int serAccID=inEvent.getVoiceFriendPhoneNoDTO().getServiceAccountID();
		int customerID=inEvent.getVoiceFriendPhoneNoDTO().getCustomerID();
		//ɾ���������
		VoiceService voiceService = new VoiceService(serviceContext);	
		String phoneNO=voiceService.deleteFriendPhoneNo(inEvent.getSeqNo());
		//����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "ɾ���������", "������룺"+phoneNO+"��ҵ���ʻ�:"+serAccID+"���ͻ�ID:"+customerID, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);	
	}
	/**
	 * ��ʼ��ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(VoiceEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
}
