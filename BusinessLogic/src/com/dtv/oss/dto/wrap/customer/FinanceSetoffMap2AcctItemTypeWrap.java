package com.dtv.oss.dto.wrap.customer;

import java.io.Serializable;
import java.sql.Timestamp;

import com.dtv.oss.dto.AcctItemTypeDTO;
import com.dtv.oss.dto.FinanceSetOffMapDTO;

public class FinanceSetoffMap2AcctItemTypeWrap implements Serializable {

	private FinanceSetOffMapDTO setOffDTO=null;
	private AcctItemTypeDTO acctTypeDTO=null;
	
	
	//支付记录的公用名
	private String paymentCommonSettingName="";
	//支付记录的公用值
	private String payCommonSettingValue="";
	//支付帐目类型名
	private String payAcctItemType="";	
	//支付金额
	private double paymentAmount=0;
	//支付产生时间
	private Timestamp paymentTime=null;
	
	//费用记录的公用名
	private String feeCommonSettingName="";
	//费用记录的公用值
	private String feeCommonSettingValue="";
	//费用帐目类型名
	private String feeAcctItemType="";
	//费用金额
	private double feeAmount=0;
	//费用产生时间
	private Timestamp feeTime=null;
	
	public FinanceSetoffMap2AcctItemTypeWrap(){
		setOffDTO=new FinanceSetOffMapDTO();
		acctTypeDTO=new AcctItemTypeDTO();
	}
	
	
	public String getFeeAcctItemType() {
		return feeAcctItemType;
	}


	public void setFeeAcctItemType(String feeAcctItemType) {
		this.feeAcctItemType = feeAcctItemType;
	}


	public String getPayAcctItemType() {
		return payAcctItemType;
	}


	public void setPayAcctItemType(String payAcctItemType) {
		this.payAcctItemType = payAcctItemType;
	}


	public String getFeeCommonSettingName() {
		return feeCommonSettingName;
	}

	public void setFeeCommonSettingName(String feeCommonSettingName) {
		this.feeCommonSettingName = feeCommonSettingName;
	}

	public String getFeeCommonSettingValue() {
		return feeCommonSettingValue;
	}

	public void setFeeCommonSettingValue(String feeCommonSettingValue) {
		this.feeCommonSettingValue = feeCommonSettingValue;
	}

	public String getPayCommonSettingValue() {
		return payCommonSettingValue;
	}

	public void setPayCommonSettingValue(String payCommonSettingValue) {
		this.payCommonSettingValue = payCommonSettingValue;
	}

	public String getPaymentCommonSettingName() {
		return paymentCommonSettingName;
	}

	public void setPaymentCommonSettingName(String paymentCommonSettingName) {
		this.paymentCommonSettingName = paymentCommonSettingName;
	}

	public FinanceSetoffMap2AcctItemTypeWrap(FinanceSetOffMapDTO dto1, AcctItemTypeDTO dto2){
		this.setOffDTO=dto1;
		this.acctTypeDTO=dto2;
	}
	
	public FinanceSetoffMap2AcctItemTypeWrap(FinanceSetOffMapDTO dto1){
		this.setOffDTO=dto1;
		this.acctTypeDTO=new AcctItemTypeDTO();
	}
	
	public AcctItemTypeDTO getAcctTypeDTO() {
		return acctTypeDTO;
	}

	public void setAcctTypeDTO(AcctItemTypeDTO acctTypeDTO) {
		this.acctTypeDTO = acctTypeDTO;
	}

	public FinanceSetOffMapDTO getSetOffDTO() {
		return setOffDTO;
	}
	
	public void setSetOffDTO(FinanceSetOffMapDTO setOffDTO) {
		this.setOffDTO = setOffDTO;
	}
	
	public double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Timestamp getFeeTime() {
		return feeTime;
	}

	public void setFeeTime(Timestamp feeTime) {
		this.feeTime = feeTime;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Timestamp getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Timestamp paymentTime) {
		this.paymentTime = paymentTime;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
