/*
 * Created on 2006-1-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.dto.custom;

import com.dtv.oss.dto.AccountAdjustmentDTO;
import com.dtv.oss.dto.*;

/**
 * @author 240910y
 * µ÷ÕÊÖ§¸¶
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PaymentAdjustWrapDTO  implements java.io.Serializable {
	/**
	 * 
	 */
	public PaymentAdjustWrapDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PaymentAdjustWrapDTO(PaymentRecordDTO paymentRecordDTO,AccountAdjustmentDTO accountAdjustmentDTO) {
		this.paymentRecordDTO = paymentRecordDTO;
		this.accountAdjustmentDTO = accountAdjustmentDTO;
	}
	private PaymentRecordDTO paymentRecordDTO=null;
	private AccountAdjustmentDTO accountAdjustmentDTO=null;
	public void setPaymentRecordDTO(PaymentRecordDTO paymentRecordDTO){
		this.paymentRecordDTO = paymentRecordDTO;
	}
	
	public PaymentRecordDTO getPaymentRecordDTO(){
		return this.paymentRecordDTO;
	}
	public void setAccountAdjustmentDTO(AccountAdjustmentDTO accountAdjustmentDTO){
		this.accountAdjustmentDTO = accountAdjustmentDTO;
	}
	
	public AccountAdjustmentDTO getAccountAdjustmentDTO(){
		return this.accountAdjustmentDTO;
	}

}
