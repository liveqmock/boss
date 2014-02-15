package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CAWalletDTO implements ReflectionSupport,Serializable {
    private int walletId;
    private int customerId;
    private int serviceAccountId;
    private String scSerialNo;
    private String status;
    private int totalPoint;
    private String caWalletCode;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    
    public int getWalletId() {
		return walletId;
	}

	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
		put("customerId");
	}
	
	public int getServiceAccountId() {
		return serviceAccountId;
	}

	public void setServiceAccountId(int serviceAccountId) {
		this.serviceAccountId = serviceAccountId;
		put("serviceAccountId");
	}
	
	public String getScSerialNo() {
		return scSerialNo;
	}

	public void setScSerialNo(String scSerialNo) {
		this.scSerialNo = scSerialNo;
		put("scSerialNo");
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
		put("totalPoint");
	}

	public String getCaWalletCode() {
		return caWalletCode;
	}

	public void setCaWalletCode(String caWalletCode) {
		this.caWalletCode = caWalletCode;
		put("caWalletCode");
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
        if (!(obj instanceof CAWalletDTO))
            return false;
        CAWalletDTO that = (CAWalletDTO) obj;
        if (that.walletId != this.walletId)
            return false;
        if (that.customerId != this.customerId)
            return false;
        if (that.serviceAccountId != this.serviceAccountId)
            return false;
        if (!(that.scSerialNo == null ? this.scSerialNo == null :
              that.scSerialNo.equals(this.scSerialNo)))
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (that.totalPoint != this.totalPoint)
            return false;
        if (!(that.caWalletCode == null ? this.caWalletCode == null :
              that.caWalletCode.equals(this.caWalletCode)))
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
        result = 37 * result + (int)this.walletId;
        result = 37 * result + (int)this.customerId;
        result = 37 * result + (int)this.serviceAccountId;
        result = 37 * result + this.scSerialNo.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + (int)this.totalPoint;
        result = 37 * result + this.caWalletCode.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("walletId:").append(walletId);
        returnStringBuffer.append("customerId:").append(customerId);
        returnStringBuffer.append("serviceAccountId:").append(serviceAccountId);
        returnStringBuffer.append("scSerialNo:").append(scSerialNo);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("totalPoint:").append(totalPoint);
        returnStringBuffer.append("caWalletCode:").append(caWalletCode);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }


}
