package com.dtv.oss.dto.custom;

import java.io.Serializable;

public class Catvterminal_DTO extends OrderByCriteria implements ReflectionSupport,Serializable {
  private Integer id;
  private String status;
  private int county;
  private int orgid;
  private String catvID;
  private int constructionBatchID;
  private String recordSource;
  private String statusReason;
  private String detailedAddress;
  private String postCode;
  private int streetStationID;
  private int zoneStationID;
  private int userID;
  private int portNumber;
  private String fiberNode;
  private String cableType;
  private String bidirectionFlag;
  private long createTime;
  private long activeTime;
  private long pauseTime;
  private long dt_Create;
  private long dt_Lastmod;
  ///////////////////////////////////
  // support reflection
  private java.util.Map map = null;

  ////////////////////////////////////
  //  construction
  ///////////////////////////////////
  public Catvterminal_DTO() {
    map = new java.util.HashMap();
  }

  public Catvterminal_DTO(int id,String status,int county,int orgid,String catvid,
                          int batchid,String recordsource,String reason,String address,
                  String postcode,int userid,int portnum,String fibernode,String cabletype,
                  String bidi,long createtime,long activetime,long pausetime, long create,long mod) {
    this.id = new Integer(id);
    this.status = status;
    this.county = county;
    this.orgid = orgid;
    this.catvID = catvid;
    this.constructionBatchID = batchid;
    this.recordSource = recordsource;
    this.statusReason = reason;
    this.detailedAddress = address;
    this.postCode = postcode;
    this.userID = userid;
    this.portNumber = portnum;
    this.fiberNode = fibernode;
    this.cableType = cabletype;
    this.bidirectionFlag = bidi;
    this.createTime = createtime;
    this.activeTime = activetime;
    this.pauseTime = pausetime;

    this.dt_Create = create;
    this.dt_Lastmod = mod;
    map = new java.util.HashMap();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    put("Status");
  }

  public int getCounty() {
    return county;
  }

  public void setCounty(int county) {
    this.county = county;
    put("County");
  }

  public int getOrgid() {
    return orgid;
  }

  public void setOrgid(int orgid) {
    this.orgid = orgid;
    put("Orgid");
  }

  public String getCatvID() {
    return catvID;
  }

  public void setCatvID(String catvID) {
    this.catvID = catvID;
    put("catvID");
  }

  public int getConstructionBatchID() {
    return constructionBatchID;
  }

  public void setConstructionBatchID(int constructionBatchID) {
    this.constructionBatchID = constructionBatchID;
    put("constructionBatchID");
  }

  public String getRecordSource() {
    return recordSource;
  }

  public void setRecordSource(String recordSource) {
    this.recordSource = recordSource;
    put("recordSource");
  }

  public String getStatusReason() {
    return statusReason;
  }

  public void setStatusReason(String statusReason) {
    this.statusReason = statusReason;
    put("statusReason");
  }

  public String getDetailedAddress() {
    return detailedAddress;
  }

  public void setDetailedAddress(String detailedAddress) {
    this.detailedAddress = detailedAddress;
    put("detailedAddress");
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
    put("postCode");
  }

  public int getStreetStationID() {
    return streetStationID;
  }

  public void setStreetStationID(int streetStationID) {
    this.streetStationID = streetStationID;
    put("streetStationID");
  }

  public int getZoneStationID() {
    return zoneStationID;
  }

  public void setZoneStationID(int zoneStationID) {
    this.zoneStationID = zoneStationID;
    put("zoneStationID");
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
    put("userID");
  }

  public int getPortNumber() {
    return portNumber;
  }

  public void setPortNumber(int portNumber) {
    this.portNumber = portNumber;
    put("portNumber");
  }

  public String getFiberNode() {
    return fiberNode;
  }

  public void setFiberNode(String fiberNode) {
    this.fiberNode = fiberNode;
    put("fiberNode");
  }

  public String getCableType() {
    return cableType;
  }

  public void setCableType(String cableType) {
    this.cableType = cableType;
    put("cableType");
  }

  public String getBidirectionFlag() {
    return bidirectionFlag;
  }

  public void setBidirectionFlag(String bidirectionFlag) {
    this.bidirectionFlag = bidirectionFlag;
    put("bidirectionFlag");
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
    put("createTime");
  }

  public long getActiveTime() {
    return activeTime;
  }

