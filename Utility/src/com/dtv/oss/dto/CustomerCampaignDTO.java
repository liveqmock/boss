package com.dtv.oss.dto;

import java.sql.Timestamp;


public class CustomerCampaignDTO implements ReflectionSupport, java.io.Serializable{
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int ccID;
  private int campaignID;
  private int csiID;
  private int customerID;
  private String contractNo;
  private int accountID;
  private int serviceAccountID;
  private int groupBargainID;
  private Timestamp dateFrom;
  private Timestamp dateTo;
  private String autoExtendFlag;
  private String comments;
  private int creatreOpID;
  private int createOrgID;
  private Timestamp prePaidTo;
  private Timestamp createTime;
  private String allowPause;
  private String allowTransition;
  private String allowTransfer;
  private String allowClose;
  private String allowAlter;
  private Timestamp nbrDate;
  
  

/**
 * @return Returns the nbrDate.
 */
public Timestamp getNbrDate() {
	return nbrDate;
}
/**
 * @param nbrDate The nbrDate to set.
 */
public void setNbrDate(Timestamp nbrDate) {
	this.nbrDate = nbrDate;
	put("nbrDate");
}
/**
 * @return Returns the autoExtendFlag.
 */
public String getAutoExtendFlag() {
	return autoExtendFlag;
}
/**
 * @param autoExtendFlag The autoExtendFlag to set.
 */
public void setAutoExtendFlag(String autoExtendFlag) {
	this.autoExtendFlag = autoExtendFlag;
	put("autoExtendFlag");
}
/**
 * @return Returns the comments.
 */
public String getComments() {
	return comments;
}
/**
 * @param comments The comments to set.
 */
public void setComments(String comments) {
	this.comments = comments;
	put("comments");
}
/**
 * @return Returns the createOrgID.
 */
public int getCreateOrgID() {
	return createOrgID;
}
/**
 * @param createOrgID The createOrgID to set.
 */
public void setCreateOrgID(int createOrgID) {
	this.createOrgID = createOrgID;
	put("createOrgID");
}
/**
 * @return Returns the createTime.
 */
public Timestamp getCreateTime() {
	return createTime;
}
/**
 * @param createTime The createTime to set.
 */
public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
	 put("createTime");
}
/**
 * @return Returns the creatreOpID.
 */
public int getCreatreOpID() {
	return creatreOpID;
}
/**
 * @param creatreOpID The creatreOpID to set.
 */
public void setCreatreOpID(int creatreOpID) {
	this.creatreOpID = creatreOpID;
	 put("creatreOpID");
}
/**
 * @return Returns the prePaidTo.
 */
public Timestamp getPrePaidTo() {
	return prePaidTo;
}
/**
 * @param prePaidTo The prePaidTo to set.
 */
public void setPrePaidTo(Timestamp prePaidTo) {
	this.prePaidTo = prePaidTo;
	 put("prePaidTo");
}
 
/**
 * @return Returns the accountID.
 */
public int getAccountID() {
	return accountID;
}
/**
 * @param accountID The accountID to set.
 */
public void setAccountID(int accountID) {
	this.accountID = accountID;
	 put("accountID");
	
}
/**
 * @return Returns the contactNo.
 */
public String getContractNo() {
	return contractNo;
}
/**
 * @param contactNo The contactNo to set.
 */
public void setContactNo(String contractNo) {
	this.contractNo = contractNo;
	 put("contractNo");
}
/**
 * @return Returns the serviceAccountID.
 */
public int getServiceAccountID() {
	return serviceAccountID;
}
/**
 * @param serviceAccountID The serviceAccountID to set.
 */
