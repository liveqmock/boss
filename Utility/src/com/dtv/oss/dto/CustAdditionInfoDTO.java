package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CustAdditionInfoDTO implements ReflectionSupport, java.io.Serializable {
    private Integer id;
    private String applicant;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private int customerId;
    private String applicationDept;
    private Timestamp beginValidDate;
    private Timestamp endValidDate;
    private String durationLimited; 
     
    public CustAdditionInfoDTO() {
    }

    public CustAdditionInfoDTO(String durationLimited,Integer id, String applicant, int customerId, String applicationDept, Timestamp beginValidDate, Timestamp endValidDate, Timestamp dtCreate, Timestamp dtLastmod) {
        setId(id);
        setDurationLimited(durationLimited);
        setApplicant(applicant);
        setDtCreate(dtCreate);
        setDtLastmod(dtLastmod);
        setCustomerId(customerId);
        setApplicationDept(applicationDept);
        setBeginValidDate(beginValidDate);
        setEndValidDate(endValidDate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getApplicant() {
        return applicant;
    }

    public void setDurationLimited(String durationLimited) {
        this.durationLimited = durationLimited;
        put("durationLimited");
    }
    public String getDurationLimited() {
        return durationLimited;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
        put("applicant");
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
        put("customerId");
    }

    public String getApplicationDept() {
        return applicationDept;
    }

    public void setApplicationDept(String applicationDept) {
        this.applicationDept = applicationDept;
        put("applicationDept");
    }

    public Timestamp getBeginValidDate() {
        return beginValidDate;
    }

    public void setBeginValidDate(Timestamp beginValidDate) {
        this.beginValidDate = beginValidDate;
        put("beginValidDate");
    }

    public Timestamp getEndValidDate() {
        return endValidDate;
    }

    public void setEndValidDate(Timestamp endValidDate) {
        this.endValidDate = endValidDate;
        put("endValidDate");
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (this.getClass().equals(obj.getClass())) {
                CustAdditionInfoDTO that = (CustAdditionInfoDTO) obj;
                return (((this.getId() == null) && (that.getId() == null)) ||
                        (this.getId() != null && this.getId().equals(that.getId()))) &&
                        (((this.getApplicant() == null) && (that.getApplicant() == null)) ||
                        (this.getApplicant() != null &&
                        this.getApplicant().equals(that.getApplicant()))) &&
                        (((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                        (this.getDtCreate() != null &&this.getDtCreate().equals(that.getDtCreate()))) &&
                        (((this.getDurationLimited() == null) && (that.getDurationLimited() == null)) ||
                          (this.getDurationLimited() != null &&this.getDurationLimited().equals(that.getDurationLimited()))) &&
                        (((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
                        (this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod()))) &&
                        this.getCustomerId() == that.getCustomerId() &&
                        (((this.getApplicationDept() == null) && (that.getApplicationDept() == null)) ||
                          (this.getApplicationDept() != null &&this.getApplicationDept().equals(that.getApplicationDept()))) &&
                        (((this.getBeginValidDate() == null) && (that.getBeginValidDate() == null)) ||
                        (this.getBeginValidDate() != null &&this.getBeginValidDate().equals(that.getBeginValidDate()))) &&
                        (((this.getEndValidDate() == null)
                        && (that.getEndValidDate() == null)) ||
                        (this.getEndValidDate() != null &&
                        this.getEndValidDate().equals(that.getEndValidDate())));
            }
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(256);
        buf.append(id);
        buf.append(",").append(applicant);
        buf.append(",").append(durationLimited);
        buf.append(",").append(dtCreate);
        buf.append(",").append(dtLastmod);
        buf.append(",").append(customerId);
        buf.append(",").append(applicationDept);
        buf.append(",").append(beginValidDate);
        buf.append(",").append(endValidDate);


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
