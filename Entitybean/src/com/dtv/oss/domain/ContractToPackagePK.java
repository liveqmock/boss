package com.dtv.oss.domain;

import java.io.Serializable;

public class ContractToPackagePK implements Serializable {
    public String contractNo;
    public int  productPackageID;
    public ContractToPackagePK() {
    }

    public ContractToPackagePK(String contractNo, int productPackageID) {
        this.contractNo = contractNo;
        this.productPackageID = productPackageID;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ContractToPackagePK))
            return false;
        ContractToPackagePK that = (ContractToPackagePK) obj;
        if (!(that.contractNo == null ? this.contractNo == null :
              that.contractNo.equals(this.contractNo)))
            return false;
        if (that.productPackageID != this.productPackageID)
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.contractNo.hashCode();
        result = 37 * result + (int)this.productPackageID;
        return result;
    }
}
