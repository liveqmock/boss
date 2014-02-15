package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import java.sql.Date;
import java.sql.Timestamp;

import com.dtv.oss.dto.FapiaoDetailDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class FapiaoDetailBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

        //setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(FapiaoDetailDTO dto) throws CreateException {
    	setUnit(dto.getUnit());
        setAmount(dto.getAmount());
        setCompleteTaxTag(dto.getCompleteTaxTag());
        setIsFixed(dto.getIsFixed());
        setAuthBy(dto.getAuthBy());
        setStatus(dto.getStatus());

        setCreateTime(dto.getCreateTime());
        setDtCreate(dto.getDtCreate());
        setDtLastmod(dto.getDtLastmod());
        setFapiaoSeqNo(dto.getFapiaoSeqNo());
        setQuantity(dto.getQuantity());

        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(FapiaoDetailDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setFapiaoSeqNo(int fapiaoSeqNo);

    public abstract int getFapiaoSeqNo();

    public abstract void setQuantity(int quantity);

    public abstract int getQuantity();

    public abstract void setUnit(String unit);

    public abstract String getUnit();

    public abstract void setAmount(double amount);

    public abstract double getAmount();

    public abstract void setCompleteTaxTag(String completeTaxTag);

    public abstract String getCompleteTaxTag();

    public abstract void setIsFixed(String isFixed);

    public abstract String getIsFixed();

    public abstract void setAuthBy(String authBy);

    public abstract String getAuthBy();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setCreateTime(Timestamp createTime);

    public abstract Timestamp getCreateTime();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLast);

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
    
    public int ejbUpdate(FapiaoDetailDTO dto) {
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