public void setServiceAccountID(int serviceAccountID) {
	this.serviceAccountID = serviceAccountID;
	 put("serviceAccountID");
}
  
  

 public int getGroupBargainID() {
    return groupBargainID;
  }

  public void setGroupBargainID(int groupBargainID) {
    this.groupBargainID = groupBargainID;
     put("groupBargainID");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
      put("status");
  }

  public Timestamp getDtCreate() {
    return dtCreate;
  }

  public void setDtCreate(Timestamp dtCreate) {
    this.dtCreate = dtCreate;
  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public int getCcID() {
    return ccID;
  }

  public void setCcID(int ccID) {
    this.ccID = ccID;

  }

  public int getCampaignID() {
    return campaignID;
  }

  public void setCampaignID(int campaignID) {
    this.campaignID = campaignID;
     put("campaignID");
  }

  public int getCsiID() {
    return csiID;
  }

  public void setCsiID(int csiID) {
    this.csiID = csiID;
      put("csiID");
  }

  public int getCustomerID() {
    return customerID;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
    put("customerID");
  }




  public Timestamp getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(Timestamp dateFrom) {
    this.dateFrom = dateFrom;
     put("dateFrom");
  }

  public Timestamp getDateTo() {
    return dateTo;
  }

  public void setDateTo(Timestamp dateTo) {
    this.dateTo = dateTo;
      put("dateTo");
  }

/**
 * @return Returns the allowAlter.
 */
public String getAllowAlter() {
	return allowAlter;
}
/**
 * @param allowAlter The allowAlter to set.
 */
public void setAllowAlter(String allowAlter) {
	this.allowAlter = allowAlter;
	 put("allowAlter");
}
/**
 * @return Returns the allowClose.
 */
public String getAllowClose() {
	return allowClose;
}
/**
 * @param allowClose The allowClose to set.
 */
public void setAllowClose(String allowClose) {
	this.allowClose = allowClose;
	 put("allowClose");
}
/**
 * @return Returns the allowPause.
 */
public String getAllowPause() {
	return allowPause;
}
/**
 * @param allowPause The allowPause to set.
 */
public void setAllowPause(String allowPause) {
	this.allowPause = allowPause;
	 put("allowPause");
}
/**
 * @return Returns the allowTransfer.
 */
public String getAllowTransfer() {
	return allowTransfer;
}
/**
 * @param allowTransfer The allowTransfer to set.
 */
public void setAllowTransfer(String allowTransfer) {
	this.allowTransfer = allowTransfer;
	put("allowTransfer");
}
/**
 * @return Returns the allowTransition.
 */
public String getAllowTransition() {
	return allowTransition;
}
/**
 * @param allowTransition The allowTransition to set.
 */
public void setAllowTransition(String allowTransition) {
	this.allowTransition = allowTransition;
	put("allowTransition");
}
  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CustomerCampaignDTO that = (CustomerCampaignDTO) obj;
        return ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                (this.getStatus() != null &&
                 this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getCcID() == that.getCcID() &&
           this.getCampaignID() == that.getCampaignID() &&
            this.getCsiID() == that.getCsiID() &&
            this.getCustomerID() == that.getCustomerID() &&
            this.getServiceAccountID() == that.getServiceAccountID()&&
            this.getGroupBargainID() == that.getGroupBargainID() &&
			this.getAccountID() == that.getAccountID()&&
			  this.getCreateOrgID() == that.getCreateOrgID() &&
	            this.getCreatreOpID() == that.getCreatreOpID() &&
	            this.getServiceAccountID() == that.getServiceAccountID()&&
			( ( (this.getContractNo() == null) && (that.getContractNo() == null)) ||
		    (this.getContractNo() != null && this.getContractNo().equals(that.getContractNo()))) &&
		    ( ( (this.getAutoExtendFlag() == null) && (that.getAutoExtendFlag() == null)) ||
			(this.getAutoExtendFlag() != null && this.getAutoExtendFlag().equals(that.getAutoExtendFlag()))) &&
			( ( (this.getComments() == null) && (that.getComments() == null)) ||
				(this.getComments() != null && this.getComments().equals(that.getComments()))) &&
			( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
				(this.getCreateTime() != null && this.getCreateTime().equals(that.getCreateTime()))) &&	
				( ( (this.getPrePaidTo() == null) && (that.getPrePaidTo() == null)) ||
						(this.getPrePaidTo() != null && this.getPrePaidTo().equals(that.getPrePaidTo()))) &&		
            ( ( (this.getDateFrom() == null) && (that.getDateFrom() == null)) ||
             (this.getDateFrom() != null &&
              this.getDateFrom().equals(that.getDateFrom()))) &&
              ( ( (this.getNbrDate() == null) && (that.getNbrDate() == null)) ||
                    (this.getNbrDate() != null &&
                     this.getNbrDate().equals(that.getNbrDate()))) &&
            ( ( (this.getDateTo() == null) && (that.getDateTo() == null)) ||
             (this.getDateTo() != null &&
              this.getDateTo().equals(that.getDateTo())));
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
                 buf.append(";status:").append(status);
                 buf.append(",csiID:").append(csiID);
                 buf.append(",").append(dtCreate);
                 buf.append(",").append(dtLastmod);
                 buf.append(",").append(ccID);
                 buf.append(",").append(campaignID);
                 buf.append(",").append(serviceAccountID);
                 buf.append(",").append(accountID);
                 buf.append(",").append(contractNo);
                 buf.append(",").append(groupBargainID);
                 buf.append(",").append(customerID);
                 buf.append(",dateFrom:").append(dateFrom);
                 buf.append(",dateTo:").append(dateTo);
                 buf.append(",").append(autoExtendFlag);
                 buf.append(",").append(comments);
                 buf.append(",").append(creatreOpID);
                 buf.append(",").append(createOrgID);
                 buf.append(",").append(prePaidTo);
                 buf.append(",").append(createTime);
                 buf.append(",").append(allowAlter);
                 buf.append(",").append(allowClose);
                 buf.append(",").append(allowTransfer);
                 buf.append(",").append(allowTransition);
                 buf.append(",").append(allowPause);
                 buf.append(",nbrDate:").append(nbrDate);
                

                 return buf.toString();
         }

         private java.util.Map map = new java.util.HashMap();

         public void put(String field) { map.put(field, Boolean.TRUE); }

         public java.util.Map getMap() { return this.map; }

}
