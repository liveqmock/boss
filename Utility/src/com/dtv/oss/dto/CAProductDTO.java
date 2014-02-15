package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CAProductDTO
    implements ReflectionSupport, java.io.Serializable {
  private String entitlement;
  private String description;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int productId;
  private int conditionId;
  private int opiID;
  
  public String getEntitlement() {
    return entitlement;
  }
 
  public void setEntitlement(String entitlement) {
    this.entitlement = entitlement;
     put("entitlement");
  }
  public int getOpiID() {
    return opiID;
}

public void setOpiID(int opiID) {
    this.opiID = opiID;
    put("opiID");
}

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
     put("description");
  }

  public Timestamp getDtCreate() {
    return dtCreate;
  }

  public void setDtCreate(Timestamp dtCreate) {
    this.dtCreate = dtCreate;
  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public int getConditionId() {
    return conditionId;
  }

  public void setConditionId(int conditionId) {
    this.conditionId = conditionId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CAProductDTO that = (CAProductDTO) obj;
        return ( ( (this.getEntitlement() == null) && (that.getEntitlement() == null)) ||
                (this.getEntitlement() != null &&
                 this.getEntitlement().equals(that.getEntitlement()))) &&
            ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
             (this.getDescription() != null &&
              this.getDescription().equals(that.getDescription()))) &&
			  
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getProductId() == that.getProductId() &&
            this.getOpiID() == that.getOpiID() &&
            this.getConditionId() == that.getConditionId();
      }
    }
    return false;
  }

  public int hashCode() {
    return   toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
                buf.append(entitlement);
                buf.append(",").append(dtCreate);
                buf.append(",").append(dtLastmod);
                buf.append(",").append(description);
                 buf.append(",").append(conditionId);
                 buf.append(",").append(opiID);
                 buf.append(productId);

    return  buf.toString();


  }
  private java.util.Map map = new java.util.HashMap();

           public void put(String field) { map.put(field, Boolean.TRUE); }

           public java.util.Map getMap() { return this.map; }

   }

