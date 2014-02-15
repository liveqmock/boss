package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BundlePrepaymentDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class BundlePrepaymentBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer campaignId) throws CreateException {

        //setCampaignId(campaignId);
        return null;
    }

    public Integer ejbCreate(BundlePrepaymentDTO dto) throws CreateException {
    	setTargetBundleId(dto.getTargetBundleId());
    	setCampaignId(new Integer (dto.getCampaignId()));
    	setBundlePrepaymentPlanId(dto.getBundlePrepaymentPlanId());
    	setTimeUnitLengthType(dto.getTimeUnitLengthType());
    	setTimeUnitLengthNumber(dto.getTimeUnitLengthNumber());
    	setAcctItemTypeId(dto.getAcctItemTypeId());
    	setFeeType(dto.getFeeType());
    	setAmount(dto.getAmount());
    	setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer campaignId) throws CreateException {
    }

    public void ejbPostCreate(BundlePrepaymentDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCampaignId(Integer campaignId);

    public abstract Integer getCampaignId();

    public abstract void setTargetBundleId(int targetBundleId);

    public abstract int getTargetBundleId();

    public abstract void setBundlePrepaymentPlanId(int bundlePrepaymentPlanId);

    public abstract int getBundlePrepaymentPlanId();

    public abstract void setTimeUnitLengthType(String timeUnitLengthType);

    public abstract String getTimeUnitLengthType();

    public abstract void setTimeUnitLengthNumber(int timeUnitLengthNumber);

    public abstract int getTimeUnitLengthNumber();

    public abstract void setAcctItemTypeId(String acctItemTypeId);

    public abstract String getAcctItemTypeId();

    public abstract void setFeeType(String feeType);

    public abstract String getFeeType();

    public abstract void setAmount(double amount);

    public abstract double getAmount();

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
    public int ejbUpdate(BundlePrepaymentDTO dto) {
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
