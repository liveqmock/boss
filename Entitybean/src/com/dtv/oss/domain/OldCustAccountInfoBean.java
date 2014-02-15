package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OldCustAccountInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class OldCustAccountInfoBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer ID) throws CreateException {

       // setID(iD);
        return null;
    }

    public Integer ejbCreate(OldCustAccountInfoDTO dto) throws CreateException {
    	setCsiID(dto.getCsiID());
    	setAccountID(dto.getAccountID());
    	setAccountName(dto.getAccountName());
    	setAccountType(dto.getAccountType());
    	setDescription(dto.getDescription());
    	setMopID(dto.getMopID());
    	setBankAccountName(dto.getBankAccountName());
    	setBankAccount(dto.getBankAccount());
    	setAddressID(dto.getAddressID());
    	setAddressFlag(dto.getAddressFlag());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    	setComments(dto.getComments()); 
        return null;
    }

    

    public void ejbPostCreate(Integer iD) throws CreateException {
    }

    public void ejbPostCreate(OldCustAccountInfoDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setID(Integer ID);

    public abstract Integer getID();

    public abstract void setCsiID(int csiID);

    public abstract int getCsiID();

    public abstract void setAccountID(int accountID);

    public abstract int getAccountID();

    public abstract void setAccountName(String accountName);

    public abstract String getAccountName();

    public abstract void setAccountType(String accountType);

    public abstract String getAccountType();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setMopID(int mopID);

    public abstract int getMopID();

    public abstract void setBankAccountName(String bankAccountName);

    public abstract String getBankAccountName();

    public abstract void setBankAccount(String bankAccount);

    public abstract String getBankAccount();

    public abstract void setAddressID(int addressID);

    public abstract int getAddressID();

    public abstract void setAddressFlag(String addressFlag);

    public abstract String getAddressFlag();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();
    
    public  abstract void setComments(java.lang.String comments);
	
	public  abstract java.lang.String getComments();
	

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
    public int ejbUpdate(OldCustAccountInfoDTO dto) {
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
