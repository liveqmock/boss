package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.AcctItemTypeDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class AcctItemTypeBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String acctItemTypeID)
			throws CreateException {
		//setAcctItemTypeID(acctItemTypeID);
		return null;
	}

	public java.lang.String ejbCreate(AcctItemTypeDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setAcctItemTypeID(dto.getAcctItemTypeID());
		setAcctItemTypeName(dto.getAcctItemTypeName());
		setSummaryAiFlag(dto.getSummaryAiFlag());
		setSummaryTo(dto.getSummaryTo());
		setSpecialSetOffFlag(dto.getSpecialSetOffFlag());
		setStatus(dto.getStatus());
		setFeeType(dto.getFeeType());
		setShowLevel(dto.getShowLevel());
		setSystemFlag(dto.getSystemFlag());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.String acctItemTypeID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(AcctItemTypeDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	public abstract void setSystemFlag(java.lang.String systemFlag);
	
	public abstract java.lang.String getSystemFlag();
	public abstract void setAcctItemTypeID(java.lang.String acctItemTypeID);

	public abstract void setAcctItemTypeName(java.lang.String acctItemTypeName);

	public abstract void setSummaryAiFlag(java.lang.String summaryAiFlag);

	public abstract void setSummaryTo(java.lang.String summaryTo);

	public abstract void setSpecialSetOffFlag(java.lang.String specialSetOffFlag);

	public abstract void setStatus(java.lang.String status);

	public abstract void setShowLevel(java.lang.String showLevel);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setFeeType(java.lang.String feeType);

	public abstract java.lang.String getAcctItemTypeID();

	public abstract java.lang.String getAcctItemTypeName();

	public abstract java.lang.String getSummaryAiFlag();

	public abstract java.lang.String getSummaryTo();

	public abstract java.lang.String getSpecialSetOffFlag();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getShowLevel();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getFeeType();

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

	public int ejbUpdate(AcctItemTypeDTO dto) {
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