package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FutureRightDTO;
 
import java.sql.Timestamp;

public interface FutureRight extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setDescription(String description);

    public String getDescription();

    public void setLockToDate(Timestamp lockToDate);

    public Timestamp getLockToDate();

    public void setCreateDate(Timestamp createDate);

    public Timestamp getCreateDate();

    public void setCreateOpId(int createOpId);

    public int getCreateOpId();

    public void setCreateOrgId(int createOrgId);

    public int getCreateOrgId();

    public void setExcuteDate(Timestamp excuteDate);

    public Timestamp getExcuteDate();

    public void setExcuteOpId(int excuteOpId);

    public int getExcuteOpId();

    public void setExcuteOrgId(int excuteOrgId);

    public int getExcuteOrgId();

    public void setCancelDate(Timestamp cancelDate);

    public Timestamp getCancelDate();

    public void setCancelOpId(int cancelOpId);

    public int getCancelOpId();

    public void setCancelOrgId(int cancelOrgId);

    public int getCancelOrgId();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getSeqNo();

    public void setReferSheetId(String referSheetId);

    public String getReferSheetId();

    public void setCsiId(int csiId);

    public int getCsiId();

    public void setCustomerId(int customerId);

    public int getCustomerId();

    public void setAccountId(int accountId);

    public int getAccountId();

    public void setValue(double value);

    public double getValue();
    public int ejbUpdate(FutureRightDTO dto);
}
