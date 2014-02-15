package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.XSlbdyfDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class XSlbdyfBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer lsh) throws CreateException {
      //  setLsh(lsh);
        return null;
    }

    public Integer ejbCreate(XSlbdyfDTO dto) throws CreateException {
    	setSltcID(dto.getSldID());
    	setLyID(dto.getLyID());
    	setSldID(dto.getSldID());
    	setTczfjh(dto.getTczfjh());
    	setKsRQ(dto.getKsRQ());
    	setJsRQ(dto.getJsRQ());
    	setZmLX(dto.getZmLX());
    	setFyLX(dto.getFyLX());
    	setYfje(dto.getYfje());
    	setZt(dto.getZt());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));   
    	
        return null;
    }

    public void ejbPostCreate(Integer lsh) throws CreateException {
    }

    public void ejbPostCreate(XSlbdyfDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setLsh(Integer lsh);

    public abstract Integer getLsh();

    public abstract void setSltcID(int sltcID);

    public abstract int getSltcID();

    public abstract void setLyID(int lyID);

    public abstract int getLyID();

    public abstract void setSldID(int sldID);

    public abstract int getSldID();

    public abstract void setTczfjh(int tczfjh);

    public abstract int getTczfjh();

    public abstract void setKsRQ(Timestamp ksRQ);

    public abstract Timestamp getKsRQ();

    public abstract void setJsRQ(Timestamp jsRQ);

    public abstract Timestamp getJsRQ();

    public abstract void setZmLX(String zmLX);

    public abstract String getZmLX();

    public abstract void setFyLX(String fyLX);

    public abstract String getFyLX();

    public abstract void setYfje(int yfje);

    public abstract int getYfje();

    public abstract void setZt(String zt);

    public abstract String getZt();

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
    public int ejbUpdate(XSlbdyfDTO dto) {
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

