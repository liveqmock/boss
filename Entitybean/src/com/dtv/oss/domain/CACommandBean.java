package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CACommandDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CACommandBean implements EntityBean {
	EntityContext entityContext;

	public CACommandPK ejbCreate(int commandID) throws CreateException {
		// setCommandID(commandID);
		return null;
	}

	public CACommandPK ejbCreate(CACommandDTO dto) throws CreateException {
		setCommandID(dto.getCommandID());
		setCommandName(dto.getCommandName());
		setCommandString(dto.getCommandString());
		setDescription(dto.getDescription());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int commandID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CACommandDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setCommandID(int commandID);

	public abstract void setCommandName(java.lang.String commandName);

	public abstract void setCommandString(java.lang.String commandString);

	public abstract void setDescription(java.lang.String description);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getCommandID();

	public abstract java.lang.String getCommandName();

	public abstract java.lang.String getCommandString();

	public abstract java.lang.String getDescription();

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

	public int ejbUpdate(CACommandDTO dto) {
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