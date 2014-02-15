package com.dtv.oss.dto;

import java.sql.Timestamp;
import java.math.BigDecimal;
/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class CustServiceInteractionDTO implements ReflectionSupport,
		java.io.Serializable {
	private int id;

	private String referSheetID;

	private String type;

	private String statusReason;

	private Timestamp createTime;

	private String status;

	private int customerID;

	private int accountID;

	private String agentName;

	private String agentCardType;

	private String agentCardID;

	private int groupCampaignID;
	
	private int portNumber;

	private String installationType;

	private String paymentStatus;

	private int referJobCardID;

	private int referBookingSheetID;

	private String comments;

	private String contactPerson;

	private String contactPhone;

	private String callBackFlag;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private String preferedTime;

	private Timestamp preferedDate;

	private java.lang.String scheduleAction;

	private java.sql.Timestamp scheduleTime;

	private int createORGId;
	
	private int createOperatorId;
	
	private String billCollectionMethod;
	
	private java.sql.Timestamp callBackDate;
	
	private String serviceCodeList;
	
	private String createReason;
	
	private int walletId;
	
	private int point;
	
	private BigDecimal value;
	
	private String agentTelephone;
	
	private java.sql.Timestamp workDate;
	
	public java.sql.Timestamp getWorkDate() {
		return workDate;
	}
	public void setWorkDate(java.sql.Timestamp workDate) {
		this.workDate = workDate;
		put("workDate");
	}
	public String getAgentTelephone() {
		return agentTelephone;
	}
	public void setAgentTelephone(String agentTelephone) {
		this.agentTelephone = agentTelephone;
		put("agentTelephone");
	}
	public String getCreateReason() {
		return createReason;
	}
	public void setCreateReason(String createReason) {
		this.createReason = createReason;
		put("createReason");
	}
	public String getServiceCodeList() {
		return serviceCodeList;
	}
	public void setServiceCodeList(String serviceCodeList) {
		this.serviceCodeList = serviceCodeList;
		put("serviceCodeList");
	}
	/**
	 * @return Returns the billColletionMethod.
	 */
	public String getBillCollectionMethod() {
		return billCollectionMethod;
	}
	/**
	 * @param billColletionMethod The billColletionMethod to set.
	 */
	public void setBillCollectionMethod(String billCollectionMethod) {
		this.billCollectionMethod = billCollectionMethod;
		put("billCollectionMethod");
	}
	/**
	 * @return Returns the createOperatorId.
	 */
	public int getCreateOperatorId() {
		return createOperatorId;
	}
	/**
	 * @param createOperatorId The createOperatorId to set.
	 */
	public void setCreateOperatorId(int createOperatorId) {
		this.createOperatorId = createOperatorId;
		put("createOperatorId");
	}
	/**
	 * @return Returns the createOperatorId.
	 */
	public int getPortNumber() {
		return portNumber;
	}
	/**
	 * @param createOperatorId The createOperatorId to set.
	 */
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
		put("portNumber");
	}
	
	
	/**
	 * @return Returns the callBackDate.
	 */
	public java.sql.Timestamp getCallBackDate() {
		return callBackDate;
	}
	/**
	 * @param callBackDate The callBackDate to set.
	 */
	public void setCallBackDate(java.sql.Timestamp callBackDate) {
		this.callBackDate = callBackDate;
		put("callBackDate");
	}
	public int getCreateORGId() {
		return createORGId;
	}

	public void setCreateORGId(int createORGId) {
		this.createORGId = createORGId;
	}

	public CustServiceInteractionDTO() {
	}

	public void setId(int id) {
		this.id = id;
		//put("id");
	}

	public int getId() {
		return id;
	}

	public void setReferSheetID(String referSheetID) {
		this.referSheetID = referSheetID;
		put("referSheetID");
	}

	public String getReferSheetID() {
		return referSheetID;
	}

	public void setScheduleAction(String scheduleAction) {
		this.scheduleAction = scheduleAction;
		put("scheduleAction");
	}

	public String getScheduleAction() {
		return scheduleAction;
	}

	public void setScheduleTime(Timestamp scheduleTime) {
		this.scheduleTime = scheduleTime;
		put("scheduleTime");
	}

	public Timestamp getScheduleTime() {
		return scheduleTime;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type=type;
		put("type");
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
		put("statusReason");
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		put("createTime");
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getStatus() {
		return status;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
		put("customerID");
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
		put("accountID");
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
		put("agentName");
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentCardType(String agentCardType) {
		this.agentCardType = agentCardType;
		put("agentCardType");
	}

	public String getAgentCardType() {
		return agentCardType;
	}

	public void setAgentCardID(String agentCardID) {
		this.agentCardID = agentCardID;
		put("agentCardID");
	}

	public String getAgentCardID() {
		return agentCardID;
	}

	public void setGroupCampaignID(int groupCampaignID) {
		this.groupCampaignID = groupCampaignID;
		put("groupCampaignID");
	}

	public int getGroupCampaignID() {
		return groupCampaignID;
	}

	public void setInstallationType(String installationType) {
		this.installationType = installationType;
		put("installationType");
	}

	public String getInstallationType() {
		return installationType;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
		put("paymentStatus");
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setReferJobCardID(int referJobCardID) {
		this.referJobCardID = referJobCardID;
		put("referJobCardID");
	}

	public int getReferJobCardID() {
		return referJobCardID;
	}

	public void setReferBookingSheetID(int referBookingSheetID) {
		this.referBookingSheetID = referBookingSheetID;
		put("referBookingSheetID");
	}

	public int getReferBookingSheetID() {
		return referBookingSheetID;
	}

	public void setComments(String comments) {
		this.comments = comments;
		put("comments");
	}

	public String getComments() {
		return comments;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
		put("contactPerson");
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
		put("contactPhone");
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setCallBackFlag(String callBackFlag) {
		this.callBackFlag = callBackFlag;
		put("callBackFlag");
	}

	public String getCallBackFlag() {
		return callBackFlag;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public void setPreferedTime(String preferedTime) {
		this.preferedTime = preferedTime;
		put("preferedTime");
	}

	public String getPreferedTime() {
		return preferedTime;
	}

	public void setPreferedDate(Timestamp preferedDate) {
		this.preferedDate = preferedDate;
		put("preferedDate");
	}

	public Timestamp getPreferedDate() {
		return preferedDate;
	}

	
	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CustServiceInteractionDTO that = (CustServiceInteractionDTO) obj;
				return this.getId() == that.getId()
						&& (((this.getReferSheetID() == null) && (that
								.getReferSheetID() == null)) || (this
								.getReferSheetID() != null && this
								.getReferSheetID().equals(
										that.getReferSheetID())))
						&& (((this.getType() == null) && (that.getType() == null)) || (this
								.getType() != null && this.getType().equals(
								that.getType())))
						&& (((this.getStatusReason() == null) && (that
								.getStatusReason() == null)) || (this
								.getStatusReason() != null && this
								.getStatusReason().equals(
										that.getStatusReason())))
						&& (((this.getCreateTime() == null) && (that
								.getCreateTime() == null)) || (this
								.getCreateTime() != null && this
								.getCreateTime().equals(that.getCreateTime())))
						&&

						(((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
						&& this.getCustomerID() == that.getCustomerID()
	                    && this.getCreateOperatorId() == that.getCreateOperatorId()
						&& this.getAccountID() == that.getAccountID()
						&&

						(((this.getAgentName() == null) && (that.getAgentName() == null)) || (this
								.getAgentName() != null && this.getAgentName()
								.equals(that.getAgentName())))&&

(((this.getBillCollectionMethod() == null) && (that.getBillCollectionMethod() == null)) || (this
		.getBillCollectionMethod() != null && this.getBillCollectionMethod()
		.equals(that.getBillCollectionMethod())))
						&& (((this.getAgentCardType() == null) && (that
								.getAgentCardType() == null)) || (this
								.getAgentCardType() != null && this
								.getAgentCardType().equals(
										that.getAgentCardType())))
						&& (((this.getAgentCardID() == null) && (that
								.getAgentCardID() == null)) || (this
								.getAgentCardID() != null && this
								.getAgentCardID().equals(that.getAgentCardID())))
						&& this.getGroupCampaignID() == that
								.getGroupCampaignID()
									&& this.getPortNumber() == that
								.getPortNumber()
						&& (((this.getInstallationType() == null) && (that
								.getInstallationType() == null)) || (this
								.getInstallationType() != null && this
								.getInstallationType().equals(
										that.getInstallationType())))
						&& (((this.getPaymentStatus() == null) && (that
								.getPaymentStatus() == null)) || (this
								.getPaymentStatus() != null && this
								.getPaymentStatus().equals(
										that.getPaymentStatus())))
						&& (((this.getCreateReason() == null) && (that
								.getCreateReason() == null)) || (this
								.getCreateReason() != null && this
								.getCreateReason().equals(
										that.getCreateReason())))
						&& this.getReferJobCardID() == that.getReferJobCardID()
						&& this.getReferBookingSheetID() == that
								.getReferBookingSheetID()
						&& this.getCreateORGId()==that.getCreateORGId() 
						&& (((this.getComments() == null) && (that
								.getComments() == null)) || (this.getComments() != null && this
								.getComments().equals(that.getComments())))
						&& (((this.getContactPerson() == null) && (that
								.getContactPerson() == null)) || (this
								.getContactPerson() != null && this
								.getContactPerson().equals(
										that.getContactPerson())))
						&& (((this.getContactPhone() == null) && (that
								.getContactPhone() == null)) || (this
								.getContactPhone() != null && this
								.getContactPhone().equals(
										that.getContactPhone())))
						&&

						(((this.getScheduleAction() == null) && (that
								.getScheduleAction() == null)) || (this
								.getScheduleAction() != null && this
								.getScheduleAction().equals(
										that.getScheduleAction())))
						&& (((this.getScheduleTime() == null) && (that
								.getScheduleTime() == null)) || (this
								.getScheduleTime() != null && this
								.getScheduleTime().equals(
										that.getScheduleTime())))
						&&

						(((this.getCallBackFlag() == null) && (that
								.getCallBackFlag() == null)) || (this
								.getCallBackFlag() != null && this
								.getCallBackFlag().equals(
										that.getCallBackFlag())))
						&&(((this.getCallBackDate() == null) && (that
								.getCallBackDate() == null)) || (this
										.getCallBackDate() != null && this
										.getCallBackDate().equals(that.getCallBackDate())))
								&& this.getPoint() == that.getPoint()
								&& this.getValue() == that.getValue()
								&& this.getWalletId() == that.getWalletId()&&
								(((this.getAgentTelephone() == null) && (that
										.getAgentTelephone() == null)) || (this
										.getAgentTelephone() != null && this
										.getAgentTelephone().equals(
												that.getAgentTelephone())))	&&	
						(((this.getDtCreate() == null) && (that.getDtCreate() == null)) || (this
								.getDtCreate() != null && this.getDtCreate()
								.equals(that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append(id);
		buf.append(",").append(referSheetID);
		buf.append(",").append(type);
		buf.append(",").append(statusReason);
		buf.append(",").append(createTime);
		buf.append(",").append(portNumber);
		buf.append(",").append(status);
		buf.append(",").append(customerID);
		buf.append(",").append(accountID);
		buf.append(",").append(billCollectionMethod);
		buf.append(",").append(agentName);
		buf.append(",").append(agentCardType);
		buf.append(",").append(agentCardID);
		buf.append(",").append(groupCampaignID);
		buf.append(",").append(installationType);
		buf.append(",").append(paymentStatus);
		buf.append(",").append(referJobCardID);
		buf.append(",").append(referBookingSheetID);
		buf.append(",").append(comments);
		buf.append(",").append(contactPerson);
		buf.append(",").append(contactPhone);
		buf.append(",").append(callBackDate);
		buf.append(",").append(serviceCodeList);

		buf.append(",").append(callBackFlag);

		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(scheduleAction);

		buf.append(",").append(scheduleTime);
		buf.append(",").append(createOperatorId);
		buf.append(",").append(createORGId);
		buf.append(",").append(createReason);
		buf.append(",").append(point);
		buf.append(",").append(value);
		buf.append(",").append(walletId);
		buf.append(",").append(agentTelephone);
		buf.append(",").append(workDate);
		
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
		put("point");
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
		put("value");
	}
	public int getWalletId() {
		return walletId;
	}
	public void setWalletId(int walletId) {
		this.walletId = walletId;
		put("walletId");
	}

}

