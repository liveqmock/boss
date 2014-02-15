package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CatvTerminalBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.String ejbCreate(CatvTerminalDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		 setId(dto.getId());
		 
		setConBatchID(dto.getConBatchID());
		setStatus(dto.getStatus());
		setRecordSource(dto.getRecordSource());
		setStatusReason(dto.getStatusReason());
		setCatvTermType(dto.getCatvTermType());
		setDetailedAddress(dto.getDetailedAddress());
		setPostcode(dto.getPostcode());
		setDistrictID(dto.getDistrictID());
		setPortNo(dto.getPortNo());
		setFiberNodeID(dto.getFiberNodeID());
		setCableType(dto.getCableType());
		setBiDirectionFlag(dto.getBiDirectionFlag());
		setCreateDate(dto.getCreateDate());
		setActiveDate(dto.getActiveDate());
		setPauseDate(dto.getPauseDate());
		setComments(dto.getComments());
		setCancelDate(dto.getCancelDate());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.String id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CatvTerminalDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.String id);

	//public abstract void setCatvCode(java.lang.String catvCode);

	public abstract void setConBatchID(int conBatchID);

	public abstract void setStatus(java.lang.String status);
	
	public abstract void setComments(java.lang.String comments);
	
	public abstract java.lang.String getComments();

	public abstract void setRecordSource(java.lang.String recordSource);

	public abstract void setStatusReason(java.lang.String statusReason);

	public abstract void setCatvTermType(java.lang.String catvTermType);
	
	public abstract java.lang.String getCatvTermType();

	public abstract void setDetailedAddress(java.lang.String detailedAddress);

	public abstract void setPostcode(java.lang.String postcode);

	public abstract void setDistrictID(int districtID);
	
	public abstract int getDistrictID();
	
    public abstract void setPortNo(int portNo);

	public abstract void setFiberNodeID(int fiberNodeID);

	public abstract void setCableType(java.lang.String cableType);

	public abstract void setBiDirectionFlag(java.lang.String biDirectionFlag);

	public abstract void setCreateDate(Timestamp createDate);

	public abstract void setActiveDate(Timestamp activeDate);

	public abstract void setPauseDate(Timestamp pauseDate);

	public abstract void setDtCreate(Timestamp dtCreate);

	public abstract void setDtLastmod(Timestamp dtLastmod);

	public abstract java.lang.String getId();

	//public abstract java.lang.String getCatvCode();

	public abstract int getConBatchID();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getRecordSource();

	public abstract java.lang.String getStatusReason();

	public abstract void setCancelDate(Timestamp cancelDate); 
	
	public abstract Timestamp getCancelDate(); 

	public abstract java.lang.String getDetailedAddress();

	public abstract java.lang.String getPostcode();

	public abstract int getPortNo();

	public abstract int getFiberNodeID();

	public abstract java.lang.String getCableType();

	public abstract java.lang.String getBiDirectionFlag();

	public abstract Timestamp getCreateDate();

	public abstract Timestamp getActiveDate();

	public abstract Timestamp getPauseDate();

	public abstract Timestamp getDtCreate();

	public abstract Timestamp getDtLastmod();

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

	public int ejbUpdate(CatvTerminalDTO dto) {
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