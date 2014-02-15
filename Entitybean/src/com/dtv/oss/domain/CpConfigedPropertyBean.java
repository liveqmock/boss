package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CpConfigedPropertyDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CpConfigedPropertyBean implements EntityBean {
	EntityContext entityContext;

	public CpConfigedPropertyPK ejbCreate(Integer psID,
			java.lang.String propertyCode) throws CreateException {
		return null;
	}

	public CpConfigedPropertyPK ejbCreate(CpConfigedPropertyDTO dto)
			throws CreateException {
		setPsID(new Integer(dto.getPsID()));
		setPropertyCode(dto.getPropertyCode());
		setPropertyValue(dto.getPropertyValue());
		setStatus(dto.getStatus());
		setStatusTime(dto.getStatusTime());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(Integer psID, java.lang.String propertyCode)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CpConfigedPropertyDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setPsID(Integer psID);

	 
	
	public abstract void setPropertyValue(java.lang.String propertyValue);

	public abstract void setPropertyCode(java.lang.String propertyCode);

	 

	public abstract void setStatus(java.lang.String status);
	
	public abstract void setStatusTime(java.sql.Timestamp statusTime);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract Integer getPsID();

	 
	public abstract java.lang.String getPropertyValue();

	public abstract java.lang.String getPropertyCode();

	 

	public abstract java.lang.String getStatus();
	
	public abstract java.sql.Timestamp getStatusTime();

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

	public int ejbUpdate(CpConfigedPropertyDTO dto) {
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