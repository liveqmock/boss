package com.dtv.oss.service.command.batch;

import com.dtv.oss.domain.Account;
import com.dtv.oss.domain.Address;
import com.dtv.oss.domain.Customer;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.wrap.customer.Account2AddressWrap;
import com.dtv.oss.dto.wrap.customer.CustomerInfoWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.AccountService;
import com.dtv.oss.service.component.BatchService;
import com.dtv.oss.service.component.CSIService;
import com.dtv.oss.service.component.CustomerService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.BatchEJBEvent;
import com.dtv.oss.service.ejbevent.batch.BatchModifyCustEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.SystemLogRecorder;

public class BatchCommand extends Command {

	private static final Class clazz = BatchCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		BatchEJBEvent inEvent = (BatchEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.BATCH_QUERY_CREATE:
	    			createBatchQuery(inEvent);
	    			break;
	    		case EJBEvent.BATCH_QUERY_MODIFY:
	    			modifyBatchQuery(inEvent);
	    			break;
	    		case EJBEvent.BATCH_QUERY_CANCEL:
	    			cancelBatchQuery(inEvent);
	    			break;
	    		case EJBEvent.BATCH_QUERY_EXCUTE:
	    			excuteBatchQuery(inEvent);
	    			break;
	    		case EJBEvent.BATCH_QUERY_RESULT_CHANGE:
	    			changeResultItemBatchQuery(inEvent);
	    			break;
	    		case EJBEvent.BATCH_MODIFY_CUST:
	    			batchModifyCust((BatchModifyCustEJBEvent)inEvent);
	    			break;	
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("未知错误。");
		}
		return response;
	}

	private void excuteBatchQuery(BatchEJBEvent inEvent) throws ServiceException{
		
		ServiceContext context=initServiceContext(inEvent);
		
		BatchService batchService=new BatchService(context);
		batchService.excuteBatchQuery(inEvent.getQueryIDList());
		
	}

	private void cancelBatchQuery(BatchEJBEvent inEvent) throws ServiceException {
		
		ServiceContext context=initServiceContext(inEvent);
		
		BatchService batchService=new BatchService(context);
		batchService.cancelBatchQuery(inEvent.getQueryIDList());
	}

	private void changeResultItemBatchQuery(BatchEJBEvent inEvent) throws ServiceException{
		
		ServiceContext context=initServiceContext(inEvent);
		
		BatchService batchService=new BatchService(context);
		batchService.changeResultItemBatchQuery(inEvent.getResultItemIDList(),inEvent.getQueryDTO().getQueryId());
	}

	private void modifyBatchQuery(BatchEJBEvent inEvent) throws ServiceException{
		
		//测试RMI
		//DisplayPerfectTime.main(null);
		ServiceContext context=initServiceContext(inEvent);
		
		BatchService batchService=new BatchService(context);
		batchService.modifyBatchQuery(inEvent.getQueryDTO());
		
	}

	private void createBatchQuery(BatchEJBEvent inEvent) throws ServiceException{
		ServiceContext context=initServiceContext(inEvent);
		
		BatchService batchService=new BatchService(context);
		batchService.createBatchQuery(inEvent.getQueryDTO());
		response.setPayload(context.get(Service.PROCESS_RESULT));
	}

	private ServiceContext initServiceContext(BatchEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
	
	private void batchModifyCust(BatchModifyCustEJBEvent inEvent) throws ServiceException{
		ServiceContext context=initServiceContext(inEvent);
	    String[] custIds =inEvent.getStrCustIds().split(";");
	    for (int i=0;i<custIds.length;i++){
	    	int custId =Integer.parseInt(custIds[i]);
	    	
	    	/*更新客户内容 */
			CustServiceInteractionDTO csiDTO  =new CustServiceInteractionDTO();
			csiDTO.setCustomerID(custId);
	    	csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_IA);
	    	context.put(Service.CSI_TYPE,CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_IA);
	    	CustomerDTO customerDto =BusinessUtility.getCustomerDTOByCustomerID(custId);
	    	CustomerInfoWrap oldCustomerInfo = BusinessUtility.getCustomerInfoWrapByID(customerDto.getCustomerID());
	    	if (inEvent.getObject_CustomerType() !=null){
	    		customerDto.setCustomerType(inEvent.getObject_CustomerType());
	    	}
	    	AddressDTO  addressDto =BusinessUtility.getAddressDTOByAddressID(customerDto.getAddressID());
	    	addressDto.setDistrictID(inEvent.getObject_DistrictID());
	    	String  addrDetail =(addressDto.getDetailAddress()==null ) ? "" :addressDto.getDetailAddress();
	    	if (inEvent.getAddrOption() !=null){
	    		if (inEvent.getAddrOption().equals("A")){
	    			addrDetail =inEvent.getObject_DetailAddress()+addrDetail;
	    		}else if (inEvent.getAddrOption().equals("D")){
	    			addrDetail =addrDetail.replaceAll(inEvent.getSource_DetailAddress(), "");
	    		}else if (inEvent.getAddrOption().equals("R")){
	    			addrDetail =addrDetail.replaceAll(inEvent.getSource_DetailAddress(), inEvent.getObject_DetailAddress());
	    		}
	    	}
	    	addressDto.setDetailAddress(addrDetail);
            // 创建受理单和相关对象
			CSIService csiService = new CSIService(context);
			csiService.createCustServiceInteraction(csiDTO);
			
			// 更新客户信息
			CustomerService customerService = new CustomerService(context);
			customerService.updateCustomer(customerDto, addressDto, null,true);
			
			Customer customer=(Customer) context.get(Service.CUSTOMER);
			Address address=(Address) context.get(Service.CUSTOMER_ADDRESS_EJB);
			
			int csiID=((Integer)context.get(Service.CSI_ID)).intValue();
			StringBuffer logDesc=new StringBuffer();
			logDesc.append("批量修改客户资料,");
			logDesc.append("受理单ID:");
			logDesc.append(csiID);
			logDesc.append(";修改客户资料,客户ID:");
			logDesc.append(customerDto.getCustomerID());
			
			logDesc.append(SystemLogRecorder.appendDescString(";区域:",oldCustomerInfo.getDistrictSettingDTO().getName(), 
					                                                  BusinessUtility.getDistrictNameById(address.getDistrictID())));
			if (inEvent.getObject_CustomerType() !=null){
				logDesc.append(SystemLogRecorder.appendDescString(";客户类型:",
						oldCustomerInfo.getCustomerTypeDesc(), BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERTYPE", 
					    customer.getCustomerType())));
			}
			
			if (inEvent.getAddrOption() !=null){
				logDesc.append(SystemLogRecorder.appendDescString(";详细地址:",oldCustomerInfo.getAddrDto().getDetailAddress(), 
						                                           address.getDetailAddress()));
			}
			
			SystemLogRecorder.createSystemLog(machineName, operatorID,
					customerDto.getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "批量修改客户资料",
					logDesc.toString(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
			
			/*  更新帐单内容   */
			 CustServiceInteractionDTO acctCsiDTO  =new CustServiceInteractionDTO();
			 acctCsiDTO.setCustomerID(custId);
			 acctCsiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_IA);
	    	 context.put(Service.CSI_TYPE,CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_IA);
	    	 
             //	  更新帐户信息
			 AccountService accountService=new AccountService(context);
			 AccountDTO  acctDto= BusinessUtility.getAccountDTOByCustID(custId);
			 Account2AddressWrap oldAccountInfo=BusinessUtility.getAccountInfoWrapByID(acctDto.getAccountID());
			 
			 AddressDTO  acctAddrDto =BusinessUtility.getAddressDTOByAddressID(acctDto.getAddressID());
			 acctAddrDto.setDistrictID(inEvent.getObject_DistrictID());
			 String  acctAddrDetail =(acctAddrDto.getDetailAddress()==null) ? "" :acctAddrDto.getDetailAddress();
		     if (inEvent.getAddrOption() !=null){
		    	 if (inEvent.getAddrOption().equals("A")){
		    	     acctAddrDetail =inEvent.getObject_DetailAddress()+acctAddrDetail;
		    	 }else if (inEvent.getAddrOption().equals("D")){
		    	  	 acctAddrDetail =acctAddrDetail.replaceAll(inEvent.getSource_DetailAddress(), "");
		    	 }else if (inEvent.getAddrOption().equals("R")){
		    	  	 acctAddrDetail =acctAddrDetail.replaceAll(inEvent.getSource_DetailAddress(), inEvent.getObject_DetailAddress());
		    	 }
		     }
		     acctAddrDto.setDetailAddress(acctAddrDetail);
			 csiService.createCustServiceInteraction(acctCsiDTO);
			 int acctCsiID=((Integer)context.get(Service.CSI_ID)).intValue();
			 accountService.updateAccountInfo(acctDto,acctAddrDto,acctCsiID);
			 
			 Account account=(Account)context.get(Service.ACCOUNT);
			 Address acctaddress=(Address)context.get(Service.ACCOUNT_ADDRESS_EJB);
			 StringBuffer acctlogDesc=new StringBuffer();
			 acctlogDesc.append("批量修改客户账户信息,");
			 acctlogDesc.append("受理单ID:");
			 acctlogDesc.append(csiID);
			 acctlogDesc.append(";账户ID:");
			 acctlogDesc.append(account.getAccountID());
			 logDesc.append(SystemLogRecorder.appendDescString(";帐单寄送地址所在区",
						oldAccountInfo.getDistrictName(), 
						BusinessUtility.getDistrictNameById(address.getDistrictID())));
			 if (inEvent.getAddrOption() !=null){
			     logDesc.append(SystemLogRecorder.appendDescString(";帐单寄送详细地址",
					            oldAccountInfo.getAddrDto().getDetailAddress(), 
					            acctaddress.getDetailAddress()));
			 }
             //	创建系统日志
	    	 SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), custId, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "批量修改客户账户信息", acctlogDesc.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	    }
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
