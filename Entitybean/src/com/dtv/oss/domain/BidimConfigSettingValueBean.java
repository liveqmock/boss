package com.dtv.oss.domain;

import javax.ejb.*;
import com.dtv.oss.dto.BidimConfigSettingValueDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BidimConfigSettingValueBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer id) throws CreateException {
    //setId(id);
    return null;
  }
  public java.lang.Integer ejbCreate(BidimConfigSettingValueDTO dto) throws CreateException {
    setSettingId(dto.getSettingId());
    setDescription(dto.getDescription());
    setCode(dto.getCode());
    setStatus(dto.getStatus());
    setDefaultOrNot(dto.getDefaultOrNot());
	setPriority(dto.getPriority());
    setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
    setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
   return null;
 }

  public void ejbPostCreate(java.lang.Integer id) throws CreateException {
    /**@todo Complete this method*/
  }

   public void ejbPostCreate(BidimConfigSettingValueDTO dto) throws CreateException {
                /** @todo Complete this method */
        }

  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setId(java.lang.Integer id);
  public abstract void setSettingId(int settingId);
  public abstract void setCode(java.lang.String code);
  public abstract void setDescription(java.lang.String description);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getId();
  public abstract int getSettingId();
  public abstract java.lang.String getCode();
  public abstract java.lang.String getDescription();
  public abstract java.lang.String getStatus();
  public abstract java.sql.Timestamp getDtCreate();
  public abstract java.sql.Timestamp getDtLastmod();
  public abstract java.lang.String getDefaultOrNot();
  public abstract void setDefaultOrNot(java.lang.String defaltOrNot);
  public abstract void setPriority(int priority);
  public abstract int getPriority();

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
  public int ejbUpdate(BidimConfigSettingValueDTO dto) {
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
