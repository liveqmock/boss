package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BankMatchDetailDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BankMatchDetailBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		//setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(BankMatchDetailDTO dto)
			throws CreateException {
		setBatchId(dto.getBatchId());
		setCreateTime(dto.getCreateTime());
		setCustomerNoFromDb(dto.getCustomerNoFromDb());
		setCustomerNoFromFile(dto.getCustomerNoFromFile());
		setAccountNoFromDb(dto.getAccountNoFromDb());
		setAccountNoFromFile(dto.getAccountNoFromFile());
		setBankAccountFromDb(dto.getBankAccountFromDb());
		setBankAccountFromFile(dto.getBankAccountFromFile());
		setOrgStatus(dto.getOrgStatus());
		setNewStatus(dto.getNewStatus());
		setStatus(dto.getStatus());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BankMatchDetailDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setBatchId(int batchId);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setCustomerNoFromDb(int customerNoFromDb);

	public abstract void setCustomerNoFromFile(int customerNoFromFile);

	public abstract void setAccountNoFromDb(int accountNoFromDb);

	public abstract void setAccountNoFromFile(int accountNoFromFile);

	public abstract void setBankAccountFromDb(java.lang.String bankAccountFromDb);

	public abstract void setBankAccountFromFile(
			java.lang.String bankAccountFromFile);

	public abstract void setOrgStatus(java.lang.String orgStatus);

	public abstract void setNewStatus(java.lang.String newStatus);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getSeqNo();

	public abstract int getBatchId();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract int getCustomerNoFromDb();

	public abstract int getCustomerNoFromFile();

	public abstract int getAccountNoFromDb();

	public abstract int getAccountNoFromFile();

	public abstract java.lang.String getBankAccountFromDb();

	public abstract java.lang.String getBankAccountFromFile();

	public abstract java.lang.String getOrgStatus();

	public abstract java.lang.String getNewStatus();

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

	public int ejbUpdate(BankMatchDetailDTO dto) {
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