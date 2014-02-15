/*
 * Created on 2007-7-16
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.ejbevent.catv;

import java.util.ArrayList;
import java.util.Collection;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CatvJobCardEJBEvent extends AbstractEJBEvent{
	   private JobCardProcessDTO jobcardProcessDto;
	   private JobCardDTO jobCardDto;
	   private boolean isSuccess;
	   private int accountID;
	   private int nextOrgID;

	   private AddressDTO addressDto;
    
	   //费用列表
	   private Collection feeList;
	   //付费列表
	   private Collection paymentList =new ArrayList();
	   //预存抵扣
	   private Collection csiPrePaymentDeductionList =new ArrayList();
	
	   //private FinancialTransactionDTO feeDto;
	   //private FinancialTransactionDTO paymentDto;
	   private Collection diagnosisInfoDtos;
    
	   private Collection materialUsage;
	   //true:实际提交 ;false:不提交，只做预判
	   private boolean doPost;
	   
	   //批量处理 begin
		CommonQueryConditionDTO  commonConditionDto;
		//批量处理 end
	
	   /**
		* @return Returns the accoutID.
		*/
	   public int getAccountID() {
		   return accountID;
	   }
	   /**
		* @param accoutID The accoutID to set.
		*/
	   public void setAccountID(int accountID) {
		   this.accountID = accountID;
	   }
	   public boolean isDoPost() {
		   return doPost;
	   }
	   public void setDoPost(boolean doPost) {
		   this.doPost = doPost;
	   }
	
	   public JobCardDTO getJobCardDto() {
		   return jobCardDto;
	   }
	   public void setJobCardDto(JobCardDTO jcDto) {
		   this.jobCardDto = jcDto;
	   }
	   public JobCardProcessDTO getJobcardProcessDto() {
		   return jobcardProcessDto;
	   }
	   public void setJobcardProcessDto(JobCardProcessDTO jobcardProcessDto) {
		   this.jobcardProcessDto = jobcardProcessDto;
	   }
	   public boolean isSuccess() {
		   return isSuccess;
	   }
	   public void setSuccess(boolean isSuccess) {
		   this.isSuccess = isSuccess;
	   }
	   public Collection getDiagnosisInfoDtos() {
		   return diagnosisInfoDtos;
	   }
	   public void setDiagnosisInfoDtos(Collection diagnosisInfoDtos) {
		   this.diagnosisInfoDtos = diagnosisInfoDtos;
	   }
	   public void setFeeList(Collection feeList) {
		   this.feeList = feeList;
	   }
	   public void setPaymentList(Collection paymentList) {
		   this.paymentList = paymentList;
	   }
	   public Collection getFeeList() {
		   return feeList;
	   }
	   public Collection getPaymentList() {
		   return paymentList;
	   }
	
	   public Collection getCsiPrePaymentDeductionList() {
		   return csiPrePaymentDeductionList;
	   }
	   public void setCsiPrePaymentDeductionList(Collection csiPrePaymentDeductionList) {
		   this.csiPrePaymentDeductionList = csiPrePaymentDeductionList;
	   }
	   public Collection getMaterialUsage() {
		   return materialUsage;
	   }
	   public void setMaterialUsage(Collection materialUsage) {
		   this.materialUsage = materialUsage;
	   }
	/**
	 * @return Returns the nextOrgID.
	 */
	public int getNextOrgID() {
		return nextOrgID;
	}

	/**
	 * @param nextOrgID The nextOrgID to set.
	 */
	public void setNextOrgID(int nextOrgID) {
		this.nextOrgID = nextOrgID;
	}


	/**
	 * @return Returns the addressDto.
	 */
	public AddressDTO getAddressDto() {
		return addressDto;
	}

	/**
	 * @param addressDto The addressDto to set.
	 */
	public void setAddressDto(AddressDTO addressDto) {
		this.addressDto = addressDto;
	}
	public CommonQueryConditionDTO getCommonConditionDto() {
		return commonConditionDto;
	}
	public void setCommonConditionDto(CommonQueryConditionDTO commonConditionDto) {
		this.commonConditionDto = commonConditionDto;
	}

}
