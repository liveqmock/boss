package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapEntitlementDTO;

public abstract class LdapEntitlementBean implements EntityBean {
    EntityContext entityContext;
    public LdapEntitlementPK ejbCreate(int deviceid, String productname) throws
            CreateException {
       // setDeviceid(deviceid);
       // setProductname(productname);
        return null;
    }

    public LdapEntitlementPK ejbCreate(LdapEntitlementDTO dto) throws CreateException {
         setDeviceid(dto.getDeviceid());
         setProductname(dto.getProductname());
        setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(int deviceid, String productname) throws
            CreateException {
    }

    public void ejbPostCreate(LdapEntitlementDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setDeviceid(int deviceid);

    public abstract int getDeviceid();

    public abstract void setProductname(String productname);

    public abstract String getProductname();

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
