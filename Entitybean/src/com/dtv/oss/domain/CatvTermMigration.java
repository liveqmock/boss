package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CatvTermMigrationDTO;

public interface CatvTermMigration extends EJBLocalObject {
    public void setComments(String comments);

    public String getComments();

    public void setMigrationOption(String migrationOption);

    public String getMigrationOption();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public java.lang.String getCatvID();

    public void setAreaID(int areaID);

    public int getAreaID();

    public void setCreateOpID(int createOpID);

    public int getCreateOpID();

    public void setCreateDate(Timestamp createDate);

    public Timestamp getCreateDate();

    public void setMigrationStatus(String migrationStatus);

    public String getMigrationStatus();

    public void setMigrationDoneDate(Timestamp migrationDoneDate);

    public Timestamp getMigrationDoneDate();

    public void setDtvCustomerID(int dtvCustomerID);

    public int getDtvCustomerID();

    public void setCreditNum(int creditNum);

    public int getCreditNum();

    public void setUsedCreditNum(int usedCreditNum);

    public int getUsedCreditNum();

    public void setWorkerName(String workerName);

    public String getWorkerName();
    public int ejbUpdate(CatvTermMigrationDTO dto);
}
