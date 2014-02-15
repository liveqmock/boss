package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.MachineRoomDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class MachineRoomBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer id) throws CreateException {
        //setId(id);
        return null;
    }

    public Integer ejbCreate(MachineRoomDTO dto) throws CreateException {
    	setMachineRoomCode(dto.getMachineRoomCode());
        setMachineRoomName(dto.getMachineRoomName());
        setDescription(dto.getDescription());
        setDetailAddress(dto.getDetailAddress());
    	setDistrictId(dto.getDistrictId());
      	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
	    setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(Integer id) throws CreateException {
    }

    public void ejbPostCreate(MachineRoomDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setId(Integer id);

    public abstract Integer getId();

    public abstract void setMachineRoomCode(String machineRoomCode);

    public abstract String getMachineRoomCode();
    
    public abstract void setDistrictId(int districtId);

    public abstract int getDistrictId();

    public abstract void setMachineRoomName(String machineRoomName);

    public abstract String getMachineRoomName();

    public abstract void setDescription(String description);

    public abstract String getDescription();

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
    public int ejbUpdate(MachineRoomDTO dto) {
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

