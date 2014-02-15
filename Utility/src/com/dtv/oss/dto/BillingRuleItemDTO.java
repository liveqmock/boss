package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BillingRuleItemDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private int brId;
  private double value;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int eventClass;
  private String eventReason;
  private int productId;
  private int destProductId;
  private int packageId;
  private int campaignId;
  private String useFormulaFlag;
  private int formulaId;
  private String custType;
  private String acctType;
 
  private String acctItemTypeId;
  private Timestamp validFrom;
  private Timestamp validTo;
  private String feeType;
  private int marketSegmentID;
  private int userType;
  private int priority;
  private String brRateType;
  
  private String rfBillingCycleFlag;
  private int feeSplitPlanID;
  private String catvTermType;
  private String cableType;
  private String biDrectionFlag;
  private int oldPortNo;
  private int newPortNo;
  
  public String getBiDrectionFlag() {
	return biDrectionFlag;
}
public void setBiDrectionFlag(String biDrectionFlag) {
	this.biDrectionFlag = biDrectionFlag;
	 put("biDrectionFlag");
}
public String getCableType() {
	return cableType;
}
public void setCableType(String cableType) {
	this.cableType = cableType;
	 put("cableType");
}
public String getCatvTermType() {
	return catvTermType;
}
public void setCatvTermType(String catvTermType) {
	this.catvTermType = catvTermType;
	 put("catvTermType");
}
public int getNewPortNo() {
	return newPortNo;
}
public void setNewPortNo(int newPortNo) {
	this.newPortNo = newPortNo;
	 put("newPortNo");
}
public int getOldPortNo() {
	return oldPortNo;
}
public void setOldPortNo(int oldPortNo) {
	this.oldPortNo = oldPortNo;
	 put("oldPortNo");
}
/**
	 * @return Returns the feeSplitPlanID.
	 */
	public int getFeeSplitPlanID() {
		return feeSplitPlanID;
	}
	/**
	 * @param feeSplitPlanID The feeSplitPlanID to set.
	 */
	public void setFeeSplitPlanID(int feeSplitPlanID) {
		this.feeSplitPlanID = feeSplitPlanID;
		 put("feeSplitPlanID");
	}
 
 /**
  * @return Returns the rfBillingCycleFlag.
  */
 public String getRfBillingCycleFlag() {
 	return rfBillingCycleFlag;
 }
 /**
  * @param rfBillingCycleFlag The rfBillingCycleFlag to set.
  */
 public void setRfBillingCycleFlag(String rfBillingCycleFlag) {
 	this.rfBillingCycleFlag = rfBillingCycleFlag;
 	put("rfBillingCycleFlag");
 }
  
 
  
  public BillingRuleItemDTO(){

  }
  /**
   * @return Returns the brCntIDMarketSegmentID.
   */
  public int getMarketSegmentID() {
  	return marketSegmentID;
  }
  /**
   * @param brCntIDMarketSegmentID The brCntIDMarketSegmentID to set.
   */
  public void setMarketSegmentID(int marketSegmentID) {
  	this.marketSegmentID = marketSegmentID;
  	put("marketSegmentID");
  }
  /**
   * @return Returns the brCntIDUserType.
   */
  public int getUserType() {
  	return userType;
  }
  /**
   * @param brCntIDUserType The brCntIDUserType to set.
   */
  public void setUserType(int userType) {
  	this.userType = userType;
  	put("userType");
  }
  /**
   * @return Returns the brRateType.
   */
  public String getBrRateType() {
  	return brRateType;
  }
  /**
   * @param brRateType The brRateType to set.
   */
  public void setBrRateType(String brRateType) {
  	this.brRateType = brRateType;
  	put("brRateType");
  }
  /**
   * @return Returns the priority.
   */
  public int getPriority() {
  	return priority;
  }
  /**
   * @param priority The priority to set.
   */
  public void setPriority(int priority) {
  	this.priority = priority;
  	put("priority");
  }
   
  /**
	 * @return Returns the feeType.
	 */
	public String getFeeType() {
		return feeType;
	}
	/**
	 * @param feeType The feeType to set.
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
		put("feeType");
	}
  public int getId() {
    return id;
  }

  public void setId(int id) {	
    this.id = id;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
     put("value");
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

  public int getEventClass() {
    return eventClass;
  }

  public void setEventClass(int eventClass) {
    this.eventClass = eventClass;
     put("eventClass");
  }

  public String getEventReason() {
    return eventReason;
  }

  public void setEventReason(String eventReason) {
    this.eventReason = eventReason;
     put("eventReason");
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
    put("productId");
  }

  public int getDestProductId() {
    return destProductId;
  }

  public void setDestProductId(int destProductId) {
    this.destProductId = destProductId;
     put("destProductId");
  }

   
  public String getCustType() {
    return custType;
  }

  public void setCustType(String custType) {
    this.custType = custType;
     put("custType");
  }

  public int getPackageId() {
    return packageId;
  }

  public void setPackageId(int packageId) {
    this.packageId = packageId;
     put("packageId");
  }

  

   

  public String getUseFormulaFlag() {
    return useFormulaFlag;
  }

  public void setUseFormulaFlag(String useFormulaFlag) {
    this.useFormulaFlag = useFormulaFlag;
     put("useFormulaFlag");
  }

  public int getFormulaId() {
    return formulaId;
  }

  public void setFormulaId(int formulaId) {
    this.formulaId = formulaId;
      put("formulaId");
  }

   

  
  public Timestamp getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(Timestamp validFrom) {
    this.validFrom = validFrom;
     put("validFrom");
  }

  public Timestamp getValidTo() {
    return validTo;
  }

  public void setValidTo(Timestamp validTo) {
    this.validTo = validTo;
     put("validTo");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BillingRuleItemDTO that = (BillingRuleItemDTO) obj;
        return  this.getId() ==  that.getId()  &&
            this.getValue() == that.getValue() &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getEventClass() == that.getEventClass() &&
            ( ( (this.getEventReason() == null) && (that.getEventReason() == null)) ||
             (this.getEventReason() != null &&
              this.getEventReason().equals(that.getEventReason()))) &&
            this.getProductId() == that.getProductId() &&
            this.getDestProductId() == that.getDestProductId() &&
            this.getNewPortNo() == that.getNewPortNo() && 
            this.getOldPortNo() == that.getOldPortNo() && 
            this.getBrId() == that.getBrId() &&
            this.getFeeSplitPlanID() == that.getFeeSplitPlanID() &&
            this.getPackageId() == that.getPackageId() &&
            this.getMarketSegmentID() == that.getMarketSegmentID() &&
            this.getUserType() == that.getUserType() &&
            this.getPriority() == that.getPriority() &&
            ( ( (this.getBrRateType() == null) && (that.getBrRateType() == null)) ||
                   (this.getBrRateType() != null &&
                    this.getBrRateType().equals(that.getBrRateType()))) &&
           
            ( ( (this.getUseFormulaFlag() == null) && (that.getUseFormulaFlag() == null)) ||
             (this.getUseFormulaFlag() != null &&
              this.getUseFormulaFlag().equals(that.getUseFormulaFlag()))) &&
              ( ( (this.getCustType() == null) && (that.getCustType() == null)) ||
                    (this.getCustType() != null &&
                     this.getCustType().equals(that.getCustType()))) &&
                     (((this.getFeeType() == null) && (that.getFeeType() == null)) ||
							(this.getFeeType() != null && this.getFeeType().equals(that.getFeeType()))) &&
              ( ( (this.getAcctType() == null) && (that.getAcctType() == null)) ||
                    (this.getAcctType() != null &&
                     this.getAcctType().equals(that.getAcctType()))) && 
                     ( ( (this.getRfBillingCycleFlag() == null) && (that.getRfBillingCycleFlag() == null)) ||
                            (this.getRfBillingCycleFlag() != null &&
                             this.getRfBillingCycleFlag().equals(that.getRfBillingCycleFlag()))) &&      
            this.getFormulaId() == that.getFormulaId() &&
            ( ( (this.getAcctItemTypeId() == null) && (that.getAcctItemTypeId() == null)) ||
             (this.getAcctItemTypeId() != null &&
              this.getAcctItemTypeId().equals(that.getAcctItemTypeId()))) &&
              ( ( (this.getCatvTermType() == null) && (that.getCatvTermType() == null)) ||
                      (this.getCatvTermType() != null &&
                       this.getCatvTermType().equals(that.getCatvTermType()))) &&
                       ( ( (this.getCableType() == null) && (that.getCableType() == null)) ||
                               (this.getCableType() != null &&
                                this.getCableType().equals(that.getCableType()))) &&
                                ( ( (this.getBiDrectionFlag() == null) && (that.getBiDrectionFlag() == null)) ||
                                        (this.getBiDrectionFlag() != null &&
                                         this.getBiDrectionFlag().equals(that.getBiDrectionFlag()))) &&
            ( ( (this.getValidFrom() == null) && (that.getValidFrom() == null))
             ||
             (this.getValidFrom() != null && this.getValidFrom().equals(that.getValidFrom()))) &&
            ( ( (this.getValidTo() == null) && (that.getValidTo() == null)) ||
             (this.getValidTo() != null &&
              this.getValidTo().equals(that.getValidTo())));
      }
    }
    return false;
  }

  public int hashCode() {
    return  toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
                buf.append(id);
               buf.append(",").append(status);
               buf.append(",").append(value);
               buf.append(",").append(dtCreate);
               buf.append(",").append(eventClass);
               buf.append(",").append(dtLastmod);
               buf.append(",").append(eventReason);
               buf.append(",").append(productId);
               buf.append(",").append(brId);
               buf.append(",").append(destProductId);
               buf.append(",").append(custType);
               buf.append(",").append(acctType);
               buf.append(",").append(packageId);
               buf.append(",").append(campaignId);
               buf.append(",").append(feeSplitPlanID);
               buf.append(",").append(useFormulaFlag);
               buf.append(",").append(formulaId);
               buf.append(",").append(acctItemTypeId);
               buf.append(",").append(rfBillingCycleFlag);
               buf.append(",").append(validFrom);
               buf.append(",").append(feeType);
               buf.append(",").append(marketSegmentID);
               buf.append(",").append(userType);
               buf.append(",").append(priority);
               buf.append(",").append(brRateType); 
               buf.append(",").append(catvTermType);
               buf.append(",").append(cableType);
               buf.append(",").append(biDrectionFlag);
               buf.append(",").append(oldPortNo);
               buf.append(",").append(newPortNo); 
               buf.append(validTo);

               return buf.toString();


  }
  private java.util.Map map = new java.util.HashMap();

      public void put(String field) { map.put(field, Boolean.TRUE); }

      public java.util.Map getMap() { return this.map; }


 
 
/**
 * @return Returns the acctItemTypeId.
 */
public String getAcctItemTypeId() {
	return acctItemTypeId;
}
/**
 * @param acctItemTypeId The acctItemTypeId to set.
 */
public void setAcctItemTypeId(String acctItemTypeId) {
	this.acctItemTypeId = acctItemTypeId;
	put("acctItemTypeId");
}
/**
 * @return Returns the acctType.
 */
public String getAcctType() {
	return acctType;
}
/**
 * @param acctType The acctType to set.
 */
public void setAcctType(String acctType) {
	this.acctType = acctType;
	put("acctType");
}
/**
 * @return Returns the brId.
 */
public int getBrId() {
	return brId;
}
/**
 * @param brId The brId to set.
 */
public void setBrId(int brId) {
	this.brId = brId;
	put("brId");
}
/**
 * @return Returns the campaignId.
 */
public int getCampaignId() {
	return campaignId;
}
/**
 * @param campaignId The campaignId to set.
 */
public void setCampaignId(int campaignId) {
	this.campaignId = campaignId;
	put("campaignId");
}
      }


