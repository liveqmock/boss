package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ProductPropertyDTO;

public interface ProductProperty extends javax.ejb.EJBLocalObject {
  public void setDescription(String description);
  public String getDescription();
  public void setResourceName(String resourceName);
  public String getResourceName();
  public void setPropertyMode(String propertyMode);
  public String getPropertyMode();
  public void setPropertyCode(String propertyCode);
  public String getPropertyCode();
  public void setPropertyType(String propertyType);
  public String getPropertyType();
  public void setPropertyValue(String propertyValue);
  public String getPropertyValue();
  
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public int getProductId();
  public String getPropertyName();
//  public void setPropertyName(String propertyName);
  public int ejbUpdate(ProductPropertyDTO dto);
}