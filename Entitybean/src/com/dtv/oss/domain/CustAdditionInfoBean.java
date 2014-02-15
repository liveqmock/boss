package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustAdditionInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustAdditionInfoBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(CustAdditionInfoDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setId(dto.getId());
		setApplicant(dto.getApplicant());
		setDurationLimited(dto.getDurationLimited());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setCustomerId(dto.getCustomerId());
		setApplicationDept(dto.getApplicationDept());
		setBeginValidDate(dto.getBeginValidDate());
		setEndValidDate(dto.getEndValidDate());

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustAdditionInfoDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setCustomerId(int customerId);

	public abstract void setApplicationDept(String applicationDept);

	public abstract void setApplicant(java.lang.String applicant);

	public abstract void setBeginValidDate(java.sql.Timestamp beginValidDate);

	public abstract void setEndValidDate(java.sql.Timestamp endValidDate);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setDurationLimited(java.lang.String durationLimited);

	public abstract java.lang.Integer getId();

	public abstract int getCustomerId();

	public abstract String getApplicationDept();

	public abstract java.lang.String getApplicant();

	public abstract java.sql.Timestamp getBeginValidDate();

	public abstract java.sql.Timestamp getEndValidDate();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getDurationLimited();

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

	public int ejbUpdate(CustAdditionInfoDTO dto) {
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