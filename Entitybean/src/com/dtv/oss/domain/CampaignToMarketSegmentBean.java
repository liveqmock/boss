package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignToMarketSegmentDTO;

abstract public class CampaignToMarketSegmentBean implements EntityBean {
	EntityContext entityContext;

	public CampaignToMarketSegmentPK ejbCreate(int campaignId,int marketSegmentId)
			throws CreateException {
		//  setCampaignId(campaignId);
		return null;
	}

	public CampaignToMarketSegmentPK ejbCreate(CampaignToMarketSegmentDTO dto)
			throws CreateException {
		setMarketSegmentId(dto.getMarketSegmentId());
		setCampaignId(dto.getCampaignId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int campaignId,int marketSegmentId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CampaignToMarketSegmentDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setCampaignId(int campaignId);

	public abstract void setMarketSegmentId(int marketSegmentId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getCampaignId();

	public abstract int getMarketSegmentId();

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
}