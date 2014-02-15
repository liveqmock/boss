package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CandssubIdDTO
    implements Serializable {
  private int hostId;	
  private String cardsn;
/**
 * @return Returns the hostId.
 */
public int getHostId() {
	return hostId;
}
/**
 * @param hostId The hostId to set.
 */
public void setHostId(int hostId) {
	this.hostId = hostId;
}
/**
 * @param subscriberId The subscriberId to set.
 */
public void setSubscriberId(int subscriberId) {
	this.subscriberId = subscriberId;
}
public int getSubscriberId() {
	return subscriberId;
}
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int subscriberId;
  public String getCardsn() {
    return cardsn;
  }

  public void setCardsn(String cardsn) {
    this.cardsn = cardsn;
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

   
  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CandssubIdDTO that = (CandssubIdDTO) obj;
        return ( ( (this.getCardsn() == null) && (that.getCardsn() == null)) ||
                (this.getCardsn() != null &&
                 this.getCardsn().equals(that.getCardsn()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
              this.getHostId() == that.getHostId() &&
             this.getSubscriberId() == that.getSubscriberId();
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
                buf.append(hostId);
                buf.append(",").append(cardsn);
                buf.append(",").append(dtCreate);
                buf.append(",").append(subscriberId);
                buf.append(dtLastmod);


    return buf.toString();
  }
}
