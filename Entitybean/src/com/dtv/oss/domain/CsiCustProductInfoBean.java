package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CsiCustProductInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CsiCustProductInfoBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(CsiCustProductInfoDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		//setId(new Integer(dto.getId()));
		setCsiID(dto.getCsiID());
		setAction(dto.getAction());
		setCustProductID(dto.getCustProductID());
		setProductID(dto.getProductID());
		setReferPackageID(dto.getReferPackageID());
		setReferDeviceID(dto.getReferDeviceID());
		setReferAccountID(dto.getReferAccountID());
		setReferServiceAccountID(dto.getReferServiceAccountID());
		setReferCampaignID(dto.getReferCampaignID());
		setReferGroupBargainID(dto.getReferGroupBargainID());
		setReferContractNo(dto.getReferContractNo());
		setDestProductID(dto.getDestProductID());
		setReferDestDeviceID(dto.getReferDestDeviceID());
		setStatus(dto.getStatus());
		setReferCCID(dto.getReferCCID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setGroupNo(dto.getGroupNo());
		setSheafNo(dto.getSheafNo());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CsiCustProductInfoDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setCsiID(int csiID);

	public abstract void setAction(java.lang.String action);

	public abstract void setCustProductID(int custProductID);

	public abstract void setProductID(int productID);

	public abstract void setReferPackageID(int referPackageID);

	public abstract void setReferDeviceID(int referDeviceID);

	public abstract void setReferAccountID(int referAccountID);

	public abstract void setReferServiceAccountID(int referServiceAccountID);

	public abstract void setReferCampaignID(int referCampaignID);

	public abstract void setReferGroupBargainID(int referGroupBargainID);

	public abstract void setReferContractNo(java.lang.String referContractNo);

	public abstract void setDestProductID(int destProductID);

	public abstract void setReferDestDeviceID(int referDestDeviceID);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setReferCCID(int referCCID);
	
	public abstract void setGroupNo(int groupNo);

	public abstract void setSheafNo(int sheafNo);

	public abstract java.lang.Integer getId();

	public abstract int getCsiID();

	public abstract java.lang.String getAction();

	public abstract int getCustProductID();

	public abstract int getProductID();

	public abstract int getReferPackageID();

	public abstract int getReferDeviceID();

	public abstract int getReferAccountID();

	public abstract int getReferServiceAccountID();

	public abstract int getReferCampaignID();

	public abstract int getReferGroupBargainID();
	
	public abstract int getGroupNo();

	public abstract int getSheafNo();

	public abstract java.lang.String getReferContractNo();

	public abstract int getDestProductID();

	public abstract int getReferDestDeviceID();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getReferCCID();

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

	public int ejbUpdate(CsiCustProductInfoDTO dto) {
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