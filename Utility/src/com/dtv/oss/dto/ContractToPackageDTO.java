package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContractToPackageDTO implements Serializable {
    private String contractNo;
    private int productPackageID;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public int getProductPackageID() {
        return productPackageID;
    }

    public void setProductPackageID(int productPackageID) {
        this.productPackageID = productPackageID;
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
        if (!(obj instanceof ContractToPackageDTO))
            return false;
        ContractToPackageDTO that = (ContractToPackageDTO) obj;
        if (!(that.contractNo == null ? this.contractNo == null :
              that.contractNo.equals(this.contractNo)))
            return false;
        if (that.productPackageID != this.productPackageID)
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
    	return toString().hashCode();
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(128);
        returnStringBuffer.append("[");
        returnStringBuffer.append("contractNo:").append(contractNo);
        returnStringBuffer.append("productPackageID:").append(productPackageID);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
