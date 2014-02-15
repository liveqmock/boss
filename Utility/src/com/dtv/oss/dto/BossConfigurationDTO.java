package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BossConfigurationDTO
    implements  ReflectionSupport, java.io.Serializable {
  private String developer;
   
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private String msoSystemName;
  private String softwareName;
  private String softwarEversion;
  private String databaseVersion;
  private int licensedMaxUser;
  private String licenseValidFrom;
  private String licenseValidTo;
  private String msoName;
  private Timestamp installTime;
  private String systemSymbolName;
  private String systemName;
  
  public String getSystemName() {
	return systemName;
}

public void setSystemName(String systemName) {
	this.systemName = systemName;
}

public String getSystemSymbolName() {
	return systemSymbolName;
}

public void setSystemSymbolName(String systemSymbolName) {
	this.systemSymbolName = systemSymbolName;
}

public BossConfigurationDTO(){

 }

  public String getDeveloper() {
    return developer;
  }

  public void setDeveloper(String developer) {
    this.developer = developer;
     put("developer");
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

   

  public String getSoftwareName() {
    return softwareName;
  }

  public void setSoftwareName(String softwareName) {
    this.softwareName = softwareName;
   // put("softwareName");
     
  }

  public String getMsoSystemName() {
    return msoSystemName;
  }

  public void setMsoSystemName(String msoSystemName) {
    this.msoSystemName = msoSystemName;
     put("msoSystemName");
  }
  public String getSoftwarEversion() {
    return softwarEversion;
  }

  public void setSoftwarEversion(String softwarEversion) {
    this.softwarEversion = softwarEversion;
     put("softwarEversion");
  }

  public String getDatabaseVersion() {
    return databaseVersion;
  }

  public void setDatabaseVersion(String databaseVersion) {
    this.databaseVersion = databaseVersion;
     put("databaseVersion");
  }

  public int getLicensedMaxUser() {
    return licensedMaxUser;
  }

  public void setLicensedMaxUser(int licensedMaxUser) {
    this.licensedMaxUser = licensedMaxUser;
    put("licensedMaxUser");
  }

  public String getLicenseValidFrom() {
    return licenseValidFrom;
  }

  public void setLicenseValidFrom(String licenseValidFrom) {
    this.licenseValidFrom = licenseValidFrom;
      put("licenseValidFrom");
  }

  public String getLicenseValidTo() {
    return licenseValidTo;
  }

  public void setLicenseValidTo(String licenseValidTo) {
    this.licenseValidTo = licenseValidTo;
     put("licenseValidTo");
  }

  public String getMsoName() {
    return msoName;
  }

  public void setMsoName(String msoName) {
    this.msoName = msoName;
     put("msoName");
  }

  public Timestamp getInstallTime() {
    return installTime;
  }

  public void setInstallTime(Timestamp installTime) {
    this.installTime = installTime;
     put("installTime");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BossConfigurationDTO that = (BossConfigurationDTO) obj;
        return ( ( (this.getDeveloper() == null) && (that.getDeveloper() == null)) ||
                (this.getDeveloper() != null &&
                 this.getDeveloper().equals(that.getDeveloper()))) &&
            ( ( (this.getMsoSystemName() == null) && (that.getMsoSystemName() == null)) ||
             (this.getMsoSystemName() != null &&
              this.getMsoSystemName().equals(that.getMsoSystemName()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            
            ( ( (this.getSoftwareName() == null) && (that.getSoftwareName() == null)) ||
             (this.getSoftwareName() != null && this.getSoftwareName().
              equals(that.getSoftwareName()))) &&
            ( ( (this.getSoftwarEversion() == null) && (that.getSoftwarEversion() == null)) ||
             (this.getSoftwarEversion() != null &&
              this.getSoftwarEversion().equals(that.getSoftwarEversion()))) &&
            ( ( (this.getDatabaseVersion() == null) && (that.getDatabaseVersion() == null)) ||
             (this.getDatabaseVersion() != null &&
              this.getDatabaseVersion().equals(that.getDatabaseVersion()))) &&
            this.getLicensedMaxUser() == that.getLicensedMaxUser() &&
            ( ( (this.getLicenseValidFrom() == null) &&
               (that.getLicenseValidFrom() == null)) ||
             (this.getLicenseValidFrom() != null &&
              this.getLicenseValidFrom().equals(that.getLicenseValidFrom()))) &&
            ( ( (this.getLicenseValidTo() == null) && (that.getLicenseValidTo() == null)) ||
             (this.getLicenseValidTo() != null &&
              this.getLicenseValidTo().equals(that.getLicenseValidTo()))) &&
            ( ( (this.getMsoName() == null) &&
               (that.getMsoName() == null)) ||
             (this.getMsoName() != null &&
              this.getMsoName().equals(that.getMsoName()))
             ) &&
            ( ( (this.getInstallTime() == null) && (that.getInstallTime() == null)) ||
             (this.getInstallTime() != null &&
              this.getInstallTime().equals(that.getInstallTime())));
      }
    }
    return false;
  }

  public int hashCode() {
    return  toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
                buf.append(developer);
              
                buf.append(",").append(softwareName);
                buf.append(",").append(softwarEversion);
                buf.append(",").append(databaseVersion);
                buf.append(",").append(licensedMaxUser);
                buf.append(",").append(licenseValidFrom);
                buf.append(",").append(licenseValidTo);
                buf.append(",").append(msoSystemName);
                buf.append(",").append(msoName);


                buf.append(installTime);

                return buf.toString();

  }
  private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }


  }

