/*
 * Created on 2005-12-6
 *
 * @author whq
 * 
 * ����������һ���Է��ü���ӿڵķ���ֵ,�������踶��һ���Է����ܽ��,������Ϣ
 */
package com.dtv.oss.service.util.ImmediateFee;

import java.util.*;

public class ImmediateFeeList implements java.io.Serializable{
  
  //�踶��һ���Է����ܽ��
  private double totalValueMustPay;

  //��Ԥ�����ܽ��
  private double totalValueAlreadyPay;

  //��ҪԤ�����Ϣ���ܽ��
  private double totalValuePreDeposit;

  //������ϸ�����ڱ�acctItemList���
  private Collection returnFeeList;
  
  //��Ԥ���Ľ����ϸ, Collection of PaymentRecordDTO
  private Collection paymentList;
  
  //������ϸ, Collection of AccountItemDTO
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