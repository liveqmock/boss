package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.UserPointsExchangeRuleDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class UserPointsExchangeRuleBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer id) throws CreateException {
    //setId(id);
    return null;
  }
  public java.lang.Integer ejbCreate(UserPointsExchangeRuleDTO dto) throws CreateException {
  	  setActivityId(dto.getActivityId());
      setExchangeGoodsTypeId(dto.getExchangeGoodsTypeId());
      setExchangeGoodsAmount(dto.getExchangeGoodsAmount());
      setExchangePointsValue(dto.getExchangePointsValue());
      setStatus(dto.getStatus());
      setCustTypeList(dto.getCustTypeList());
      setDtCreate(new Timestamp(System.currentTimeMillis()));
	   setDtLastmod(new Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer id) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(UserPointsExchangeRuleDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setCustTypeList(String custTypeList);
  public abstract String getCustTypeList();
  public abstract void setId(java.lang.Integer id);
  public abstract void setActivityId(int activityId);
  public abstract void setExchangeGoodsTypeId(int exchangeGoodsTypeId);
  public abstract void setExchangeGoodsAmount(int exchangeGoodsAmount);
  public abstract void setExchangePointsValue(int exchangePointsValue);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getId();
  public abstract int getActivityId();
  public abstract int getExchangeGoodsTypeId();
  public abstract int getExchangeGoodsAmount();
  public abstract int getExchangePointsValue();
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
  public int ejbUpdate(UserPointsExchangeRuleDTO dto) {
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