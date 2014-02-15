package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FinanceSetOffMapDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class FinanceSetOffMapBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		// setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(FinanceSetOffMapDTO dto)
			throws CreateException {
		setCreateTime(dto.getCreateTime());
		setOpId(dto.getOpId());
		setOrgId(dto.getOrgId());
		setCustId(dto.getCustId());
		setAcctId(dto.getAcctId());
		setSType(dto.getSType());
		setPlusReferType(dto.getPlusReferType());
		setPlusReferId(dto.getPlusReferId());
		setMinusReferType(dto.getMinusReferType());
		setMinusReferId(dto.getMinusReferId());
		setValue(dto.getValue());
		setStatus(dto.getStatus());
		setComments(dto.getComments());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(FinanceSetOffMapDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setOpId(int opId);

	public abstract void setOrgId(int orgId);

	public abstract void setCustId(int custId);

	public abstract void setAcctId(int acctId);

	public abstract void setSType(java.lang.String sType);

	public abstract void setPlusReferType(java.lang.String plusReferType);

	public abstract void setPlusReferId(int plusReferId);

	public abstract void setMinusReferType(java.lang.String minusReferType);

	public abstract void setMinusReferId(int minusReferId);

	public abstract void setValue(double value);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getSeqNo();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract int getOpId();

	public abstract int getOrgId();

	public abstract int getCustId();

	public abstract int getAcctId();

	public abstract java.lang.String getSType();

	public abstract java.lang.String getPlusReferType();

	public abstract int getPlusReferId();

	public abstract java.lang.String getMinusReferType();

	public abstract int getMinusReferId();

	public abstract double getValue();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	 public abstract void setComments(String comments);
	 public abstract String getComments();

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

	public int ejbUpdate(FinanceSetOffMapDTO dto) {
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