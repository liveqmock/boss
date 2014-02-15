package com.dtv.oss.dto;

import java.sql.Timestamp;

public class SystemSettingDTO implements ReflectionSupport, java.io.Serializable{
    private String name;
    private String description;
    private String valueType;
    private String value;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
        put("valueType");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        put("value");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
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
        if (!(obj instanceof SystemSettingDTO))
            return false;
        SystemSettingDTO that = (SystemSettingDTO) obj;
        if (!(that.name == null ? this.name == null :
              that.name.equals(this.name)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
            return false;
        if (!(that.valueType == null ? this.valueType == null :
              that.valueType.equals(this.valueType)))
            return false;
        if (!(that.value == null ? this.value == null :
              that.value.equals(this.value)))
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
        result = 37 * result + this.name.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.valueType.hashCode();
        result = 37 * result + this.value.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("name:").append(name);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("valueType:").append(valueType);
        returnStringBuffer.append("value:").append(value);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
