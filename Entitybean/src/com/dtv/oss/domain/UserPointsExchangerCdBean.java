package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.UserPointsExchangerCdDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class UserPointsExchangerCdBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer id) throws CreateException {
   // setId(id);
    return null;
  }
  public java.lang.Integer ejbCreate(UserPointsExchangerCdDTO dto) throws CreateException {
  	  setActivityId(dto.getActivityId());
  	  setCreateTime(dto.getCreateTime());
  	  setCreateOperatorId(dto.getCreateOperatorId());
  	  setUserId(dto.getUserId());
  	  setAccountId(dto.getAccountId());
  	  setUserPointsBefore(dto.getUserPointsBefore());
  	   setUserPointPost(dto.getUserPointPost());
  	   setExchangePoints(dto.getExchangePoints());
  	  setExchangeGoodsTypeId(dto.getExchangeGoodsTypeId());
  	   setExchangeGoodsAmount(dto.getExchangeGoodsAmount());
  	 setDtCreate(new Timestamp(System.currentTimeMillis()));
	   setDtLastmod(new Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer id) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(UserPointsExchangerCdDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setId(java.lang.Integer id);
  public abstract void setActivityId(int activityId);
  public abstract void setCreateTime(java.sql.Timestamp createTime);
  public abstract void setCreateOperatorId(int createOperatorId);
  public abstract void setUserId(int userId);
  public abstract void setAccountId(int accountId);
  public abstract void setUserPointsBefore(int userPointsBefore);
  public abstract void setUserPointPost(int userPointPost);
  public abstract void setExchangePoints(int exchangePoints);
  public abstract void setExchangeGoodsTypeId(int exchangeGoodsTypeId);
  public abstract void setExchangeGoodsAmount(int exchangeGoodsAmount);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getId();
  public abstract int getActivityId();
  public abstract java.sql.Timestamp getCreateTime();
  public abstract int getCreateOperatorId();
  public abstract int getUserId();
  public abstract int getAccountId();
  public abstract int getUserPointsBefore();
  public abstract int getUserPointPost();
  public abstract int getExchangePoints();
  public abstract int getExchangeGoodsTypeId();
  public abstract int getExchangeGoodsAmount();
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
  public int ejbUpdate(UserPointsExchangerCdDTO dto) {
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