  public void setActiveTime(long activeTime) {
    this.activeTime = activeTime;
    put("activeTime");
  }

  public long getPauseTime() {
    return pauseTime;
  }

  public void setPauseTime(long pauseTime) {
    this.pauseTime = pauseTime;
    put("pauseTime");
  }

  public long getDt_Create() {
    return dt_Create;
  }

  public void setDt_Create(long dt_Create) {
    this.dt_Create = dt_Create;
  }

  public long getDt_Lastmod() {
    return dt_Lastmod;
  }

  public void setDt_Lastmod(long dt_Lastmod) {
    this.dt_Lastmod = dt_Lastmod;
  }

  ///////////////////////////////////////////////
  ///  support reflection method
  //////////////////////////////////////////////
  public void put(String field) { map.put(field, Boolean.TRUE); }
  public java.util.Map getMap() { return this.map; }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        Catvterminal_DTO that = (Catvterminal_DTO) obj;
        return ( ( (this.getId() == null) && (that.getId() == null)) ||
                (this.getId() != null && this.getId().equals(that.getId()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            this.getCounty() == that.getCounty() &&
            this.getOrgid() == that.getOrgid() &&
            ( ( (this.getCatvID() == null) && (that.getCatvID() == null)) ||
             (this.getCatvID() != null &&
              this.getCatvID().equals(that.getCatvID()))) &&
            this.getConstructionBatchID() == that.getConstructionBatchID() &&
            ( ( (this.getRecordSource() == null) && (that.getRecordSource() == null)) ||
             (this.getRecordSource() != null &&
              this.getRecordSource().equals(that.getRecordSource()))) &&
            ( ( (this.getStatusReason() == null) && (that.getStatusReason() == null)) ||
             (this.getStatusReason() != null &&
              this.getStatusReason().equals(that.getStatusReason()))) &&
            ( ( (this.getDetailedAddress(
            ) == null) && (that.getDetailedAddress() == null)) ||
             (this.getDetailedAddress() != null &&
              this.getDetailedAddress().equals(that.getDetailedAddress()))) &&
            ( ( (this.getPostCode() == null) && (that.getPostCode() == null)) ||
             (this.getPostCode() != null &&
              this.getPostCode().equals(that.getPostCode()))) &&
            this.getStreetStationID() == that.getStreetStationID() &&
            this.getZoneStationID() == that.getZoneStationID() &&
            this.getUserID() == that.getUserID() &&
            this.getPortNumber() == that.getPortNumber() &&
            ( ( (this.getFiberNode() == null) && (that.getFiberNode() == null)) ||
             (this.getFiberNode() != null &&
              this.getFiberNode().equals(that.getFiberNode()))) &&
            ( ( (this.getCableType() == null) && (that.getCableType() == null)) ||
             (this.getCableType() != null &&
              this.getCableType().equals(that.getCableType()))) &&
            ( ( (this.getBidirectionFlag() == null) && (that.getBidirectionFlag() == null)) ||
             (this.getBidirectionFlag() != null &&
              this.getBidirectionFlag().equals(that.getBidirectionFlag()))) &&
            this.getCreateTime() == that
            .getCreateTime() && this.getActiveTime() == that.getActiveTime() &&
            this.getPauseTime() == that.getPauseTime() &&
            this.getDt_Create() == that.getDt_Create() &&
            this.getDt_Lastmod() == that.getDt_Lastmod();
      }
    }
    return false;
  }

  public int hashCode() {
    return (id + "" + status + county + "" + orgid + catvID +
            constructionBatchID + "" + recordSource + statusReason +
            detailedAddress + postCode + streetStationID + "" + zoneStationID +
            userID + "" + portNumber + fiberNode + cableType + bidirectionFlag +
            createTime + "" + activeTime + pauseTime + "" + dt_Create +
            dt_Lastmod).hashCode();
  }

  public String toString() {
    return id + ", " + status + ", " + county + ", " + orgid + ", " + catvID +
        ", " + constructionBatchID + ", " + recordSource + ", " + statusReason +
        ", " + detailedAddress + ", " + postCode + ", " + streetStationID +
        ", " + zoneStationID + ", " + userID + ", " + portNumber + ", " +
        fiberNode + ", " + cableType + ", " + bidirectionFlag + ", " +
        createTime + ", " + activeTime + ", " + pauseTime + ", " + dt_Create +
        ", " + dt_Lastmod;
  }
}
