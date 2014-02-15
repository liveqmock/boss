package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CandssubIdDTO;

abstract public class CandssubIdBean implements EntityBean {
	EntityContext entityContext;

	public CandssubIdPK ejbCreate(int hostId,
			java.lang.String cardsn) throws CreateException {
		// setCardsn(cardsn);
		// setSubscriberId(subscriberId);
		return null;
	}

	public CandssubIdPK ejbCreate(CandssubIdDTO dto) throws CreateException {
		setHostId(dto.getHostId());
		setCardsn(dto.getCardsn());
		setSubscriberId(dto.getSubscriberId());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int hostId,java.lang.String cardsn) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CandssubIdDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setCardsn(java.lang.String cardsn);
	
	public abstract void setHostId(int hostId);

	public abstract void setSubscriberId(int subscriberId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getCardsn();

	public abstract int getSubscriberId();
	
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
}