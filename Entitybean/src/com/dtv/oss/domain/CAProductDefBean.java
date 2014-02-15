package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CAProductDefDTO;

public abstract class CAProductDefBean implements EntityBean {
    EntityContext entityContext;
    public CAProductDefPK ejbCreate(int hostID, int opiID, String caProductID) throws
            CreateException {

        //setHostID(hostID);
       // setOpiID(opiID);
       // setCaProductID(caProductID);
        return null;
    }

    public CAProductDefPK ejbCreate(CAProductDefDTO dto) throws CreateException {
    	setHostID(dto.getHostID());
    	setOpiID(dto.getOpiID());
    	setCaProductID(dto.getCaProductID());
    	setProductName(dto.getProductName());
    	setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(int hostID, int opiID, String caProductID) throws CreateException {
    }

    public void ejbPostCreate(CAProductDefDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setHostID(int hostID);

    public abstract int getHostID();

    public abstract void setOpiID(int opiID);

    public abstract int getOpiID();

    public abstract void setCaProductID(String caProductID);

    public abstract String getCaProductID();

    public abstract void setProductName(String productName);

    public abstract String getProductName();

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
