package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.*;

import com.dtv.oss.dto.BadDebtProcessLogDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BadDebtProcessLogBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer pId) throws CreateException {
    //setPId(pId);
    return null;
  }
  public java.lang.Integer ejbCreate(BadDebtProcessLogDTO dto) throws CreateException {
  	 setBadDebtNo(dto.getBadDebtNo());
     setOpId(dto.getOpId());
     setOrgId(dto.getOrgId());
     setCreateTime(dto.getCreateTime());
     setAction(dto.getAction());
     setDescription(dto.getDescription());
     setStatus(dto.getStatus());
     setDtCreate(new Timestamp(System.currentTimeMillis()));
	 setDtLastmod(new Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer pId) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(BadDebtProcessLogDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setPId(java.lang.Integer pId);
  public abstract void setBadDebtNo(int badDebtNo);
  public abstract void setOpId(int opId);
  public abstract void setOrgId(int orgId);
  public abstract void setCreateTime(java.sql.Timestamp createTime);
  public abstract void setAction(java.lang.String action);
  public abstract void setDescription(java.lang.String description);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getPId();
  public abstract int getBadDebtNo();
  public abstract int getOpId();
  public abstract int getOrgId();
  public abstract java.sql.Timestamp getCreateTime();
  public abstract java.lang.String getAction();
  public abstract java.lang.String getDescription();
  public abstract java.lang.String getStatus();
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
  public int ejbUpdate(BadDebtProcessLogDTO dto) {
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