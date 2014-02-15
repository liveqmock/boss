package com.dtv.oss.domain;

import javax.ejb.*;

import com.dtv.oss.dto.CustProblemFeeSettingDTO;

abstract public class CustProblemFeeSettingBean implements EntityBean {
  EntityContext entityContext;
  public CustProblemFeeSettingPK ejbCreate(java.lang.String problemLevel, java.lang.String problemCategory, int eventClass) throws CreateException {
    
    return null;
  }
  public CustProblemFeeSettingPK ejbCreate(CustProblemFeeSettingDTO dto) throws CreateException {
    /**@todo Complete this method*/
  	setProblemLevel(dto.getProblemLevel());
    setProblemCategory(dto.getProblemCategory());
    setEventClass(dto.getEventClass());
    setStatus(dto.getStatus());
	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
	setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.String problemLevel, java.lang.String problemCategory, int eventClass) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(CustProblemFeeSettingDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setProblemLevel(java.lang.String problemLevel);
  public abstract void setProblemCategory(java.lang.String problemCategory);
  public abstract void setEventClass(int eventClass);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.String getProblemLevel();
  public abstract java.lang.String getProblemCategory();
  public abstract int getEventClass();
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
}