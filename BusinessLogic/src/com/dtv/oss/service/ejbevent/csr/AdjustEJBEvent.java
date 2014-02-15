package com.dtv.oss.service.ejbevent.csr;

import java.util.Collection;
import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class AdjustEJBEvent extends AbstractEJBEvent {
	//true:ʵ���ύ ;false:���ύ��ֻ��Ԥ��
	private boolean doPost;
	public AdjustEJBEvent(int actionType) {
		this.actionType = actionType;
	}
	//���ʷ�ʽ��ȡֵ�������ˣ�D��δ���ˣ�W��������Ҫ��ֵ��ֱ������Ϊnull�Ϳ����ˣ���
	private String adjustMethod;
	private int customerID;
	private int accountID;
	private int id;//����id�����Ǳ��޵�id�������˵���
	public void setID(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public int getAccountID() {
		return accountID;
	}
	//����
	private Collection  feeAdjustmentWrapDtoList;
	//֧��
	private Collection  paymentAdjustWrapDtoList;
	//Ԥ��ֿ�
	private Collection  preDeductionAdjustWrapDtoList;
	
	public boolean isDoPost() {
		return doPost;
	}
	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
	}
	
	public String getAdjustMethod() {
		return this.adjustMethod;
	}
	public void setAdjustMethod(String adjustMethod) {
		this.adjustMethod = adjustMethod;
	}
	public void setFeeAdjustmentWrapDtoList(Collection feeAdjustmentWrapDtoList){
		this.feeAdjustmentWrapDtoList =feeAdjustmentWrapDtoList;
	}
	
	public Collection getFeeAdjustmentWrapDtoList(){
		return  this.feeAdjustmentWrapDtoList;
	}
	
	public void  setPaymentAdjustWrapDtoList(Collection paymentAdjustWrapDtoList){
		this.paymentAdjustWrapDtoList =paymentAdjustWrapDtoList;
	}
	
	public Collection getPaymentAdjustWrapDtoList(){
		return paymentAdjustWrapDtoList;
	}
	public void  setPreDeductionAdjustWrapDtoList(Collection preDeductionAdjustWrapDtoList){
		this.preDeductionAdjustWrapDtoList =preDeductionAdjustWrapDtoList;
	}
	
	public Collection getPreDeductionAdjustWrapDtoList(){
		return preDeductionAdjustWrapDtoList;
	}

}
