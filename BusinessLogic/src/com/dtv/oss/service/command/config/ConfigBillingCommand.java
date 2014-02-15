package com.dtv.oss.service.command.config;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.BillingRuleService;
import com.dtv.oss.service.component.BrConditionService;
import com.dtv.oss.service.component.MethodOfPaymentService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigBillingEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;

public class ConfigBillingCommand extends Command {

	 private static final Class clazz = ConfigBillingCommand.class;
		private int operatorID = 0;
		private String machineName = "";	
		CommandResponseImp response = null;
		private ServiceContext context;

		public CommandResponse execute(EJBEvent ev) throws CommandException {
			response = new CommandResponseImp(null);
			ConfigBillingEJBEvent inEvent = (ConfigBillingEJBEvent) ev;
			operatorID = inEvent.getOperatorID();
			machineName = ev.getRemoteHostAddress();
			LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
			 
			try {
				switch (inEvent.getActionType()) {
				  					
					//创建支付条件
					case EJBEvent.BILLING_RULE_CONDITION_CREATE:
						createBrCondition(inEvent);
						break;
				 
				 
					//修改支付条件
					case EJBEvent.BILLING_RULE_CONDITION_MODIFY:
						modifyBrCondition(inEvent);
						break;
						
                    //删除支付条件
					case EJBEvent.BILLING_RULE_CONDITION_DELE:
						deleteBrCondition(inEvent);
						break;		
                   //创建付费方式
					case EJBEvent.METHOD_OF_PAYMENT_CREATE:
						createMethodOfPayment(inEvent);
						break;
				 
				 
					//修改付费方式
					case EJBEvent.METHOD_OF_PAYMENT_MODIFY:
						modifyMethodOfPayment(inEvent);
						break;
						
                    //删除付费方式
					case EJBEvent.METHOD_OF_PAYMENT_DELE:
						deleteMethodOfPayment(inEvent);
						break;
                   //创建付费规则
					case EJBEvent.BILLING_RULE_CREATE:
						createBillingRule(inEvent);
						break;	
                   //	修改付费规则
					case EJBEvent.BILLING_RULE_MODIFY:
						modityBillingRule(inEvent);
						break;
						 //	删除付费规则
					case EJBEvent.BILLING_RULE_DELE:
						deleteBillingRule(inEvent);
						break;
					case EJBEvent.RECACULATE_RULE:
						recaculateBillingRule(inEvent);
						break;
						 //	删除付费规则
					case EJBEvent.FEE_PLAN_CREATE:
						createFeeSplit(inEvent);
						break;
					case EJBEvent.FEE_PLAN_MODIFY:
						modifyFeeSplit(inEvent);
						break;
					case EJBEvent.FEE_PLAN_ITEM_CREATE:
						createFeeSplitItem(inEvent);
						break;
					case EJBEvent.FEE_PLAN_ITEM_MODIFY:
						modifyFeeSplitItem(inEvent);
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
		
	

		private void createBrCondition(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BrConditionService brConditionService=new BrConditionService(context);
			brConditionService.brConditionCreate(inEvent.getBrDto());
			
			this.response.setPayload(context.get(Service.BRCONDITION_ID));
		}
		
		private void modifyBrCondition(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BrConditionService brConditionService=new BrConditionService(context);
			brConditionService.brConditionModify(inEvent.getBrDto());
		}
		
		private void deleteBrCondition(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BrConditionService brConditionService=new BrConditionService(context);
			brConditionService.brConditionDelete(inEvent.getBrDto());
		} 
		
		private void createMethodOfPayment(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			MethodOfPaymentService mpService=new MethodOfPaymentService(context);
			mpService.methodOfPaymentCreate(inEvent.getMethodOfPaymentDto());
			
			 
		}
		
		private void modifyMethodOfPayment(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			MethodOfPaymentService mpService=new MethodOfPaymentService(context);
			mpService.methodOfPaymentModify(inEvent.getMethodOfPaymentDto());
		}
		
		private void deleteMethodOfPayment(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			MethodOfPaymentService mpService=new MethodOfPaymentService(context);
			mpService.methodOfPaymentDelete(inEvent.getMethodOfPaymentDto());
		} 
		
		 
	 
		private ServiceContext initServiceContext(ConfigBillingEJBEvent event){
			ServiceContext serviceContext=new ServiceContext();
		    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
		    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
		    return serviceContext;
		}
		private void createBillingRule(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BillingRuleService mpService=new BillingRuleService(context);
			mpService.billingRuleCreate(inEvent.getBillingRuleDto());
			
			 
		}
		private void modityBillingRule(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BillingRuleService mpService=new BillingRuleService(context);
			mpService.billingRuleModify(inEvent.getBillingRuleDto());
			
			 
		}
		private void deleteBillingRule(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BillingRuleService mpService=new BillingRuleService(context);
			mpService.billingRuleDelete(inEvent.getBillingRuleDto());
			
			 
		}
		private void recaculateBillingRule(ConfigBillingEJBEvent inEvent) throws ServiceException, HomeFactoryException{
			ServiceContext context=initServiceContext(inEvent);
			BillingRuleService mpService=new BillingRuleService(context);
			mpService.callRecaculate();
			
			 
		}
		private void createFeeSplit(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BillingRuleService brService=new BillingRuleService(context);
			brService.feeSplitCreate(inEvent.getFeeSplitPlanDto());
			
			 
		}
		private void modifyFeeSplit(ConfigBillingEJBEvent inEvent) throws ServiceException, HomeFactoryException{
			ServiceContext context=initServiceContext(inEvent);
			BillingRuleService mpService=new BillingRuleService(context);
			mpService.feeSplitModify(inEvent.getFeeSplitPlanDto());
			
			 
		}
		private void createFeeSplitItem(ConfigBillingEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BillingRuleService brService=new BillingRuleService(context);
			brService.feeSplitItemCreate(inEvent.getFeeSplitPlanItemDto());
			
			 
		}
		private void modifyFeeSplitItem(ConfigBillingEJBEvent inEvent) throws ServiceException, HomeFactoryException{
			ServiceContext context=initServiceContext(inEvent);
			BillingRuleService mpService=new BillingRuleService(context);
			mpService.feeSplitItemModify(inEvent.getFeeSplitPlanItemDto());
			
			 
		}
			
			
}
