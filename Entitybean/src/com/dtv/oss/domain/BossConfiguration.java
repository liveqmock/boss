package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BossConfigurationDTO;

public interface BossConfiguration extends javax.ejb.EJBLocalObject {
	public void setDeveloper(String developer);
	
	public String getDeveloper();

	public String getMsoName();

	public void setMsoName(String msoName);

	

	public void setLicensedMaxUser(int licensedMaxUser);

	public int getLicensedMaxUser();

	public void setLicenseValidFrom(String licenseValidFrom);

	public String getLicenseValidFrom();

	public void setLicenseValidTo(String licenseValidTo);

	public String getLicenseValidTo();

	public void setMsoSystemName(String msoSystemName);

	public String getMsoSystemName();

	public void setInstallTime(Timestamp installTime);

	public Timestamp getInstallTime();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();
	
    public String getSoftwareName();

	public void setSoftwarEversion(String softwarEversion);

	public String getSoftwarEversion();

	public void setDatabaseVersion(String databaseVersion);

	public String getDatabaseVersion();

	public int ejbUpdate(BossConfigurationDTO dto);
}