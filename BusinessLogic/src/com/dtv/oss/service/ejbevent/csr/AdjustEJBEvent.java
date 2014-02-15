package com.dtv.oss.service.ejbevent.csr;

import java.util.Collection;
import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class AdjustEJBEvent extends AbstractEJBEvent {
	//true:实际提交 ;false:不提交，只做预判
	private boolean doPost;
	public AdjustEJBEvent(int actionType) {
		this.actionType = actionType;
	}
	//调帐方式：取值（已销账：D；未销账：W；余额：不需要传值，直接设置为null就可以了）；
	private String adjustMethod;
	private int customerID;
	private int accountID;
	private int id;//受理单id或者是报修单id或者是账单号
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
	//费用
	private Collection  feeAdjustmentWrapDtoList;
	//支付
	private Collection  paymentAdjustWrapDtoList;
	//预存抵扣
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
