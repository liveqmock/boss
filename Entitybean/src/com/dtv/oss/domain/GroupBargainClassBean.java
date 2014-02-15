package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.GroupBargainClassDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class GroupBargainClassBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		// setID(iD);
		return null;
	}

	public java.lang.Integer ejbCreate(GroupBargainClassDTO dto)
			throws CreateException {
		setName(dto.getName());
		setDescription(dto.getDescription());
		setKeepBilling(dto.getKeepBilling());
		setNewProductPackage1(dto.getNewProductPackage1());
		setNewProductPackage2(dto.getNewProductPackage2());
		setNewProductPackage3(dto.getNewProductPackage3());
		setNewProductPackage4(dto.getNewProductPackage4());
		setNewProductPackage5(dto.getNewProductPackage5());
		setTimeLengthUnitType(dto.getTimeLengthUnitType());
		setTimeLengthUnitNumber(dto.getTimeLengthUnitNumber());
		setAllowPause(dto.getAllowPause());
		setAllTransition(dto.getAllTransition());
		setAllowTransfer(dto.getAllowTransfer());
		setAllowCancel(dto.getAllowCancel());
		setStatus(dto.getStatus());
		setCreateOpID(dto.getCreateOpID());
		setCreateTime(dto.getCreateTime());
		setUpdateOpID(dto.getUpdateOpID());
		setUpdateTime(dto.getUpdateTime());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer ID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(GroupBargainClassDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setName(java.lang.String name);

	public abstract void setKeepBilling(java.lang.String keepBilling);

	public abstract void setDescription(java.lang.String description);

	public abstract void setNewProductPackage1(int newProductPackage1);

	public abstract void setNewProductPackage2(int newProductPackage2);

	public abstract void setNewProductPackage3(int newProductPackage3);

	public abstract void setNewProductPackage4(int newProductPackage4);

	public abstract void setNewProductPackage5(int newProductPackage5);

	public abstract void setTimeLengthUnitType(
			java.lang.String timeLengthUnitType);

	public abstract void setTimeLengthUnitNumber(int timeLengthUnitNumber);

	public abstract void setAllowPause(java.lang.String allowPause);

	public abstract void setAllTransition(java.lang.String allTransition);

	public abstract void setAllowTransfer(java.lang.String allowTransfer);

	public abstract void setAllowCancel(java.lang.String allowCancel);

	public abstract void setStatus(java.lang.String status);

	public abstract void setCreateOpID(int createOpID);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setUpdateOpID(int updateOpID);

	public abstract void setUpdateTime(java.sql.Timestamp updateTime);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract java.lang.String getName();

	public abstract java.lang.String getKeepBilling();

	public abstract java.lang.String getDescription();

	public abstract int getNewProductPackage1();

	public abstract int getNewProductPackage2();

	public abstract int getNewProductPackage3();

	public abstract int getNewProductPackage4();

	public abstract int getNewProductPackage5();

	public abstract java.lang.String getTimeLengthUnitType();

	public abstract int getTimeLengthUnitNumber();

	public abstract java.lang.String getAllowPause();

	public abstract java.lang.String getAllTransition();

	public abstract java.lang.String getAllowTransfer();

	public abstract java.lang.String getAllowCancel();

	public abstract java.lang.String getStatus();

	public abstract int getCreateOpID();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract int getUpdateOpID();

	public abstract java.sql.Timestamp getUpdateTime();

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

	public int ejbUpdate(GroupBargainClassDTO dto) {
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