package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.UserPointsExchangeCondDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class UserPointsExchangeCondBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer condId)
			throws CreateException {
		//setCondId(condId);
		return null;
	}

	public java.lang.Integer ejbCreate(UserPointsExchangeCondDTO dto)
			throws CreateException {
		setActivityId(dto.getActivityId());
		setCustTypeList(dto.getCustTypeList());
		setAccountClassList(dto.getAccountClassList());
		setMopIdList(dto.getMopIdList());
		setPointRange1(dto.getPointRange1());
		setPointRange2(dto.getPointRange2());
		setExchangeGoodsTypeID(dto.getExchangeGoodsTypeID());
		setStatus(dto.getStatus());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer condId) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(UserPointsExchangeCondDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setExchangeGoodsTypeID(int exchangeGoodsTypeID);
	
	public abstract void setStatus(String status);
	
	public abstract void setCondId(java.lang.Integer condId);

	public abstract void setActivityId(int activityId);

	public abstract void setCustTypeList(java.lang.String custTypeList);

	public abstract void setAccountClassList(java.lang.String accountClassList);

	public abstract void setMopIdList(java.lang.String mopIdList);

	public abstract void setPointRange1(int pointRange1);

	public abstract void setPointRange2(int pointRange2);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getCondId();

	public abstract int getActivityId();

	public abstract java.lang.String getCustTypeList();

	public abstract java.lang.String getAccountClassList();
	
	public abstract java.lang.String getStatus();

	public abstract int getExchangeGoodsTypeID();

	public abstract java.lang.String getMopIdList();

	public abstract int getPointRange1();

	public abstract int getPointRange2();

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
	public int ejbUpdate(UserPointsExchangeCondDTO dto) {
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