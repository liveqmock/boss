package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CommonSettingDataBean implements EntityBean {
	EntityContext entityContext;

	public CommonSettingDataPK ejbCreate(java.lang.String name,
			java.lang.String key) throws CreateException {
		return null;
	}

	public CommonSettingDataPK ejbCreate(CommonSettingDataDTO dto)
			throws CreateException {
		setName(dto.getName());
		setKey(dto.getKey());
		setValue(dto.getValue());
		setGrade(dto.getGrade());
		setDescription(dto.getDescription());
		setStatus(dto.getStatus());
		setDefaultOrNot(dto.getDefaultOrNot());
		setPriority(dto.getPriority());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.String name, java.lang.String key)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CommonSettingDataDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setName(java.lang.String name);

	public abstract void setKey(java.lang.String key);

	public abstract void setValue(java.lang.String value);

	public abstract void setDescription(java.lang.String description);

	public abstract void setStatus(java.lang.String status);
	
	public abstract java.lang.String getDefaultOrNot();
	
	public abstract void setDefaultOrNot(java.lang.String defaultOrNot);
	
	public abstract void setGrade(int grade);
	
	public abstract int getGrade();
	
	public abstract void setPriority(int priority);
	
	public abstract int getPriority();

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getName();

	public abstract java.lang.String getKey();

	public abstract java.lang.String getValue();

	public abstract java.lang.String getDescription();

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

	public int ejbUpdate(CommonSettingDataDTO dto) {
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