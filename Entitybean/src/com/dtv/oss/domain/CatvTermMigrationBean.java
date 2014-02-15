package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CatvTermMigrationDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CatvTermMigrationBean implements EntityBean {
    EntityContext entityContext;
    public java.lang.String ejbCreate(java.lang.String catvID) throws CreateException {

        //setCatvID(catvID);
        return null;
    }

    public java.lang.String ejbCreate(CatvTermMigrationDTO dto) throws CreateException {
    	
    	try {
			EntityBeanAutoUpdate.update(dto, this);
			setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		    setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		} catch (Exception e) {
			e.printStackTrace();
			 
		}
        return null;
    }

    public void ejbPostCreate(java.lang.String catvID) throws CreateException {
    }

    public void ejbPostCreate(CatvTermMigrationDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCatvID(java.lang.String catvID);

    public abstract java.lang.String getCatvID();

    public abstract void setAreaID(int areaID);

    public abstract int getAreaID();

    public abstract void setCreateOpID(int createOpID);

    public abstract int getCreateOpID();

    public abstract void setCreateDate(Timestamp createDate);

    public abstract Timestamp getCreateDate();

    public abstract void setMigrationStatus(String migrationStatus);

    public abstract String getMigrationStatus();

    public abstract void setMigrationDoneDate(Timestamp migrationDoneDate);

    public abstract Timestamp getMigrationDoneDate();

    public abstract void setDtvCustomerID(int dtvCustomerID);

    public abstract int getDtvCustomerID();

    public  abstract void setCreditNum(int creditNum);

    public abstract int getCreditNum();

    public abstract void setUsedCreditNum(int usedCreditNum);

    public abstract int getUsedCreditNum();

    public abstract void setWorkerName(String workerName);

    public abstract String getWorkerName();

    public abstract void setComments(String comments);

    public abstract String getComments();

    public abstract void setMigrationOption(String migrationOption);

    public abstract String getMigrationOption();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();

    public void ejbLoad() {
    }

    public void ejbStore() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void unsetEntityContext() {
        this.entityContext = null;
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }
    public int ejbUpdate(CatvTermMigrationDTO dto) {
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
