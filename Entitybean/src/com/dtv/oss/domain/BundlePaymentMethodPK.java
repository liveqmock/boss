package com.dtv.oss.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class BundlePaymentMethodPK implements Serializable {
    public int  bundleID;
    public String rfBillingCycleFlag;
    public BundlePaymentMethodPK() {
    }

    public BundlePaymentMethodPK(int bundleID, String rfBillingCycleFlag) {
        this.bundleID = bundleID;
        this.rfBillingCycleFlag = rfBillingCycleFlag;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BundlePaymentMethodPK))
            return false;
        BundlePaymentMethodPK that = (BundlePaymentMethodPK) obj;
        if (that.bundleID != this.bundleID)
            return false;
        if (!(that.rfBillingCycleFlag == null ? this.rfBillingCycleFlag == null :
              that.rfBillingCycleFlag.equals(this.rfBillingCycleFlag)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.bundleID;
        result = 37 * result + this.rfBillingCycleFlag.hashCode();
        return result;
    }
}
