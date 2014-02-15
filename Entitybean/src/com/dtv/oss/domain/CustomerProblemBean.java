package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustomerProblemDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustomerProblemBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(CustomerProblemDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		//setId(dto.getId());
		 setCreateOpId(dto.getCreateOpId()); 
		 setCreateOrgID(dto.getCreateOrgID());   
		setFeeClass(dto.getFeeClass());
        setProcessOrgId(dto.getProcessOrgId()); 
       
		setProblemLevel(dto.getProblemLevel());
		setReferJobCardID(dto.getReferJobCardID());
        setCustAccountID(dto.getCustAccountID());  
		setCustID(dto.getCustID());
		setCustServiceAccountID(dto.getCustServiceAccountID());

		setContactPerson(dto.getContactPerson());
		setContactphone(dto.getContactphone());
		setProblemDesc(dto.getProblemDesc());
		
		setCallBackDate(dto.getCallBackDate());

		setCreateDate(dto.getCreateDate());
		setDueDate(dto.getDueDate());

		setStatus(dto.getStatus());
		setCallBackFlag(dto.getCallBackFlag());

		setAddressID(dto.getAddressID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		setProblemCategory(dto.getProblemCategory());
		setIsManualTransfer(dto.getIsManualTransfer());
		setManualTransferFromOrgID(dto.getManualTransferFromOrgID());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustomerProblemDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setManualTransferFromOrgID(int manualTransferFromOrgID);
	
	public abstract int getManualTransferFromOrgID();
	
    public abstract void setIsManualTransfer(String isManualTransfer);
	
	public abstract String getIsManualTransfer();
	
	public abstract void setCreateOpId(int createOpId);

	public abstract void setCreateOrgID(int createOrgID);
	
	public abstract int getCreateOpId();

	public abstract int getCreateOrgID();
	
	public abstract void setId(java.lang.Integer id);

	public abstract void setFeeClass(java.lang.String feeClass);

	public abstract void setProblemLevel(java.lang.String problemLevel);

	public abstract void setReferJobCardID(int referJobCardID);

	public abstract void setCustID(int custID);
	
	public abstract void setCustAccountID(int custAccountID);
	
	public abstract int getCustAccountID();
	
	public abstract void setProcessOrgId(int processOrgId);


	public abstract void setCustServiceAccountID(int custServiceAccountID);

	public abstract void setContactPerson(java.lang.String contactPerson);

	public abstract void setContactphone(java.lang.String contactphone);

	public abstract void setCreateDate(java.sql.Timestamp createDate);

	public abstract void setDueDate(java.sql.Timestamp dueDate);
	
	public abstract void setCallBackDate(java.sql.Timestamp callBackDate);
	
	public abstract java.sql.Timestamp getCallBackDate();

	public abstract void setStatus(java.lang.String status);

	public abstract void setCallBackFlag(java.lang.String callBackFlag);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setAddressID(int addressID);

	public abstract void setProblemCategory(java.lang.String problemCategory);

	public abstract void setProblemDesc(java.lang.String problemDesc);

	public abstract java.lang.Integer getId();

	public abstract java.lang.String getProblemLevel();

	public abstract int getReferJobCardID();

	public abstract int getCustID();
	
	public abstract int getProcessOrgId();

	public abstract int getCustServiceAccountID();

	public abstract java.lang.String getFeeClass();

	public abstract java.lang.String getContactPerson();

	public abstract java.lang.String getContactphone();

	public abstract java.sql.Timestamp getCreateDate();

	public abstract java.sql.Timestamp getDueDate();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getCallBackFlag();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getAddressID();

	public abstract java.lang.String getProblemCategory();

	public abstract java.lang.String getProblemDesc();

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

	public int ejbUpdate(CustomerProblemDTO dto) {
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