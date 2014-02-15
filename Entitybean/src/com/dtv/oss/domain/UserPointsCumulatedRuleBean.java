package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.UserPointsCumulatedRuleDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class UserPointsCumulatedRuleBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer id) throws CreateException {
    //setId(id);
    return null;
  }
  public java.lang.Integer ejbCreate(UserPointsCumulatedRuleDTO dto) throws CreateException {
  	  setDescr(dto.getDescr());
      setCustTypeList(dto.getCustTypeList());
      setCondEvent(dto.getCondEvent());
      setCondValue1(dto.getCondValue1());
      setProductID(dto.getProductID());
      setCondValue2(dto.getCondValue2());
      setAddedPoints(dto.getAddedPoints());
      setStatus(dto.getStatus());
      setDtCreate(new Timestamp(System.currentTimeMillis()));
	  setDtLastmod(new Timestamp(System.currentTimeMillis()));
      return null;
  }
  public void ejbPostCreate(java.lang.Integer id) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(UserPointsCumulatedRuleDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setId(java.lang.Integer id);
  public abstract void setProductID(int productID);
  public abstract void setDescr(java.lang.String descr);
  public abstract void setCustTypeList(java.lang.String custTypeList);
  public abstract void setCondEvent(int condEvent);
  public abstract void setCondValue1(double condvalue1);
  public abstract void setCondValue2(double condValue2);
  public abstract void setAddedPoints(int addedPoints);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getId();
  public abstract java.lang.String getDescr();
  public abstract java.lang.String getCustTypeList();
  public abstract int getCondEvent();
  public abstract int getProductID();
  public abstract double getCondValue1();
  public abstract double getCondValue2();
  public abstract int getAddedPoints();
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
  public int ejbUpdate(UserPointsCumulatedRuleDTO dto){
    /** @todo Complete this method */
    if(dto.getDtLastmod()==null){
      return -1;
    }

    if(!dto.getDtLastmod().equals(getDtLastmod())){

      return -1;
    } else{
      try{
            EntityBeanAutoUpdate.update(dto, this);
      } catch(Exception e){
            e.printStackTrace();
            return -1;
      }
      setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
      return 0;
    }
}
}
