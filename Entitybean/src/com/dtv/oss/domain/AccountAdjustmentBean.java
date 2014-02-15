package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.AccountAdjustmentDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class AccountAdjustmentBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		return null;
	}

	public java.lang.Integer ejbCreate(AccountAdjustmentDTO dto)
			throws CreateException {

		setCreateOrgID(dto.getCreateOrgID());
		setAdjustmentType(dto.getAdjustmentType());
		setReferRecordType(dto.getReferRecordType()); 
		setCreateOpID(dto.getCreateOpID());
		setCreateTime(dto.getCreateTime());
		setCreateOpID(dto.getCreateOpID());
		setReason(dto.getReason());
		setStatus(dto.getStatus());
		setCustID(dto.getCustID());
		setAcctID(dto.getAcctID());
		setComments(dto.getComments());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		setReferRecordID(dto.getReferRecordID());
		setReferSheetID(dto.getReferSheetID());
		setAdjustmentDate(dto.getAdjustmentDate());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(AccountAdjustmentDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setReferSheetID(int referSheetID);
	
	public abstract void setReferRecordID(int referRecordID);
	 
	public abstract void setReferRecordType(String referRecordType);
	

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setCustID(int custID);

	public abstract void setAcctID(int acctID);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setCreateOrgID(int createOrgID);

	public abstract void setReason(java.lang.String reason);

	public abstract void setStatus(java.lang.String status);

	 

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setComments(java.lang.String comments);

	public abstract void setAdjustmentDate(Timestamp adjustmentDate);	 
	public abstract Timestamp getAdjustmentDate();	 
	
	public abstract void setAdjustmentType(java.lang.String adjustmentType);

	public abstract void setCreateOpID(int createOpID);

	public abstract java.lang.Integer getSeqNo();

	public abstract int getCustID();

	public abstract int getAcctID();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract int getCreateOrgID();

	public abstract java.lang.String getReason();

	public abstract java.lang.String getStatus();

	 

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getComments();

	 
	public abstract java.lang.String getAdjustmentType();

	public abstract int getCreateOpID();
	
	public abstract int getReferSheetID();
	
	public abstract int getReferRecordID();
	 
	public abstract String getReferRecordType();

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

	public int ejbUpdate(AccountAdjustmentDTO dto) {
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