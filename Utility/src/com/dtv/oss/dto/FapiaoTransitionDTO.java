package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FapiaoTransitionDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private int volumnSeqNo;
    private int total;
    private int opID;
    private int orgID;
    private String action;
    private String fileName;
    private String status;
    private Timestamp createTime;
    private String printCode;
    private String printNumber;
    private String taxControlCode;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getVolumnSeqNo() {
        return volumnSeqNo;
    }

    public void setVolumnSeqNo(int volumnSeqNo) {
        this.volumnSeqNo = volumnSeqNo;
        put("volumnSeqNo");
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        put("total");
    }

    public int getOpID() {
        return opID;
    }

    public void setOpID(int opID) {
        this.opID = opID;
        put("opID");
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
        put("orgID");
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
        put("action");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        put("fileName");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        put("createTime");
    }

    public String getPrintCode() {
        return printCode;
    }

    public void setPrintCode(String printCode) {
        this.printCode = printCode;
        put("printCode");
    }

    public String getPrintNumber() {
        return printNumber;
    }

    public void setPrintNumber(String printNumber) {
        this.printNumber = printNumber;
        put("printNumber");
    }

    public String getTaxControlCode() {
        return taxControlCode;
    }

    public void setTaxControlCode(String taxControlCode) {
        this.taxControlCode = taxControlCode;
        put("taxControlCode");
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
        put("dtCreate");
    }

    public Timestamp getDtLastmod() {
        return dtLastmod;
    }

    public void setDtLastmod(Timestamp dtLastmod) {
        this.dtLastmod = dtLastmod;
        put("dtLastmod");
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof FapiaoTransitionDTO))
            return false;
        FapiaoTransitionDTO that = (FapiaoTransitionDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.volumnSeqNo != this.volumnSeqNo)
            return false;
        if (that.total != this.total)
            return false;
        if (that.opID != this.opID)
            return false;
        if (that.orgID != this.orgID)
            return false;
        if (!(that.action == null ? this.action == null :
              that.action.equals(this.action)))
            return false;
        if (!(that.fileName == null ? this.fileName == null :
              that.fileName.equals(this.fileName)))
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
            return false;
        if (!(that.printCode == null ? this.printCode == null :
              that.printCode.equals(this.printCode)))
            return false;
        if (!(that.printNumber == null ? this.printNumber == null :
              that.printNumber.equals(this.printNumber)))
            return false;
        if (!(that.taxControlCode == null ? this.taxControlCode == null :
              that.taxControlCode.equals(this.taxControlCode)))
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.seqNo;
        result = 37 * result + (int)this.volumnSeqNo;
        result = 37 * result + (int)this.total;
        result = 37 * result + (int)this.opID;
        result = 37 * result + (int)this.orgID;
        result = 37 * result + this.action.hashCode();
        result = 37 * result + this.fileName.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + this.printCode.hashCode();
        result = 37 * result + this.printNumber.hashCode();
        result = 37 * result + this.taxControlCode.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(448);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("volumnSeqNo:").append(volumnSeqNo);
        returnStringBuffer.append("total:").append(total);
        returnStringBuffer.append("opID:").append(opID);
        returnStringBuffer.append("orgID:").append(orgID);
        returnStringBuffer.append("action:").append(action);
        returnStringBuffer.append("fileName:").append(fileName);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("printCode:").append(printCode);
        returnStringBuffer.append("printNumber:").append(printNumber);
        returnStringBuffer.append("taxControlCode:").append(taxControlCode);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
