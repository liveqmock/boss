/*
 * Created on 2004-8-9
 *
 * @author Mac Wang
 */
package com.dtv.oss.service.ejbevent.csr;

import java.util.*;
import com.dtv.oss.dto.*;

public class BookEJBEvent extends OpenAccountGeneralEJBEvent {
	//新客户信息
	private NewCustomerInfoDTO newCustInfo;
	//新客户账户信息
	private NewCustAccountInfoDTO newCustAcctInfo;
	//客户地址
	private AddressDTO  custAddressDTO;
	//账户地址
	private AddressDTO  acctAddressDTO;
	//客户市场信息
	private Collection newCustMarketInfoList;
	//产品属性
	private Map productPropertyMap;
	//押金信息
    private double  forcedDeposit ;
    //是否代理
    private boolean isAgent;
    //流转部门
    private int nextOrgID;
    //终端信息
    private String catvID;
    private int addPortNum;
    private CatvTerminalDTO catvTerminalDTO; 
    //客户化计费规则
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
