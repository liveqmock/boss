package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BossConfigurationDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BossConfigurationBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String softwareName)
			throws CreateException {
		 
		return null;
	}

	public java.lang.String ejbCreate(BossConfigurationDTO dto)
			throws CreateException {
		 
		setSoftwareName(dto.getSoftwareName());
		setSoftwarEversion(dto.getSoftwarEversion());
		setDatabaseVersion(dto.getDatabaseVersion());
		setDeveloper(dto.getDeveloper());
		setMsoSystemName(dto.getMsoSystemName());
		setLicensedMaxUser(dto.getLicensedMaxUser());
		setLicenseValidFrom(dto.getLicenseValidFrom());
		setLicenseValidTo(dto.getLicenseValidTo());
		setMsoName(dto.getMsoName());
		setInstallTime(dto.getInstallTime());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.String softwareName)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BossConfigurationDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	 
	public abstract void setSoftwareName(java.lang.String softwareName);

	public abstract void setSoftwarEversion(java.lang.String softwarEversion);

	public abstract void setDatabaseVersion(java.lang.String databaseVersion);

	public abstract void setDeveloper(java.lang.String developer);

	public abstract void setMsoSystemName(java.lang.String msoSystemName);

	public abstract void setLicensedMaxUser(int licensedMaxUser);

	public abstract void setLicenseValidFrom(java.lang.String licenseValidFrom);

	public abstract void setLicenseValidTo(java.lang.String licenseValidTo);

	public abstract void setMsoName(
			java.lang.String msoName);

	public abstract void setInstallTime(java.sql.Timestamp installTime);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	 

	public abstract java.lang.String getSoftwareName();

	public abstract java.lang.String getSoftwarEversion();

	public abstract java.lang.String getDatabaseVersion();

	public abstract java.lang.String getDeveloper();

	public abstract java.lang.String getMsoName();

	public abstract int getLicensedMaxUser();

	public abstract java.lang.String getLicenseValidFrom();

	public abstract java.lang.String getLicenseValidTo();

	public abstract java.lang.String getMsoSystemName();

	public abstract java.sql.Timestamp getInstallTime();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public void ejbLoad() {
		/** @todo Complete this method */
	}

	public void ejbStore() {
		/** @todo Complete this method */
	}

	public void ejbActivate() {
		/** @todo Complete this method */
	}

	public void ejbPassivate() {
		/** @todo Complete this method */
	}

	public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	public int ejbUpdate(BossConfigurationDTO dto) {
		/** @todo Complete this method */
		if (dto.getDtLastmod() == null) {
			return -1;
		}

		if (!dto.getDtLastmod().equals(getDtLastmod())) {

			return -1;
		} else {
			try {
				EntityBeanAutoUpdate.update(dto, this);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			return 0;
		}
	}

}