package com.dtv.oss.service.ejbevent.csr;

import java.util.Collection;

import com.dtv.oss.dto.AccountAdjustmentDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class AccountAdjustEJBEvent extends AbstractEJBEvent {

	public static final int ACCOUNT_ADJUST_SPECIALFEE =0;
	public static final int ACCOUNT_ADJUST_FEE=1;
	public static final int ACCOUNT_ADJUST_PAYMENT=2;
	public static final int ACCOUNT_ADJUST_PREPAYMENTDEDUCTION=3;
	public static final int ACCOUNT_ADJUST_PREPAYMENT=4;
	public static final int BILL_ADJUST_FEE=5;
	public static final int BILL_ADJUST_PAYMENT=6;
	public static final int BILL_ADJUST_PREPAYMENTDEDUCTION=7;
	public static final int BILL_ADJUST_PREPAYMENT=8;
	public static final int PROBLEM_ADJUST_FEE=9;
	public static final int PROBLEM_ADJUST_PAYMENT=10;
	public static final int CSI_ADJUST_FEE=11;
	public static final int CSI_ADJUST_PAYMENT=12;
	
	private boolean doPost;
	public AccountAdjustEJBEvent() {
	}

	public AccountAdjustEJBEvent(int actionType) {
		this.actionType = actionType;
	}
	
	private AccountAdjustmentDTO accountAdjustmentDTO;
	private AccountItemDTO accountItemDTO;
	private PaymentRecordDTO paymentRecordDTO;
	private PrepaymentDeductionRecordDTO prepaymentDeductionRecordDTO;
	
	public AccountAdjustmentDTO getAccountAdjustmentDTO() {
		return accountAdjustmentDTO;
	}
	public void setAccountAdjustmentDTO(AccountAdjustmentDTO accountAdjustmentDTO) {
		this.accountAdjustmentDTO = accountAdjustmentDTO;
	}
	public AccountItemDTO getAccountItemDTO() {
		return accountItemDTO;
	}
	public void setAccountItemDTO(AccountItemDTO accountItemDTO) {
		this.accountItemDTO = accountItemDTO;
	}
	public PaymentRecordDTO getPaymentRecordDTO() {
		return paymentRecordDTO;
	}
	public void setPaymentRecordDTO(PaymentRecordDTO paymentRecordDTO) {
		this.paymentRecordDTO = paymentRecordDTO;
	}
	public PrepaymentDeductionRecordDTO getPrepaymentDeductionRecordDTO() {
		return prepaymentDeductionRecordDTO;
	}
	public void setPrepaymentDeductionRecordDTO(
			PrepaymentDeductionRecordDTO prepaymentDeductionRecordDTO) {
		this.prepaymentDeductionRecordDTO = prepaymentDeductionRecordDTO;
	}
	
}
