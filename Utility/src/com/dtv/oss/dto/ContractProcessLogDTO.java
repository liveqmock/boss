package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ContractProcessLogDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private String contractNo;
    private String action;
    private String description;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private Timestamp actionTime;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
        put("contractNo");
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
        put("action");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
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

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
        put("actionTime");
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ContractProcessLogDTO))
            return false;
        ContractProcessLogDTO that = (ContractProcessLogDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
         
             
        if (!(that.contractNo == null ? this.contractNo == null :
              that.contractNo.equals(this.contractNo)))
            return false;
        if (!(that.action == null ? this.action == null :
              that.action.equals(this.action)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        if (!(that.actionTime == null ? this.actionTime == null :
              that.actionTime.equals(this.actionTime)))
            return false;
        return true;
    }

    public int hashCode() {
    	return toString().hashCode();
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("contractNo:").append(contractNo);
        returnStringBuffer.append("action:").append(action);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("actionTime:").append(actionTime);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}
}
