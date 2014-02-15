/**
 * 
 */
package com.dtv.oss.dto.custom;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author 240910y
 *
 */
public class CommonBusinessParamDTO implements Serializable {

	/**
	 * 
	 * 在构造方法operatorID设置明确的值,其他的可以不用设置
	 */
	public CommonBusinessParamDTO(int operatorID) {  
		this.operatorID = operatorID;
	}
	public CommonBusinessParamDTO() {  
	}
	public CommonBusinessParamDTO(String customerType,String opensourcetype,String  csiType,int operatorID) {
		this.customerType = customerType;
		this.opensourcetype = opensourcetype; 
		this.csiType = csiType;
		this.operatorID = operatorID;
	}
	public CommonBusinessParamDTO(int customerID,int  accountID,int  serviceaccountID,int operatorID) {
		this.customerID = customerID;
		this.accountID = accountID; 
		this.serviceaccountID = serviceaccountID;
		this.operatorID = operatorID;
	}
	//客户类型
	private String customerType; 
	//客户来源渠道
	private String opensourcetype; 
	//受理单类型
	private String  csiType     ;
	//客户id
	private int customerID;
	//帐户id
	private int  accountID ;
	//业务帐户id
	private int  serviceaccountID  ;
	//产品列表
	private Collection productList   ; 
	//操作员id
	/**
	 * 
	 */
	private int operatorID;
	
	//用于支持可选包,在包里面选择的可选产品放到里面(key=packageID,value=Collection(产品id))
	private HashMap optioProductSelectMap;
	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the accountID
	 */
	public int getAccountID() {
		return accountID;
	}
	/**
	 * @param accountID the accountID to set
	 */
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	/**
	 * @return the csiType
	 */
	public String getCsiType() {
		return csiType;
	}
	/**
	 * @param csiType the csiType to set
	 */
	public void setCsiType(String csiType) {
		this.csiType = csiType;
	}
	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	/**
	 * @return the opensourcetype
	 */
	public String getOpensourcetype() {
		return opensourcetype;
	}
	/**
	 * @param opensourcetype the opensourcetype to set
	 */
	public void setOpensourcetype(String opensourcetype) {
		this.opensourcetype = opensourcetype;
	}
	/**
	 * @return the optioProductSelectMap
	 */
	public HashMap getOptioProductSelectMap() {
		return optioProductSelectMap;
	}
	/**
	 * @param optioProductSelectMap the optioProductSelectMap to set
	 */
	public void setOptioProductSelectMap(HashMap optioProductSelectMap) {
		this.optioProductSelectMap = optioProductSelectMap;
	}
	/**
	 * @return the productList
	 */
	public Collection getProductList() {
		return productList;
	}
	/**
	 * @param productList the productList to set
	 */
	public void setProductList(Collection productList) {
		this.productList = productList;
	}
	/**
	 * @return the serviceaccountID
	 */
	public int getServiceaccountID() {
		return serviceaccountID;
	}
	/**
	 * @param serviceaccountID the serviceaccountID to set
	 */
	public void setServiceaccountID(int serviceaccountID) {
		this.serviceaccountID = serviceaccountID;
	}
	/**
	 * @return the operatorID
	 */
	public int getOperatorID() {
		return operatorID;
	}
	/**
	 * @param operatorID the operatorID to set
	 */
	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}
 

	public String toString(){
		StringBuffer buff=new StringBuffer();
		
		buff.append("customerType:" + customerType);
		buff.append(",opensourcetype:" + opensourcetype);
		buff.append(",csiType:" + csiType);
		buff.append(",customerID:" + customerID);
		buff.append(",accountID:" + accountID);
		buff.append(",serviceaccountID:" + serviceaccountID);
		buff.append(",productList:" + productList);
		buff.append(",operatorID:" + operatorID);
		buff.append(",optioProductSelectMap:" + optioProductSelectMap);
		
		return buff.toString();
	}
}
