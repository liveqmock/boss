package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustomerCampaignBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer ccID)
			throws CreateException {
		//setCcID(ccID);
		return null;
	}

	public java.lang.Integer ejbCreate(CustomerCampaignDTO dto)
			throws CreateException {
		setCampaignID(dto.getCampaignID());
		setCsiID(dto.getCsiID());
		setCustomerID(dto.getCustomerID());
		setGroupBargainID(dto.getGroupBargainID());
		setStatus(dto.getStatus());
		setDateFrom(dto.getDateFrom());
		setAccountID(dto.getAccountID());
		setContractNo(dto.getContractNo());
		setServiceAccountID(dto.getServiceAccountID());
		setDateTo(dto.getDateTo());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setAllowAlter(dto.getAllowAlter());
		setAllowClose(dto.getAllowClose());
		setAllowPause(dto.getAllowPause());
		setAllowTransfer(dto.getAllowTransfer());
		setAllowTransition(dto.getAllowTransition());
		setPrePaidTo(dto.getPrePaidTo());
		setAutoExtendFlag(dto.getAutoExtendFlag());
		setComments(dto.getComments());
		setCreateTime(dto.getCreateTime());
		setCreateOrgID(dto.getCreateOrgID());
		setCreatreOpID(dto.getCreatreOpID());
		setNbrDate(dto.getNbrDate());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer ccID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustomerCampaignDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	
	public abstract void setPrePaidTo(java.sql.Timestamp prePaidTo);
	
	public abstract java.sql.Timestamp getPrePaidTo();
	
	public abstract void setNbrDate(java.sql.Timestamp nbrDate);
	
	public abstract java.sql.Timestamp getNbrDate();
	
	public abstract void setAutoExtendFlag(java.lang.String autoExtendFlag);
	
	public abstract java.lang.String getAutoExtendFlag();
	
	public abstract void setComments(java.lang.String comments);
	
	public abstract java.lang.String getComments();
	
	public abstract void setCreateTime(java.sql.Timestamp createTime);
	
	public abstract java.sql.Timestamp getCreateTime();
	
	public abstract void setCreateOrgID(int createOrgID);
	
	public abstract int getCreateOrgID();
	
	public abstract void setCreatreOpID(int creatreOpID);
	
	public abstract int getCreatreOpID();
	
	public abstract void setAllowAlter(java.lang.String allowAlter);
	
	public abstract java.lang.String getAllowAlter();
	
	public abstract void setAllowClose(java.lang.String allowClose);
	
	public abstract java.lang.String getAllowClose();
	
	public abstract void setAllowPause(java.lang.String allowPause);
	
	public abstract java.lang.String getAllowPause();
	
	public abstract void setAllowTransfer(java.lang.String allowTransfer);
	
	public abstract java.lang.String getAllowTransfer();
	
	public abstract void setAllowTransition(java.lang.String allowTransition);
	
	public abstract java.lang.String getAllowTransition();
	
	public abstract void setCcID(java.lang.Integer ccID);

	public abstract void setCampaignID(int campaignID);

	public abstract void setCsiID(int csiID);

	public abstract void setCustomerID(int customerID);

	public abstract void setStatus(java.lang.String status);
	
	public abstract void setAccountID(int accountID);

	public abstract void setContractNo(java.lang.String contractNo);

	public abstract void setServiceAccountID(int serviceAccountID);

	public abstract void setDateFrom(java.sql.Timestamp dateFrom);

	public abstract void setDateTo(java.sql.Timestamp dateTo);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setGroupBargainID(int groupBargainID);

	public abstract java.lang.Integer getCcID();

	public abstract int getCampaignID();

	public abstract int getCsiID();

	public abstract int getCustomerID();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDateFrom();
	
	public abstract int getAccountID();

	public abstract java.lang.String getContractNo();

	public abstract int getServiceAccountID( );

	public abstract java.sql.Timestamp getDateTo();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getGroupBargainID();

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

	public int ejbUpdate(CustomerCampaignDTO dto) {
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