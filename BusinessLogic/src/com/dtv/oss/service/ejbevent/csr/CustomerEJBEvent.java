/*
 * Created on 2005-10-11
 *
 * @author Stone Liang
 */
package com.dtv.oss.service.ejbevent.csr;

//import java.util.Collection;

import java.util.ArrayList;
import java.util.Collection;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.*;;

public class CustomerEJBEvent extends CsrAbstractEJbevent {
    public CustomerEJBEvent(int actionType) {
		this.actionType = actionType;
	}
    //�ͻ�Э��
    private  Collection protocolList;
	//�ͻ���ַ
	private AddressDTO  custAddressDTO;
	//�˻���ַ
	private AddressDTO  acctAddressDTO;
	//�Ƿ񴴽�����
	private boolean createJobCard;
	//�����б� ��collection of FT DTO��
	private Collection csiFeeList;		
	//�����б�collection of  FT DTO��
	private Collection csiPaymentList =new ArrayList();	
	
	private Collection csiPrePaymentDeductionList =new ArrayList(); //collection of FT DTO
	//�˻���Ϣ
	private AccountDTO accountDTO  ;
	//�ͻ��г���Ϣ
	private Collection custMarketInfoList;
	//�ͻ���Ϣ
	private CustomerDTO customerDTO ;
	//���������Ƿ�����˷ѵ�Ԥ����
	private 	boolean isReadyForeturnFee;
	public Collection getCustMarketInfoList() {
		return custMarketInfoList;
	}
	public void setCustMarketInfoList(Collection custMarketInfoList) {
		this.custMarketInfoList = custMarketInfoList;
	}
	
	public AccountDTO getAccountDTO() {
		return accountDTO;
	}
	public void setAccountDTO(AccountDTO accountDTO) {
		this.accountDTO = accountDTO;
	}
	
	
	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}
	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}
	
	/**
	 * @return Returns the acctAddrDto.
	 */
	public AddressDTO getAcctAddressDTO() {
		return acctAddressDTO;
	}
	/**
	 * @param acctAddrDto The acctAddrDto to set.
	 */
	public void setAcctAddrDto(AddressDTO acctAddressDTO) {
		this.acctAddressDTO = acctAddressDTO;
	}
	/**
	 * @return Returns the custAddrDto.
	 */
	public AddressDTO getCustAddressDTO() {
		return custAddressDTO;
	}
	/**
	 * @param custAddrDto The custAddrDto to set.
	 */
	public void setCustAddrDto(AddressDTO custAddressDTO) {
		this.custAddressDTO = custAddressDTO;
	}
	public boolean getCreateJobCard() {
		return createJobCard;
	}
	public void setCreateJobCard(boolean createJobCard) {
		this.createJobCard = createJobCard;
	}
	/**
	 * @return
	 */
	public Collection getCsiFeeList() {
		return csiFeeList;
	}

	/**
	 * @return
	 */
	public Collection getCsiPaymentList() {
		return csiPaymentList;
	}
	
	public Collection getCsiPrePaymentDeductionList() {
		return csiPrePaymentDeductionList;
	}

	/**
	 * @param collection
	 */
	public void setCsiFeeList(Collection collection) {
		csiFeeList = collection;
	}

	/**
	 * @param collection
	 */
	public void setCsiPaymentList(Collection collection) {
		csiPaymentList = collection;
	}
	
	public void setCsiPrePaymentDeductionList(Collection csiPrePaymentDeductionList) {
		this.csiPrePaymentDeductionList = csiPrePaymentDeductionList;
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
	/**
	 * @return the protocolList
	 */
	public Collection getProtocolList() {
		return protocolList;
	}
	/**
	 * @param protocolList the protocolList to set
	 */
	public void setProtocolList(Collection protocolList) {
		this.protocolList = protocolList;
	}
	

}
