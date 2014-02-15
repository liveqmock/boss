/**
 * 
 */
package com.dtv.oss.service.command.csr;

import java.util.Collection;
import java.util.Iterator;

import com.dtv.oss.domain.AccountAdjustment;
import com.dtv.oss.dto.AccountAdjustmentDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
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
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.*;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.SystemLogRecorder;

/**
 * @author 240910y
 *
 */
public class AdjustCommand extends Command {
	private static final Class clazz = AdjustCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    AdjustEJBEvent inEvent = (AdjustEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.ADJUSTMENT:
	    			adjust(inEvent);
	    			break;
	    		case EJBEvent.INVOICE_ADJUSTMENT:
	    			invoiceAdjust(inEvent);
	    			break;
	    		case EJBEvent.REPAIR_ADJUST_OP:
	    			repairAdjust(inEvent);
	    			break;
	    		case EJBEvent.CSI_ADJUSTMENT:
	    			csiAdjust(inEvent);
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
	 * �ʵ�����
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void invoiceAdjust(AdjustEJBEvent inEvent) throws ServiceException{
		//��ʼ��ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		String logDescript ="";
		//ȡ�ÿͻ�id
		int customerID =inEvent.getCustomerID();
		//��������Ƿ��ܶ��˻����е��˲���
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		
		Collection feeAdjustlist =inEvent.getFeeAdjustmentWrapDtoList();
		Collection payAdjustlist =inEvent.getPaymentAdjustWrapDtoList();
			
		//�������ҳ������ĵ��˷��ú͵���֧��
		if(CommonConstDefinition.SETOFFFLAG_W.equals(inEvent.getAdjustMethod())){
			businessRuleService.checkInvoiceAjustment(inEvent.getID(),inEvent.getAccountID(),CommonConstDefinition.SETOFFFLAG_W,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
		    logDescript ="δ�����˵�";
		}else if(CommonConstDefinition.SETOFFFLAG_D.equals(inEvent.getAdjustMethod())){
			businessRuleService.checkInvoiceAjustment(inEvent.getID(),inEvent.getAccountID(),CommonConstDefinition.SETOFFFLAG_D,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
			logDescript ="�������˵�";
		}
		
		int operatorID =PublicService.getCurrentOperatorID(serviceContext);
		
		//��ʼ����
	 	Collection accountAdjustmentCol = FeeService.adjustFeeAndPayment(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_I,
	 			                          inEvent.getID(),inEvent.getCustomerID(),inEvent.getAccountID(),
	 			                          feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),
	 			                          operatorID); 	
	 	
		FeeService.changInvoiceAccordAdjust(inEvent.getActionType(),inEvent.getID(),inEvent.getAdjustMethod(),feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),inEvent.getCustomerID(),inEvent.getAccountID(),operatorID);	

		//����ϵͳ��־

		logDescript="�ʵ�����--"+logDescript+",�ʵ���:"+inEvent.getID()+",�˻�ID:"+inEvent.getAccountID();
		createSystemLogWithAdjust(accountAdjustmentCol,
				PublicService.getRemoteHostAddress(serviceContext),
				PublicService.getCurrentOperatorID(serviceContext), customerID,"�ʵ�����",logDescript);		
		
//		Iterator acctAdjustIter =accountAdjustmentCol.iterator();
//		int index=0;
//		int recordCount=accountAdjustmentCol.size();
//		while (acctAdjustIter.hasNext()){
//			index++;
//			AccountAdjustment acctAdjust =(AccountAdjustment)acctAdjustIter.next();
//	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
//	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
//					SystemLogRecorder.LOGMODULE_SYSTEM, "�ʵ�����",logDescript
//					+",���ε��ʹ�"+recordCount+"�ʼ�¼,��ǰΪ��"+index+"��;"
//					+getAdjustmentLogDesc(acctAdjust.getSeqNo().intValue()), 
//					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);		
//		}
		

	}
	/**
	 * ���޵�����
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void repairAdjust(AdjustEJBEvent inEvent) throws ServiceException{
		//��ʼ��ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		String logDescript ="";
		//ȡ�ÿͻ�id
		int customerID =inEvent.getCustomerID();
		//��������Ƿ��ܶ��˻����е��˲���
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		
		Collection feeAdjustlist =inEvent.getFeeAdjustmentWrapDtoList();
		Collection payAdjustlist =inEvent.getPaymentAdjustWrapDtoList();

		//�������ҳ������ĵ��˷��ú͵���֧��
		businessRuleService.checkCPAjustment(inEvent.getID(),inEvent.getAccountID(),inEvent.getAdjustMethod(),
				feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
		int operatorID =PublicService.getCurrentOperatorID(serviceContext);
		//��ʼ����
		Collection accountAdjustmentCol =FeeService.adjustFeeAndPayment(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_C,
	 			                         inEvent.getID(),inEvent.getCustomerID(),
	 			                         inEvent.getAccountID(),feeAdjustlist,
	 			                         payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),
	 			                         operatorID); 	
    	
		Iterator acctAdjustIter =accountAdjustmentCol.iterator();
		
		if(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT.equals(inEvent.getAdjustMethod())){
			logDescript ="δ���ʱ��޵�";
		}else {
			logDescript ="�����ʱ��޵�";
		}
		
		//����ϵͳ��־
		logDescript="���޵�����--"+logDescript+",���޵���ID:"+inEvent.getID()+",�˻�ID:"+inEvent.getAccountID();
		createSystemLogWithAdjust(accountAdjustmentCol,
				PublicService.getRemoteHostAddress(serviceContext),
				PublicService.getCurrentOperatorID(serviceContext), customerID,"���޵�����",logDescript);		
//		int index=0;
//		int recordCount=accountAdjustmentCol.size();
//		while (acctAdjustIter.hasNext()){
//			index++;
//			AccountAdjustment acctAdjust =(AccountAdjustment)acctAdjustIter.next();
//	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
//	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
//					SystemLogRecorder.LOGMODULE_SYSTEM, "���޵�����",logDescript
//					+",���ε��ʹ�"+recordCount+"�ʼ�¼,��ǰΪ��"+index+"��;"
//					+getAdjustmentLogDesc(acctAdjust.getSeqNo().intValue()),
//					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
//		}
	}

	/**
	 * ��������
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void csiAdjust(AdjustEJBEvent inEvent) throws ServiceException{
		//��ʼ��ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//ȡ�ÿͻ�id
		int customerID =inEvent.getCustomerID();
		//��������Ƿ��ܶ��˻����е��˲���
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		
		Collection feeAdjustlist =inEvent.getFeeAdjustmentWrapDtoList();
		Collection payAdjustlist =inEvent.getPaymentAdjustWrapDtoList();

		//�������ҳ������ĵ��˷��ú͵���֧��
		businessRuleService.checkCsiAjustment(inEvent.getID(),inEvent.getAccountID(),
				feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
		int operatorID =PublicService.getCurrentOperatorID(serviceContext);
		//��ʼ����
		Collection accountAdjustmentCol =FeeService.adjustFeeAndPayment(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_S,
	 			                         inEvent.getID(),inEvent.getCustomerID(),
	 			                         inEvent.getAccountID(),feeAdjustlist,
	 			                         payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),
	 			                         operatorID); 	
    	
		//����ϵͳ��־
		String logDescript="��������,������ID:"+inEvent.getID()	+",�˻�ID:"+inEvent.getAccountID();
		createSystemLogWithAdjust(accountAdjustmentCol,
				PublicService.getRemoteHostAddress(serviceContext),
				PublicService.getCurrentOperatorID(serviceContext), customerID,"��������",logDescript);
//		int index=0;
//		int recordCount=accountAdjustmentCol.size();
//	 	Iterator acctAdjustIter =accountAdjustmentCol.iterator();
//		while (acctAdjustIter.hasNext()){
//			index++;
//			AccountAdjustment acctAdjust =(AccountAdjustment)acctAdjustIter.next();
//	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
//	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
//					SystemLogRecorder.LOGMODULE_SYSTEM, "��������", "��������,������ID:"+inEvent.getID()
//					+",�˻�ID:"+inEvent.getAccountID()
//					+",���ε��ʹ�"+recordCount+"�ʼ�¼,��ǰΪ��"+index+"��"
//					+getAdjustmentLogDesc(acctAdjust.getSeqNo().intValue()), 
//					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
//		}
	}
	
	/**
	 * �˻�����
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void adjust(AdjustEJBEvent inEvent) throws ServiceException{
		//��ʼ��ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		String logDescript ="";
		//ȡ�ÿͻ�id
		int customerID =inEvent.getCustomerID();
		//��������Ƿ��ܶ��˻����е��˲���
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkAdjustInfo(inEvent);
		
		Collection feeAdjustlist =inEvent.getFeeAdjustmentWrapDtoList();
		Collection payAdjustlist =inEvent.getPaymentAdjustWrapDtoList();

		//�������ҳ������ĵ��˷��ú͵���֧��
		//δ���˷��õ���
		if(CommonConstDefinition.SETOFFFLAG_W.equals(inEvent.getAdjustMethod())){
			businessRuleService.checkAccountAjustment(inEvent.getAccountID(),CommonConstDefinition.SETOFFFLAG_W,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
			logDescript ="δ�����õ���";
		//�����˷��õ���
		}else if(CommonConstDefinition.SETOFFFLAG_D.equals(inEvent.getAdjustMethod())){
			businessRuleService.checkAccountAjustment(inEvent.getAccountID(),CommonConstDefinition.SETOFFFLAG_D,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
		    logDescript ="�Ѹ����õ���";
		//�ʻ�������
		}else{
			businessRuleService.checkAccountAjustment(inEvent.getAccountID(),null,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
			logDescript ="�ʻ�������";
		}
		
		int operatorID =PublicService.getCurrentOperatorID(serviceContext);
		//��ʼ����
		Collection accountAdjustmentCol = FeeService.adjustFeeAndPayment(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_M,
				                          inEvent.getID(),inEvent.getCustomerID(),inEvent.getAccountID(),
				                          feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),
				                          operatorID);
		
		logDescript="�ʻ�����--"+logDescript+",�˻�ID:"+inEvent.getAccountID();
		createSystemLogWithAdjust(accountAdjustmentCol,
				PublicService.getRemoteHostAddress(serviceContext),
				PublicService.getCurrentOperatorID(serviceContext), customerID,"�ʻ�����",logDescript);
		
		//����ϵͳ��־
//    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
//    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
//				SystemLogRecorder.LOGMODULE_SYSTEM, "�ʻ�����","�ʻ�����,"
//				+logDescript+",�˻�ID:"+inEvent.getAccountID()
//				+",�漰���ĵ��ʵ�:"+acctAdjustmentSeqnos, 
//				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * ����д��־����,��Ϊÿһ��¼дһ����־
	 * @param accountAdjustmentCol
	 * @param machineName
	 * @param operatorID
	 * @param customerID
	 * @param operation
	 * @param logDesc
	 * @throws ServiceException
	 */
	private void createSystemLogWithAdjust(Collection accountAdjustmentCol,String machineName,int operatorID,
			int customerID,String operation,String logDesc) throws ServiceException{
		
		Iterator acctAdjustIter =accountAdjustmentCol.iterator();
		int index=0;
		int recordCount=accountAdjustmentCol.size();
		while (acctAdjustIter.hasNext()){
			index++;
			Object[] acctAdjust =(Object[])acctAdjustIter.next();
			AccountAdjustment adjustDTO=(AccountAdjustment) acctAdjust[0];
			double amount=0;
			Object referObj=acctAdjust[1];
			if(referObj==null)continue;
			if(referObj instanceof AccountItemDTO){
				amount=((AccountItemDTO)referObj).getValue();
			}else if(referObj instanceof PaymentRecordDTO){
				amount=((PaymentRecordDTO)referObj).getAmount();
			}else if(referObj instanceof PrepaymentDeductionRecordDTO){
				amount=((PrepaymentDeductionRecordDTO)referObj).getAmount();
			}

			StringBuffer re=new StringBuffer();

			re.append("��ǰ���ʵ�:");
			re.append(adjustDTO.getSeqNo());
			re.append(",��������:");
			re.append(BusinessUtility.getCommonNameByKey("SET_F_ACCOUNTADJUSTMENTTYPE",adjustDTO.getAdjustmentType()));
			re.append(",����ԭ��:");
			re.append(BusinessUtility.getCommonNameByKey("SET_F_ACCOUNTADJUSTMENTREASON",adjustDTO.getReason()));
			re.append(",���ʶ���:(");
			re.append(adjustDTO.getReferRecordID());
			re.append(")");
			re.append(BusinessUtility.getCommonNameByKey("SET_F_ADJUSTMENTREFERRECORDTYPE",adjustDTO.getReferRecordType()));
			re.append(",���:");
			re.append(amount);

			SystemLogRecorder.createSystemLog(machineName,operatorID,customerID, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, operation,
					logDesc	
					+",���ε��ʹ�"+recordCount+"�ʼ�¼,��ǰΪ��"+index+"��;"
					+re.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
	}
	/**
	 * ��ʼ��ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(AdjustEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		String description="";
		String action="";
		switch (event.getActionType()) {
    		case EJBEvent.ADJUSTMENT:
				description="�ͻ��˻�����";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
	        	break;
    		case EJBEvent.CSI_ADJUSTMENT:
				description="��������";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    		case EJBEvent.INVOICE_ADJUSTMENT:
				description="�ʵ�����";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    		case EJBEvent.REPAIR_ADJUST_OP:
				description="���޵�����";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    			break;
		}
		serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
}
