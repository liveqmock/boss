package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BillingRuleDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private double value;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int eventClass;
  private String eventReason;
  private int productId;
  private int destProductId;
  private int brcntIdCustType;
  private int brcntIdAcctType;
  private int packageId;
  private int brcntIdCampaign;
   
  private String useFormulaFlag;
  private int formulaId;
  private String acctItemTypeId;
  private String brName;
  
  private String brDesc;
  private String allowReturn;
  private Timestamp validFrom;
  private int brCntIDMarketSegmentID;
  private int brCntIDUserType;
  private int priority;
  private String brRateType;
  private String rfBillingCycleFlag;
  private int feeSplitPlanID;
  private int destPackageId;
  private int brCntIDCATVTermType;
  private int brCntIDCableType;
  private int brCntIDBiDrectionFlag;
  private int oldPortNo;
  private int newPortNo;
  
  
  public int getBrCntIDBiDrectionFlag() {
	return brCntIDBiDrectionFlag;
}
public void setBrCntIDBiDrectionFlag(int brCntIDBiDrectionFlag) {
	this.brCntIDBiDrectionFlag = brCntIDBiDrectionFlag;
	 put("brCntIDBiDrectionFlag");
}
public int getBrCntIDCableType() {
	return brCntIDCableType;
}
public void setBrCntIDCableType(int brCntIDCableType) {
	this.brCntIDCableType = brCntIDCableType;
	 put("brCntIDCableType");
}
public int getBrCntIDCATVTermType() {
	return brCntIDCATVTermType;
}
public void setBrCntIDCATVTermType(int brCntIDCATVTermType) {
	this.brCntIDCATVTermType = brCntIDCATVTermType;
	put("brCntIDCATVTermType");
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
/**
 * @return Returns the brCntIDMarketSegmentID.
 */
public int getBrCntIDMarketSegmentID() {
	return brCntIDMarketSegmentID;
}
/**
 * @param brCntIDMarketSegmentID The brCntIDMarketSegmentID to set.
 */
public void setBrCntIDMarketSegmentID(int brCntIDMarketSegmentID) {
	this.brCntIDMarketSegmentID = brCntIDMarketSegmentID;
	put("brCntIDMarketSegmentID");
}
/**
 * @return Returns the brCntIDUserType.
 */
public int getBrCntIDUserType() {
	return brCntIDUserType;
}
/**
 * @param brCntIDUserType The brCntIDUserType to set.
 */
public void setBrCntIDUserType(int brCntIDUserType) {
	this.brCntIDUserType = brCntIDUserType;
	put("brCntIDUserType");
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
 * @return Returns the allowReturn.
 */
public String getAllowReturn() {
	return allowReturn;
}
/**
 * @param allowReturn The allowReturn to set.
 */
public void setAllowReturn(String allowReturn) {
	this.allowReturn = allowReturn;
	put("allowReturn");
}
  private Timestamp validTo;
  public BillingRuleDTO(){

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

  public int getBrcntIdCustType() {
    return brcntIdCustType;
  }

  public void setBrcntIdCustType(int brcntIdCustType) {
    this.brcntIdCustType = brcntIdCustType;
     put("brcntIdCustType");
  }

  public int getBrcntIdAcctType() {
    return brcntIdAcctType;
  }

  public void setBrcntIdAcctType(int brcntIdAcctType) {
    this.brcntIdAcctType = brcntIdAcctType;
     put("brcntIdAcctType");
  }

  public int getPackageId() {
    return packageId;
  }

  public void setPackageId(int packageId) {
    this.packageId = packageId;
     put("packageId");
  }

  public int getBrcntIdCampaign() {
    return brcntIdCampaign;
  }

  public void setBrcntIdCampaign(int brcntIdCampaign) {
    this.brcntIdCampaign = brcntIdCampaign;
    put("brcntIdCampaign");
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

  public String getAcctItemTypeId() {
    return acctItemTypeId;
  }

  public void setAcctItemTypeId(String acctItemTypeId) {
    this.acctItemTypeId = acctItemTypeId;
    put("acctItemTypeId");
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
        BillingRuleDTO that = (BillingRuleDTO) obj;
        return  this.getId() ==  that.getId()  &&
        this.getDestPackageId() ==  that.getDestPackageId()  &&
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
            this.getBrcntIdCustType() == that.getBrcntIdCustType() &&
            this.getBrcntIdAcctType() == that.getBrcntIdAcctType() &&
            this.getPackageId() == that.getPackageId() &&
             this.getBrcntIdCampaign() == that.getBrcntIdCampaign() &&
             this.getBrCntIDMarketSegmentID() == that.getBrCntIDMarketSegmentID() &&
             this.getBrCntIDUserType() == that.getBrCntIDUserType() &&
             this.getPriority() == that.getPriority() &&
             
             this.getBrCntIDCATVTermType() == that.getBrCntIDCATVTermType() &&
             this.getBrCntIDBiDrectionFlag() == that.getBrCntIDBiDrectionFlag() &&
             this.getBrCntIDCableType() == that.getBrCntIDCableType() &&
             this.getOldPortNo() == that.getOldPortNo() &&
             this.getNewPortNo() == that.getNewPortNo() &&
             
             this.getFeeSplitPlanID() == that.getFeeSplitPlanID()  &&
             ( ( (this.getBrRateType() == null) && (that.getBrRateType() == null)) ||
                    (this.getBrRateType() != null &&
                     this.getBrRateType().equals(that.getBrRateType()))) &&
            ( ( (this.getUseFormulaFlag() == null) && (that.getUseFormulaFlag() == null)) ||
             (this.getUseFormulaFlag() != null &&
              this.getUseFormulaFlag().equals(that.getUseFormulaFlag()))) &&
              ( ( (this.getAllowReturn() == null) && (that.getAllowReturn() == null)) ||
                    (this.getAllowReturn() != null &&
                     this.getAllowReturn().equals(that.getAllowReturn()))) &&
              ( ( (this.getBrName() == null) && (that.getBrName() == null)) ||
                    (this.getBrName() != null &&
                     this.getBrName().equals(that.getBrName()))) && 
                     ( ( (this.getBrDesc() == null) && (that.getBrDesc() == null)) ||
                            (this.getBrDesc() != null &&
                             this.getBrDesc().equals(that.getBrDesc()))) && 		 
            this.getFormulaId() == that.getFormulaId() &&
            ( ( (this.getAcctItemTypeId() == null) && (that.getAcctItemTypeId() == null)) ||
             (this.getAcctItemTypeId() != null &&
              this.getAcctItemTypeId().equals(that.getAcctItemTypeId()))) &&
              ( ( (this.getRfBillingCycleFlag() == null) && (that.getRfBillingCycleFlag() == null)) ||
                    (this.getRfBillingCycleFlag() != null &&
                     this.getRfBillingCycleFlag().equals(that.getRfBillingCycleFlag()))) &&
          
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

               buf.append(",").append(destProductId);
               buf.append(",").append(feeSplitPlanID);
               buf.append(",").append(brcntIdCustType);
               buf.append(",").append(brcntIdAcctType);
               buf.append(",").append(packageId);
               buf.append(",").append(brcntIdCampaign);
               
               buf.append(",").append(useFormulaFlag);
               buf.append(",").append(formulaId);
               buf.append(",").append(acctItemTypeId);
               buf.append(",").append(brName);
               buf.append(",").append(brDesc);
               buf.append(",").append(validFrom);
               buf.append(",").append(allowReturn);
               buf.append(",").append(brCntIDMarketSegmentID);
               buf.append(",").append(brCntIDUserType);
               buf.append(",").append(priority);
               buf.append(",").append(brRateType);
               buf.append(",").append(rfBillingCycleFlag);
               buf.append(",").append(destPackageId);
               
               buf.append(",").append(brCntIDCATVTermType);
               buf.append(",").append(brCntIDCableType);
               buf.append(",").append(brCntIDBiDrectionFlag);
               buf.append(",").append(oldPortNo);
               buf.append(",").append(newPortNo); 
               buf.append(validTo);

               return buf.toString();


  }
  private java.util.Map map = new java.util.HashMap();

      public void put(String field) { map.put(field, Boolean.TRUE); }

      public java.util.Map getMap() { return this.map; }


/**
 * @return Returns the brDesc.
 */
public String getBrDesc() {
	return brDesc;
}
/**
 * @param brDesc The brDesc to set.
 */
public void setBrDesc(String brDesc) {
	this.brDesc = brDesc;
	put("brDesc");
}
/**
 * @return Returns the brName.
 */
public String getBrName() {
	return brName;
}
/**
 * @param brName The brName to set.
 */
public void setBrName(String brName) {
	this.brName = brName;
	put("brName");
}
public int getDestPackageId() {
	return destPackageId;
}
public void setDestPackageId(int destPackageId) {
	this.destPackageId = destPackageId;
	put("destPackageId");
}
      }


