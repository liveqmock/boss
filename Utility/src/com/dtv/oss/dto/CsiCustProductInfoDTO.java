package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CsiCustProductInfoDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private int csiID;
	private String action;
	private int custProductID;
	private int productID;
	private int referPackageID;
	private int referDeviceID;
	private int referAccountID;
	private int referServiceAccountID;
	private int referCampaignID;
	private int referGroupBargainID;
	private String referContractNo;
	private int destProductID;
	private int referDestDeviceID;
	private String status;
    private int referCCID;
    private int groupNo;
    private int sheafNo;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CsiCustProductInfoDTO()
	{
	}

	public CsiCustProductInfoDTO(int id,int referCCID, int csiID, String action, int custProductID, int productID, int referPackageID, int referDeviceID, int referAccountID, int referServiceAccountID, int referCampaignID, int referGroupBargainID, String referContractNo, int destProductID, int referDestDeviceID, String status, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setId(id);
		setCsiID(csiID);
		setAction(action);
		setCustProductID(custProductID);
		setProductID(productID);
		setReferPackageID(referPackageID);
		setReferDeviceID(referDeviceID);
		setReferAccountID(referAccountID);
        setReferCCID(referCCID);
		setReferServiceAccountID(referServiceAccountID);
		setReferCampaignID(referCampaignID);
		setReferGroupBargainID(referGroupBargainID);
		setReferContractNo(referContractNo);
		setDestProductID(destProductID);
		setReferDestDeviceID(referDestDeviceID);
		setStatus(status);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
	}

	public void setId(int id)
	{
		this.id = id;
		//put("id");
	}

	public int getId()
	{
		return id;
	}
    public void setCsiID(int csiID)
    {
        this.csiID = csiID;
        put("csiID");
    }

    public int getCsiID()
    {
        return csiID;
    }

	public void setReferCCID(int referCCID)
	{
		this.referCCID = referCCID;
		put("referCCID");
	}

	public int getReferCCID()
	{
		return referCCID;
	}

	public void setAction(String action)
	{
		this.action = action;
		put("action");
	}

	public String getAction()
	{
		return action;
	}

	public void setCustProductID(int custProductID)
	{
		this.custProductID = custProductID;
		put("custProductID");
	}

	public int getCustProductID()
	{
		return custProductID;
	}

	public void setProductID(int productID)
	{
		this.productID = productID;
		put("productID");
	}

	public int getProductID()
	{
		return productID;
	}

	public void setReferPackageID(int referPackageID)
	{
		this.referPackageID = referPackageID;
		put("referPackageID");
	}

	public int getReferPackageID()
	{
		return referPackageID;
	}

	public void setReferDeviceID(int referDeviceID)
	{
		this.referDeviceID = referDeviceID;
		put("referDeviceID");
	}

	public int getReferDeviceID()
	{
		return referDeviceID;
	}

	public void setReferAccountID(int referAccountID)
	{
		this.referAccountID = referAccountID;
		put("referAccountID");
	}

	public int getReferAccountID()
	{
		return referAccountID;
	}

	public void setReferServiceAccountID(int referServiceAccountID)
	{
		this.referServiceAccountID = referServiceAccountID;
		put("referServiceAccountID");
	}

	public int getReferServiceAccountID()
	{
		return referServiceAccountID;
	}

	public void setReferCampaignID(int referCampaignID)
	{
		this.referCampaignID = referCampaignID;
		put("referCampaignID");
	}

	public int getReferCampaignID()
	{
		return referCampaignID;
	}

	public void setReferGroupBargainID(int referGroupBargainID)
	{
		this.referGroupBargainID = referGroupBargainID;
		put("referGroupBargainID");
	}

	public int getReferGroupBargainID()
	{
		return referGroupBargainID;
	}

	public void setReferContractNo(String referContractNo)
	{
		this.referContractNo = referContractNo;
		put("referContractNo");
	}

	public String getReferContractNo()
	{
		return referContractNo;
	}

	public void setDestProductID(int destProductID)
	{
		this.destProductID = destProductID;
		put("destProductID");
	}

	public int getDestProductID()
	{
		return destProductID;
	}

	public void setReferDestDeviceID(int referDestDeviceID)
	{
		this.referDestDeviceID = referDestDeviceID;
		put("referDestDeviceID");
	}

	public int getReferDestDeviceID()
	{
		return referDestDeviceID;
	}

	public void setStatus(String status)
	{
		this.status = status;
		put("status");
	}

	public String getStatus()
	{
		return status;
	}

	public void setDtCreate(Timestamp dtCreate)
	{
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate()
	{
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod)
	{
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod()
	{
		return dtLastmod;
	}



	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				CsiCustProductInfoDTO that = (CsiCustProductInfoDTO) obj;
				return
					this.getId() == that.getId()  &&
					this.getCsiID() == that.getCsiID()  &&
					(((this.getAction() == null) && (that.getAction() == null)) ||
						(this.getAction() != null && this.getAction().equals(that.getAction()))) &&
					this.getCustProductID() == that.getCustProductID()  &&
					this.getProductID() == that.getProductID()  &&
					this.getReferPackageID() == that.getReferPackageID()  &&
					this.getReferDeviceID() == that.getReferDeviceID()  &&
                   this.getReferCCID() == that.getReferCCID()  &&
					this.getReferAccountID() == that.getReferAccountID()  &&
					this.getReferServiceAccountID() == that.getReferServiceAccountID()  &&
					this.getReferCampaignID() == that.getReferCampaignID()  &&
					this.getReferGroupBargainID() == that.getReferGroupBargainID()  &&
					(((this.getReferContractNo() == null) && (that.getReferContractNo() == null)) ||
						(this.getReferContractNo() != null && this.getReferContractNo().equals(that.getReferContractNo()))) &&
					this.getDestProductID() == that.getDestProductID()  &&
					this.getReferDestDeviceID() == that.getReferDestDeviceID()  &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())));
			}
		}
		return false;
	}

	public int hashCode()
	{
		return toString().hashCode();
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append("<id=").append(id).append(",");;
		buf.append("csiID=").append(csiID).append(",");
		buf.append("action=").append(action).append(",");
		buf.append("custProductID=").append(custProductID).append(",");
		buf.append("productID").append(productID).append(",");
		buf.append("referPackageID=").append(referPackageID).append(",");
		buf.append("referDeviceID=").append(referDeviceID).append(",");
		buf.append("referAccountID=").append(referAccountID).append(",");
		buf.append("referServiceAccountID=").append(referServiceAccountID).append(",");
		buf.append("referCampaignID=").append(referCampaignID).append(",");
		buf.append("referGroupBargainID=").append(referGroupBargainID).append(",");
		buf.append("referContractNo=").append(referContractNo).append(",");
		buf.append("destProductID=").append(destProductID).append(",");
		buf.append("referDestDeviceID=").append(referDestDeviceID).append(",");
		buf.append("status=").append(status).append(",");
        buf.append("referCCID=").append(referCCID).append(",");
		buf.append("dtCreate=").append(dtCreate).append(",");
		buf.append("dtLastmod=").append(dtLastmod).append(",");
		buf.append("groupNo=").append(groupNo).append(",");
		buf.append("sheafNo=").append(sheafNo);
		buf.append(">\n");
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int getSheafNo() {
		return sheafNo;
	}

	public void setSheafNo(int sheafNo) {
		this.sheafNo = sheafNo;
	}

}

