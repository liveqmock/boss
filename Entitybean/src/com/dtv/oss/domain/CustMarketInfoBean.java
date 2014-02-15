package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustMarketInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustMarketInfoBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {

		return null;
	}

	public java.lang.Integer ejbCreate(CustMarketInfoDTO dto)
			throws CreateException {
		setCustomerId(dto.getCustomerId());
		setInfoValueId(dto.getInfoValueId());
		setInfoSettingId(dto.getInfoSettingId());
		setMemo(dto.getMemo());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustMarketInfoDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	
	public abstract void setMemo(String memo);


	public abstract void setCustomerId(int customerId);

	public abstract void setInfoValueId(int infoValueId);

	public abstract void setInfoSettingId(int infoSettingId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
	
	public abstract void setId(java.lang.Integer id);
	public abstract java.lang.Integer getId();
	

	public abstract int getCustomerId();

	public abstract int getInfoValueId();

	public abstract int getInfoSettingId();
	
	public abstract String getMemo();


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

	public int ejbUpdate(CustMarketInfoDTO dto) {
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