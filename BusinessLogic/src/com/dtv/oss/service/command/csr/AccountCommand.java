/*
 * Created on 2005-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.command.csr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.dtv.oss.domain.Account;
import com.dtv.oss.domain.Address;
import com.dtv.oss.domain.Invoice;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
import com.dtv.oss.dto.custom.BatchNoDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.dto.wrap.customer.Account2AddressWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.*;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.*;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.*;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountCommand extends Command {
	private static final Class clazz = AccountCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    AccountEJBEvent inEvent = (AccountEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.UPDATEACCOUNTINFO:
	    			alterAccountInfo(inEvent);
	    			break;
	    		case EJBEvent.CLOSECUSTOMERACCOUNT:
	    			closeAccount(inEvent);
	    			break;
	    		case EJBEvent.OPENACCOUNTFORCUSTOMER:
	    			createAccount(inEvent);
	    			break;
	    		case EJBEvent.PRESAVE:
	    			depositInAdvance(inEvent);
	    			break;
	    		case EJBEvent.PAYCHEK:
	    			checkInvoice(inEvent);
	    			break;
	    		case EJBEvent.PAYBYBILL:
	    			payInvoice(inEvent);
	    			break;
	    		case EJBEvent.BATCHALTEACCOUNTINFO:
	    			batchAlterAccountInfo(inEvent);
	    			break;
	    		case EJBEvent.CHECKBANKINFOFORMAT:
	    			checkBankInfoFormat(inEvent);
	    			break;
	    		case EJBEvent.INVOICE_BATCH_PAY:
	    			batchPayInvoice(inEvent);
	    			break;
	    		case EJBEvent.ACCOUNT_MANUAL_FEE:
	    			payManualFee(inEvent);
	    			break;
	    		case EJBEvent.SINGLE_ACCOUNT_PAY:
	    			batchPayAndPrey(inEvent);
	    			break;
	    		//多帐户批量支付帐单，多余的钱做预存
	    		case EJBEvent.MERGEINVOICE_BATCH_PAY:
	    			mergeInvoiceBatchPay(inEvent);
	    			break;
	    		case EJBEvent.BATCHPRESAVE:
	    			batchDepositInAdvance(inEvent);
	    			break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    unkown.printStackTrace();
		    throw new CommandException("发生未知的错误。");
		}
		return response;
	}
	/**
	 * 手工收费
	 * @param inEvent
	 */
	private void payManualFee(AccountEJBEvent inEvent) throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		CSIService csiService = new CSIService(serviceContext);
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		if(csiDTO == null)
			throw new ServiceException("受理单为空!");
		if(inEvent.getCsiFeeList() == null || inEvent.getCsiPaymentList() == null)
			throw new ServiceException("费用或者支付为空！");
		
		serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS);
		csiService.createCustServiceInteraction(csiDTO);
		int billingCycleID = BusinessUtility.getBillingCycleIDByTypeID();
		double cashDepositTotal = 0;
		double tokenDepositTotal = 0;
		
		for(Iterator i = inEvent.getCsiFeeList().iterator();i.hasNext();){
			AccountItemDTO acctItemDto = (AccountItemDTO)i.next();
			acctItemDto.setReferID(csiDTO.getId());
			acctItemDto.setBillingCycleID(billingCycleID);
		}
		for(Iterator i = inEvent.getCsiPaymentList().iterator();i.hasNext();){
			PaymentRecordDTO payDto = (PaymentRecordDTO)i.next();
			payDto.setReferID(csiDTO.getId());
			payDto.setSourceRecordID(csiDTO.getId());
			payDto.setPaidTo(csiDTO.getId());
			if(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(payDto.getPayType())){
				if(CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(payDto.getPrepaymentType()))
					cashDepositTotal += payDto.getAmount();
				else if(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY.equals(payDto.getPrepaymentType()))
					tokenDepositTotal += payDto.getAmount();
			}
		}
		FeeService.createFeeAndPaymentAndPreDuciton(inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),null,null);
		PayFeeUtil.updateAccount(inEvent.getCsiDto().getAccountID(), cashDepositTotal, CommonConstDefinition.PREPAYMENTTYPE_CASH);
		PayFeeUtil.updateAccount(inEvent.getCsiDto().getAccountID(), tokenDepositTotal, CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY);
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
						PublicService.getCurrentOperatorID(serviceContext), csiDTO.getCustomerID(), 
						SystemLogRecorder.LOGMODULE_CUSTSERV, "手工收费", "受理单ID：" + csiDTO.getId() + "; 帐户ID：" + csiDTO.getAccountID() , 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	
	}
	
	/**
	 * 批量支付帐单
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void batchPayInvoice(AccountEJBEvent inEvent)throws ServiceException {
		Collection invoiceList=inEvent.getCsiFeeList();
		ServiceContext serviceContext=initServiceContext(inEvent);
		serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW);
		CSIService csiService=new CSIService(serviceContext);
		Collection paymentList=inEvent.getCsiPaymentList();
		Collection acctCols =new ArrayList();
        
		//	检查逻辑
		Iterator checkItr =invoiceList.iterator();
		while (checkItr.hasNext()){
			 InvoiceDTO iDto=(InvoiceDTO)checkItr.next();
			 InvoiceDTO checkInvoiceDto = BusinessUtility.getInvoiceDTOByInvoiceNo(iDto.getInvoiceNo());
			 System.out.println("checkInvoiceDto.getInvoiceNo():"+checkInvoiceDto.getInvoiceNo()+";状态:"+checkInvoiceDto.getStatus());
			 if (CommonConstDefinition.INVOICE_STATUS_PAID.equals(checkInvoiceDto.getStatus())){
				 throw new ServiceException("帐户号:"+checkInvoiceDto.getAcctID()+"的帐单:"+checkInvoiceDto.getInvoiceNo()+"已支付,不能再支付!");
			 }
		}
		
		Iterator itr=invoiceList.iterator();
		while(itr.hasNext()){
			InvoiceDTO iDto=(InvoiceDTO)itr.next();
			if (!acctCols.contains(new Integer(iDto.getAcctID()))){
				acctCols.add(new Integer(iDto.getAcctID()));
				CustServiceInteractionDTO csi_dto=new CustServiceInteractionDTO();
				csi_dto.setAccountID(iDto.getAcctID());
				csi_dto.setCustomerID(iDto.getCustID());
				csi_dto.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PI);
				csi_dto.setBillCollectionMethod(CommonConstDefinition.CSI_PAYMENT_OPTION_IM);
				csiService.createCustServiceInteraction(csi_dto,iDto.getInvoiceNo());
				serviceContext.put(Service.CSI_DTO, csi_dto);
				csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);  
			}
			
			CustServiceInteractionDTO csiDTO=(CustServiceInteractionDTO)serviceContext.get(Service.CSI_DTO);
			
			AccountDTO acctDto=BusinessUtility.getAcctDTOByAcctID(iDto.getAcctID());
			int actionType=EJBEvent.PAYBYBILL;
			
			FeeService.payInvoice(csiDTO,acctDto,iDto,actionType,getFeeListForBatchPay(iDto),getPayListForBatchPay(paymentList,iDto),null,inEvent.getOperatorID());
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), csiDTO.getCustomerID(), 
	    			SystemLogRecorder.LOGMODULE_CUSTSERV, "批量支付帐单", "受理单的ID："+csiDTO.getId()+";账户ID："+acctDto.getAccountID()+";帐户名："+acctDto.getAccountName()+";帐单号："+iDto.getInvoiceNo()+";支付金额："+iDto.getPayAmount(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}	
	}
	
	
	private Collection getPayListForBatchPay(Collection totalPayList,InvoiceDTO dto) throws ServiceException{
		System.out.println("totalPayList----->"+totalPayList);
		Collection payList=new ArrayList();
		double punishment=BusinessUtility.getPunishmentValue(dto.getDueDate(),dto.getAmount());
		double amount=dto.getAmount()+punishment;
		dto.setPayAmount(amount);
		dto.setPunishment(punishment);
		Iterator pItr=totalPayList.iterator();
		while(pItr.hasNext()){
			PaymentRecordDTO pDto=(PaymentRecordDTO)pItr.next();
			
			System.out.println("amount-------->"+amount);
			System.out.println("pDto.getAmount()-------->"+pDto.getAmount());
			
			if(amount<=pDto.getAmount()){
				PaymentRecordDTO payDto=new PaymentRecordDTO();
				payDto.setMopID(pDto.getMopID());
				payDto.setAmount(amount);
				payDto.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_BILLING);
				payDto.setTicketType(pDto.getTicketType());
			   	payDto.setTicketNo(pDto.getTicketNo());
			   	payDto.setInvoiceNo(dto.getInvoiceNo());
				double theAmount=pDto.getAmount();
				pDto.setAmount(theAmount-amount);
				payList.add(payDto);
				return payList;
			}else if(pDto.getAmount()>0){
				PaymentRecordDTO payDto=new PaymentRecordDTO();
				payDto.setMopID(pDto.getMopID());
				payDto.setAmount(pDto.getAmount());
				payDto.setTicketType(pDto.getTicketType());
			   	payDto.setTicketNo(pDto.getTicketNo());
				payDto.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_BILLING);
				payDto.setInvoiceNo(dto.getInvoiceNo());
				amount-=pDto.getAmount();
				pDto.setAmount(0f);
				payList.add(payDto);
			}
		}
		return payList;
	}
	
	private Collection getFeeListForBatchPay(InvoiceDTO dto)throws ServiceException{
		Collection lstFee=new ArrayList();
		double punishment=BusinessUtility.getPunishmentValue(dto.getDueDate(),dto.getAmount());
		if(punishment==0.0f)
			return lstFee;
		AccountItemDTO acctItemDto =new AccountItemDTO();
		acctItemDto.setAcctItemTypeID(CommonConstDefinition.ACCTITEMTYPEID_PUNISHMENT);
		acctItemDto.setCustID(dto.getCustID());
		acctItemDto.setAcctID(dto.getAcctID());
		acctItemDto.setValue(punishment);
		acctItemDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		acctItemDto.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
		int billingCycleID=BusinessUtility.getPunishmentBillingCycleID(dto.getInvoiceCycleId());
		acctItemDto.setBillingCycleID(billingCycleID);
		acctItemDto.setInvoiceFlag(CommonConstDefinition.FTINVOICEDFLAG_YES);
		acctItemDto.setInvoiceNO(dto.getInvoiceNo());
		lstFee.add(acctItemDto);
		return lstFee;
	}
	/**
	 * 修改客户账户信息
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void alterAccountInfo(AccountEJBEvent inEvent) throws ServiceException{
		Account2AddressWrap oldAccountInfo=BusinessUtility.getAccountInfoWrapByID(inEvent.getAccountDTO().getAccountID());
		ServiceContext serviceContext=initServiceContext(inEvent);
		//创建受理单信息   	
    	CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
    	int customerID =inEvent.getCustomerID();
    	//设置客户id和客户账户id
    	csiDTO.setAccountID(inEvent.getAccountDTO().getAccountID());
    	csiDTO.setCustomerID(customerID);
    	//创建受理单信息
    	CSIService cSIService=new CSIService(serviceContext);
    	cSIService.createCustServiceInteraction(csiDTO);
    	
    	//校验银行帐号,wp@20080227
    	AccountDTO accountDTO=inEvent.getAccountDTO();
    	
		if(accountDTO.getBankAccount()!=null && !"".equals(accountDTO.getBankAccount())){
			if(accountDTO.getMopID()!=0){
				String functionResult = BusinessUtility.callFunctionForCheckBankAccount(accountDTO.getMopID(),accountDTO.getBankAccount());
				if(!"true".equals(functionResult))
					throw new ServiceException(functionResult);
			}
		}
    	
        // 更新帐户信息
    	int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		AccountService accountService=new AccountService(serviceContext);
		accountService.updateAccountInfo(inEvent.getAccountDTO(),inEvent.getAcctAddressDTO(),csiID);
		Account account=(Account)serviceContext.get(Service.ACCOUNT);
		Address address=(Address)serviceContext.get(Service.ACCOUNT_ADDRESS_EJB);
        	
		//处理费用的内容待追加
		StringBuffer logDesc=new StringBuffer();
		logDesc.append("修改客户账户信息,");
		logDesc.append("受理单ID:");
		logDesc.append(csiID);
		logDesc.append(";账户ID:");
		logDesc.append(inEvent.getAccountDTO().getAccountID());
		logDesc.append(SystemLogRecorder.appendDescString(";账户名:",
				oldAccountInfo.getAcctDto().getAccountName(), 
				inEvent.getAccountDTO().getAccountName()));
		logDesc.append(SystemLogRecorder.appendDescString(";付费类型",
				oldAccountInfo.getMethodOfPaymentName(), 
				BusinessUtility.getMethodOfPaymentNameById(account.getMopID())));
		logDesc.append(SystemLogRecorder.appendDescString(";银行帐号",
				oldAccountInfo.getAcctDto().getBankAccount(), 
				account.getBankAccount()));
		logDesc.append(SystemLogRecorder.appendDescString(";银行帐户名",
				oldAccountInfo.getAcctDto().getBankAccountName(), 
				account.getBankAccountName()));
		logDesc.append(SystemLogRecorder.appendDescString(";帐单寄送地址所在区",
				oldAccountInfo.getDistrictName(), 
				BusinessUtility.getDistrictNameById(address.getDistrictID())));
		logDesc.append(SystemLogRecorder.appendDescString(";帐单寄送邮编",
				oldAccountInfo.getAddrDto().getPostcode(), 
				address.getPostcode()));
		logDesc.append(SystemLogRecorder.appendDescString(";帐单寄送详细地址",
				oldAccountInfo.getAddrDto().getDetailAddress(), 
				address.getDetailAddress()));
		logDesc.append(SystemLogRecorder.appendDescString(";备注信息",
				oldAccountInfo.getAcctDto().getComments(), 
				inEvent.getAccountDTO().getComments()));

    	//创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "修改客户账户信息", logDesc.toString(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * 批量修改账户
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void batchAlterAccountInfo(AccountEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		int csiID =inEvent.getCsiID();
		int customerID =inEvent.getCustomerID();
		Collection addressDTOList =inEvent.getAcctAddressDTOList();
		Collection accountDTOList =inEvent.getAccountDTOList();
		AccountService acctService =new AccountService(serviceContext);
		acctService.batchUpdateAccountInfo(csiID,addressDTOList,accountDTOList);
		
		Iterator  addressIteror = addressDTOList.iterator();
 	    Iterator  accountIteror = accountDTOList.iterator();
 	    String strAcctID ="";
 	    String strAcctName ="";
 	    String strAddrID ="";
		while (accountIteror.hasNext()){
		    AddressDTO addressDto =(AddressDTO)addressIteror.next();
		    AccountDTO accountDto =(AccountDTO)accountIteror.next();
		    if (strAcctID.equals("")){
		    	strAcctID =""+accountDto.getAccountID();
		    } else{
		    	strAcctID =strAcctID +","+accountDto.getAccountID();
		    }
		    if (strAcctName.equals("")){
		    	strAcctName =accountDto.getAccountName();
		    } else {
		    	strAcctName =strAcctName +","+accountDto.getAccountName();
		    }
		    if (strAddrID.equals("")){
		    	strAddrID ="" +addressDto.getAddressID();
		    } else {
		    	strAddrID =strAddrID +","+addressDto.getAddressID();
		    }
		}
        //	创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "修改客户账户信息", "受理单的ID："+csiID+"；账户ID:"+strAcctID+";账户名："+strAcctName+";账户地址ID："+strAddrID, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * 校验银行信息格式
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void checkBankInfoFormat(AccountEJBEvent inEvent) throws ServiceException{
		Collection accountDTOList =inEvent.getAccountDTOList();
		
 	    Iterator  accountIteror = accountDTOList.iterator();

		while (accountIteror.hasNext()){
		    AccountDTO accountDto =(AccountDTO)accountIteror.next();
			// 校验银行帐号,wp@20080227
			if (accountDto.getBankAccount() != null
					&& !"".equals(accountDto.getBankAccount())) {
				if (accountDto.getMopID() != 0) {
					String functionResult = BusinessUtility
							.callFunctionForCheckBankAccount(accountDto.getMopID(),
									accountDto.getBankAccount());
					if (!"true".equals(functionResult))
						throw new ServiceException(functionResult);
				}
			}	
		}

	}	
	
	/**
	 * 客户销账户
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void closeAccount(AccountEJBEvent inEvent) throws ServiceException{
		//初始化ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//取得客户id
		int customerID =inEvent.getCustomerID();
		//取得受理单信息
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		//用来检查是否能对账户进行销户操作
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkOperationAccount(inEvent);
		//如果不是提交，则返回费用列表给前台
		if(!inEvent.isDoPost()){
			if(!inEvent.isReadyForeturnFee()){
				//费用列表
				Collection shouldPayFeeList=null;
				shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),0,this.operatorID,null);
				this.response.setPayload(shouldPayFeeList);
				return;
			}else{
				//费用列表
				CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
				FeeService.dealAccountFeeAndPayment(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
				this.response.setPayload(inEvent.getCsiFeeList());
				return;
			}
		}
		
		//创键受理单信息
    	CSIService cSIService=new CSIService(serviceContext);
    	cSIService.createCustServiceInteraction(csiDTO);
		//修改账户的状态
		AccountService.updateAccountInfo(inEvent.getAccountDTO(),inEvent.getActionType());

		//费用处理和产品费用计算
		//FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),operatorID);
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		cSIService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		//创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "取消帐户", "取消帐户,受理单的ID："+csiDTO.getId()+"；账户ID:"+inEvent.getAccountDTO().getAccountID()+"；账户名："
				+inEvent.getAccountDTO().getAccountName()+"；客户ID: "+inEvent.getAccountDTO().getCustomerID(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 账户预存
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void depositInAdvance(AccountEJBEvent inEvent) throws ServiceException{
		//初始化ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//取得客户id
		int customerID =inEvent.getCustomerID();
		//取得受理单信息
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		//用来检查是否能对账户进行预存操作
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkOperationAccount(inEvent);
		
		//创键受理单信息
    	CSIService cSIService=new CSIService(serviceContext);
    	cSIService.createCustServiceInteraction(csiDTO);
    	
		//处理费用的内容待追加
    	Account account =FeeService.prePayment(inEvent.getCsiDto(),inEvent.getCsiPaymentList(),inEvent.getOperatorID());
    	cSIService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		//创建系统日志
		AccountDTO acctDTO=BusinessUtility.getAcctDTOByAcctID(inEvent.getAccountDTO().getAccountID());
		String cashBalanceDesc = (acctDTO.getCashDeposit() != account
				.getCashDeposit()) ? (";预存现金:" + (account.getCashDeposit() - acctDTO
				.getCashDeposit()))
				: "";
		String tokenBalanceDesc = (acctDTO.getTokenDeposit() != account
				.getTokenDeposit()) ? (";预存虚拟货币:" + (account.getTokenDeposit() - acctDTO
				.getTokenDeposit()))
				: "";
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "预付费", "预付费,受理单的ID："+csiDTO.getId()
				+"；账户ID:"+acctDTO.getAccountID()
				+"；账户名："+acctDTO.getAccountName()
				+cashBalanceDesc+tokenBalanceDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
				
		this.response.setPayload(new Integer(csiDTO.getId()));
	}	
	private void checkInvoice(AccountEJBEvent inEvent) throws ServiceException{
		InvoiceDTO invoiceDto =inEvent.getInvoiceDTO();
		invoiceDto =BusinessUtility.getInvoiceDTOByBarcode(invoiceDto.getBarCode());
		String strStatus =invoiceDto.getStatus();
		if(CommonConstDefinition.INVOICE_STATUS_WAIT.equals(strStatus) || CommonConstDefinition.INVOICE_STATUS_OWE.equals(strStatus))
	    {
			ArrayList list =new ArrayList();
			list.add(invoiceDto);
			this.response.setPayload(list);
			return;
	    }else{
	    	throw new ServiceException("该帐单已付，不能再次支付！");
	    }
	}
	
	/**
	 * 支付账单
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void payInvoice(AccountEJBEvent inEvent) throws ServiceException{
		//初始化ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		int invoiceNo = inEvent.getInvoiceDTO().getInvoiceNo(); 
		
		//取得客户id
		int customerID =inEvent.getCustomerID();
		inEvent.getAccountDTO().setCustomerID(customerID);
		//取得受理单信息
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		
		//创键受理单信息
    	CSIService cSIService=new CSIService(serviceContext);
        cSIService.createCustServiceInteraction(csiDTO,invoiceNo);
        String acctName = inEvent.getAccountDTO().getAccountName();
		//处理费用的内容待追加
        Invoice invoice=FeeService.payInvoice(inEvent.getCsiDto(),inEvent.getAccountDTO(),inEvent.getInvoiceDTO(),inEvent.getActionType(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),inEvent.getOperatorID());
        cSIService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);

    	//创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "支付账单", "受理单的ID："
				+csiDTO.getId()+";帐单号: "
				+invoiceNo+";账户ID:"
				+inEvent.getAccountDTO().getAccountID()
				+";账户名："+ (acctName==null?"":acctName)
				+";支付金额:"+invoice.getPayAmount(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 新开账户
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void createAccount(AccountEJBEvent inEvent) throws ServiceException{
		//初始化ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//取得客户id
		int customerID =inEvent.getCustomerID();
		//get the customerstyle 
		String custStyle=null;
		 
		//取得受理单信息
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		//账户地址
		AddressDTO  acctAddressDTO=inEvent.getAcctAddressDTO();
		//账户信息
		AccountDTO accountDTO=inEvent.getAccountDTO();
		//用来检查是否能进行新开账户的操作
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkCreateAccount(inEvent);
		
    	//校验银行帐号,wp@20080227    	
		if(accountDTO.getBankAccount()!=null && !"".equals(accountDTO.getBankAccount())){
			if(accountDTO.getMopID()!=0){
				String functionResult = BusinessUtility.callFunctionForCheckBankAccount(accountDTO.getMopID(),accountDTO.getBankAccount());
				if(!"true".equals(functionResult))
					throw new ServiceException(functionResult);
			}
		}
    			
		
		//add by chenjiang
		custStyle=businessRuleService.getCustStyle(customerID);
		if(!inEvent.isDoPost()){
			CommonFeeAndPayObject commonObj= PublicService.encapsulateBaseParamForOpenAcct(inEvent.getCsiDto(),inEvent.getCustomerID(),inEvent.getAccountDTO());
			Collection shouldPayFeeList=FeeService.calculateImmediateFeeForOpen(inEvent.getCsiDto(),null, null,commonObj);
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//创键受理单信息
    	CSIService cSIService=new CSIService(serviceContext);
    	cSIService.createCustServiceInteraction(csiDTO);
        //修改账户的状态
		AccountService accountService=new AccountService(serviceContext);
		accountService.create(accountDTO,acctAddressDTO,true);
		//在受理单中设置帐户的id
		csiDTO.setAccountID(accountDTO.getAccountID());
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		cSIService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		
		//创建系统日志
	    if("G".equalsIgnoreCase(custStyle)){
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "新建账户", "新建账户,受理单的ID："+csiDTO.getId()+"；账户ID:"+inEvent.getAccountDTO().getAccountID()+"；账户名："+inEvent.getAccountDTO().getAccountName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
				
	    }
	    else
	    	
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "新建账户", "新建账户,受理单的ID："+csiDTO.getId()+"；账户ID:"+inEvent.getAccountDTO().getAccountID()+"；账户名："+inEvent.getAccountDTO().getAccountName(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		
	}
	/**
	 * 初始化ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(AccountEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		String description="";
		String action="";
		switch (event.getActionType()) {
			case EJBEvent.UPDATEACCOUNTINFO:
				description="更新客户账户信息";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			case EJBEvent.CLOSECUSTOMERACCOUNT:	
				description="客户账户销户";
			    action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			case EJBEvent.OPENACCOUNTFORCUSTOMER:
				description="客户新增账户";
		        action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    			break;
    		case EJBEvent.PRESAVE:
				description="客户预存";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    			break;
    		case EJBEvent.PAYBYBILL:
				description="客户支付账单";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    			break;
		}
		serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
	
	private void mergeInvoiceBatchPay(AccountEJBEvent inEvent)throws ServiceException {
		 //批量支付帐单
		 batchPayInvoice(inEvent);
		 //批量预存
		 Collection csiPrePayList =inEvent.getCsiPrePaymentDeductionList();
		 Iterator   csiPrePayItr =csiPrePayList.iterator();
		 while (csiPrePayItr.hasNext()){
			 PaymentRecordDTO dto = (PaymentRecordDTO)csiPrePayItr.next();
			 ServiceContext serviceContext=initServiceContext(inEvent);
			 serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW);
			 CSIService csiService=new CSIService(serviceContext);
			 CustServiceInteractionDTO csiDTO=new CustServiceInteractionDTO();
			 csiDTO.setAccountID(dto.getAcctID());
			 csiDTO.setCustomerID(dto.getCustID());
			 csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_AD);
			 csiDTO.setBillCollectionMethod(CommonConstDefinition.CSI_PAYMENT_OPTION_IM);
			 csiService.createCustServiceInteraction(csiDTO);
			 Collection prePayCol =new ArrayList();
			 prePayCol.add(dto);
			 Account account =FeeService.prePayment(csiDTO,prePayCol,inEvent.getOperatorID());
			 csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);  
			 // 创建系统日志
			 AccountDTO acctDTO=BusinessUtility.getAcctDTOByAcctID(dto.getAcctID());
			 String cashBalanceDesc = (acctDTO.getCashDeposit() != account
					                     .getCashDeposit()) ? (";预存现金:" + (account.getCashDeposit() - acctDTO
					                     .getCashDeposit()))
					                      : "";
			 String tokenBalanceDesc = (acctDTO.getTokenDeposit() != account
					                     .getTokenDeposit()) ? (";预存虚拟货币:" + (account.getTokenDeposit() - acctDTO
					                     .getTokenDeposit()))
					                     : "";
			 SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
					PublicService.getCurrentOperatorID(serviceContext), acctDTO.getCustomerID(), 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "预付费", "预付费,受理单的ID："+csiDTO.getId()
					+"；账户ID:"+acctDTO.getAccountID()
					+"；账户名："+acctDTO.getAccountName()
					+cashBalanceDesc+tokenBalanceDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP); 
		 }
	}
	
	/*
	 * 单帐户的批量支付帐单和预存
	 */
	private void batchPayAndPrey(AccountEJBEvent inEvent)throws ServiceException {
		 int invoiceCount =BusinessUtility.getUnpaidInvoiceCount(inEvent.getAccountDTO().getAccountID());
		 Collection pCol =inEvent.getCsiPaymentList();
		 Iterator pIter =pCol.iterator();
		 double sumPrePayAmount =0;
		 Collection payCol =new ArrayList();
		 Collection prePayCol =new ArrayList();
		 AccountDTO acctDto=BusinessUtility.getAcctDTOByAcctID(inEvent.getAccountDTO().getAccountID());
		 Collection invoiceList=inEvent.getCsiFeeList();
		 //检查逻辑
		 Iterator checkItr =invoiceList.iterator();
		 while (checkItr.hasNext()){
			 InvoiceDTO iDto=(InvoiceDTO)checkItr.next();
			 InvoiceDTO checkInvoiceDto = BusinessUtility.getInvoiceDTOByInvoiceNo(iDto.getInvoiceNo());
			 if (CommonConstDefinition.INVOICE_STATUS_PAID.equals(checkInvoiceDto.getStatus())){
				 throw new ServiceException("帐单号:"+checkInvoiceDto.getInvoiceNo()+"已支付,不能再支付!");
			 }
		 }
		 
		 while (pIter.hasNext()){
		     PaymentRecordDTO pDto=(PaymentRecordDTO)pIter.next();
			 if (pDto.getPayType().equals(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE)){
				 sumPrePayAmount =sumPrePayAmount +pDto.getAmount();
				 pDto.setCustID(acctDto.getCustomerID());
				 pDto.setAcctID(acctDto.getAccountID());
				 prePayCol.add(pDto);
			 }else{
				 pDto.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_BILLING);
				 payCol.add(pDto);
			 }
		 }
		 if (invoiceCount >inEvent.getCsiFeeList().size() && sumPrePayAmount>0){
		     throw new ServiceException("该帐户还有尚未缴清的帐单,支付金额不能大于显示的帐单金额!");
		 }
		 	 
		 Iterator itr=invoiceList.iterator();
		 ServiceContext serviceContext=initServiceContext(inEvent);
		 serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW);
		 CSIService csiService=new CSIService(serviceContext);
		 CustServiceInteractionDTO csiDTO=new CustServiceInteractionDTO();
		 csiDTO.setAccountID(acctDto.getAccountID());
		 csiDTO.setCustomerID(acctDto.getCustomerID());
		 csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PI);
		 csiDTO.setBillCollectionMethod(CommonConstDefinition.CSI_PAYMENT_OPTION_IM);
		 int actionType=EJBEvent.PAYBYBILL;
		 csiService.createCustServiceInteraction(csiDTO);
		 // 处理帐单
		 while(itr.hasNext()){
			  InvoiceDTO iDto=(InvoiceDTO)itr.next();
			  FeeService.payInvoice(csiDTO,acctDto,iDto,actionType,getFeeListForBatchPay(iDto),getPayListForBatchPay(payCol,iDto),null,inEvent.getOperatorID());
				
			  SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
		    			PublicService.getCurrentOperatorID(serviceContext), csiDTO.getCustomerID(), 
		    			SystemLogRecorder.LOGMODULE_CUSTSERV, "批量支付帐单", "受理单的ID："+csiDTO.getId()+";账户ID："+acctDto.getAccountID()+";帐户名："+acctDto.getAccountName()+";帐单号："+iDto.getInvoiceNo()+";支付金额："+iDto.getPayAmount(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		 }	
		csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		
        // 处理预存
		if (prePayCol.isEmpty()) return;
		CustServiceInteractionDTO prepCsiDTO=new CustServiceInteractionDTO();
		prepCsiDTO.setAccountID(acctDto.getAccountID());
		prepCsiDTO.setCustomerID(acctDto.getCustomerID());
		prepCsiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_AD);
		prepCsiDTO.setBillCollectionMethod(CommonConstDefinition.CSI_PAYMENT_OPTION_IM);
		csiService.createCustServiceInteraction(prepCsiDTO);
	    Account account =FeeService.prePayment(prepCsiDTO,prePayCol,inEvent.getOperatorID());
	    csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);

	    // 创建系统日志
	    AccountDTO acctDTO=BusinessUtility.getAcctDTOByAcctID(inEvent.getAccountDTO().getAccountID());
	    String cashBalanceDesc = (acctDTO.getCashDeposit() != account
			                     .getCashDeposit()) ? (";预存现金:" + (account.getCashDeposit() - acctDTO
			                     .getCashDeposit()))
			                      : "";
	    String tokenBalanceDesc = (acctDTO.getTokenDeposit() != account
			                     .getTokenDeposit()) ? (";预存虚拟货币:" + (account.getTokenDeposit() - acctDTO
			                     .getTokenDeposit()))
			                     : "";
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
			PublicService.getCurrentOperatorID(serviceContext), acctDto.getCustomerID(), 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "预付费", "预付费,受理单的ID："+prepCsiDTO.getId()
			+"；账户ID:"+acctDTO.getAccountID()
			+"；账户名："+acctDTO.getAccountName()
			+cashBalanceDesc+tokenBalanceDesc, 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP); 
	}	
	/**
	 * 批量账户预存
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void batchDepositInAdvance(AccountEJBEvent inEvent) throws ServiceException{
		//初始化ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//取得批量处理需要的参数：
		List batchPreSaveParaList = inEvent.getBatchPreSaveParaList();
		List txtAccountIDList = (List)batchPreSaveParaList.get(0);
		List txtCustomerIDList = (List)batchPreSaveParaList.get(1);
		List PayList = (List)batchPreSaveParaList.get(2);
		
		//用来检查是否能对账户进行预存操作
		/*
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkOperationAccount(inEvent);
		**/
		
		
		//循环处理批量预存
		for(int i=0;i<txtAccountIDList.size();i++)
		{
		//取得客户id
			int customerID =(new Integer(txtCustomerIDList.get(i)+"")).intValue();
			int AccountID =(new Integer(txtAccountIDList.get(i)+"")).intValue();
			//取得受理单信息
			CustServiceInteractionDTO csiDTO=new CustServiceInteractionDTO();
			csiDTO.setCustomerID(customerID);
			csiDTO.setAccountID(AccountID);
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_AD);
			csiDTO.setBillCollectionMethod(CommonConstDefinition.CSI_PAYMENT_OPTION_IM);
			//创键受理单信息
	    	CSIService cSIService=new CSIService(serviceContext);
	    	cSIService.createCustServiceInteraction(csiDTO);
			//处理费用的内容待追加
	    	Account account =FeeService.prePayment(csiDTO,(List)PayList.get(i),operatorID);
	    	cSIService.updateCSIPayStatus(serviceContext,csiDTO.getPaymentStatus());
           //创建系统日志
			AccountDTO acctDTO=BusinessUtility.getAcctDTOByAcctID(AccountID);
			String cashBalanceDesc = (acctDTO.getCashDeposit() != account
					.getCashDeposit()) ? (";预存现金:" + (account.getCashDeposit() - acctDTO
					.getCashDeposit()))
					: "";
			String tokenBalanceDesc = (acctDTO.getTokenDeposit() != account
					.getTokenDeposit()) ? (";预存虚拟货币:" + (account.getTokenDeposit() - acctDTO
					.getTokenDeposit()))
					: "";
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "预付费", "预付费,受理单的ID："+csiDTO.getId()
					+"；账户ID:"+acctDTO.getAccountID()
					+"；账户名："+acctDTO.getAccountName()
					+cashBalanceDesc+tokenBalanceDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}	
		//this.response.setPayload(new Integer(csiDTO.getId()));
	}
}
