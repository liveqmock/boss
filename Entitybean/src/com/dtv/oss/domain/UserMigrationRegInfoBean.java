package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.UserMigrationRegInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class UserMigrationRegInfoBean implements EntityBean {
    EntityContext entityContext;
    public java.lang.String ejbCreate(java.lang.String catvID) throws CreateException {

        //setCatvID(catvID);
        return null;
    }

    public java.lang.String ejbCreate(UserMigrationRegInfoDTO dto) throws CreateException {
    	
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

    public void ejbPostCreate(UserMigrationRegInfoDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCatvID(java.lang.String catvID);

    public abstract java.lang.String getCatvID();

    public abstract void setReferSheetNo(String referSheetNo);

    public abstract String getReferSheetNo();

    public abstract void setUserIntention(String userIntention);

    public abstract String getUserIntention();

    public abstract void setUserFeedBack(String userFeedBack);

    public abstract String getUserFeedBack();

    public abstract void setUserFeedBackDetail(String userFeedBackDetail);

    public abstract String getUserFeedBackDetail();

    public abstract void setBuyPayTvFlag(String buyPayTvFlag);

    public abstract String getBuyPayTvFlag();

    public abstract void setComments(String comments);

    public abstract String getComments();

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
    public int ejbUpdate(UserMigrationRegInfoDTO dto) {
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
