package com.dtv.oss.dto;
import java.sql.Timestamp;

public class AccountItemDTO  implements ReflectionSupport, java.io.Serializable
{
	private int aiNO;
	private int batchNO;
	private int custID;
	private int acctID;
	private int serviceAccountID;
	private String acctItemTypeID;
	private int psID;
	private double value;
	private Timestamp date1;
	private Timestamp date2;
	private int billingCycleID;
	private String status;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private int brID;
    private Timestamp createTime;
    private int operatorID;
    private int orgID;
    private java.lang.String referType;
    private int referID;
    private String forcedDepositFlag; 
    private String invoiceFlag;
    private int invoiceNO;
    private String setOffFlag; 
    private double setOffAmount;
	
    private int sourceRecordID;
    private String feeType;
    private String adjustmentFlag;
    private int adjustmentNo;
    private String comments;
    private int ccID;
    private int feeSplitPlanID;
    private String rfBillingCycleFlag;
    
    private int groupNo,sheafNo;
    
    
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
	/**
	 * @return Returns the ccID.
	 */
	public int getCcID() {
		return ccID;
	}
	/**
	 * @param ccID The ccID to set.
	 */
	public void setCcID(int ccID) {
		this.ccID = ccID;
		 put("ccID");
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
	 * @return Returns the adjustmentFlag.
	 */
	public String getAdjustmentFlag() {
		return adjustmentFlag;
	}
	/**
	 * @param adjustmentFlag The adjustmentFlag to set.
	 */
	public void setAdjustmentFlag(String adjustmentFlag) {
		this.adjustmentFlag = adjustmentFlag;
		 put("adjustmentFlag");
	}
	/**
	 * @return Returns the adjustmentNo.
	 */
	public int getAdjustmentNo() {
		return adjustmentNo;
	}
	/**
	 * @param adjustmentNo The adjustmentNo to set.
	 */
	public void setAdjustmentNo(int adjustmentNo) {
		this.adjustmentNo = adjustmentNo;
		 put("adjustmentNo");
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
	/**
	 * @return Returns the productID.
	 */
	public int getSourceRecordID() {
		return sourceRecordID;
	}
	/**
	 * @param productID The productID to set.
	 */
	public void setSourceRecordID(int sourceRecordID) {
		this.sourceRecordID = sourceRecordID;
		put("productID");
	}
	/**
	 * @return Returns the productID.
	 */
	public int getProductID() {
		return productID;
	}
	/**
	 * @param productID The productID to set.
	 */
	public void setProductID(int productID) {
		this.productID = productID;
		put("productID");
	}
    private String creatingMethod;
    private int productID;
    
	/**
	 * @return Returns the creatingMethod.
	 */
	public String getCreatingMethod() {
		return creatingMethod;
	}
	/**
	 * @param creatingMethod The creatingMethod to set.
	 */
	public void setCreatingMethod(String creatingMethod) {
		this.creatingMethod = creatingMethod;
		 put("creatingMethod");
	}
	 

	public void setAiNO(int aiNO)
	{
		this.aiNO = aiNO;
	}

	public int getAiNO()
	{
		return aiNO;
	}
	public void setBrID(int brID)
	{
		this.brID = brID;
	}

	public int getBrID()
	{
		return brID;
	}

	public void setBatchNO(int batchNO)
	{
		this.batchNO = batchNO;
                put("batchNO");
	}

	public int getBatchNO()
	{
		return batchNO;
	}

	public void setCustID(int custID)
	{
		this.custID = custID;
                  put("custID");
	}

	public int getCustID()
	{
		return custID;
	}

	public void setAcctID(int acctID)
	{
		this.acctID = acctID;
                  put("acctID");
	}

	public int getAcctID()
	{
		return acctID;
	}

	public void setServiceAccountID(int serviceAccountID)
	{
		this.serviceAccountID = serviceAccountID;
                 put("serviceAccountID");
	}

	public int getServiceAccountID()
	{
		return serviceAccountID;
	}

	public void setAcctItemTypeID(String acctItemTypeID)
	{
		this.acctItemTypeID = acctItemTypeID;
                  put("acctItemTypeID");
	}

	public String getAcctItemTypeID()
	{
		return acctItemTypeID;
	}

	public void setPsID(int psID)
	{
		this.psID = psID;
                  put("psID");
	}

	public int getPsID()
	{
		return psID;
	}

	public void setValue(double value)
	{
		this.value = value;
                 put("value");
	}

	public double getValue()
	{
		return value;
	}

	public void setDate1(Timestamp date1)
	{
		this.date1 = date1;
                put("date1");
	}

	public Timestamp getDate1()
	{
		return date1;
	}

	public void setDate2(Timestamp  date2)
	{
		this.date2 = date2;
                 put("date2");
	}

	public Timestamp getDate2()
	{
		return date2;
	}

	public void setBillingCycleID(int billingCycleID)
	{
		this.billingCycleID = billingCycleID;
                  put("billingCycleID");
	}

	public int getBillingCycleID()
	{
		return billingCycleID;
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
    public void setCreateTime(Timestamp createTime)
    {
          this.createTime = createTime;
           put("createTime");
     }

     public Timestamp getCreateTime() {
     	
                return createTime;
                
       }
       public void setOperatorID(int operatorID){
               this.operatorID=operatorID;
                put("operatorID");
       }
       public int getOperatorID() {
                return operatorID;
      }
      public void setOrgID(int orgID){
               this.orgID=orgID;
                put("orgID");
       }
       public int getOrgID() {
              return orgID;
      }
      public void setReferID(int referID){
             this.referID=referID;
             put("referID");
     }
     public int getReferID() {
            return referID;
       }
     public void setReferType(String referType){
            this.referType=referType;
            put("referType");
      }
     public String getReferType() {
           return referType;
      }
     public void setInvoiceNO(int invoiceNO){
          this.invoiceNO=invoiceNO;
          put("invoiceNO");
    }
     public int getInvoiceNO() {
          return invoiceNO;
     }
      public void setInvoiceFlag(String invoiceFlag){
          this.invoiceFlag=invoiceFlag;
          put("invoiceFlag");
    }
     public String getInvoiceFlag() {
          return invoiceFlag;
     }
    public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				AccountItemDTO that = (AccountItemDTO) obj;
				return this.getAiNO() == that.getAiNO()
						&& this.getGroupNo() == that.getGroupNo()
						&& this.getSheafNo() == that.getSheafNo()
						&& this.getBatchNO() == that.getBatchNO()
						&& this.getCustID() == that.getCustID()
						&& this.getSourceRecordID() == that.getSourceRecordID()
						&& this.getAcctID() == that.getAcctID()
						&& this.getBrID() == that.getBrID()
						&& this.getInvoiceNO() == that.getInvoiceNO()
						&& this.getOrgID() == that.getOrgID()
						&& this.getReferID() == that.getReferID()
						&& this.getOperatorID() == that.getOperatorID()
						&& this.getServiceAccountID() == that.getServiceAccountID()
						&& (((this.getAcctItemTypeID() == null) && (that.getAcctItemTypeID() == null)) || (this
								.getAcctItemTypeID() != null && this.getAcctItemTypeID().equals(that.getAcctItemTypeID())))
						&& (((this.getComments() == null) && (that.getComments() == null)) || (this.getComments() != null && this
								.getComments().equals(that.getComments())))
						&& (((this.getInvoiceFlag() == null) && (that.getInvoiceFlag() == null)) || (this.getInvoiceFlag() != null && this
								.getInvoiceFlag().equals(that.getInvoiceFlag())))
						&& (((this.getReferType() == null) && (that.getReferType() == null)) || (this.getReferType() != null && this
								.getReferType().equals(that.getReferType())))
						&& this.getPsID() == that.getPsID()
						&& this.getProductID() == that.getProductID()
						&& this.getCcID() == that.getCcID()
						&& this.getFeeSplitPlanID() == that.getFeeSplitPlanID()
						&& this.getValue() == that.getValue()
						&& this.getAdjustmentNo() == that.getAdjustmentNo()
						&& (((this.getDate1() == null) && (that.getDate1() == null)) || (this.getDate1() != null && this
								.getDate1().equals(that.getDate1())))
						&& (((this.getRfBillingCycleFlag() == null) && (that.getRfBillingCycleFlag() == null)) || (this
								.getRfBillingCycleFlag() != null && this.getRfBillingCycleFlag().equals(
								that.getRfBillingCycleFlag())))
						&& (((this.getDate2() == null) && (that.getDate2() == null)) || (this.getDate2() != null && this
								.getDate2().equals(that.getDate2())))
						&& this.getBillingCycleID() == that.getBillingCycleID()
						&& (((this.getAdjustmentFlag() == null) && (that.getAdjustmentFlag() == null)) || (this
								.getAdjustmentFlag() != null && this.getAdjustmentFlag().equals(that.getAdjustmentFlag())))
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this.getStatus() != null && this
								.getStatus().equals(that.getStatus())))
						&& (((this.getFeeType() == null) && (that.getFeeType() == null)) || (this.getFeeType() != null && this
								.getFeeType().equals(that.getFeeType())))
						&& (((this.getCreatingMethod() == null) && (that.getCreatingMethod() == null)) || (this
								.getCreatingMethod() != null && this.getCreatingMethod().equals(that.getCreatingMethod())))
						&& (((this.getForcedDepositFlag() == null) && (that.getForcedDepositFlag() == null)) || (this
								.getForcedDepositFlag() != null && this.getForcedDepositFlag()
								.equals(that.getForcedDepositFlag())))
						&& (((this.getSetOffFlag() == null) && (that.getSetOffFlag() == null)) || (this.getSetOffFlag() != null && this
								.getSetOffFlag().equals(that.getSetOffFlag())))
						&& (((this.getDtCreate() == null) && (that.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
						&& (((this.getCreateTime() == null) && (that.getCreateTime() == null)) || (this.getCreateTime() != null && this
								.getCreateTime().equals(that.getCreateTime())))
						&& (((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) || (this.getDtLastmod() != null && this
								.getDtLastmod().equals(that.getDtLastmod())));
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
		buf.append(aiNO);
		buf.append(",").append(batchNO);
		buf.append(",").append(custID);
		buf.append(",").append(acctID);
		buf.append(",").append(serviceAccountID);
		buf.append(",").append(acctItemTypeID);
		buf.append(",psID=").append(psID);
		buf.append(",value=").append(value);
		buf.append(",").append(date1);
		buf.append(",").append(date2);
		buf.append(",").append(brID);
		buf.append(",").append(billingCycleID);
		buf.append(",").append(status);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(creatingMethod);
        buf.append(",").append(createTime);
        buf.append(",").append(operatorID);
        buf.append(",").append(orgID);
        buf.append(",").append(referType);
        buf.append(",").append(referID);
        buf.append(",").append(invoiceFlag);
        buf.append(",").append(invoiceNO);
        buf.append(",").append(setOffFlag);
        buf.append(",").append(setOffAmount);
        buf.append(",").append(productID);
        buf.append(",").append(forcedDepositFlag);
        buf.append(",").append(sourceRecordID);
        buf.append(",").append(feeType);
        buf.append(",").append(comments);
        buf.append(",").append(ccID);
        buf.append(",").append(feeSplitPlanID);
        buf.append(",").append(rfBillingCycleFlag);
        buf.append(",groupNo=").append(groupNo);
        buf.append(",sheafNo=").append(sheafNo);
        return buf.toString();
	}



        private java.util.Map map = new java.util.HashMap();


  public void put(String field) { map.put(field, Boolean.TRUE); }

        public java.util.Map getMap() { return this.map; }


	/**
	 * @return Returns the forcedDepositFlag.
	 */
	public String getForcedDepositFlag() {
		return forcedDepositFlag;
	}
	/**
	 * @param forcedDepositFlag The forcedDepositFlag to set.
	 */
	public void setForcedDepositFlag(String forcedDepositFlag) {
		this.forcedDepositFlag = forcedDepositFlag;
		put("forcedDepositFlag");
	}
	/**
	 * @return Returns the setOffAmount.
	 */
	public double getSetOffAmount() {
		return setOffAmount;
	}
	/**
	 * @param setOffAmount The setOffAmount to set.
	 */
	public void setSetOffAmount(double setOffAmount) {
		this.setOffAmount = setOffAmount;
		put("setOffAmount");
	}
	/**
	 * @return Returns the setOffFlag.
	 */
	public String getSetOffFlag() {
		return setOffFlag;
	}
	/**
	 * @param setOffFlag The setOffFlag to set.
	 */
	public void setSetOffFlag(String setOffFlag) {
		this.setOffFlag = setOffFlag;
		put("setOffFlag");
	}
}