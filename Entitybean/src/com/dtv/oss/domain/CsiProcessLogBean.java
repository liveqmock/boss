package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CsiProcessLogDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CsiProcessLogBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(CsiProcessLogDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		//setId(dto.getId());
		setCsiID(dto.getCsiID());
		setAction(dto.getAction());
		setDescription(dto.getDescription());
		setActionTime(dto.getActionTime());
		setOperatorID(dto.getOperatorID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setWorkResult(dto.getWorkResult());
		setWorkResultReason(dto.getWorkResultReason());
		setOrgID(dto.getOrgID());
		setInvoiceNo(dto.getInvoiceNo());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CsiProcessLogDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setCsiID(int csiID);

	public abstract void setAction(java.lang.String action);

	public abstract void setDescription(java.lang.String description);

	public abstract void setActionTime(java.sql.Timestamp actionTime);

	public abstract void setOperatorID(int operatorID);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setOrgID(int orgID);

	public abstract void setInvoiceNo(int invoiceNo);

	public abstract void setWorkResult(java.lang.String workResult);

	public abstract void setWorkResultReason(java.lang.String workResultReason);

	public abstract java.lang.Integer getId();

	public abstract int getCsiID();

	public abstract java.lang.String getAction();

	public abstract java.lang.String getDescription();

	public abstract java.sql.Timestamp getActionTime();

	public abstract int getOperatorID();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getOrgID();

	public abstract int getInvoiceNo();

	public abstract java.lang.String getWorkResult();

	public abstract java.lang.String getWorkResultReason();

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

	public int ejbUpdate(CsiProcessLogDTO dto) {
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