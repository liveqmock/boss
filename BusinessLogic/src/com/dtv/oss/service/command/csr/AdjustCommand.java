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
		    throw new CommandException("发生未知的错误。");
		}
		return response;
	}
	/**
	 * 帐单调帐
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void invoiceAdjust(AdjustEJBEvent inEvent) throws ServiceException{
		//初始化ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		String logDescript ="";
		//取得客户id
		int customerID =inEvent.getCustomerID();
		//用来检查是否能对账户进行调账操作
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		
		Collection feeAdjustlist =inEvent.getFeeAdjustmentWrapDtoList();
		Collection payAdjustlist =inEvent.getPaymentAdjustWrapDtoList();
			
		//用来检查页面输入的调账费用和调账支付
		if(CommonConstDefinition.SETOFFFLAG_W.equals(inEvent.getAdjustMethod())){
			businessRuleService.checkInvoiceAjustment(inEvent.getID(),inEvent.getAccountID(),CommonConstDefinition.SETOFFFLAG_W,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
		    logDescript ="未销帐账单";
		}else if(CommonConstDefinition.SETOFFFLAG_D.equals(inEvent.getAdjustMethod())){
			businessRuleService.checkInvoiceAjustment(inEvent.getID(),inEvent.getAccountID(),CommonConstDefinition.SETOFFFLAG_D,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
			logDescript ="已销帐账单";
		}
		
		int operatorID =PublicService.getCurrentOperatorID(serviceContext);
		
		//开始调账
	 	Collection accountAdjustmentCol = FeeService.adjustFeeAndPayment(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_I,
	 			                          inEvent.getID(),inEvent.getCustomerID(),inEvent.getAccountID(),
	 			                          feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),
	 			                          operatorID); 	
	 	
		FeeService.changInvoiceAccordAdjust(inEvent.getActionType(),inEvent.getID(),inEvent.getAdjustMethod(),feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),inEvent.getCustomerID(),inEvent.getAccountID(),operatorID);	

		//创建系统日志

		logDescript="帐单调帐--"+logDescript+",帐单号:"+inEvent.getID()+",账户ID:"+inEvent.getAccountID();
		createSystemLogWithAdjust(accountAdjustmentCol,
				PublicService.getRemoteHostAddress(serviceContext),
				PublicService.getCurrentOperatorID(serviceContext), customerID,"帐单调账",logDescript);		
		
//		Iterator acctAdjustIter =accountAdjustmentCol.iterator();
//		int index=0;
//		int recordCount=accountAdjustmentCol.size();
//		while (acctAdjustIter.hasNext()){
//			index++;
//			AccountAdjustment acctAdjust =(AccountAdjustment)acctAdjustIter.next();
//	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
//	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
//					SystemLogRecorder.LOGMODULE_SYSTEM, "帐单调账",logDescript
//					+",本次调帐共"+recordCount+"笔记录,当前为第"+index+"条;"
//					+getAdjustmentLogDesc(acctAdjust.getSeqNo().intValue()), 
//					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);		
//		}
		

	}
	/**
	 * 报修单调帐
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void repairAdjust(AdjustEJBEvent inEvent) throws ServiceException{
		//初始化ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		String logDescript ="";
		//取得客户id
		int customerID =inEvent.getCustomerID();
		//用来检查是否能对账户进行调账操作
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		
		Collection feeAdjustlist =inEvent.getFeeAdjustmentWrapDtoList();
		Collection payAdjustlist =inEvent.getPaymentAdjustWrapDtoList();

		//用来检查页面输入的调账费用和调账支付
		businessRuleService.checkCPAjustment(inEvent.getID(),inEvent.getAccountID(),inEvent.getAdjustMethod(),
				feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
		int operatorID =PublicService.getCurrentOperatorID(serviceContext);
		//开始调账
		Collection accountAdjustmentCol =FeeService.adjustFeeAndPayment(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_C,
	 			                         inEvent.getID(),inEvent.getCustomerID(),
	 			                         inEvent.getAccountID(),feeAdjustlist,
	 			                         payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),
	 			                         operatorID); 	
    	
		Iterator acctAdjustIter =accountAdjustmentCol.iterator();
		
		if(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT.equals(inEvent.getAdjustMethod())){
			logDescript ="未销帐报修单";
		}else {
			logDescript ="已销帐报修单";
		}
		
		//创建系统日志
		logDescript="报修单调账--"+logDescript+",报修单的ID:"+inEvent.getID()+",账户ID:"+inEvent.getAccountID();
		createSystemLogWithAdjust(accountAdjustmentCol,
				PublicService.getRemoteHostAddress(serviceContext),
				PublicService.getCurrentOperatorID(serviceContext), customerID,"报修单调账",logDescript);		
//		int index=0;
//		int recordCount=accountAdjustmentCol.size();
//		while (acctAdjustIter.hasNext()){
//			index++;
//			AccountAdjustment acctAdjust =(AccountAdjustment)acctAdjustIter.next();
//	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
//	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
//					SystemLogRecorder.LOGMODULE_SYSTEM, "报修单调账",logDescript
//					+",本次调帐共"+recordCount+"笔记录,当前为第"+index+"条;"
//					+getAdjustmentLogDesc(acctAdjust.getSeqNo().intValue()),
//					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
//		}
	}

	/**
	 * 受理单调账
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void csiAdjust(AdjustEJBEvent inEvent) throws ServiceException{
		//初始化ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//取得客户id
		int customerID =inEvent.getCustomerID();
		//用来检查是否能对账户进行调账操作
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		
		Collection feeAdjustlist =inEvent.getFeeAdjustmentWrapDtoList();
		Collection payAdjustlist =inEvent.getPaymentAdjustWrapDtoList();

		//用来检查页面输入的调账费用和调账支付
		businessRuleService.checkCsiAjustment(inEvent.getID(),inEvent.getAccountID(),
				feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
		int operatorID =PublicService.getCurrentOperatorID(serviceContext);
		//开始调账
		Collection accountAdjustmentCol =FeeService.adjustFeeAndPayment(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_S,
	 			                         inEvent.getID(),inEvent.getCustomerID(),
	 			                         inEvent.getAccountID(),feeAdjustlist,
	 			                         payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),
	 			                         operatorID); 	
    	
		//创建系统日志
		String logDescript="受理单调账,受理单的ID:"+inEvent.getID()	+",账户ID:"+inEvent.getAccountID();
		createSystemLogWithAdjust(accountAdjustmentCol,
				PublicService.getRemoteHostAddress(serviceContext),
				PublicService.getCurrentOperatorID(serviceContext), customerID,"受理单调账",logDescript);
//		int index=0;
//		int recordCount=accountAdjustmentCol.size();
//	 	Iterator acctAdjustIter =accountAdjustmentCol.iterator();
//		while (acctAdjustIter.hasNext()){
//			index++;
//			AccountAdjustment acctAdjust =(AccountAdjustment)acctAdjustIter.next();
//	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
//	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
//					SystemLogRecorder.LOGMODULE_SYSTEM, "受理单调账", "受理单调账,受理单的ID:"+inEvent.getID()
//					+",账户ID:"+inEvent.getAccountID()
//					+",本次调帐共"+recordCount+"笔记录,当前为第"+index+"条"
//					+getAdjustmentLogDesc(acctAdjust.getSeqNo().intValue()), 
//					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
//		}
	}
	
	/**
	 * 账户调账
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void adjust(AdjustEJBEvent inEvent) throws ServiceException{
		//初始化ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		String logDescript ="";
		//取得客户id
		int customerID =inEvent.getCustomerID();
		//用来检查是否能对账户进行调账操作
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkAdjustInfo(inEvent);
		
		Collection feeAdjustlist =inEvent.getFeeAdjustmentWrapDtoList();
		Collection payAdjustlist =inEvent.getPaymentAdjustWrapDtoList();

		//用来检查页面输入的调账费用和调账支付
		//未销账费用调帐
		if(CommonConstDefinition.SETOFFFLAG_W.equals(inEvent.getAdjustMethod())){
			businessRuleService.checkAccountAjustment(inEvent.getAccountID(),CommonConstDefinition.SETOFFFLAG_W,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
			logDescript ="未付费用调帐";
		//已销账费用调帐
		}else if(CommonConstDefinition.SETOFFFLAG_D.equals(inEvent.getAdjustMethod())){
			businessRuleService.checkAccountAjustment(inEvent.getAccountID(),CommonConstDefinition.SETOFFFLAG_D,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
		    logDescript ="已付费用调帐";
		//帐户余额调帐
		}else{
			businessRuleService.checkAccountAjustment(inEvent.getAccountID(),null,feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList());
			logDescript ="帐户余额调帐";
		}
		
		int operatorID =PublicService.getCurrentOperatorID(serviceContext);
		//开始调账
		Collection accountAdjustmentCol = FeeService.adjustFeeAndPayment(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_M,
				                          inEvent.getID(),inEvent.getCustomerID(),inEvent.getAccountID(),
				                          feeAdjustlist,payAdjustlist,inEvent.getPreDeductionAdjustWrapDtoList(),
				                          operatorID);
		
		logDescript="帐户调账--"+logDescript+",账户ID:"+inEvent.getAccountID();
		createSystemLogWithAdjust(accountAdjustmentCol,
				PublicService.getRemoteHostAddress(serviceContext),
				PublicService.getCurrentOperatorID(serviceContext), customerID,"帐户调账",logDescript);
		
		//创建系统日志
//    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
//    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
//				SystemLogRecorder.LOGMODULE_SYSTEM, "帐户调账","帐户调账,"
//				+logDescript+",账户ID:"+inEvent.getAccountID()
//				+",涉及到的调帐单:"+acctAdjustmentSeqnos, 
//				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 调帐写日志方法,会为每一记录写一条日志
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

			re.append("当前调帐单:");
			re.append(adjustDTO.getSeqNo());
			re.append(",调帐类型:");
			re.append(BusinessUtility.getCommonNameByKey("SET_F_ACCOUNTADJUSTMENTTYPE",adjustDTO.getAdjustmentType()));
			re.append(",调帐原因:");
			re.append(BusinessUtility.getCommonNameByKey("SET_F_ACCOUNTADJUSTMENTREASON",adjustDTO.getReason()));
			re.append(",调帐对象:(");
			re.append(adjustDTO.getReferRecordID());
			re.append(")");
			re.append(BusinessUtility.getCommonNameByKey("SET_F_ADJUSTMENTREFERRECORDTYPE",adjustDTO.getReferRecordType()));
			re.append(",金额:");
			re.append(amount);

			SystemLogRecorder.createSystemLog(machineName,operatorID,customerID, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, operation,
					logDesc	
					+",本次调帐共"+recordCount+"笔记录,当前为第"+index+"条;"
					+re.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
	}
	/**
	 * 初始化ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(AdjustEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		String description="";
		String action="";
		switch (event.getActionType()) {
    		case EJBEvent.ADJUSTMENT:
				description="客户账户调账";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
	        	break;
    		case EJBEvent.CSI_ADJUSTMENT:
				description="受理单调账";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    		case EJBEvent.INVOICE_ADJUSTMENT:
				description="帐单调账";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    		case EJBEvent.REPAIR_ADJUST_OP:
				description="报修单调账";
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
