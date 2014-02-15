package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class BillBoardBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

       // setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(BillBoardDTO dto) throws CreateException {
    	setName(dto.getName());
    	setDescription(dto.getDescription());
    	setDateFrom(dto.getDateFrom());
    	setDateTo(dto.getDateTo());
    	setStatus(dto.getStatus());
    	setGrade(dto.getGrade());
    	setPublishPerson(dto.getPublishPerson());
    	setPublishDate(dto.getPublishDate());
    	setPublishReason(dto.getPublishReason());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(BillBoardDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();
    
    public abstract void setGrade(String grade);

    public abstract String getGrade();

    public abstract void setPublishPerson(String publishPerson);

    public abstract String getPublishPerson();

    public abstract void setPublishDate(Timestamp publishDate);

    public abstract Timestamp getPublishDate();

    public abstract void setPublishReason(String publishReason);

    public abstract String getPublishReason();


    public abstract void setName(String name);

    public abstract String getName();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setDateFrom(Timestamp dateFrom);

    public abstract Timestamp getDateFrom();

    public abstract void setDateTo(Timestamp dateTo);

    public abstract Timestamp getDateTo();

    public abstract void setStatus(String status);

    public abstract String getStatus();

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
    public int ejbUpdate(BillBoardDTO dto) {
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
