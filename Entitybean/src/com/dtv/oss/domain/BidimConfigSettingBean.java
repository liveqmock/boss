package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BidimConfigSettingDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BidimConfigSettingBean implements EntityBean {
   EntityContext entityContext;

  public java.lang.Integer ejbCreate(java.lang.Integer id) throws CreateException {
   // setId(id);
    return null;
  }
  public java.lang.Integer ejbCreate(BidimConfigSettingDTO dto) throws CreateException {
     setName(dto.getName());
     setDescription(dto.getDescription());
     setConfigType(dto.getConfigType());
     setConfigSubType(dto.getConfigSubType());
     setServiceId(dto.getServiceId());
     setValueType(dto.getValueType());
     setAllowNull(dto.getAllowNull());
     setStatus(dto.getStatus());
     setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
     setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    return null;
  }

  public void ejbPostCreate(java.lang.Integer id) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(BidimConfigSettingDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  
  public abstract void setId(java.lang.Integer id);
  public abstract void setName(java.lang.String name);
  public abstract void setDescription(java.lang.String description);
  public abstract void setConfigType(java.lang.String configType);
  public abstract void setConfigSubType(java.lang.String configSubType);
  public abstract void setServiceId(int serviceId);
  public abstract void setValueType(java.lang.String valueType);
  public abstract void setAllowNull(java.lang.String allowNull);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getId();
  public abstract java.lang.String getName();
  public abstract java.lang.String getDescription();
  public abstract java.lang.String getConfigType();
  public abstract java.lang.String getConfigSubType();
  public abstract int getServiceId();
  public abstract java.lang.String getValueType();
  public abstract java.lang.String getAllowNull();
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
  public int ejbUpdate(BidimConfigSettingDTO dto) {
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