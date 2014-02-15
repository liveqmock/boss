package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DtvMigrationAreaDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class DtvMigrationAreaBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer id) throws CreateException {
        //setId(id);
        return null;
    }

    public Integer ejbCreate(DtvMigrationAreaDTO dto) throws CreateException {
    	try {
    		
    		System.out.println("Bean-----------------\n"+dto);
			EntityBeanAutoUpdate.update(dto, this);
			setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		    setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		} catch (Exception e) {
			e.printStackTrace();
			 
		}
    	 
        return null;
       
    }

    

    public void ejbPostCreate(Integer id) throws CreateException {
    }

    public void ejbPostCreate(DtvMigrationAreaDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setId(Integer id);

    public abstract Integer getId();

    public abstract void setCreateDate(Timestamp createDate);

    

    public abstract Timestamp getCreateDate();

    public abstract void setCreateOpId(Integer createOpId);

    public abstract Integer getCreateOpId();

    public abstract void setAreaCode(String areaCode);

    public abstract String getAreaCode();

    public abstract void setAreaName(String areaName);

    public abstract String getAreaName();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setRegionalAreaId(int regionalAreaId);

    public abstract int getRegionalAreaId();

    public abstract void setMigrationTeamName(String migrationTeamName);

    public abstract String getMigrationTeamName();

    public abstract void setMigrationTeamId(String migrationTeamId);

    public abstract String getMigrationTeamId();

    public abstract void setBatchStartDate(Timestamp batchStartDate);

    public abstract Timestamp getBatchStartDate();
    
	public abstract int getPlanMigrationRoomNum();
	
	public abstract void setPlanMigrationRoomNum(int planMigrationRoomNum);
    
    public abstract void setBatchEndDate(Timestamp batchEndDate);

    public abstract Timestamp getBatchEndDate();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setStatusDate(Timestamp statusDate);

    public abstract Timestamp getStatusDate();

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
    public int ejbUpdate(DtvMigrationAreaDTO dto) {
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
