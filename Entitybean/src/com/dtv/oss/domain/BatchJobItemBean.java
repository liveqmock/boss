package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BatchJobItemDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BatchJobItemBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer itemNO) throws CreateException {
    //setItemNO(itemNO);
    return null;
  }
  public java.lang.Integer ejbCreate(BatchJobItemDTO dto) throws CreateException {
    /**@todo Complete this method*/
  	setBatchId(dto.getBatchId());
  	setCustomerId(dto.getCustomerId());
    setAccountId(dto.getAccountId());
    setUserId(dto.getUserId());
    setCustPackageId(dto.getCustPackageId());
    setPsId(dto.getPsId());
    setCcId(dto.getCcId());
    setRcdData(dto.getRcdData());
    setStatus(dto.getStatus());
    setStatusTime(dto.getStatusTime());
    setDtCreate(new Timestamp(System.currentTimeMillis()));
	setDtLastmod(new Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer itemNO) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(BatchJobItemDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setItemNO(java.lang.Integer itemNO);
  public abstract void setBatchId(int batchId);
  public abstract void setCustomerId(int customerId);
  public abstract void setAccountId(int accountId);
  public abstract void setUserId(int userId);
  public abstract void setCustPackageId(int custPackageId);
  public abstract void setPsId(int psId);
  public abstract void setCcId(int ccId);
  public abstract void setRcdData(java.lang.String rcdData);
  public abstract void setStatus(java.lang.String status);
  public abstract void setStatusTime(java.sql.Timestamp statusTime);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getItemNO();
  public abstract int getBatchId();
  public abstract int getCustomerId();
  public abstract int getAccountId();
  public abstract int getUserId();
  public abstract int getCustPackageId();
  public abstract int getPsId();
  public abstract int getCcId();
  public abstract java.lang.String getRcdData();
  public abstract java.lang.String getStatus();
  public abstract java.sql.Timestamp getStatusTime();
  public abstract java.sql.Timestamp getDtCreate();
  public abstract java.sql.Timestamp getDtLastmod();
  public void ejbLoad() {
    /**@todo Complete this method*/
  }
  public void ejbStore() {
    /**@todo Complete this method*/
  }
  public void ejbActivate() {
    /**@todo Complete this method*/
  }
  public void ejbPassivate() {
    /**@todo Complete this method*/
  }
  public void unsetEntityContext() {
    this.entityContext = null;
  }
  public void setEntityContext(EntityContext entityContext) {
    this.entityContext = entityContext;
  }
  public int ejbUpdate(BatchJobItemDTO dto) {
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