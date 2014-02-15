package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BatchJobProcessLogDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BatchJobProcessLogBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer seqNO) throws CreateException {
    //setSeqNO(seqNO);
    return null;
  }
  public java.lang.Integer ejbCreate(BatchJobProcessLogDTO dto) throws CreateException {
    /**@todo Complete this method*/
  	   setBatchId(dto.getBatchId());
  	   setItemNO(dto.getItemNO());
  	   setAction(dto.getAction());
  	   setResult(dto.getResult());
  	   setComments(dto.getComments());
  	   setOperatorId(dto.getOperatorId());
  	   setOrgId(dto.getOrgId());
  	   setCreateTime(dto.getCreateTime());
  	   setDtCreate(new Timestamp(System.currentTimeMillis()));
 	  setDtLastmod(new Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer seqNO) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(BatchJobProcessLogDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setSeqNO(java.lang.Integer seqNO);
  public abstract void setBatchId(int batchId);
  public abstract void setItemNO(int itemNO);
  public abstract void setAction(java.lang.String action);
  public abstract void setResult(java.lang.String result);
  public abstract void setComments(java.lang.String comments);
  public abstract void setOperatorId(int operatorId);
  public abstract void setOrgId(int orgId);
  public abstract void setCreateTime(java.sql.Timestamp createTime);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getSeqNO();
  public abstract int getBatchId();
  public abstract int getItemNO();
  public abstract java.lang.String getAction();
  public abstract java.lang.String getResult();
  public abstract java.lang.String getComments();
  public abstract int getOperatorId();
  public abstract int getOrgId();
  public abstract java.sql.Timestamp getCreateTime();
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
  public int ejbUpdate(BatchJobProcessLogDTO dto) {
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