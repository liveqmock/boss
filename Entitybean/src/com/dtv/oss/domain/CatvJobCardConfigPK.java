package com.dtv.oss.domain;

import java.io.Serializable;

public class CatvJobCardConfigPK implements Serializable {
    public String csiType;
    public String jobCardType;
    public String jobCardSubType;
    public CatvJobCardConfigPK() {
    }

    public CatvJobCardConfigPK(String csiType, String jobCardType,
                               String jobCardSubType) {
        this.csiType = csiType;
        this.jobCardType = jobCardType;
        this.jobCardSubType = jobCardSubType;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CatvJobCardConfigPK))
            return false;
        CatvJobCardConfigPK that = (CatvJobCardConfigPK) obj;
        if (!(that.csiType == null ? this.csiType == null :
              that.csiType.equals(this.csiType)))
            return false;
        if (!(that.jobCardType == null ? this.jobCardType == null :
              that.jobCardType.equals(this.jobCardType)))
            return false;
        if (!(that.jobCardSubType == null ? this.jobCardSubType == null :
              that.jobCardSubType.equals(this.jobCardSubType)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.csiType.hashCode();
        result = 37 * result + this.jobCardType.hashCode();
        result = 37 * result + this.jobCardSubType.hashCode();
        return result;
    }
}
