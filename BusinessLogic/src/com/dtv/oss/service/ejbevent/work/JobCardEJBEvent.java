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
 * �ӿ�˵����
 * 1����װԤԼ(EJBEvent.CONTACT_USER_FOR_INSTALLATION)
 * ������JobCardDTO, JobCardProcessDTO�Ͱ�װ����ID��ͨ��JobCardDTO��jobCardID��
 * 2��ά��ԤԼ(EJBEvent.CONTACT_USER_FOR_REPAIR)
 * ������JobCardDTO, JobCardProcessDTO��ά�޹���ID��ͨ��JobCardDTO��jobCardID��
 * 
 * 3������ά�޹�����EJBEvent.CLOSE_REPAIR_INFO��
 * ������JobCardProcessDTO�͹���ID��ͨ��JobCardProcessDTO��jcId��
 * 4��������װ������EJBEvent.CLOSE_INSTALLATION_INFO��
 * ������JobCardProcessDTO�͹���ID��ͨ��JobCardProcessDTO��jcId��
 * 
 * 5��ȡ��ά�޹���(EJBEvent.CANCEL_REPAIR_JOB_CARD)
 * ������JobCardProcessDTO�͹���ID��ͨ��JobCardProcessDTO��jcId��
 * 6��ȡ����װ����(EJBEvent.CANCEL_INSTALLATION_JOB_CARD)
 * ������JobCardProcessDTO�͹���ID��ͨ��JobCardProcessDTO��jcId��
 *
 * 7��¼��ά����Ϣ(EJBEvent.REGISTER_REPAIR_INFO)
 * ������JobCardDTO,JobCardProcessDTO��ά�޹���ID��ͨ��JobCardDTO��JobCardId������ά���Ƿ�ɹ�����־��isSuccess����
 * ������Ϣ��֧����Ϣ��������ڣ�����FinancialTransactionDTO��Ŀǰ��ȥ�����������Ϣ��DiagnosisInfoDTO�ļ��ϣ�������ڣ�
 * 8��¼�밲װ��Ϣ(EJBEvent.REGISTER_INSTALLATION_INFO)
 * ������JobCardDTO,JobCardProcessDTO�Ͱ�װ����ID��ͨ��JobCardDTO��JobCardId��������װ�Ƿ�ɹ�����־��isSuccess����
 * ������Ϣ��֧����Ϣ��������ڣ�����FinancialTransactionDTO��Ŀǰ��ȥ����
 * 
 * 9�������޸�(EJBEvent.UPDATE)
 * ������JobCardDTO����dto�е�jobCardId
 */
public class JobCardEJBEvent extends AbstractEJBEvent {
    private JobCardProcessDTO jobcardProcessDto;
    private JobCardDTO jobCardDto;
    AddressDTO addressDTO;
    AddressDTO oldAddressDTO;
    private boolean isSuccess;
    private int accountID;
    //���Ӷ��ֹ����������õĴ�����Ӱ�쵽�����ķ��ü����֧������
    private String customizeFeeValue;
    
	//�����б�
	private Collection feeList;
	//�����б�
	private Collection paymentList =new ArrayList();
	//Ԥ��ֿ�
	private Collection csiPrePaymentDeductionList =new ArrayList();
	
    private Collection diagnosisInfoDtos;
    
    private Collection materialUsage;
	//true:ʵ���ύ ;false:���ύ��ֻ��Ԥ��
	private boolean doPost;
	
	//����ԤԼ���� begin
	private CommonQueryConditionDTO  commonConditionDto;
	//����ԤԼ���� end
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
