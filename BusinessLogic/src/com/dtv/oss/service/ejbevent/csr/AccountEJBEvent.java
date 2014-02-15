/*
 * Created on 2005-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.csr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dtv.oss.dto.*;


/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountEJBEvent extends CsrAbstractEJbevent {
	public AccountEJBEvent(int actionType) {
		this.actionType = actionType;
	}
	//费用列表 （collection of FT DTO）
	private Collection csiFeeList;		
	//付费列表（collection of  FT DTO）
	private Collection csiPaymentList =new ArrayList();	
	//预存抵扣列表
	private Collection csiPrePaymentDeductionList =new ArrayList(); //collection of FT DTO
	//账户地址
	private AddressDTO  acctAddressDTO;
	//账户信息
	private AccountDTO accountDTO  ;
	//账单号
	private InvoiceDTO invoiceDTO ;
	//客户id
	private int customerID ;
	
	private int csiID;
	
	//账户地址集合
	private Collection acctAddressDTOList;
	
	//账户信息集合
	private Collection accountDTOList;
	//用来决定是否进行退费的预处理
	private 	boolean isReadyForeturnFee;
	
   //	批量预存参数
	private List batchPreSaveParaList;
	public InvoiceDTO getInvoiceDTO() {
		return this.invoiceDTO;
	}
	public void setInvoiceDTO(InvoiceDTO invoiceDTO) {
		this.invoiceDTO = invoiceDTO;
	}
	
	public int getCustomerID() {
		return this.customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return
	 */
	public Collection getCsiFeeList() {
		return this.csiFeeList;
	}

	/**
	 * @return
	 */
	public Collection getCsiPaymentList() {
		return this.csiPaymentList;
	}
	
	public Collection getCsiPrePaymentDeductionList() {
		return csiPrePaymentDeductionList;
	}

	/**
	 * @param collection
	 */
	public void setCsiFeeList(Collection collection) {
		this.csiFeeList = collection;
	}

	/**
	 * @param collection
	 */
	public void setCsiPaymentList(Collection collection) {
		this.csiPaymentList = collection;
	}
	
	public void setCsiPrePaymentDeductionList(Collection csiPrePaymentDeductionList) {
		this.csiPrePaymentDeductionList = csiPrePaymentDeductionList;
	}
	/**
	 * @return Returns the acctAddrDto.
	 */
	public AddressDTO getAcctAddressDTO() {
		return this.acctAddressDTO;
	}
	/**
	 * @param acctAddrDto The acctAddrDto to set.
	 */
	public void setAcctAddrDto(AddressDTO acctAddressDTO) {
		this.acctAddressDTO = acctAddressDTO;
	}
	public AccountDTO getAccountDTO() {
		return this.accountDTO;
	}
	public void setAccountDTO(AccountDTO accountDTO) {
		this.accountDTO = accountDTO;
	}
	
	public Collection getAccountDTOList() {
		return accountDTOList;
	}
	public void setAccountDTOList(Collection accountDTOList) {
		this.accountDTOList = accountDTOList;
	}
	
	public Collection getAcctAddressDTOList() {
		return acctAddressDTOList;
	}
	public void setAcctAddressDTOList(Collection acctAddressDTOList) {
		this.acctAddressDTOList = acctAddressDTOList;
	}
	
	public int getCsiID() {
		return csiID;
	}
	public void setCsiID(int csiID) {
		this.csiID = csiID;
	}
	/**
	 * @return the isReadyForeturnFee
	 */
	public boolean isReadyForeturnFee() {
		return isReadyForeturnFee;
	}
	/**
	 * @param isReadyForeturnFee the isReadyForeturnFee to set
	 */
	public void setReadyForeturnFee(boolean isReadyForeturnFee) {
		this.isReadyForeturnFee = isReadyForeturnFee;
	}
	
	public List getBatchPreSaveParaList() {
		return batchPreSaveParaList;
	}
	public void setBatchPreSaveParaList(List batchPreSaveParaList) {
		this.batchPreSaveParaList = batchPreSaveParaList;
	}
}
