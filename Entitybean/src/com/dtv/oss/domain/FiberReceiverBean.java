package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FiberReceiverDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class FiberReceiverBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer id) throws CreateException {
      //  setId(id);
        return null;
    }

    public Integer ejbCreate(FiberReceiverDTO dto) throws CreateException {
    	setFiberReceiverCode(dto.getFiberReceiverCode());
    	setDescription(dto.getDescription());
    	setFiberTransmitterId(dto.getFiberTransmitterId());
    	setDetailAddress(dto.getDetailAddress());
    	setDistrictId(dto.getDistrictId());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer id) throws CreateException {
    }

    public void ejbPostCreate(FiberReceiverDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setId(Integer id);

    public abstract Integer getId();
    
    public abstract void setDistrictId(int districtId);

    public abstract int getDistrictId();

    public abstract void setFiberReceiverCode(String fiberReceiverCode);

    public abstract String getFiberReceiverCode();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setFiberTransmitterId(int fiberTransmitterId);

    public abstract int getFiberTransmitterId();

    public abstract void setDetailAddress(String detailAddress);

    public abstract String getDetailAddress();

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
    public int ejbUpdate(FiberReceiverDTO dto) {
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
