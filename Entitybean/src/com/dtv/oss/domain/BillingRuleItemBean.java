package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BillingRuleItemDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BillingRuleItemBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(BillingRuleItemDTO dto)
			throws CreateException {
		setEventClass(dto.getEventClass());
		setEventReason(dto.getEventReason());
		setProductId(dto.getProductId());
		setDestProductId(dto.getDestProductId());
		setCampaignId(dto.getCampaignId());
		setCustType(dto.getCustType());
		setPackageId(dto.getPackageId());
		setAcctType(dto.getAcctType());
		setUseFormulaFlag(dto.getUseFormulaFlag());
		setFormulaId(dto.getFormulaId());
		setAcctItemTypeId(dto.getAcctItemTypeId());
		setValue(dto.getValue());
		setValidFrom(dto.getValidFrom());
		setValidTo(dto.getValidTo());
		setBrId(dto.getBrId());
		setStatus(dto.getStatus());
		setFeeType(dto.getFeeType());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		setMarketSegmentID(dto.getMarketSegmentID());
		setUserType(dto.getUserType());
		setBrRateType(dto.getBrRateType());
		setPriority(dto.getPriority()); 
		setRfBillingCycleFlag(dto.getRfBillingCycleFlag());
		setFeeSplitPlanID(dto.getFeeSplitPlanID());
		
		setCatvTermType(dto.getCatvTermType());
		setCableType(dto.getCableType());
		setBiDrectionFlag(dto.getBiDrectionFlag()); 
		setOldPortNo(dto.getOldPortNo());
		setNewPortNo(dto.getNewPortNo());
		
		 
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BillingRuleItemDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	
	 public abstract void setBiDrectionFlag(String biDrectionFlag);
		
	public abstract String getBiDrectionFlag();
    public abstract void setCatvTermType(String CatvTermType);
	
	public abstract String getCatvTermType();
	
    public abstract void setCableType(String cableType);
	
	public abstract String getCableType();
	
    public abstract void setOldPortNo(int oldPortNo);
	
	public abstract int getOldPortNo();
	
    public abstract void setNewPortNo(int newPortNo);
	
	public abstract int getNewPortNo();
	
	public abstract void setMarketSegmentID(int marketSegmentID);

	public abstract void setFeeSplitPlanID(int feeSplitPlanID);
	
	public abstract int getFeeSplitPlanID();
	
	public abstract void setUserType(int userType);

	public abstract void setBrRateType(String brRateType);
	
	
	public abstract void setRfBillingCycleFlag(String rfBillingCycleFlag);
	
	public abstract String getRfBillingCycleFlag();
	
	public abstract void setPriority(int priority);
	
	public abstract int getMarketSegmentID();

	public abstract int getUserType( );

	public abstract String getBrRateType( );
	
	public abstract int getPriority();
	 public abstract void setFeeType(String feeType);
	  
	  public abstract String getFeeType();
	public abstract void setId(java.lang.Integer id);

	public abstract void setEventClass(int eventClass);

	public abstract void setEventReason(java.lang.String eventReason);

	public abstract void setProductId(int productId);

	public abstract void setDestProductId(int destProductId);

	public abstract void setCustType(String custType);

	public abstract void setAcctType(String  acctType);

	 
	public abstract void setPackageId(int packageId);

	public abstract void setCampaignId(int CampaignId); 

	public abstract void setUseFormulaFlag(java.lang.String useFormulaFlag);

	public abstract void setFormulaId(int formulaId);

	public abstract void setAcctItemTypeId(java.lang.String acctItemTypeId);

	public abstract void setValue(double value);

	public abstract void setBrId(int brId);
	
	public abstract int getBrId();

	public abstract void setValidFrom(java.sql.Timestamp validFrom);

	public abstract void setValidTo(java.sql.Timestamp validTo);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getEventClass();

	public abstract java.lang.String getEventReason();

	public abstract int getProductId();

	public abstract int getDestProductId();

	public abstract String getCustType();

	public abstract String getAcctType();

	public abstract int getPackageId();
	
	 

	public abstract int getCampaignId();

	 


	public abstract java.lang.String getUseFormulaFlag();

	public abstract int getFormulaId();

	public abstract java.lang.String getAcctItemTypeId();

	public abstract double getValue();

	 

	public abstract java.sql.Timestamp getValidFrom();

	public abstract java.sql.Timestamp getValidTo();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public void ejbLoad() {
		/** @todo Complete this method */
	}

	public void ejbStore() {
		/** @todo Complete this method */
	}

	public void ejbActivate() {
		/** @todo Complete this method */
	}

	public void ejbPassivate() {
		/** @todo Complete this method */
	}

	public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	public int ejbUpdate(BillingRuleItemDTO dto) {
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