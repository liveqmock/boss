package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FapiaoDetailDTO;
import com.dtv.oss.dto.FapiaoTransitionDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

import java.sql.Date;
import java.sql.Timestamp;

public abstract class FapiaoTransitionBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

        //setSeqNo(seqNo);
        return null;
    }
    
    public Integer ejbCreate(FapiaoTransitionDTO dto) throws CreateException {
    	setAction(dto.getAction());
        setFileName(dto.getFileName());
        setStatus(dto.getStatus());
        setCreateTime(dto.getCreateTime());
        setPrintCode(dto.getPrintCode());
        
        setPrintNumber(dto.getPrintNumber());
        setTaxControlCode(dto.getTaxControlCode());
        setDtCreate(dto.getDtCreate());
        setDtLastmod(dto.getDtLastmod());
        setVolumnSeqNo(dto.getVolumnSeqNo());

        setTotal(dto.getTotal());
        setOpID(dto.getOpID());
        setOrgID(dto.getOrgID());

        return null;
    }
    
    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }
    
    public void ejbPostCreate(FapiaoTransitionDTO dto) throws CreateException {
    }
    
    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setVolumnSeqNo(int volumnSeqNo);

    public abstract int getVolumnSeqNo();

    public abstract void setTotal(int total);

    public abstract int getTotal();

    public abstract void setOpID(int opID);

    public abstract int getOpID();

    public abstract void setOrgID(int orgID);

    public abstract int getOrgID();

    public abstract void setAction(String action);

    public abstract String getAction();

    public abstract void setFileName(String fileName);

    public abstract String getFileName();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setCreateTime(Timestamp createTime);

    public abstract Timestamp getCreateTime();

    public abstract void setPrintCode(String printCode);

    public abstract String getPrintCode();

    public abstract void setPrintNumber(String printNumber);

    public abstract String getPrintNumber();

    public abstract void setTaxControlCode(String taxControlCode);

    public abstract String getTaxControlCode();

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
    
    public int ejbUpdate(FapiaoTransitionDTO dto) {
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
