package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CatvJobCardConfigDTO implements Serializable {
    private String csiType;
    private String jobCardType;
    private String jobCardSubType;
    private String jobcardStatus;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public String getCsiType() {
        return csiType;
    }

    public void setCsiType(String csiType) {
        this.csiType = csiType;
    }

    public String getJobCardType() {
        return jobCardType;
    }

    public void setJobCardType(String jobCardType) {
        this.jobCardType = jobCardType;
    }

    public String getJobCardSubType() {
        return jobCardSubType;
    }

    public void setJobCardSubType(String jobCardSubType) {
        this.jobCardSubType = jobCardSubType;
    }

    public String getJobcardStatus() {
        return jobcardStatus;
    }

    public void setJobcardStatus(String jobcardStatus) {
        this.jobcardStatus = jobcardStatus;
    }

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

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CatvJobCardConfigDTO))
            return false;
        CatvJobCardConfigDTO that = (CatvJobCardConfigDTO) obj;
        if (!(that.csiType == null ? this.csiType == null :
              that.csiType.equals(this.csiType)))
            return false;
        if (!(that.jobCardType == null ? this.jobCardType == null :
              that.jobCardType.equals(this.jobCardType)))
            return false;
        if (!(that.jobCardSubType == null ? this.jobCardSubType == null :
              that.jobCardSubType.equals(this.jobCardSubType)))
            return false;
        if (!(that.jobcardStatus == null ? this.jobcardStatus == null :
              that.jobcardStatus.equals(this.jobcardStatus)))
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
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
        result = 37 * result + this.csiType.hashCode();
        result = 37 * result + this.jobCardType.hashCode();
        result = 37 * result + this.jobCardSubType.hashCode();
        result = 37 * result + this.jobcardStatus.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("csiType:").append(csiType);
        returnStringBuffer.append("jobCardType:").append(jobCardType);
        returnStringBuffer.append("jobCardSubType:").append(jobCardSubType);
        returnStringBuffer.append("jobcardStatus:").append(jobcardStatus);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
     
}
