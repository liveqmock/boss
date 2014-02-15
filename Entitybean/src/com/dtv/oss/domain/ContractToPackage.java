package com.dtv.oss.domain;
import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

public interface ContractToPackage extends EJBLocalObject {

    public String getContractNo();

    public int getProductPackageID();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
