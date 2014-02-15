/*
 * Created on 2005-12-6
 *
 * @author whq
 * 
 * 该类代表调用一次性费用计算接口的返回值,包括“需付的一次性费用总金额,”等信息
 */
package com.dtv.oss.service.util.ImmediateFee;

import java.util.*;

public class ImmediateFeeList implements java.io.Serializable{
  
  //需付的一次性费用总金额
  private double totalValueMustPay;

  //已预付的总金额
  private double totalValueAlreadyPay;

  //需要预存的信息费总金额
  private double totalValuePreDeposit;

  //费用明细，现在被acctItemList替代
  private Collection returnFeeList;
  
  //已预付的金额明细, Collection of PaymentRecordDTO
  private Collection paymentList;
  
  //费用明细, Collection of AccountItemDTO
  private Collection acctItemList;	
  
  public ImmediateFeeList(){
	this.totalValueMustPay = 0.0f;
	this.totalValueAlreadyPay = 0.0f;
	this.totalValuePreDeposit = 0.0f;
	this.returnFeeList = new ArrayList();
	this.paymentList = new ArrayList();
	this.acctItemList = new ArrayList();
  }

  public ImmediateFeeList(double totalValueMustPay, 
          				  double totalValueAlreadyPay, 
          				  double totalValuePreDeposit, 
          				  Collection returnFeeList, 
          				  Collection paymentList, 
          				  Collection acctItemList){
	this.totalValueMustPay = totalValueMustPay;
	this.totalValueAlreadyPay = totalValueAlreadyPay;
	this.totalValuePreDeposit = totalValuePreDeposit;
	this.returnFeeList = returnFeeList;
	this.paymentList = paymentList;
	this.acctItemList = acctItemList;
  }

  //the getter functions
  public double getTotalValueMustPay(){
	return this.totalValueMustPay;
  }

  public double getTotalValueAlreadyPay(){
	return this.totalValueAlreadyPay;
  }

  public double getTotalValuePreDeposit(){
	return this.totalValuePreDeposit;
  }

  Collection getReturnFeeList(){
	return this.returnFeeList;
  }
  public Collection getPaymentList() {
      return paymentList;
  }
  public Collection getAcctItemList() {
      return acctItemList;
  }
  
  //the setter functions
  public void setTotalValueMustPay(double totalValueMustPay){
	this.totalValueMustPay = totalValueMustPay;
  }

  public void setTotalValueAlreadyPay(double totalValueAlreadyPay){
	this.totalValueAlreadyPay = totalValueAlreadyPay;
  }

  public void setTotalValuePreDeposit(double totalValuePreDeposit){
	this.totalValuePreDeposit = totalValuePreDeposit;
  }

  void setReturnFeeList(Collection returnFeeList){
	this.returnFeeList = returnFeeList;
  }

  public void setPaymentList(Collection paymentList) {
    this.paymentList = paymentList;
  }

  public void setAcctItemList(Collection acctItemList) {
    this.acctItemList = acctItemList;
  }
  
  public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("totalValueMustPay="+totalValueMustPay);
      sb.append(",totalValueAlreadyPay="+totalValueAlreadyPay);
      sb.append(",totalValuePreDeposit="+totalValuePreDeposit);
      sb.append(",acctItemList:"+acctItemList);
      sb.append(",paymentList:"+paymentList);
      return sb.toString();
  }
}