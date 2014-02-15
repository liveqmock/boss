package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CASentDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CASentBean implements EntityBean {
	EntityContext entityContext;

	public CASentPK ejbCreate(int queueId, int eventId, int transId)
			throws CreateException {
		// setQueueId(queueId);
		// setEventId(eventId);
		//setTransId(transId);
		return null;
	}

	public CASentPK ejbCreate(CASentDTO dto) throws CreateException {
		setQueueId(dto.getQueueID());
		setEventId(dto.getEventID());
		setTransId(dto.getTransID());
		setSentData(dto.getSentData());
		setSendString(dto.getSendString());
		setSentTime(dto.getSentTime());
		setStatus(dto.getStatus());
		setErrorCode(dto.getErrorCode());
		setHostId(dto.getHostID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int queueId, int eventId, int transId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CASentDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setQueueId(int queueId);

	public abstract void setEventId(int eventId);

	public abstract void setTransId(int transId);

	public abstract void setSentData(java.lang.String sentData);

	public abstract void setSendString(java.lang.String sendString);

	public abstract void setSentTime(java.sql.Timestamp sentTime);

	public abstract void setStatus(java.lang.String status);

	public abstract void setErrorCode(java.lang.String errorCode);

	public abstract void setHostId(int hostId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getQueueId();

	public abstract int getEventId();

	public abstract int getTransId();

	public abstract java.lang.String getSentData();

	public abstract java.lang.String getSendString();

	public abstract java.sql.Timestamp getSentTime();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getErrorCode();

	public abstract int getHostId();

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

	public int ejbUpdate(CASentDTO dto) {
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