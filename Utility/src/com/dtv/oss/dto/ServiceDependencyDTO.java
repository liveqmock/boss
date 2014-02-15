package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ServiceDependencyDTO
    implements ReflectionSupport, java.io.Serializable {
  private String type;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int serviceId;
  private int referServiceId;
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
    put("type");
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

  public int getServiceId() {
    return serviceId;
  }

  public void setServiceId(int serviceId) {
    this.serviceId = serviceId;
    put("serviceId");
  }

  public int getReferServiceId() {
    return referServiceId;
  }

  public void setReferServiceId(int referServiceId) {
    this.referServiceId = referServiceId;
     put("referServiceId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ServiceDependencyDTO that = (ServiceDependencyDTO) obj;
        return ( ( (this.getType() == null) && (that.getType() == null)) ||
                (this.getType() != null && this.getType().equals(that.getType()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getServiceId() == that.getServiceId() &&
            this.getReferServiceId() == that.getReferServiceId();
      }
    }
    return false;
  }

  public int hashCode() {
     return toString().hashCode();
   }



  public String toString()
          {
                  StringBuffer buf = new StringBuffer(256);
                  buf.append(type);
                  buf.append(",").append(serviceId);
                  buf.append(",").append(referServiceId);

                  buf.append(",").append(dtCreate);
                  buf.append(",").append(dtLastmod);
                  return buf.toString();
          }

          private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }

  }

