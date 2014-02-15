package com.dtv.oss.dto.wrap.customer;

import java.io.Serializable;
import java.sql.Timestamp;

import com.dtv.oss.dto.AcctItemTypeDTO;
import com.dtv.oss.dto.FinanceSetOffMapDTO;

public class FinanceSetoffMap2AcctItemTypeWrap implements Serializable {

	private FinanceSetOffMapDTO setOffDTO=null;
	private AcctItemTypeDTO acctTypeDTO=null;
	
	
	//֧����¼�Ĺ�����
	private String paymentCommonSettingName="";
	//֧����¼�Ĺ���ֵ
	private String payCommonSettingValue="";
	//֧����Ŀ������
	private String payAcctItemType="";	
	//֧�����
	private double paymentAmount=0;
	//֧������ʱ��
	private Timestamp paymentTime=null;
	
	//���ü�¼�Ĺ�����
	private String feeCommonSettingName="";
	//���ü�¼�Ĺ���ֵ
	private String feeCommonSettingValue="";
	//������Ŀ������
	private String feeAcctItemType="";
	//���ý��
	private double feeAmount=0;
	//���ò���ʱ��
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
