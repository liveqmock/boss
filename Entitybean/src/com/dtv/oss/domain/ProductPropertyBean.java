package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ProductPropertyBean implements EntityBean {
  EntityContext entityContext;
  public ProductPropertyPK ejbCreate(int productId, java.lang.String propertyName) throws CreateException {
    
    return null;
  }
  public ProductPropertyPK ejbCreate(ProductPropertyDTO dto) throws CreateException {
  	  setProductId(dto.getProductId());
      setPropertyName(dto.getPropertyName());
      setDescription(dto.getDescription());
      setResourceName(dto.getResourceName());
      setPropertyMode(dto.getPropertyMode());
      setPropertyType(dto.getPropertyType());
      setPropertyValue(dto.getPropertyValue());
      setPropertyCode(dto.getPropertyCode());
      setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
	  setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(int productId, java.lang.String propertyName) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(ProductPropertyDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setProductId(int productId);
  public abstract void setPropertyName(java.lang.String propertyName);
  public abstract void setDescription(java.lang.String description);
  public abstract void setResourceName(java.lang.String resourceName);
  public abstract void setPropertyMode(java.lang.String propertyMode);
  public abstract void setPropertyType(java.lang.String propertyType);
  public abstract void setPropertyValue(java.lang.String propertyValue);
  public abstract void setPropertyCode(java.lang.String propertyCode);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract int getProductId();
  public abstract java.lang.String getPropertyName();
  public abstract java.lang.String getDescription();
  public abstract java.lang.String getResourceName();
  public abstract java.lang.String getPropertyMode();
  public abstract java.lang.String getPropertyType();
  public abstract java.lang.String getPropertyValue();
  public abstract java.lang.String getPropertyCode();
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
  public int ejbUpdate(ProductPropertyDTO dto) {
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