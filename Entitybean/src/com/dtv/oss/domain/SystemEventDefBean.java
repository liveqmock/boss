package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.SystemEventDefDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class SystemEventDefBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer eventClass)
			throws CreateException {
		// setEventClass(eventClass);
		return null;
	}

	public java.lang.Integer ejbCreate(SystemEventDefDTO dto)
			throws CreateException {
		setEventClass(new Integer(dto.getEventClass()));
		setEventName(dto.getEventName());
		setEventDesc(dto.getEventDesc());
		setEventType(dto.getEventType());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer eventClass)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(SystemEventDefDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setEventClass(java.lang.Integer eventClass);

	public abstract void setEventName(java.lang.String eventName);

	public abstract void setEventDesc(java.lang.String eventDesc);

	public abstract void setEventType(java.lang.String eventType);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getEventClass();

	public abstract java.lang.String getEventName();

	public abstract java.lang.String getEventDesc();

	public abstract java.lang.String getEventType();

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

	public int ejbUpdate(SystemEventDefDTO dto) {
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