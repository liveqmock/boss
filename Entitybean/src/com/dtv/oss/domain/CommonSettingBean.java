package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CommonSettingDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CommonSettingBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String name)
			throws CreateException {
		// setName(name);
		return null;
	}

	public java.lang.String ejbCreate(CommonSettingDTO dto)
			throws CreateException {
		setName(dto.getName());
		setDescription(dto.getDescription());
		setModule(dto.getModule());
		setAttable(dto.getAttable());
		setField(dto.getField());
		setFixed(dto.getFixed());
		setType(dto.getType());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.String name) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CommonSettingDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setName(java.lang.String name);

	public abstract void setDescription(java.lang.String description);

	public abstract void setModule(java.lang.String module);

	public abstract void setAttable(java.lang.String attable);

	public abstract void setField(java.lang.String field);

	public abstract void setFixed(java.lang.String fixed);

	public abstract void setType(java.lang.String type);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getName();

	public abstract java.lang.String getDescription();

	public abstract java.lang.String getModule();

	public abstract java.lang.String getAttable();

	public abstract java.lang.String getField();

	public abstract java.lang.String getFixed();

	public abstract java.lang.String getType();

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

	public int ejbUpdate(CommonSettingDTO dto) {
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