package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.SystemEventReasonDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class SystemEventReasonBean implements EntityBean {
	EntityContext entityContext;

	public SystemEventReasonPK ejbCreate(int eventClass,
			java.lang.String reasonCode) throws CreateException {

		return null;
	}

	public SystemEventReasonPK ejbCreate(SystemEventReasonDTO dto)
			throws CreateException {
		setEventClass(dto.getEventClass());
		setReasonCode(dto.getReasonCode());
		setReasonDesc(dto.getReasonDesc());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(int eventClass, java.lang.String reasonCode)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(SystemEventReasonDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setEventClass(int eventClass);

	public abstract void setReasonCode(java.lang.String reasonCode);

	public abstract void setReasonDesc(java.lang.String reasonDesc);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getEventClass();

	public abstract java.lang.String getReasonCode();

	public abstract java.lang.String getReasonDesc();

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

	public int ejbUpdate(SystemEventReasonDTO dto) {
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