/*
 * Created on 2004-8-9
 *
 * @author Mac Wang
 */
package com.dtv.oss.service.ejbevent.csr;

import java.util.*;
import com.dtv.oss.dto.*;

public class BookEJBEvent extends OpenAccountGeneralEJBEvent {
	//�¿ͻ���Ϣ
	private NewCustomerInfoDTO newCustInfo;
	//�¿ͻ��˻���Ϣ
	private NewCustAccountInfoDTO newCustAcctInfo;
	//�ͻ���ַ
	private AddressDTO  custAddressDTO;
	//�˻���ַ
	private AddressDTO  acctAddressDTO;
	//�ͻ��г���Ϣ
	private Collection newCustMarketInfoList;
	//��Ʒ����
	private Map productPropertyMap;
	//Ѻ����Ϣ
    private double  forcedDeposit ;
    //�Ƿ����
    private boolean isAgent;
    //��ת����
    private int nextOrgID;
    //�ն���Ϣ
    private String catvID;
    private int addPortNum;
    private CatvTerminalDTO catvTerminalDTO; 
    //�ͻ����Ʒѹ���
    private Map customerBillingRuleMap;
    
	//	for booking
	public BookEJBEvent(int actionType) {
		this.actionType = actionType;
	}
	public NewCustAccountInfoDTO getNewCustAcctInfo() {
		return newCustAcctInfo;
	}
	public void setNewCustAcctInfo(NewCustAccountInfoDTO newCustAcctInfo) {
		this.newCustAcctInfo = newCustAcctInfo;
	}
	public NewCustomerInfoDTO getNewCustInfo() {
		return newCustInfo;
	}
	public void setNewCustInfo(NewCustomerInfoDTO newCustInfo) {
		this.newCustInfo = newCustInfo;
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
    
    public double getForcedDeposit() {
		return forcedDeposit;
	}
	public void setForcedDeposit(double forcedDeposit) {
		this.forcedDeposit = forcedDeposit;
	}
	
	public boolean isAgent() {
        return isAgent;
    }

    public void setAgent(boolean agent) {
        isAgent = agent;
    }
    /**
     * @return Returns the newCustMarketInfoList.
     */
    public Collection getNewCustMarketInfoList() {
        return newCustMarketInfoList;
    }
    /**
     * @param newCustMarketInfoList The newCustMarketInfoList to set.
     */
    public void setNewCustMarketInfoList(Collection newCustMarketInfoList) {
        this.newCustMarketInfoList = newCustMarketInfoList;
    }
	/**
	 * @return Returns the productPropertyMap.
	 */
	public Map getProductPropertyMap() {
		return productPropertyMap;
	}
	/**
	 * @param productPropertyMap The productPropertyMap to set.
	 */
	public void setProductPropertyMap(Map productPropertyMap) {
		this.productPropertyMap = productPropertyMap;
	}
	
	/**
	 * @return Returns the nextOrgID.
	 */
	public int getNextOrgID() {
		return nextOrgID;
	}

	/**
	 * @param nextOrgID The nextOrgID to set.
	 */
	public void setNextOrgID(int nextOrgID) {
		this.nextOrgID = nextOrgID;
	}
	public int getAddPortNum() {
		return addPortNum;
	}
	public void setAddPortNum(int addPortNum) {
		this.addPortNum = addPortNum;
	}
	public String getCatvID() {
		return catvID;
	}
	public void setCatvID(String catvID) {
		this.catvID = catvID;
	}
	
	public CatvTerminalDTO getCatvTerminalDTO() {
		return catvTerminalDTO;
	}
	public void setCatvTerminalDTO(CatvTerminalDTO catvTerminalDTO) {
		this.catvTerminalDTO = catvTerminalDTO;
	}
	/**
	 * @return the customerBillingRuleMap
	 */
	public Map getCustomerBillingRuleMap() {
		return customerBillingRuleMap;
	}
	/**
	 * @param customerBillingRuleMap the customerBillingRuleMap to set
	 */
	public void setCustomerBillingRuleMap(Map customerBillingRuleMap) {
		this.customerBillingRuleMap = customerBillingRuleMap;
	}

}
