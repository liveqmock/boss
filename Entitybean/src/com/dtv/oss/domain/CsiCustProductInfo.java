package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CsiCustProductInfoDTO;

public interface CsiCustProductInfo extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCsiID(int csiID);

	public int getCsiID();

	public void setAction(String action);

	public String getAction();

	public void setCustProductID(int custProductID);

	public int getCustProductID();

	public void setProductID(int productID);

	public int getProductID();

	public void setReferPackageID(int referPackageID);

	public int getReferPackageID();

	public void setReferDeviceID(int referDeviceID);

	public int getReferDeviceID();

	public void setReferAccountID(int referAccountID);

	public int getReferAccountID();

	public void setReferServiceAccountID(int referServiceAccountID);

	public int getReferServiceAccountID();

	public void setReferCampaignID(int referCampaignID);

	public int getReferCampaignID();

	public void setReferGroupBargainID(int referGroupBargainID);

	public int getReferGroupBargainID();

	public void setReferContractNo(String referContractNo);

	public String getReferContractNo();

	public void setDestProductID(int destProductID);

	public int getDestProductID();

	public void setReferDestDeviceID(int referDestDeviceID);

	public int getReferDestDeviceID();

	public void setStatus(String status);
	
	public void setGroupNo(int groupNo);
	
	public void setSheafNo(int sheafNo);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setReferCCID(int referCCID);

	public int getReferCCID();
	
	public int getGroupNo();

	public int getSheafNo();

	public int ejbUpdate(CsiCustProductInfoDTO dto);
}