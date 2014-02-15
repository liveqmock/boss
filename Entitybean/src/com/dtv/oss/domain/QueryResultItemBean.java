package com.dtv.oss.domain;
import com.dtv.oss.dto.QueryResultItemDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

import javax.ejb.*;

abstract public class QueryResultItemBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer itemNO) throws CreateException {
    //setItemNO(itemNO);
    return null;
  }
  public java.lang.Integer ejbCreate(QueryResultItemDTO dto) throws CreateException {
    /**@todo Complete this method*/
   setQueryId(dto.getQueryId());
   setCustomerId(dto.getCustomerId());
   setAccountId(dto.getAccountId());
   setUserId(dto.getUserId());
   setPackageId(dto.getPackageId());
   setProductId(dto.getProductId());
   setPsId(dto.getPsId());
   setCcId(dto.getCcId());
   setStatus(dto.getStatus());
   setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
  setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

    return null;
  }
  public void ejbPostCreate(java.lang.Integer itemNO) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(QueryResultItemDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setItemNO(java.lang.Integer itemNO);
  public abstract void setQueryId(int queryId);
  public abstract void setCustomerId(int customerId);
  public abstract void setAccountId(int accountId);
  public abstract void setUserId(int userId);
  public abstract void setPackageId(int packageId);
  public abstract void setProductId(int productId);
  public abstract void setPsId(int psId);
  public abstract void setCcId(int ccId);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  
  public abstract java.lang.Integer getItemNO();
  public abstract int getQueryId();
  public abstract int getCustomerId();
  public abstract int getAccountId();
  public abstract int getUserId();
  public abstract int getPackageId();
  public abstract int getPsId();
  public abstract int getCcId();
  
  public abstract java.lang.String getStatus();
  public abstract int getProductId();
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
  public int ejbHomeEjbUpdate(QueryResultItemDTO dto) {
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
