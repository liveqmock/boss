/*
 * Created on 2006-1-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.dto.custom;
import com.dtv.oss.dto.*;
/**
 * @author 240910y
 * µ÷ÕÊ·ÑÓÃ
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FeeAdjustmentWrapDTO implements java.io.Serializable {
	
	/**
	 * 
	 */
	public FeeAdjustmentWrapDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FeeAdjustmentWrapDTO(AccountItemDTO accountItemDTO,AccountAdjustmentDTO accountAdjustmentDTO) {
		this.accountItemDTO = accountItemDTO;
		this.accountAdjustmentDTO = accountAdjustmentDTO;
	}
	private AccountItemDTO accountItemDTO=null;
	private AccountAdjustmentDTO accountAdjustmentDTO=null;
	public void setAccountItemDTO(AccountItemDTO accountItemDTO){
		this.accountItemDTO = accountItemDTO;
	}
	
	public AccountItemDTO getAccountItemDTO(){
		return this.accountItemDTO;
	}
	public void setAccountAdjustmentDTO(AccountAdjustmentDTO accountAdjustmentDTO){
		this.accountAdjustmentDTO = accountAdjustmentDTO;
	}
	
	public AccountAdjustmentDTO getAccountAdjustmentDTO(){
		return this.accountAdjustmentDTO;
	}
}
