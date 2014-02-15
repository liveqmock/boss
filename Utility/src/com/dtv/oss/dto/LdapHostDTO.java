package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class LdapHostDTO implements Serializable,ReflectionSupport {
    private int hostID;
    private String hostName;
    private String protocolType;
    private String ipAddress;
    private int port;
    private String loginID;
    private String loginPWD;
    private String cmentrydir;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getHostID() {
        return hostID;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
        put("hostName");
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
        put("protocolType");
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        put("ipAddress");
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
        put("port");
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
        put("loginID");
    }

    public String getLoginPWD() {
        return loginPWD;
    }

    public void setLoginPWD(String loginPWD) {
        this.loginPWD = loginPWD;
        put("loginPWD");
    }

    public String getCmentrydir() {
        return cmentrydir;
    }

    public void setCmentrydir(String cmentrydir) {
        this.cmentrydir = cmentrydir;
        put("cmentrydir");
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
        if (!(obj instanceof LdapHostDTO))
            return false;
        LdapHostDTO that = (LdapHostDTO) obj;
        if  (that.hostID  !=this.hostID)
            return false;
        if (!(that.hostName == null ? this.hostName == null :
              that.hostName.equals(this.hostName)))
            return false;
        if (!(that.protocolType == null ? this.protocolType == null :
              that.protocolType.equals(this.protocolType)))
            return false;
        if (!(that.ipAddress == null ? this.ipAddress == null :
              that.ipAddress.equals(this.ipAddress)))
            return false;
        if (that.port != this.port)
            return false;
        if (!(that.loginID == null ? this.loginID == null :
              that.loginID.equals(this.loginID)))
            return false;
        if (!(that.loginPWD == null ? this.loginPWD == null :
              that.loginPWD.equals(this.loginPWD)))
            return false;
        if (!(that.cmentrydir == null ? this.cmentrydir == null :
              that.cmentrydir.equals(this.cmentrydir)))
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
        result = 37 * result + (int)this.hostID;
        result = 37 * result + this.hostName.hashCode();
        result = 37 * result + this.protocolType.hashCode();
        result = 37 * result + this.ipAddress.hashCode();
        result = 37 * result + (int)this.port;
        result = 37 * result + this.loginID.hashCode();
        result = 37 * result + this.loginPWD.hashCode();
        result = 37 * result + this.cmentrydir.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(352);
        returnStringBuffer.append("[");
        returnStringBuffer.append("hostID:").append(hostID);
        returnStringBuffer.append("hostName:").append(hostName);
        returnStringBuffer.append("protocolType:").append(protocolType);
        returnStringBuffer.append("ipAddress:").append(ipAddress);
        returnStringBuffer.append("port:").append(port);
        returnStringBuffer.append("loginID:").append(loginID);
        returnStringBuffer.append("loginPWD:").append(loginPWD);
        returnStringBuffer.append("cmentrydir:").append(cmentrydir);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
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
