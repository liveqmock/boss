/*
 * Created on 2005-10-14
 *
 * @author whq
 */
package com.dtv.oss.service.ejbevent.work;

import java.util.Collection;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
import com.dtv.oss.dto.*;

/*
 * �ӿ�˵����
 * 1�����޹����޸�(�Ѳ���JobCardEJBEvent)
 * ������jobCardDto����dto�е�JobCardId
 * 2�����޵��ɹ�(EJBEvent.ASSIGN_REPAIR)
 * ������JobCardDTO�ļ��ϣ�����ÿ��JobCardDTO����������޵�ID��ͨ��jobCardDTO��ReferSheetId�����ã�
 * 3�����޵�����(EJBEvent.PROCESS_CUSTOMER_PROBLEM)
 * ������CustomerProblemDTO��DiagnosisInfoDTO�ļ��ϣ�������ڣ�,
 * ����Ǵ�������ϣ�����Ҫ����NextOrgID
 * 4�����޵��ֹ���ת(EJBEvent.MANUAL_TRANSFER_REPAIR_SHEET)
 * ������CustProblemProcessDTO�ͱ��޵�ID��ͨ��CustProblemProcessDTO��CustProblemID��
 * 5�����޻ط�(EJBEvent.CALL_CUSTOMER_FOR_REPAIR)
 * ������CustomerProblemDTO���䱨�޵�ID��ͨ��Id�����ã�
 * 6���ֹ��������޵�(EJBEvent.MANUAL_CLOSE_REPAIR_SHEET)
 * ������CustProblemProcessDTO�ļ��ϣ�����ÿ��CustProblemProcessDTO�������ñ��޵�ID��ͨ��CustProblemProcessDTO��CustProblemID��
 * 7����ֹά��(EJBEvent.TERMINATE_REPAIR_INFO)
 * ������CustProblemProcessDTO�ļ��ϣ�����ÿ��CustProblemProcessDTO�������ñ��޵�ID��ͨ��CustProblemProcessDTO��CustProblemID��
 * 
 */
public class RepairEJBEvent extends AbstractEJBEvent {
    private JobCardDTO jobCardDto;
    private CustomerProblemDTO customerProblemDto;
    private CustomerDTO custDto;
    private CustProblemProcessDTO custProblemProcessDto;
    private Collection diagnosisInfoDtos;
    private Collection custProblemProcessDtos;
    private Collection jobCardDtos;
    private int acctID;
    private int nextOrgID;
    private String diagnosisResult;
//  �����б�
	private Collection feeList;
//	true:ʵ���ύ ;false:���ύ��ֻ��Ԥ��
	private boolean doPost;
	private String memo;
public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
/**
 * @return Returns the doPost.
 */
public boolean isDoPost() {
	return doPost;
}
/**
 * @param doPost The doPost to set.
 */
public void setDoPost(boolean doPost) {
	this.doPost = doPost;
}
/**
 * @return Returns the feeList.
 */
public Collection getFeeList() {
	return feeList;
}
/**
 * @param feeList The feeList to set.
 */
public void setFeeList(Collection feeList) {
	this.feeList = feeList;
}
//    /*
//     * �����������ȷ�����õĲ����Ƿ�Ϊ������ʽ��ȱʡΪ��������ʽ����ˣ�ĳ�������Ϳ���ͬʱ֧�����ַ�ʽ��.
//     * ���ĳ����֧��������ʽ���������õĲ���Ϊ��������ʽʱ��Ӧ�����ļ���.
//     * 
//     */
//    private boolean isBatch = false;
    
    public CustomerDTO getCustDto() {
        return custDto;
    }
    public void setCustDto(CustomerDTO custDto) {
        this.custDto = custDto;
    }
    public JobCardDTO getJobCardDto() {
        return jobCardDto;
    }
    public void setJobCardDto(JobCardDTO jcDto) {
        this.jobCardDto = jcDto;
    }
    public CustomerProblemDTO getCustomerProblemDto() {
        return customerProblemDto;
    }
    public void setCustomerProblemDto(CustomerProblemDTO customerProblemDto) {
        this.customerProblemDto = customerProblemDto;
    }
    public Collection getDiagnosisInfoDtos() {
        return diagnosisInfoDtos;
    }
    public void setDiagnosisInfoDtos(Collection diagnosisInfoDtos) {
        this.diagnosisInfoDtos = diagnosisInfoDtos;
    }
    public int getNextOrgID() {
        return nextOrgID;
    }
    public void setNextOrgID(int nextOrgID) {
        this.nextOrgID = nextOrgID;
    }
    public CustProblemProcessDTO getCustProblemProcessDto() {
        return custProblemProcessDto;
    }
    public void setCustProblemProcessDto(
            CustProblemProcessDTO custProblemProcessDto) {
        this.custProblemProcessDto = custProblemProcessDto;
    }
    public Collection getCustProblemProcessDtos() {
        return custProblemProcessDtos;
    }
    public void setCustProblemProcessDtos(Collection custProblemProcessDtos) {
        this.custProblemProcessDtos = custProblemProcessDtos;
    }
    public Collection getJobCardDtos() {
        return jobCardDtos;
    }
    public void setJobCardDtos(Collection jobCardDtos) {
        this.jobCardDtos = jobCardDtos;
    }
	/**
	 * @return Returns the acctID.
	 */
	public int getAcctID() {
		return acctID;
	}
	/**
	 * @param acctID The acctID to set.
	 */
	public void setAcctID(int acctID) {
		this.acctID = acctID;
	}
	public String getDiagnosisResult() {
		return diagnosisResult;
	}
	public void setDiagnosisResult(String diagnosisResult) {
		this.diagnosisResult = diagnosisResult;
	}
}
