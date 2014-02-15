package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ForcedDepositDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ForcedDepositBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		// setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(ForcedDepositDTO dto)
			throws CreateException {
		setCustomerID(dto.getCustomerID());
		setUserID(dto.getUserID());
		setCsiID(dto.getCsiID());
		setStatus(dto.getStatus());
		setMoney(dto.getMoney());
		setFromDate(dto.getFromDate());
		setToDate(dto.getToDate());
		setRefersheetID(dto.getRefersheetID());
		setDescription(dto.getDescription());
		setCreateOperator(dto.getCreateOperator());
		setCreateorgID(dto.getCreateorgID());
		setCreateTime(dto.getCreateTime());
		setWithdrawCsiID(dto.getWithdrawCsiID());
		setWithdrawMoney(dto.getWithdrawMoney());

		setSeizureMoney(dto.getSeizureMoney());
		setProcessOperatorID(dto.getProcessOperatorID());
		setProcessOrgID(dto.getProcessOrgID());
		setProcessTime(dto.getProcessTime());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	 

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ForcedDepositDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setCustomerID(int customerID);
	
	public abstract void setWithdrawCsiID(int withdrawCsiID);

	public abstract void setUserID(int userID);

	public abstract void setCsiID(int csiID);

	public abstract void setStatus(java.lang.String status);

	public abstract void setMoney(double money);

	public abstract void setFromDate(java.sql.Timestamp fromDate);

	public abstract void setToDate(java.sql.Timestamp toDate);

	public abstract void setRefersheetID(java.lang.String refersheetID);

	public abstract void setDescription(java.lang.String description);

	public abstract void setCreateOperator(int createOperator);

	public abstract void setCreateorgID(int createorgID);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setWithdrawMoney(double withdrawMoney);

	public abstract void setSeizureMoney(double seizureMoney);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setProcessOperatorID(int processOperatorID);

	public abstract void setProcessOrgID(int processOrgID);

	public abstract void setProcessTime(java.sql.Timestamp processTime);

	public abstract java.lang.Integer getSeqNo();

	public abstract int getCustomerID();

	public abstract int getUserID();

	public abstract int getCsiID();

	public abstract java.lang.String getStatus();

	public abstract double getMoney();

	public abstract java.sql.Timestamp getFromDate();

	public abstract java.sql.Timestamp getToDate();

	public abstract java.lang.String getRefersheetID();

	public abstract java.lang.String getDescription();

	public abstract int getCreateOperator();

	public abstract int getCreateorgID();
	
	public abstract int getWithdrawCsiID();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract double getWithdrawMoney();

	public abstract double getSeizureMoney();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getProcessOperatorID();

	public abstract int getProcessOrgID();

	public abstract java.sql.Timestamp getProcessTime();

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

	public int ejbUpdate(ForcedDepositDTO dto) {
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