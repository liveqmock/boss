package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CAQueueDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CAQueueBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer queueId)
			throws CreateException {
		// setQueueId(queueId);
		return null;
	}

	public java.lang.Integer ejbCreate(CAQueueDTO dto) throws CreateException {

		setEventId(dto.getEventID());
		setEventClass(dto.getEventClass());
		setCommandId(dto.getCommandID());
		setCustomerId(dto.getCustomerID());
		setScnr(dto.getScnr());
		setStbnr(dto.getStbnr());
		setProductId(dto.getProductID());
		setOldScnr(dto.getOldScnr());
		setOldStbnr(dto.getOldStbnr());
		setOldProductId(dto.getOldProductId());
		setStatus(dto.getStatus());
		setCreateTime(dto.getCreateTime());
		setDoneTime(dto.getDoneTime());
		setHostId(dto.getHostID());
		setCondId(dto.getCondID());
		setOpiID(dto.getOpiID());
		 
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer queueId) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CAQueueDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setQueueId(java.lang.Integer queueId);

	public abstract void setEventId(int eventId);
	
	public abstract void setOpiID(int opiID);
	
	public abstract int getOpiID();

	public abstract void setEventClass(int eventClass);

	public abstract void setCommandId(int commandId);

	public abstract void setCustomerId(int customerId);

	public abstract void setScnr(java.lang.String scnr);

	public abstract void setStbnr(java.lang.String stbnr);

	public abstract void setProductId(int productId);

	public abstract void setOldScnr(java.lang.String oldScnr);

	public abstract void setOldStbnr(java.lang.String oldStbnr);

	public abstract void setOldProductId(int oldProductId);

	public abstract void setStatus(java.lang.String status);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setDoneTime(java.sql.Timestamp doneTime);

	public abstract void setHostId(int hostId);

	public abstract void setCondId(int condId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getQueueId();

	public abstract int getEventId();

	public abstract int getEventClass();

	public abstract int getCommandId();

	public abstract int getCustomerId();

	public abstract java.lang.String getScnr();

	public abstract java.lang.String getStbnr();

	public abstract int getProductId();

	public abstract java.lang.String getOldScnr();

	public abstract java.lang.String getOldStbnr();

	public abstract int getOldProductId();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract java.sql.Timestamp getDoneTime();

	public abstract int getHostId();

	public abstract int getCondId();

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

	public int ejbUpdate(CAQueueDTO dto) {
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