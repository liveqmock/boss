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
	 * �ڹ��췽��operatorID������ȷ��ֵ,�����Ŀ��Բ�������
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
	//�ͻ�����
	private String customerType; 
	//�ͻ���Դ����
	private String opensourcetype; 
	//��������
	private String  csiType     ;
	//�ͻ�id
	private int customerID;
	//�ʻ�id
	private int  accountID ;
	//ҵ���ʻ�id
	private int  serviceaccountID  ;
	//��Ʒ�б�
	private Collection productList   ; 
	//����Աid
	/**
	 * 
	 */
	private int operatorID;
	
	//����֧�ֿ�ѡ��,�ڰ�����ѡ��Ŀ�ѡ��Ʒ�ŵ�����(key=packageID,value=Collection(��Ʒid))
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
