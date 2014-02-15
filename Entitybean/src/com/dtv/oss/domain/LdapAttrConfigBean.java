package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapAttrConfigDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class LdapAttrConfigBean implements EntityBean {
	EntityContext entityContext;

	public String ejbCreate(String attrName)
			throws CreateException {
		//setAddressID(addressID);
		return null;
	}

	public String ejbCreate(LdapAttrConfigDTO dto) throws CreateException {
		/** @todo Complete this method */
		setAttrName(dto.getAttrName());
		setFixedFlag(dto.getFixedFlag());
		setFixedValue(dto.getFixedValue());
		setLength(dto.getLength());
		setPrefix(dto.getPrefix());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(String attrName)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(LdapAttrConfigDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setAttrName(java.lang.String attrName);

	public abstract void setFixedFlag(java.lang.String fixedFlag);

	public abstract void setFixedValue(java.lang.String fixedValue);
	
	public abstract void setLength(int length);
	
	public abstract void setPrefix(java.lang.String prefix);
	
	public abstract void setStatus(java.lang.String status);
	
    public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getAttrName();

	public abstract java.lang.String getFixedFlag();

	public abstract java.lang.String getFixedValue();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	public abstract java.lang.String getPrefix();

	public abstract java.lang.String getStatus();

	public abstract int getLength();
	
	

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

	public int ejbUpdate(LdapAttrConfigDTO dto) {
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