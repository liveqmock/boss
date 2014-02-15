package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.NewCustAccountInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class NewCustAccountInfoBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(NewCustAccountInfoDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		//setId(dto.getId());
		setCsiID(dto.getCsiID());
		setAccountID(dto.getAccountID());
		setDescription(dto.getDescription());
		setMopID(dto.getMopID());
		setBankAccountName(dto.getBankAccountName());
		setBankAccount(dto.getBankAccount());
		setAddressID(dto.getAddressID());
		setAddressFlag(dto.getAddressFlag());
		setAccountType(dto.getAccountType());
		setAccountName(dto.getAccountName());
		setComments(dto.getComments());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(NewCustAccountInfoDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	public  abstract void setComments(java.lang.String comments);
	
	public  abstract java.lang.String getComments();
	
	public abstract void setId(java.lang.Integer id);

	public abstract void setCsiID(int csiID);

	public abstract void setAccountID(int accountID);

	public abstract void setDescription(java.lang.String description);
	
	public abstract void setAccountName(java.lang.String accountName);
	
	public abstract java.lang.String getAccountName();

	public abstract void setMopID(int mopID);

	public abstract void setBankAccountName(java.lang.String bankAccountName);

	public abstract void setBankAccount(java.lang.String bankAccount);

	public abstract void setAddressID(int addressID);

	public abstract void setAddressFlag(java.lang.String addressFlag);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setAccountType(java.lang.String accountType);

	public abstract java.lang.Integer getId();

	public abstract int getCsiID();

	public abstract int getAccountID();

	public abstract java.lang.String getDescription();

	public abstract int getMopID();

	public abstract java.lang.String getBankAccountName();

	public abstract java.lang.String getBankAccount();

	public abstract int getAddressID();

	public abstract java.lang.String getAddressFlag();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getAccountType();

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

	public int ejbUpdate(NewCustAccountInfoDTO dto) {
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