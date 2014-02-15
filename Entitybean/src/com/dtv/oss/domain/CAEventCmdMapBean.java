package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CAEventCmdMapDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CAEventCmdMapBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer mapID)
			throws CreateException {
		// setMapID(mapID);
		return null;
	}

	public java.lang.Integer ejbCreate(CAEventCmdMapDTO dto)
			throws CreateException {
		setMapCmdID(dto.getMapCmdID());
		setMapEventID(dto.getMapEventID());
		setMapConditionID(dto.getMapConditionID());
		setDescription(dto.getDescription());
		setPriority(dto.getPriority());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer mapID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CAEventCmdMapDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setMapID(java.lang.Integer mapID);

	public abstract void setMapCmdID(int mapCmdID);

	public abstract void setMapEventID(int mapEventID);

	public abstract void setMapConditionID(int mapConditionID);

	public abstract void setDescription(java.lang.String description);

	public abstract void setPriority(int priority);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getMapID();

	public abstract int getMapCmdID();

	public abstract int getMapEventID();

	public abstract int getMapConditionID();

	public abstract java.lang.String getDescription();

	public abstract int getPriority();

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

	public int ejbUpdate(CAEventCmdMapDTO dto) {
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