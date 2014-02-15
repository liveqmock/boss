package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BankMatchHeaderDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BankMatchHeaderBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		// setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(BankMatchHeaderDTO dto)
			throws CreateException {
		setMopId(dto.getMopId());
		setInterfaceId(dto.getInterfaceId());
		setBatchNo(dto.getBatchNo());
		setStartTime(dto.getStartTime());
		setEndTime(dto.getEndTime());
		setOperatorId(dto.getOperatorId());
		setOrgId(dto.getOrgId());
		setStatus(dto.getStatus());
		setInputFileName(dto.getInputFileName());
		setTotalRecordNo(dto.getTotalRecordNo());
		setInvalidRecordNo(dto.getInvalidRecordNo());
		setSuccessRecordNo(dto.getSuccessRecordNo());
		setFailRecordNo(dto.getFailRecordNo());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BankMatchHeaderDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setMopId(int mopId);

	public abstract void setInterfaceId(int interfaceId);

	public abstract void setBatchNo(int batchNo);

	public abstract void setStartTime(java.sql.Timestamp startTime);

	public abstract void setEndTime(java.sql.Timestamp endTime);

	public abstract void setOperatorId(int operatorId);

	public abstract void setOrgId(int orgId);

	public abstract void setStatus(java.lang.String status);

	public abstract void setInputFileName(java.lang.String inputFileName);

	public abstract void setTotalRecordNo(int totalRecordNo);

	public abstract void setInvalidRecordNo(int invalidRecordNo);

	public abstract void setSuccessRecordNo(int successRecordNo);

	public abstract void setFailRecordNo(int failRecordNo);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getMopId();

	public abstract int getInterfaceId();

	public abstract int getBatchNo();

	public abstract java.sql.Timestamp getStartTime();

	public abstract java.sql.Timestamp getEndTime();

	public abstract int getOperatorId();

	public abstract int getOrgId();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getInputFileName();

	public abstract int getTotalRecordNo();

	public abstract int getInvalidRecordNo();

	public abstract int getSuccessRecordNo();

	public abstract int getFailRecordNo();

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

	public int ejbUpdate(BankMatchHeaderDTO dto) {
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