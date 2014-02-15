package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ConstructionBatchDTO;
import com.dtv.oss.dto.MachineRoomDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

import java.sql.Date;
import java.sql.Timestamp;

public abstract class ConstructionBatchBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer batchId) throws CreateException {

        setBatchId(batchId);
        return null;
    }

    public Integer ejbCreate(ConstructionBatchDTO dto) throws CreateException {
    	
    	try {
			EntityBeanAutoUpdate.update(dto, this);
			setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		    setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		} catch (Exception e) {
			e.printStackTrace();
			 
		}
    	 
        return null;
    }

    public void ejbPostCreate(Integer batchId) throws CreateException {
    }

    public void ejbPostCreate(ConstructionBatchDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setBatchId(Integer batchId);

    public abstract Integer getBatchId();

    public abstract void setReferSheetNo(String referSheetNo);

    public abstract String getReferSheetNo();

    public abstract void setInputFileName(String inputFileName);

    public abstract String getInputFileName();

    public abstract void setCreateOrgId(int createOrgId);

    public abstract int getCreateOrgId();

    public abstract void setCreateDate(Timestamp createDate);

    public abstract Timestamp getCreateDate();

    public abstract void setCreateOperatorId(int createOperatorId);

    public abstract int getCreateOperatorId();

    public abstract void setAuditDate(Timestamp auditDate);

    public abstract Timestamp getAuditDate();

    public abstract void setAuditOperatorId(int auditOperatorId);

    public abstract int getAuditOperatorId();

    public abstract void setAuditOrgId(int auditOrgId);

    public abstract int getAuditOrgId();

    public abstract void setFiberNodeId(int fiberNodeId);

    public abstract int getFiberNodeId();

    public abstract void setBuilderName(String builderName);

    public abstract String getBuilderName();

    public abstract void setPostCode(String postCode);

    public abstract String getPostCode();

    public abstract void setConstructionName(String constructionName);

    public abstract String getConstructionName();

    public abstract void setDistrictId(int districtId);

    public abstract int getDistrictId();

    public abstract void setAddressPrefix(String addressPrefix);

    public abstract String getAddressPrefix();

    public abstract void setTotalHouseNumber(int totalHouseNumber);

    public abstract int getTotalHouseNumber();

    public abstract void setNewHouseNumber(int newHouseNumber);

    public abstract int getNewHouseNumber();

    public abstract void setNewPortNumber(int newPortNumber);

    public abstract int getNewPortNumber();

    public abstract void setCatvTermStatus(String catvTermStatus);

    public abstract String getCatvTermStatus();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();
    
    public abstract void setBiDirectionFlag(String biDirectionFlag);

    public abstract String getBiDirectionFlag();

    public abstract void setCableType(String cableType);

    public abstract String getCableType();

    public abstract void setCatvTermType(String catvTermType);

    public abstract String getCatvTermType();

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
    public int ejbUpdate(ConstructionBatchDTO dto) {
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

 
