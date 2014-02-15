package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.VODInterfaceProductDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class VodProductBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer smsProductID) throws CreateException {

       // setSmsProductID(smsProductID);
        return null;
    }

    public Integer ejbCreate(VODInterfaceProductDTO dto) throws CreateException {
    	setSmsProductID(new Integer(dto.getSmsProductID()));
    	setVodProductIDlist(dto.getVodProductIDList());
    	setVodServiceIDlist(dto.getVodServiceIDList());
    	setBillingCodeList(dto.getBillingCodeList());
    	setNewsaflag(dto.getNewsaflag());
    	setStatus(dto.getStatus());
    	setAcctItemTypeID(dto.getAcctItemTypeID());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer smsProductID) throws CreateException {
    }

    public void ejbPostCreate(VODInterfaceProductDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSmsProductID(Integer smsProductID);

    public abstract Integer getSmsProductID();

    public abstract void setVodProductIDlist(String vodProductIDlist);

    public abstract String getVodProductIDlist();

    public abstract void setVodServiceIDlist(String vodServiceIDlist);

    public abstract String getVodServiceIDlist();

    public abstract void setBillingCodeList(String billingCodeList);

    public abstract String getBillingCodeList();

    public abstract void setNewsaflag(String newsaflag);

    public abstract String getNewsaflag();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();

    public abstract void setAcctItemTypeID(String acctItemTypeID);

    public abstract String getAcctItemTypeID();

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
    public int ejbUpdate(VODInterfaceProductDTO dto) {
		/** @todo Complete this method */
		if (dto.getDtLastMod() == null) {
			return -1;
		}

		if (!dto.getDtLastMod().equals(getDtLastmod())) {

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
