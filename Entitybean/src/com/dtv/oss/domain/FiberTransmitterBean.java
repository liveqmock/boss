package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FiberTransmitterDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class FiberTransmitterBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer id) throws CreateException {
      //  setId(id);
        return null;
    }

    public Integer ejbCreate(FiberTransmitterDTO dto) throws CreateException {
    	
    	setFiberTransmitterCode(dto.getFiberTransmitterCode());
    	setDescription(dto.getDescription());
    	setMachineRoomId(dto.getMachineRoomId());
    	setDetailAddress(dto.getDetailAddress());
    	setDistrictId(dto.getDistrictId());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(Integer id) throws CreateException {
    }

    public void ejbPostCreate(FiberTransmitterDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setId(Integer id);

    public abstract Integer getId();

    public abstract void setFiberTransmitterCode(String fiberTransmitterCode);

    public abstract String getFiberTransmitterCode();

    public abstract void setDescription(String description);

    public abstract String getDescription();
    
    public abstract void setDistrictId(int districtId);

    public abstract int getDistrictId();

    public abstract void setMachineRoomId(int machineRoomId);

    public abstract int getMachineRoomId();

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
    public int ejbUpdate(FiberTransmitterDTO dto) {
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
