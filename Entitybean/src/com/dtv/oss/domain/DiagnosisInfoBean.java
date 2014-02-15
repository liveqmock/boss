package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DiagnosisInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class DiagnosisInfoBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		// setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(DiagnosisInfoDTO dto)
			throws CreateException {
		setInfoSettingId(dto.getInfoSettingId());
		setReferSourceType(dto.getReferSourceType());
		setReferSourceId(dto.getReferSourceId());
		setInfoValueId(dto.getInfoValueId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setMemo(dto.getMemo());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(DiagnosisInfoDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setInfoSettingId(int InfoSettingId);

	public abstract void setReferSourceType(java.lang.String ReferSourceType);

	public abstract void setReferSourceId(int referSourceId);

	public abstract void setInfoValueId(int InfoValueId);
	
	public abstract void setMemo(java.lang.String memo);
	
	public abstract String getMemo();

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getInfoSettingId();

	public abstract java.lang.String getReferSourceType();

	public abstract int getReferSourceId();

	public abstract int getInfoValueId();
	
	

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

	public int ejbUpdate(DiagnosisInfoDTO dto) {
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