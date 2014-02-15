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
	    		//���ʻ�����֧���ʵ��������Ǯ��Ԥ��
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
		    throw new CommandException("����δ֪�Ĵ���");
		}
		return response;
	}
	/**
	 * �ֹ��շ�
	 * @param inEvent
	 */
	private void payManualFee(AccountEJBEvent inEvent) throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		CSIService csiService = new CSIService(serviceContext);
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		if(csiDTO == null)
			throw new ServiceException("����Ϊ��!");
		if(inEvent.getCsiFeeList() == null || inEvent.getCsiPaymentList() == null)
			throw new ServiceException("���û���֧��Ϊ�գ�");
		
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
						SystemLogRecorder.LOGMODULE_CUSTSERV, "�ֹ��շ�", "����ID��" + csiDTO.getId() + "; �ʻ�ID��" + csiDTO.getAccountID() , 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	
	}
	
	/**
	 * ����֧���ʵ�
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
        
		//	����߼�
		Iterator checkItr =invoiceList.iterator();
		while (checkItr.hasNext()){
			 InvoiceDTO iDto=(InvoiceDTO)checkItr.next();
			 InvoiceDTO checkInvoiceDto = BusinessUtility.getInvoiceDTOByInvoiceNo(iDto.getInvoiceNo());
			 System.out.println("checkInvoiceDto.getInvoiceNo():"+checkInvoiceDto.getInvoiceNo()+";״̬:"+checkInvoiceDto.getStatus());
			 if (CommonConstDefinition.INVOICE_STATUS_PAID.equals(checkInvoiceDto.getStatus())){
				 throw new ServiceException("�ʻ���:"+checkInvoiceDto.getAcctID()+"���ʵ�:"+checkInvoiceDto.getInvoiceNo()+"��֧��,������֧��!");
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
	    			SystemLogRecorder.LOGMODULE_CUSTSERV, "����֧���ʵ�", "������ID��"+csiDTO.getId()+";�˻�ID��"+acctDto.getAccountID()+";�ʻ�����"+acctDto.getAccountName()+";�ʵ��ţ�"+iDto.getInvoiceNo()+";֧����"+iDto.getPayAmount(), 
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
	 * �޸Ŀͻ��˻���Ϣ
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void alterAccountInfo(AccountEJBEvent inEvent) throws ServiceException{
		Account2AddressWrap oldAccountInfo=BusinessUtility.getAccountInfoWrapByID(inEvent.getAccountDTO().getAccountID());
		ServiceContext serviceContext=initServiceContext(inEvent);
		//����������Ϣ   	
    	CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
    	int customerID =inEvent.getCustomerID();
    	//���ÿͻ�id�Ϳͻ��˻�id
    	csiDTO.setAccountID(inEvent.getAccountDTO().getAccountID());
    	csiDTO.setCustomerID(customerID);
    	//����������Ϣ
    	CSIService cSIService=new CSIService(serviceContext);
    	cSIService.createCustServiceInteraction(csiDTO);
    	
    	//У�������ʺ�,wp@20080227
    	AccountDTO accountDTO=inEvent.getAccountDTO();
    	
		if(accountDTO.getBankAccount()!=null && !"".equals(accountDTO.getBankAccount())){
			if(accountDTO.getMopID()!=0){
				String functionResult = BusinessUtility.callFunctionForCheckBankAccount(accountDTO.getMopID(),accountDTO.getBankAccount());
				if(!"true".equals(functionResult))
					throw new ServiceException(functionResult);
			}
		}
    	
        // �����ʻ���Ϣ
    	int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		AccountService accountService=new AccountService(serviceContext);
		accountService.updateAccountInfo(inEvent.getAccountDTO(),inEvent.getAcctAddressDTO(),csiID);
		Account account=(Account)serviceContext.get(Service.ACCOUNT);
		Address address=(Address)serviceContext.get(Service.ACCOUNT_ADDRESS_EJB);
        	
		//������õ����ݴ�׷��
		StringBuffer logDesc=new StringBuffer();
		logDesc.append("�޸Ŀͻ��˻���Ϣ,");
		logDesc.append("����ID:");
		logDesc.append(csiID);
		logDesc.append(";�˻�ID:");
		logDesc.append(inEvent.getAccountDTO().getAccountID());
		logDesc.append(SystemLogRecorder.appendDescString(";�˻���:",
				oldAccountInfo.getAcctDto().getAccountName(), 
				inEvent.getAccountDTO().getAccountName()));
		logDesc.append(SystemLogRecorder.appendDescString(";��������",
				oldAccountInfo.getMethodOfPaymentName(), 
				BusinessUtility.getMethodOfPaymentNameById(account.getMopID())));
		logDesc.append(SystemLogRecorder.appendDescString(";�����ʺ�",
				oldAccountInfo.getAcctDto().getBankAccount(), 
				account.getBankAccount()));
		logDesc.append(SystemLogRecorder.appendDescString(";�����ʻ���",
				oldAccountInfo.getAcctDto().getBankAccountName(), 
				account.getBankAccountName()));
		logDesc.append(SystemLogRecorder.appendDescString(";�ʵ����͵�ַ������",
				oldAccountInfo.getDistrictName(), 
				BusinessUtility.getDistrictNameById(address.getDistrictID())));
		logDesc.append(SystemLogRecorder.appendDescString(";�ʵ������ʱ�",
				oldAccountInfo.getAddrDto().getPostcode(), 
				address.getPostcode()));
		logDesc.append(SystemLogRecorder.appendDescString(";�ʵ�������ϸ��ַ",
				oldAccountInfo.getAddrDto().getDetailAddress(), 
				address.getDetailAddress()));
		logDesc.append(SystemLogRecorder.appendDescString(";��ע��Ϣ",
				oldAccountInfo.getAcctDto().getComments(), 
				inEvent.getAccountDTO().getComments()));

    	//����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�޸Ŀͻ��˻���Ϣ", logDesc.toString(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * �����޸��˻�
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
        //	����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�޸Ŀͻ��˻���Ϣ", "������ID��"+csiID+"���˻�ID:"+strAcctID+";�˻�����"+strAcctName+";�˻���ַID��"+strAddrID, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * У��������Ϣ��ʽ
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void checkBankInfoFormat(AccountEJBEvent inEvent) throws ServiceException{
		Collection accountDTOList =inEvent.getAccountDTOList();
		
 	    Iterator  accountIteror = accountDTOList.iterator();

		while (accountIteror.hasNext()){
		    AccountDTO accountDto =(AccountDTO)accountIteror.next();
			// У�������ʺ�,wp@20080227
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
	 * �ͻ����˻�
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void closeAccount(AccountEJBEvent inEvent) throws ServiceException{
		//��ʼ��ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//ȡ�ÿͻ�id
		int customerID =inEvent.getCustomerID();
		//ȡ��������Ϣ
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		//��������Ƿ��ܶ��˻�������������
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkOperationAccount(inEvent);
		//��������ύ���򷵻ط����б��ǰ̨
		if(!inEvent.isDoPost()){
			if(!inEvent.isReadyForeturnFee()){
				//�����б�
				Collection shouldPayFeeList=null;
				shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),0,this.operatorID,null);
				this.response.setPayload(shouldPayFeeList);
				return;
			}else{
				//�����б�
				CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
				FeeService.dealAccountFeeAndPayment(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
				this.response.setPayload(inEvent.getCsiFeeList());
				return;
			}
		}
		
		//����������Ϣ
    	CSIService cSIService=new CSIService(serviceContext);
    	cSIService.createCustServiceInteraction(csiDTO);
		//�޸��˻���״̬
		AccountService.updateAccountInfo(inEvent.getAccountDTO(),inEvent.getActionType());

		//���ô���Ͳ�Ʒ���ü���
		//FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),operatorID);
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		cSIService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		//����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "ȡ���ʻ�", "ȡ���ʻ�,������ID��"+csiDTO.getId()+"���˻�ID:"+inEvent.getAccountDTO().getAccountID()+"���˻�����"
				+inEvent.getAccountDTO().getAccountName()+"���ͻ�ID: "+inEvent.getAccountDTO().getCustomerID(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * �˻�Ԥ��
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void depositInAdvance(AccountEJBEvent inEvent) throws ServiceException{
		//��ʼ��ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//ȡ�ÿͻ�id
		int customerID =inEvent.getCustomerID();
		//ȡ��������Ϣ
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		//��������Ƿ��ܶ��˻�����Ԥ�����
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkOperationAccount(inEvent);
		
		//����������Ϣ
    	CSIService cSIService=new CSIService(serviceContext);
    	cSIService.createCustServiceInteraction(csiDTO);
    	
		//������õ����ݴ�׷��
    	Account account =FeeService.prePayment(inEvent.getCsiDto(),inEvent.getCsiPaymentList(),inEvent.getOperatorID());
    	cSIService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		//����ϵͳ��־
		AccountDTO acctDTO=BusinessUtility.getAcctDTOByAcctID(inEvent.getAccountDTO().getAccountID());
		String cashBalanceDesc = (acctDTO.getCashDeposit() != account
				.getCashDeposit()) ? (";Ԥ���ֽ�:" + (account.getCashDeposit() - acctDTO
				.getCashDeposit()))
				: "";
		String tokenBalanceDesc = (acctDTO.getTokenDeposit() != account
				.getTokenDeposit()) ? (";Ԥ���������:" + (account.getTokenDeposit() - acctDTO
				.getTokenDeposit()))
				: "";
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "Ԥ����", "Ԥ����,������ID��"+csiDTO.getId()
				+"���˻�ID:"+acctDTO.getAccountID()
				+"���˻�����"+acctDTO.getAccountName()
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
	    	throw new ServiceException("���ʵ��Ѹ��������ٴ�֧����");
	    }
	}
	
	/**
	 * ֧���˵�
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void payInvoice(AccountEJBEvent inEvent) throws ServiceException{
		//��ʼ��ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		int invoiceNo = inEvent.getInvoiceDTO().getInvoiceNo(); 
		
		//ȡ�ÿͻ�id
		int customerID =inEvent.getCustomerID();
		inEvent.getAccountDTO().setCustomerID(customerID);
		//ȡ��������Ϣ
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		
		//����������Ϣ
    	CSIService cSIService=new CSIService(serviceContext);
        cSIService.createCustServiceInteraction(csiDTO,invoiceNo);
        String acctName = inEvent.getAccountDTO().getAccountName();
		//������õ����ݴ�׷��
        Invoice invoice=FeeService.payInvoice(inEvent.getCsiDto(),inEvent.getAccountDTO(),inEvent.getInvoiceDTO(),inEvent.getActionType(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),inEvent.getOperatorID());
        cSIService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);

    	//����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "֧���˵�", "������ID��"
				+csiDTO.getId()+";�ʵ���: "
				+invoiceNo+";�˻�ID:"
				+inEvent.getAccountDTO().getAccountID()
				+";�˻�����"+ (acctName==null?"":acctName)
				+";֧�����:"+invoice.getPayAmount(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * �¿��˻�
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void createAccount(AccountEJBEvent inEvent) throws ServiceException{
		//��ʼ��ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//ȡ�ÿͻ�id
		int customerID =inEvent.getCustomerID();
		//get the customerstyle 
		String custStyle=null;
		 
		//ȡ��������Ϣ
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		//�˻���ַ
		AddressDTO  acctAddressDTO=inEvent.getAcctAddressDTO();
		//�˻���Ϣ
		AccountDTO accountDTO=inEvent.getAccountDTO();
		//��������Ƿ��ܽ����¿��˻��Ĳ���
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkCreateAccount(inEvent);
		
    	//У�������ʺ�,wp@20080227    	
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
		//����������Ϣ
    	CSIService cSIService=new CSIService(serviceContext);
    	cSIService.createCustServiceInteraction(csiDTO);
        //�޸��˻���״̬
		AccountService accountService=new AccountService(serviceContext);
		accountService.create(accountDTO,acctAddressDTO,true);
		//�������������ʻ���id
		csiDTO.setAccountID(accountDTO.getAccountID());
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		cSIService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		
		//����ϵͳ��־
	    if("G".equalsIgnoreCase(custStyle)){
	    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "�½��˻�", "�½��˻�,������ID��"+csiDTO.getId()+"���˻�ID:"+inEvent.getAccountDTO().getAccountID()+"���˻�����"+inEvent.getAccountDTO().getAccountName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
				
	    }
	    else
	    	
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�½��˻�", "�½��˻�,������ID��"+csiDTO.getId()+"���˻�ID:"+inEvent.getAccountDTO().getAccountID()+"���˻�����"+inEvent.getAccountDTO().getAccountName(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		
	}
	/**
	 * ��ʼ��ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(AccountEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		String description="";
		String action="";
		switch (event.getActionType()) {
			case EJBEvent.UPDATEACCOUNTINFO:
				description="���¿ͻ��˻���Ϣ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			case EJBEvent.CLOSECUSTOMERACCOUNT:	
				description="�ͻ��˻�����";
			    action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			case EJBEvent.OPENACCOUNTFORCUSTOMER:
				description="�ͻ������˻�";
		        action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    			break;
    		case EJBEvent.PRESAVE:
				description="�ͻ�Ԥ��";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    			break;
    		case EJBEvent.PAYBYBILL:
				description="�ͻ�֧���˵�";
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
		 //����֧���ʵ�
		 batchPayInvoice(inEvent);
		 //����Ԥ��
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
			 // ����ϵͳ��־
			 AccountDTO acctDTO=BusinessUtility.getAcctDTOByAcctID(dto.getAcctID());
			 String cashBalanceDesc = (acctDTO.getCashDeposit() != account
					                     .getCashDeposit()) ? (";Ԥ���ֽ�:" + (account.getCashDeposit() - acctDTO
					                     .getCashDeposit()))
					                      : "";
			 String tokenBalanceDesc = (acctDTO.getTokenDeposit() != account
					                     .getTokenDeposit()) ? (";Ԥ���������:" + (account.getTokenDeposit() - acctDTO
					                     .getTokenDeposit()))
					                     : "";
			 SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
					PublicService.getCurrentOperatorID(serviceContext), acctDTO.getCustomerID(), 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "Ԥ����", "Ԥ����,������ID��"+csiDTO.getId()
					+"���˻�ID:"+acctDTO.getAccountID()
					+"���˻�����"+acctDTO.getAccountName()
					+cashBalanceDesc+tokenBalanceDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP); 
		 }
	}
	
	/*
	 * ���ʻ�������֧���ʵ���Ԥ��
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
		 //����߼�
		 Iterator checkItr =invoiceList.iterator();
		 while (checkItr.hasNext()){
			 InvoiceDTO iDto=(InvoiceDTO)checkItr.next();
			 InvoiceDTO checkInvoiceDto = BusinessUtility.getInvoiceDTOByInvoiceNo(iDto.getInvoiceNo());
			 if (CommonConstDefinition.INVOICE_STATUS_PAID.equals(checkInvoiceDto.getStatus())){
				 throw new ServiceException("�ʵ���:"+checkInvoiceDto.getInvoiceNo()+"��֧��,������֧��!");
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
		     throw new ServiceException("���ʻ�������δ������ʵ�,֧�����ܴ�����ʾ���ʵ����!");
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
		 // �����ʵ�
		 while(itr.hasNext()){
			  InvoiceDTO iDto=(InvoiceDTO)itr.next();
			  FeeService.payInvoice(csiDTO,acctDto,iDto,actionType,getFeeListForBatchPay(iDto),getPayListForBatchPay(payCol,iDto),null,inEvent.getOperatorID());
				
			  SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
		    			PublicService.getCurrentOperatorID(serviceContext), csiDTO.getCustomerID(), 
		    			SystemLogRecorder.LOGMODULE_CUSTSERV, "����֧���ʵ�", "������ID��"+csiDTO.getId()+";�˻�ID��"+acctDto.getAccountID()+";�ʻ�����"+acctDto.getAccountName()+";�ʵ��ţ�"+iDto.getInvoiceNo()+";֧����"+iDto.getPayAmount(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		 }	
		csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		
        // ����Ԥ��
		if (prePayCol.isEmpty()) return;
		CustServiceInteractionDTO prepCsiDTO=new CustServiceInteractionDTO();
		prepCsiDTO.setAccountID(acctDto.getAccountID());
		prepCsiDTO.setCustomerID(acctDto.getCustomerID());
		prepCsiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_AD);
		prepCsiDTO.setBillCollectionMethod(CommonConstDefinition.CSI_PAYMENT_OPTION_IM);
		csiService.createCustServiceInteraction(prepCsiDTO);
	    Account account =FeeService.prePayment(prepCsiDTO,prePayCol,inEvent.getOperatorID());
	    csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);

	    // ����ϵͳ��־
	    AccountDTO acctDTO=BusinessUtility.getAcctDTOByAcctID(inEvent.getAccountDTO().getAccountID());
	    String cashBalanceDesc = (acctDTO.getCashDeposit() != account
			                     .getCashDeposit()) ? (";Ԥ���ֽ�:" + (account.getCashDeposit() - acctDTO
			                     .getCashDeposit()))
			                      : "";
	    String tokenBalanceDesc = (acctDTO.getTokenDeposit() != account
			                     .getTokenDeposit()) ? (";Ԥ���������:" + (account.getTokenDeposit() - acctDTO
			                     .getTokenDeposit()))
			                     : "";
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
			PublicService.getCurrentOperatorID(serviceContext), acctDto.getCustomerID(), 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "Ԥ����", "Ԥ����,������ID��"+prepCsiDTO.getId()
			+"���˻�ID:"+acctDTO.getAccountID()
			+"���˻�����"+acctDTO.getAccountName()
			+cashBalanceDesc+tokenBalanceDesc, 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP); 
	}	
	/**
	 * �����˻�Ԥ��
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void batchDepositInAdvance(AccountEJBEvent inEvent) throws ServiceException{
		//��ʼ��ServiceContext
		ServiceContext serviceContext=initServiceContext(inEvent);
		//ȡ������������Ҫ�Ĳ�����
		List batchPreSaveParaList = inEvent.getBatchPreSaveParaList();
		List txtAccountIDList = (List)batchPreSaveParaList.get(0);
		List txtCustomerIDList = (List)batchPreSaveParaList.get(1);
		List PayList = (List)batchPreSaveParaList.get(2);
		
		//��������Ƿ��ܶ��˻�����Ԥ�����
		/*
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkOperationAccount(inEvent);
		**/
		
		
		//ѭ����������Ԥ��
		for(int i=0;i<txtAccountIDList.size();i++)
		{
		//ȡ�ÿͻ�id
			int customerID =(new Integer(txtCustomerIDList.get(i)+"")).intValue();
			int AccountID =(new Integer(txtAccountIDList.get(i)+"")).intValue();
			//ȡ��������Ϣ
			CustServiceInteractionDTO csiDTO=new CustServiceInteractionDTO();
			csiDTO.setCustomerID(customerID);
			csiDTO.setAccountID(AccountID);
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_AD);
			csiDTO.setBillCollectionMethod(CommonConstDefinition.CSI_PAYMENT_OPTION_IM);
			//����������Ϣ
	    	CSIService cSIService=new CSIService(serviceContext);
	    	cSIService.createCustServiceInteraction(csiDTO);
			//������õ����ݴ�׷��
	    	Account account =FeeService.prePayment(csiDTO,(List)PayList.get(i),operatorID);
	    	cSIService.updateCSIPayStatus(serviceContext,csiDTO.getPaymentStatus());
           //����ϵͳ��־
			AccountDTO acctDTO=BusinessUtility.getAcctDTOByAcctID(AccountID);
			String cashBalanceDesc = (acctDTO.getCashDeposit() != account
					.getCashDeposit()) ? (";Ԥ���ֽ�:" + (account.getCashDeposit() - acctDTO
					.getCashDeposit()))
					: "";
			String tokenBalanceDesc = (acctDTO.getTokenDeposit() != account
					.getTokenDeposit()) ? (";Ԥ���������:" + (account.getTokenDeposit() - acctDTO
					.getTokenDeposit()))
					: "";
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "Ԥ����", "Ԥ����,������ID��"+csiDTO.getId()
					+"���˻�ID:"+acctDTO.getAccountID()
					+"���˻�����"+acctDTO.getAccountName()
					+cashBalanceDesc+tokenBalanceDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}	
		//this.response.setPayload(new Integer(csiDTO.getId()));
	}
}
