package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.NewCustomerMarketInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class NewCustomerMarketInfoBean implements EntityBean {
	EntityContext entityContext;

	public Integer ejbCreate(Integer ID) throws CreateException {
		 
		return null;
	}

	public Integer ejbCreate(NewCustomerMarketInfoDTO dto)
			throws CreateException {
		setNewCustomerId(dto.getNewCustomerId());
		setInfoSettingId(dto.getInfoSettingId());
		setInfoValueId(dto.getInfoValueId());
		setCsiID(dto.getCsiID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setMemo(dto.getMemo());
		return null;
	}

	public void ejbPostCreate(Integer ID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(NewCustomerMarketInfoDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setNewCustomerId(int newCustomerId);

	public abstract void setMemo(String memo);
	
	public abstract String getMemo();
	
	public abstract void setCsiID(int csiID);

	public abstract int getCsiID();
	
	public abstract void setID(Integer ID);

	public abstract Integer getID();
	
	public abstract void setInfoSettingId(int infoSettingId);

	public abstract void setInfoValueId(int infoValueId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getNewCustomerId();

	public abstract int getInfoSettingId();

	public abstract int getInfoValueId();

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

	public int ejbUpdate(NewCustomerMarketInfoDTO dto) {
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