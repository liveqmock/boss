package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ContractToPackageDTO;

public abstract class ContractToPackageBean implements EntityBean {
    EntityContext entityContext;
    public ContractToPackagePK ejbCreate(String contractNo,
                                         int productPackageID) throws
            CreateException {

     //   setContractNo(contractNo);
     //   setProductPackageID(productPackageID);
        return null;
    }

    public ContractToPackagePK  ejbCreate(ContractToPackageDTO dto) throws CreateException {
    	setContractNo(dto.getContractNo());
        setProductPackageID(dto.getProductPackageID());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(String contractNo, int productPackageID) throws
            CreateException {
    }

    public void ejbPostCreate(ContractToPackageDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setContractNo(String contractNo);

    public abstract String getContractNo();

    public abstract void setProductPackageID(int productPackageID);

    public abstract int getProductPackageID();

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
}
