package com.dtv.oss.dto.custom;

import java.io.Serializable;

import com.dtv.oss.dto.AccountAdjustmentDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;

public class PreDeductionAdjustWrapDTO implements Serializable {
	/**
	 * 
	 */
	public PreDeductionAdjustWrapDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PreDeductionAdjustWrapDTO(PrepaymentDeductionRecordDTO preDeductionDTO,AccountAdjustmentDTO accountAdjustmentDTO) {
		this.preDeductionDTO = preDeductionDTO;
		this.accountAdjustmentDTO = accountAdjustmentDTO;
	}
	private PrepaymentDeductionRecordDTO preDeductionDTO=null;
	private AccountAdjustmentDTO accountAdjustmentDTO=null;
	
	
	public void setAccountAdjustmentDTO(AccountAdjustmentDTO accountAdjustmentDTO){
		this.accountAdjustmentDTO = accountAdjustmentDTO;
	}
	
	public AccountAdjustmentDTO getAccountAdjustmentDTO(){
		return this.accountAdjustmentDTO;
	}
	public void setPrepaymentDTO(PrepaymentDeductionRecordDTO preDeductionDTO){
		this.preDeductionDTO = preDeductionDTO;
	}
	
	public PrepaymentDeductionRecordDTO getPreDeductionDTO(){
		return this.preDeductionDTO;
	}
}
