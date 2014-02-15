/*
 * Created on 2005-10-25
 *
 * @author whq
 */
package com.dtv.oss.service.ejbevent.work;

import java.util.ArrayList;
import java.util.Collection;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
import com.dtv.oss.dto.*;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

/*
 * 接口说明：
 * 1、安装预约(EJBEvent.CONTACT_USER_FOR_INSTALLATION)
 * 需设置JobCardDTO, JobCardProcessDTO和安装工单ID（通过JobCardDTO的jobCardID）
 * 2、维修预约(EJBEvent.CONTACT_USER_FOR_REPAIR)
 * 需设置JobCardDTO, JobCardProcessDTO和维修工单ID（通过JobCardDTO的jobCardID）
 * 
 * 3、结束维修工单（EJBEvent.CLOSE_REPAIR_INFO）
 * 需设置JobCardProcessDTO和工单ID（通过JobCardProcessDTO的jcId）
 * 4、结束安装工单（EJBEvent.CLOSE_INSTALLATION_INFO）
 * 需设置JobCardProcessDTO和工单ID（通过JobCardProcessDTO的jcId）
 * 
 * 5、取消维修工单(EJBEvent.CANCEL_REPAIR_JOB_CARD)
 * 需设置JobCardProcessDTO和工单ID（通过JobCardProcessDTO的jcId）
 * 6、取消安装工单(EJBEvent.CANCEL_INSTALLATION_JOB_CARD)
 * 需设置JobCardProcessDTO和工单ID（通过JobCardProcessDTO的jcId）
 *
 * 7、录入维修信息(EJBEvent.REGISTER_REPAIR_INFO)
 * 需设置JobCardDTO,JobCardProcessDTO和维修工单ID（通过JobCardDTO的JobCardId），“维修是否成功”标志（isSuccess），
 * 费用信息，支付信息（如果存在，都是FinancialTransactionDTO，目前已去掉），诊断信息（DiagnosisInfoDTO的集合，如果存在）
 * 8、录入安装信息(EJBEvent.REGISTER_INSTALLATION_INFO)
 * 需设置JobCardDTO,JobCardProcessDTO和安装工单ID（通过JobCardDTO的JobCardId），“安装是否成功”标志（isSuccess），
 * 费用信息，支付信息（如果存在，都是FinancialTransactionDTO，目前已去掉）
 * 
 * 9、工单修改(EJBEvent.UPDATE)
 * 需设置JobCardDTO及其dto中的jobCardId
 */
public class JobCardEJBEvent extends AbstractEJBEvent {
    private JobCardProcessDTO jobcardProcessDto;
    private JobCardDTO jobCardDto;
    AddressDTO addressDTO;
    AddressDTO oldAddressDTO;
    private boolean isSuccess;
    private int accountID;
    //增加对手工开工单费用的处理（不影响到其他的费用计算和支付处理）
    private String customizeFeeValue;
    
	//费用列表
	private Collection feeList;
	//付费列表
	private Collection paymentList =new ArrayList();
	//预存抵扣
	private Collection csiPrePaymentDeductionList =new ArrayList();
	
    private Collection diagnosisInfoDtos;
    
    private Collection materialUsage;
	//true:实际提交 ;false:不提交，只做预判
	private boolean doPost;
	
	//批量预约处理 begin
	private CommonQueryConditionDTO  commonConditionDto;
	//批量预约处理 end
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
	 * @return the addressDTO
	 */
	public AddressDTO getAddressDTO() {
		return addressDTO;
	}
	/**
	 * @param addressDTO the addressDTO to set
	 */
	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	public String getCustomizeFeeValue() {
		return customizeFeeValue;
	}
	public void setCustomizeFeeValue(String customizeFeeValue) {
		this.customizeFeeValue = customizeFeeValue;
	}
	/**
	 * @return the oldAddressDTO
	 */
	public AddressDTO getOldAddressDTO() {
		return oldAddressDTO;
	}
	/**
	 * @param oldAddressDTO the oldAddressDTO to set
	 */
	public void setOldAddressDTO(AddressDTO oldAddressDTO) {
		this.oldAddressDTO = oldAddressDTO;
	}
	public CommonQueryConditionDTO getCommonConditionDto() {
		return commonConditionDto;
	}
	public void setCommonConditionDto(CommonQueryConditionDTO commonConditionDto) {
		this.commonConditionDto = commonConditionDto;
	}
	
}
