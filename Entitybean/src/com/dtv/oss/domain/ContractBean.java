package com.dtv.oss.domain;


import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class ContractBean implements EntityBean {
    EntityContext entityContext;
    public String ejbCreate(String contractNo) throws CreateException {

       // setContractNo(contractNo);
    	return null;
    }

    public String ejbCreate(ContractDTO dto) throws CreateException {
    	
    	setContractNo(dto.getContractNo());
    	setName(dto.getName());
    	setDescription(dto.getDescription());
    	setDatefrom(dto.getDatefrom());
    	setNormaldate(dto.getNormaldate());
    	setDateto(dto.getDateto());
    	setRentFeeTotal(dto.getRentFeeTotal());
    	setOneOffFeeTotal(dto.getOneOffFeeTotal());
    	setPrepayAmount(dto.getPrepayAmount());
    	setPrepayMopID(dto.getPrepayMopID());
    	setUsedDate(dto.getUsedDate());
    	setUsedCustomerID(dto.getUsedCustomerID());
    	setSheetNo(dto.getSheetNo());
    	setUserCount(dto.getUserCount());
    	setUsedCount(dto.getUsedCount());
    	setSourceOrg(dto.getSourceOrg());
    	setStatus(dto.getStatus());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    	return null;
    }

    public void ejbPostCreate(String contractNo) throws CreateException {
    }

    public void ejbPostCreate(ContractDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setContractNo(String contractNo);

    public abstract String getContractNo();

    public abstract void setName(String name);

    public abstract String getName();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setDatefrom(Timestamp datefrom);

    public abstract Timestamp getDatefrom();

    public abstract void setNormaldate(Timestamp normaldate);

    public abstract Timestamp getNormaldate();

    public abstract void setDateto(Timestamp dateto);

    public abstract Timestamp getDateto();

    public abstract void setRentFeeTotal(double rentFeeTotal);

    public abstract double getRentFeeTotal();

    public abstract void setOneOffFeeTotal(double oneOffFeeTotal);

    public abstract double getOneOffFeeTotal();

    public abstract void setPrepayAmount(double prepayAmount);

    public abstract double getPrepayAmount();

    public abstract void setPrepayMopID(int prepayMopID);

    public abstract int getPrepayMopID();

    public abstract void setUsedDate(Timestamp usedDate);

    public abstract Timestamp getUsedDate();

    public abstract void setUsedCustomerID(int usedCustomerID);

    public abstract int getUsedCustomerID();

    public abstract void setSheetNo(String sheetNo);

    public abstract String getSheetNo();

    public abstract void setUserCount(int userCount);

    public abstract int getUserCount();

    public abstract void setUsedCount(int usedCount);

    public abstract int getUsedCount();

    public abstract void setSourceOrg(int sourceOrg);

    public abstract int getSourceOrg();

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
    public int ejbUpdate(ContractDTO dto) {
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
