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
 * 接口说明：
 * 1、报修工单修改(已并入JobCardEJBEvent)
 * 需设置jobCardDto及其dto中的JobCardId
 * 2、报修单派工(EJBEvent.ASSIGN_REPAIR)
 * 需设置JobCardDTO的集合，其中每个JobCardDTO必须包含报修单ID（通过jobCardDTO的ReferSheetId来设置）
 * 3、报修单创建(EJBEvent.PROCESS_CUSTOMER_PROBLEM)
 * 需设置CustomerProblemDTO和DiagnosisInfoDTO的集合（如果存在）,
 * 如果是大面积故障，还需要设置NextOrgID
 * 4、报修单手工流转(EJBEvent.MANUAL_TRANSFER_REPAIR_SHEET)
 * 需设置CustProblemProcessDTO和报修单ID（通过CustProblemProcessDTO的CustProblemID）
 * 5、报修回访(EJBEvent.CALL_CUSTOMER_FOR_REPAIR)
 * 需设置CustomerProblemDTO及其报修单ID（通过Id来设置）
 * 6、手工结束报修单(EJBEvent.MANUAL_CLOSE_REPAIR_SHEET)
 * 需设置CustProblemProcessDTO的集合，其中每个CustProblemProcessDTO必须设置报修单ID（通过CustProblemProcessDTO的CustProblemID）
 * 7、终止维修(EJBEvent.TERMINATE_REPAIR_INFO)
 * 需设置CustProblemProcessDTO的集合，其中每个CustProblemProcessDTO必须设置报修单ID（通过CustProblemProcessDTO的CustProblemID）
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
//  费用列表
	private Collection feeList;
//	true:实际提交 ;false:不提交，只做预判
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
//     * 这个变量用来确定调用的操作是否为批量方式，缺省为非批量方式（因此，某个操作就可以同时支持两种方式）.
//     * 如果某操作支持批量方式，则其设置的参数为非批量方式时对应参数的集合.
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
