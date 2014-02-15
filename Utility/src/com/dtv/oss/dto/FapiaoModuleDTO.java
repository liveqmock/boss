package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FapiaoModuleDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private String type;
    private String moduleName;
    private String fieldName;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        put("type");
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
        put("moduleName");
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
        put("fieldName");
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
        if (!(obj instanceof FapiaoModuleDTO))
            return false;
        FapiaoModuleDTO that = (FapiaoModuleDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (!(that.type == null ? this.type == null :
              that.type.equals(this.type)))
            return false;
        if (!(that.moduleName == null ? this.moduleName == null :
              that.moduleName.equals(this.moduleName)))
            return false;
        if (!(that.fieldName == null ? this.fieldName == null :
              that.fieldName.equals(this.fieldName)))
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
        result = 37 * result + this.type.hashCode();
        result = 37 * result + this.moduleName.hashCode();
        result = 37 * result + this.fieldName.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(192);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("type:").append(type);
        returnStringBuffer.append("moduleName:").append(moduleName);
        returnStringBuffer.append("fieldName:").append(fieldName);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
