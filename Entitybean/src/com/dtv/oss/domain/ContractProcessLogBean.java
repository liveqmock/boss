package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ContractProcessLogDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class ContractProcessLogBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

      //  setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(ContractProcessLogDTO dto) throws CreateException {
    	setContractNo(dto.getContractNo());
    	setAction(dto.getAction());
    	setDescription(dto.getDescription());
    	setActionTime(dto.getActionTime());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(ContractProcessLogDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setContractNo(String contractNo);

    public abstract String getContractNo();

    public abstract void setAction(String action);

    public abstract String getAction();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    

    public abstract Timestamp getDtLastmod();

    public abstract void setActionTime(Timestamp actionTime);

    public abstract Timestamp getActionTime();

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
    public int ejbUpdate(ContractProcessLogDTO dto) {
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
