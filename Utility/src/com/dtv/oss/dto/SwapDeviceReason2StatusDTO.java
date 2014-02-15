package com.dtv.oss.dto;

import java.sql.Timestamp;

public class SwapDeviceReason2StatusDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private String reasonStrList;
    private String toStatus;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private String status;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getReasonStrList() {
        return reasonStrList;
    }

    public void setReasonStrList(String reasonStrList) {
        this.reasonStrList = reasonStrList;
        put("reasonStrList");
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
        put("toStatus");
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
        if (!(obj instanceof SwapDeviceReason2StatusDTO))
            return false;
        SwapDeviceReason2StatusDTO that = (SwapDeviceReason2StatusDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (!(that.reasonStrList == null ? this.reasonStrList == null :
              that.reasonStrList.equals(this.reasonStrList)))
            return false;
        if (!(that.toStatus == null ? this.toStatus == null :
              that.toStatus.equals(this.toStatus)))
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
        result = 37 * result + (int)this.seqNo;
        result = 37 * result + this.reasonStrList.hashCode();
        result = 37 * result + this.toStatus.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(160);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("reasonStrList:").append(reasonStrList);
        returnStringBuffer.append("toStatus:").append(toStatus);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		 put("status");
	}
	 private java.util.Map map = new java.util.HashMap();

		public void put(String field) { map.put(field, Boolean.TRUE); }

		public java.util.Map getMap() { return this.map; }

}
