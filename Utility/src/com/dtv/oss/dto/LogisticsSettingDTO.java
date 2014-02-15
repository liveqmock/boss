package com.dtv.oss.dto;

import java.sql.Timestamp;

public class LogisticsSettingDTO
    implements ReflectionSupport, java.io.Serializable {
  private String status="";
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private String inAndOut="";
  private int outOrgnization;
  private String matchAndPreauth="";
  private int preauthProductid1;
  private int preauthProductid2;
  private int preauthProductid3;
  private int preauthProductid4;
  private int preauthProductid5;
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public String getInAndOut() {
    return inAndOut;
  }

  public void setInAndOut(String inAndOut) {
    this.inAndOut = inAndOut;
    put("inAndOut");
  }

  public int getOutOrgnization() {
    return outOrgnization;
  }

  public void setOutOrgnization(int outOrgnization) {
    this.outOrgnization = outOrgnization;
    put("outOrgnization");
  }

  public String getMatchAndPreauth() {
    return matchAndPreauth;
  }

  public void setMatchAndPreauth(String matchAndPreauth) {
    this.matchAndPreauth = matchAndPreauth;
    put("matchAndPreauth");
  }

  public int getPreauthProductid1() {
    return preauthProductid1;
  }

  public void setPreauthProductid1(int preauthProductid1) {
    this.preauthProductid1 = preauthProductid1;
    put("preauthProductid1");
  }

  public int getPreauthProductid2() {
    return preauthProductid2;
  }

  public void setPreauthProductid2(int preauthProductid2) {
    this.preauthProductid2 = preauthProductid2;
    put("preauthProductid2");
  }

  public int getPreauthProductid3() {
    return preauthProductid3;
  }

  public void setPreauthProductid3(int preauthProductid3) {
    this.preauthProductid3 = preauthProductid3;
    put("preauthProductid3");
  }

  public int getPreauthProductid4() {
    return preauthProductid4;
  }

  public void setPreauthProductid4(int preauthProductid4) {
    this.preauthProductid4 = preauthProductid4;
    put("preauthProductid4");
  }

  public int getPreauthProductid5() {
    return preauthProductid5;
  }

  public void setPreauthProductid5(int preauthProductid5) {
    this.preauthProductid5 = preauthProductid5;
    put("preauthProductid5");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        LogisticsSettingDTO that = (LogisticsSettingDTO) obj;
        return ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                (this.getStatus() != null &&
                 this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&

             this.getSeqNo() == that.getSeqNo()&&
            ( ( (this.getInAndOut() == null) && (that.getInAndOut() == null)) ||
             (this.getInAndOut() != null &&
              this.getInAndOut().equals(that.getInAndOut()))) &&
            this.getOutOrgnization() == that.getOutOrgnization() &&
            ( ( (this.getMatchAndPreauth() == null) && (that.getMatchAndPreauth() == null)) ||
             (this.getMatchAndPreauth() != null &&
              this.getMatchAndPreauth().equals(that.getMatchAndPreauth()))) &&
            this.getPreauthProductid1() == that.getPreauthProductid1() &&
            this.getPreauthProductid2() == that.getPreauthProductid2() &&
            this.getPreauthProductid3() == that.getPreauthProductid3() &&
            this.getPreauthProductid4() == that.getPreauthProductid4() &&
            this.getPreauthProductid5() == that.getPreauthProductid5();
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
    buf.append(status);
    buf.append(",").append(dtCreate);
    buf.append(",").append(dtLastmod);
     buf.append(",").append(seqNo);
    buf.append(",").append(inAndOut);
    buf.append(",").append(outOrgnization);
    buf.append(",").append(matchAndPreauth);
    buf.append(",").append(preauthProductid1);
    buf.append(",").append(preauthProductid2);
    buf.append(",").append(preauthProductid3);
    buf.append(",").append(preauthProductid4);
    buf.append(",").append(preauthProductid5);
    return   buf.toString();
  }
  private java.util.Map map = new java.util.HashMap();

  public void put(String field) {
    map.put(field, Boolean.TRUE);
  }

  public java.util.Map getMap() {
    return this.map;
  }

}
