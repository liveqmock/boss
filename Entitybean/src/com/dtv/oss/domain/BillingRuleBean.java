package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BillingRuleDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BillingRuleBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(BillingRuleDTO dto)
			throws CreateException {
		setEventClass(dto.getEventClass());
		setEventReason(dto.getEventReason());
		setProductId(dto.getProductId());
		setDestProductId(dto.getDestProductId());
		setDestPackageId(dto.getDestPackageId());
		setBrcntIdCustType(dto.getBrcntIdCustType());
		setBrcntIdAcctType(dto.getBrcntIdAcctType());
		setPackageId(dto.getPackageId());
		setBrcntIdCampaign(dto.getBrcntIdCampaign());
		 
		setUseFormulaFlag(dto.getUseFormulaFlag());
		setFormulaId(dto.getFormulaId());
		setAcctItemTypeId(dto.getAcctItemTypeId());
		setValue(dto.getValue());
		setAllowReturn(dto.getAllowReturn()); 
		setValidFrom(dto.getValidFrom());
		setValidTo(dto.getValidTo());
		setStatus(dto.getStatus());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		setBrDesc(dto.getBrDesc());
		setBrName(dto.getBrName());
		setBrCntIDMarketSegmentID(dto.getBrCntIDMarketSegmentID());
		setBrCntIDUserType(dto.getBrCntIDUserType());
		setBrRateType(dto.getBrRateType());
		setRfBillingCycleFlag(dto.getRfBillingCycleFlag());
		setPriority(dto.getPriority());
		setFeeSplitPlanID(dto.getFeeSplitPlanID());
		setBrCntIDBiDrectionFlag(dto.getBrCntIDBiDrectionFlag());
		setBrCntIDCableType(dto.getBrCntIDCableType());
		setBrCntIDCATVTermType(dto.getBrCntIDCATVTermType());
		setNewPortNo(dto.getNewPortNo());
		setOldPortNo(dto.getOldPortNo());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BillingRuleDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	
     public abstract void setOldPortNo(int oldPortNo);
	
	public abstract int getOldPortNo();
    public abstract void setNewPortNo(int newPortNo);
	
	public abstract int getNewPortNo();
    public abstract void setBrCntIDCATVTermType(int brCntIDCATVTermType);
	
	public abstract int getBrCntIDCATVTermType();
	
    public abstract void setBrCntIDCableType(int brCntIDCableType);
	
	public abstract int getBrCntIDCableType();
	
    public abstract void setBrCntIDBiDrectionFlag(int brCntIDBiDrectionFlag);
	
	public abstract int getBrCntIDBiDrectionFlag();
	
	public abstract void setDestPackageId(int destPackageId);
	
	public abstract int getDestPackageId();
	
    public abstract void setFeeSplitPlanID(int feeSplitPlanID);
	
	public abstract int getFeeSplitPlanID();
	
	public abstract void setBrCntIDMarketSegmentID(int brCntIDMarketSegmentID);

	public abstract void setBrCntIDUserType(int brCntIDUserType);

	public abstract void setBrRateType(String brRateType);
	
	public abstract void setRfBillingCycleFlag(String rfBillingCycleFlag);
	
	public abstract String getRfBillingCycleFlag();
	
	
	public abstract void setPriority(int priority);
	
	public abstract int getBrCntIDMarketSegmentID();

	public abstract int getBrCntIDUserType( );

	public abstract String getBrRateType( );
	
	public abstract int getPriority();
	
	public abstract void setId(java.lang.Integer id);

	public abstract void setEventClass(int eventClass);

	public abstract void setEventReason(java.lang.String eventReason);

	public abstract void setProductId(int productId);

	public abstract void setDestProductId(int destProductId);

	public abstract void setBrcntIdCustType(int brcntIdCustType);

	public abstract void setBrcntIdAcctType(int brcntIdAcctType);

	public abstract void setBrDesc(String brDesc);
	
	public abstract void setAllowReturn(String allowReturn);

	public abstract void setBrName(String brName);

	public abstract void setPackageId(int packageId);

	public abstract void setBrcntIdCampaign(int brcntIdCampaign); 

	public abstract void setUseFormulaFlag(java.lang.String useFormulaFlag);

	public abstract void setFormulaId(int formulaId);

	public abstract void setAcctItemTypeId(java.lang.String acctItemTypeId);

	public abstract void setValue(double value);

	 

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

	public abstract int getBrcntIdCustType();

	public abstract int getBrcntIdAcctType();

	public abstract int getPackageId();
	
	public abstract String getAllowReturn();

	public abstract int getBrcntIdCampaign();

	public abstract String getBrName();

	public abstract String getBrDesc();


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

	public int ejbUpdate(BillingRuleDTO dto) {
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