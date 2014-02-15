package com.dtv.oss.dto.wrap.customer;

import java.io.Serializable;
import java.sql.Timestamp;

import com.dtv.oss.dto.AccountAdjustmentDTO;

public class AccountAdjust2ReferRecordWrap implements Serializable {

	private AccountAdjustmentDTO acctAdjDTO=null;
	
	private double referAmount=0;
	
	private Timestamp referRecordTimer=null;
	
	public AccountAdjust2ReferRecordWrap(){
		acctAdjDTO=new AccountAdjustmentDTO();
	}
	
	public AccountAdjustmentDTO getAcctAdjDTO() {
		return acctAdjDTO;
	}


	public void setAcctAdjDTO(AccountAdjustmentDTO acctAdjDTO) {
		this.acctAdjDTO = acctAdjDTO;
	}


	public double getReferAmount() {
		return referAmount;
	}


	public void setReferAmount(double referAmount) {
		this.referAmount = referAmount;
	}


	public Timestamp getReferRecordTimer() {
		return referRecordTimer;
	}


	public void setReferRecordTimer(Timestamp referRecordTimer) {
		this.referRecordTimer = referRecordTimer;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
