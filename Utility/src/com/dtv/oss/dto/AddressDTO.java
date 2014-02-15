package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class AddressDTO
    implements ReflectionSupport, java.io.Serializable {

  public String postcode;

  private int districtID;

  private String detailAddress;

  private int addressID;

  private Timestamp dtCreate;

  private Timestamp dtLastmod;

  public AddressDTO() {
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
    put("postcode");

  }

  public String getPostcode() {
    return postcode;
  }

  public void setDistrictID(int districtID) {
    this.districtID = districtID;
    put("districtID");

  }

  public int getDistrictID() {
    return districtID;
  }

  public void setDetailAddress(String detailAddress) {
    this.detailAddress = detailAddress;
    put("detailAddress");

  }

  public String getDetailAddress() {
    return detailAddress;
  }

  public void setAddressID(int addressID) {
    this.addressID = addressID;
    //put("addressID");
  }

  public int getAddressID() {
    return addressID;
  }

  public void setDtCreate(Timestamp dtCreate) {
    this.dtCreate = dtCreate;
  }

  public Timestamp getDtCreate() {
    return dtCreate;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);

    buf.append(",").append(postcode);
    buf.append(",").append(districtID);

    buf.append(",").append(detailAddress);
    buf.append(",").append(addressID);
    buf.append(",").append(dtCreate);
    buf.append(",").append(dtLastmod);
    return buf.toString();
  }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field) {
    map.put(field, Boolean.TRUE);
  }

  public java.util.Map getMap() {
    return this.map;
  }

}
