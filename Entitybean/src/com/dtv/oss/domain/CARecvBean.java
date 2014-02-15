package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CARecvDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CARecvBean implements EntityBean {
	EntityContext entityContext;

	public CARecvPK ejbCreate(int queueId, int eventId, int transId)
			throws CreateException {
		// setQueueId(queueId);
		//setEventId(eventId);
		//setTransId(transId);
		return null;
	}

	public CARecvPK ejbCreate(CARecvDTO dto) throws CreateException {
		setQueueId(dto.getQueueID());
		setEventId(dto.getEventID());
		setTransId(dto.getTransID());
		setRecvData(dto.getRecvData());
		setRecvstring(dto.getRecvString());
		setErrorCode(dto.getErrorCode());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(int queueId, int eventId, int transId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CARecvDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setQueueId(int queueId);

	public abstract void setEventId(int eventId);

	public abstract void setTransId(int transId);

	public abstract void setRecvData(java.lang.String recvData);

	public abstract void setRecvstring(java.lang.String recvstring);

	public abstract void setErrorCode(java.lang.String errorCode);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getQueueId();

	public abstract int getEventId();

	public abstract int getTransId();

	public abstract java.lang.String getRecvData();

	public abstract java.lang.String getRecvstring();

	public abstract java.lang.String getErrorCode();

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

	public int ejbUpdate(CARecvDTO dto) {
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