package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustProductProcessParaDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustProductProcessParaBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(CustProductProcessParaDTO dto)
			throws CreateException {
		setPsId(dto.getPsId());
		setCpcmId(dto.getCpcmId());
		setAction(dto.getAction());
		setActionTime(dto.getActionTime());
		setOrgId(dto.getOrgId());
		setOperatorId(dto.getOperatorId());
		setProcessTime(dto.getProcessTime());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustProductProcessParaDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setPsId(int psId);

	public abstract void setCpcmId(int cpcmId);

	public abstract void setAction(java.lang.String action);

	public abstract void setActionTime(java.sql.Timestamp actionTime);

	public abstract void setOrgId(int orgId);

	public abstract void setOperatorId(int operatorId);

	public abstract void setProcessTime(java.sql.Timestamp processTime);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getPsId();

	public abstract int getCpcmId();

	public abstract java.lang.String getAction();

	public abstract java.sql.Timestamp getActionTime();

	public abstract int getOrgId();

	public abstract int getOperatorId();

	public abstract java.sql.Timestamp getProcessTime();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

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

	public int ejbUpdate(CustProductProcessParaDTO dto) {
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