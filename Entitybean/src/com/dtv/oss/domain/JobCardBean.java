package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class JobCardBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer jobCardId)
			throws CreateException {
		// setJobCardId(jobCardId);
		return null;
	}

	public java.lang.Integer ejbCreate(JobCardDTO dto) throws CreateException {
		setJobType(dto.getJobType());
		setPreferedDate(dto.getPreferedDate());
		setReferSheetId(dto.getReferSheetId());
		setCustId(dto.getCustId());
		setCustServiceAccountId(dto.getCustServiceAccountId());
		setContactPerson(dto.getContactPerson());
		setContactPhone(dto.getContactPhone());
		setAddressId(dto.getAddressId());
		setWorkCount(dto.getWorkCount());
		setDueDate(dto.getDueDate());
		setFirstWorkDate(dto.getFirstWorkDate());
		setStatus(dto.getStatus());
		setTroubleSubType(dto.getTroubleSubType());
		setTroubleType(dto.getTroubleType());
		setResolutionType(dto.getResolutionType());
		setSubType(dto.getSubType());
		setPreferedTime(dto.getPreferedTime());
		setWorkDate(dto.getWorkDate());
		setWorkResult(dto.getWorkResult());
		setOutOfDateReason(dto.getOutOfDateReason());
		setWorkTime(dto.getWorkTime());
		setWorkResultReason(dto.getWorkResultReason());
		setAddPortNumber(dto.getAddPortNumber());
		setProcessOrgId(dto.getProcessOrgId());
		setOldPortNumber(dto.getOldPortNumber());
		setAccountID(dto.getAccountID());
		setDescription(dto.getDescription());
		setCreateTime(dto.getCreateTime());
		setCreateOpID(dto.getCreateOpID());
		setCreateOrgID(dto.getCreateOrgID());
		setReferType(dto.getReferType());
		setCreateMethod(dto.getCreateMethod());
		setManualCreateReason(dto.getManualCreateReason());
		setPayStatus(dto.getPayStatus());
		setPayDate(dto.getPayDate());
		setStatusReasonDate(dto.getStatusReasonDate());
		setCatvID(dto.getCatvID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setOldAddressId(dto.getOldAddressId());
		
		return null;
	}

	public void ejbPostCreate(java.lang.Integer jobCardId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(JobCardDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract java.sql.Timestamp getCreateTime();

	public abstract void setCreateOpID(int createOpID);

	public abstract int getCreateOpID();

	public abstract void setCreateOrgID(int createOrgID);

	public abstract int getCreateOrgID();

	public abstract void setReferType(java.lang.String referType);

	public abstract java.lang.String getReferType();

	public abstract void setCreateMethod(java.lang.String createMethod);

	public abstract java.lang.String getCreateMethod();

	public abstract void setManualCreateReason(
			java.lang.String manualCreateReason);

	public abstract java.lang.String getManualCreateReason();

	public abstract void setPayStatus(java.lang.String payStatus);

	public abstract java.lang.String getPayStatus();

	public abstract void setPayDate(java.sql.Timestamp payDate);

	public abstract java.sql.Timestamp getPayDate();

	public abstract void setStatusReasonDate(java.sql.Timestamp statusReasonDate);

	public abstract java.sql.Timestamp getStatusReasonDate();

	public abstract void setCatvID(java.lang.String catvID);

	public abstract java.lang.String getCatvID();

	public abstract void setWorkResultReason(java.lang.String workResultReason);

	public abstract java.lang.String getWorkResultReason();

	public abstract void setProcessOrgId(int processOrgId);

	public abstract int getProcessOrgId();

	public abstract void setAddPortNumber(int addPortNumber);

	public abstract int getAddPortNumber();

	public abstract void setOldPortNumber(int oldPortNumber);

	public abstract int getOldPortNumber();

	public abstract void setDescription(java.lang.String description);

	public abstract java.lang.String getDescription();

	public abstract void setAccountID(int accountID);

	public abstract int getAccountID();

	public abstract void setPreferedTime(java.lang.String preferedTime);

	public abstract void setWorkDate(java.sql.Timestamp workDate);

	public abstract void setWorkResult(java.lang.String workResult);

	public abstract void setOutOfDateReason(java.lang.String outOfDateReason);

	public abstract java.lang.String getWorkTime();

	public abstract java.lang.String getPreferedTime();

	public abstract java.sql.Timestamp getWorkDate();

	public abstract java.lang.String getWorkResult();

	public abstract java.lang.String getOutOfDateReason();

	public abstract void setWorkTime(java.lang.String workTime);

	public abstract void setJobCardId(java.lang.Integer jobCardId);

	public abstract void setJobType(java.lang.String jobType);

	public abstract void setReferSheetId(int referSheetId);

	public abstract void setCustId(int custId);

	public abstract void setCustServiceAccountId(int custServiceAccountId);

	public abstract void setContactPerson(java.lang.String contactPerson);

	public abstract void setContactPhone(java.lang.String contactPhone);

	public abstract void setAddressId(int addressId);

	public abstract void setWorkCount(int workCount);

	public abstract void setDueDate(java.sql.Timestamp dueDate);

	public abstract void setFirstWorkDate(java.sql.Timestamp firstWorkDate);

	public abstract void setStatus(java.lang.String status);

	public abstract void setPreferedDate(java.sql.Timestamp preferedDate);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setTroubleSubType(java.lang.String troubleSubType);

	public abstract void setTroubleType(java.lang.String troubleType);

	public abstract void setResolutionType(java.lang.String resolutionType);

	public abstract void setSubType(java.lang.String subType);
	
	public abstract void setOldAddressId(int oldAddressId);

	public abstract java.lang.String getSubType();

	public abstract java.lang.Integer getJobCardId();

	public abstract java.lang.String getJobType();

	public abstract int getReferSheetId();

	public abstract int getCustId();

	public abstract int getCustServiceAccountId();

	public abstract java.lang.String getContactPerson();

	public abstract java.lang.String getContactPhone();

	public abstract int getAddressId();

	public abstract int getWorkCount();

	public abstract java.sql.Timestamp getDueDate();

	public abstract java.sql.Timestamp getPreferedDate();

	public abstract java.sql.Timestamp getFirstWorkDate();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getTroubleSubType();

	public abstract java.lang.String getTroubleType();

	public abstract java.lang.String getResolutionType();

	public abstract int getOldAddressId();
	
	public void ejbLoad() {
		/** @todo Complete this method */
	}

	public void ejbStore() {
		/** @todo Complete this method */
	}

	public void ejbActivate() {
		/** @todo Complete this method */
	}

	public void ejbPassivate() {
		/** @todo Complete this method */
	}

	public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	public int ejbUpdate(JobCardDTO dto) {
		/** @todo Complete this method */
		if (dto.getDtLastmod() == null) {
			return -1;
		}

		if (!dto.getDtLastmod().equals(getDtLastmod())) {

			return -1;
		} else {
			try {
				EntityBeanAutoUpdate.update(dto, this);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			return 0;
		}
	}

}