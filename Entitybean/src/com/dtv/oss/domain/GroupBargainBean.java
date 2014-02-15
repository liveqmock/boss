package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.GroupBargainDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class GroupBargainBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(GroupBargainDTO dto)
			throws CreateException {
		setCampaignId(dto.getCampaignId());
		setBargainNo(dto.getBargainNo());
		setDescription(dto.getDescription());
		setCreateTime(dto.getCreateTime());
		setDataFrom(dto.getDataFrom());
		setNormalTimeTo(dto.getNormalTimeTo());
		setDateTo(dto.getDateTo());
		setTotalSheets(dto.getTotalSheets());
		setMopId(dto.getMopId());
		setUsedSheets(dto.getUsedSheets());
		setAbortsheets(dto.getAbortSheets());
		setPrepayDepositFee(dto.getPrepayDepositFee());
		setPrepayImmediateFee(dto.getPrepayImmediateFee());
		setOrgId(dto.getOrgId());
		setAgentId(dto.getAgentId());
		setIsCampaign(dto.getIsCampaign());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {

	}

	public void ejbPostCreate(GroupBargainDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setPrepayDepositFee(double prepayDepositFee);
	
	public abstract void setPrepayImmediateFee(double prepayImmediateFee);
	
	public abstract void setMopId(int mopId);
	
	public abstract void setId(java.lang.Integer id);

	public abstract void setCampaignId(int campaignId);

	public abstract void setBargainNo(java.lang.String bargainNo);

	public abstract void setDescription(java.lang.String description);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setDataFrom(java.sql.Timestamp dataFrom);

	public abstract void setNormalTimeTo(java.sql.Timestamp normalTimeTo);

	public abstract void setDateTo(java.sql.Timestamp dateTo);

	public abstract void setTotalSheets(int totalSheets);

	public abstract void setUsedSheets(int usedSheets);

	public abstract void setAbortsheets(int abortsheets);

	 

	public abstract void setOrgId(int orgId);

	public abstract void setAgentId(int agentId);

	public abstract void setIsCampaign(java.lang.String isCampaign);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getCampaignId();

	public abstract java.lang.String getBargainNo();

	public abstract java.lang.String getDescription();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract java.sql.Timestamp getDataFrom();

	public abstract java.sql.Timestamp getNormalTimeTo();

	public abstract java.sql.Timestamp getDateTo();

	public abstract int getTotalSheets();
	
	public abstract int getMopId();


	public abstract int getUsedSheets();

	public abstract int getAbortsheets();

	 

	public abstract int getOrgId();

	public abstract int getAgentId();

	public abstract java.lang.String getIsCampaign();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	public abstract double getPrepayDepositFee();
	
	public abstract double getPrepayImmediateFee();

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

	public int ejbUpdate(GroupBargainDTO dto) {
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