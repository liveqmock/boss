package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CampaignDTO implements ReflectionSupport, java.io.Serializable
{
	private String keepBilling;
	private String description;
	private Timestamp dateFrom;
	private Timestamp dateTo;
	private String custTypeList;
    private String openSourceTypeList;
	 
	 
	private String allowPause;
	private String allowTransition;
	private String allowTransfer;
	private String allowClose;
	private String status;
	private String timeLengthUnitType;
	private int timeLengthUnitNumber;
	 
	
	private int campaignID;
	private String campaignName;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private String allowAlter;
	private String campaignType;
	private String obligationFlag ;
	private String csiTypeList;
	private String rfBillingFlag;
	private String groupBargainFlag;
	private String autoExtendFlag;
	private String rfBillingCycleFlag;
	private String paymentAwardFlag;
	private String bundlePrePaymentFlag;
	private String multiCheckFlag;
	private int    campainpriority;
	
	
	/**
	 * @return Returns the autoExtendFlag.
	 */
	public String getMultiCheckFlag() {
		return multiCheckFlag;
	}
	/**
	 * @param autoExtendFlag The autoExtendFlag to set.
	 */
	public void setMultiCheckFlag(String multiCheckFlag) {
		this.multiCheckFlag = multiCheckFlag;
		put("multiCheckFlag");
	}
	/**
	 * @return Returns the paymentAwardFlag.
	 */
	public String getPaymentAwardFlag() {
		return paymentAwardFlag;
	}
	/**
	 * @param paymentAwardFlag The paymentAwardFlag to set.
	 */
	public void setPaymentAwardFlag(String paymentAwardFlag) {
		this.paymentAwardFlag = paymentAwardFlag;
		put("paymentAwardFlag");
	}
	/**
	 * @return Returns the bundlePrePaymentFlag.
	 */
	public String getBundlePrePaymentFlag() {
		return bundlePrePaymentFlag;
	}
	/**
	 * @param bundlePrePaymentFlag The bundlePrePaymentFlag to set.
	 */
	public void setBundlePrePaymentFlag(String bundlePrePaymentFlag) {
		this.bundlePrePaymentFlag = bundlePrePaymentFlag;
		put("bundlePrePaymentFlag");
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
	 * @return Returns the csiTypeList.
	 */
	public String getCsiTypeList() {
		return csiTypeList;
	}
	/**
	 * @param csiTypeList The csiTypeList to set.
	 */
	public void setCsiTypeList(String csiTypeList) {
		this.csiTypeList = csiTypeList;
		put("csiTypeList");
	}
	/**
	 * @return Returns the rfBillingFlag.
	 */
	public String getRfBillingFlag() {
		return rfBillingFlag;
	}
	/**
	 * @param rfBillingFlag The rfBillingFlag to set.
	 */
	public void setRfBillingFlag(String rfBillingFlag) {
		this.rfBillingFlag = rfBillingFlag;
		put("rfBillingFlag");
	}
	/**
	 * @return Returns the obligationFlag.
	 */
	public String getObligationFlag() {
		return obligationFlag;
	}
	/**
	 * @param obligationFlag The obligationFlag to set.
	 */
	public void setObligationFlag(String obligationFlag) {
		this.obligationFlag = obligationFlag;
		put("obligationFlag");
	}
	/**
	 * @return Returns the timeLengthUnitNumber.
	 */
	public int getTimeLengthUnitNumber() {
		return timeLengthUnitNumber;
	}
	/**
	 * @param timeLengthUnitNumber The timeLengthUnitNumber to set.
	 */
	public void setTimeLengthUnitNumber(int timeLengthUnitNumber) {
		this.timeLengthUnitNumber = timeLengthUnitNumber;
		put("timeLengthUnitNumber");
	}
	/**
	 * @return Returns the timeLengthUnitType.
	 */
	public String getTimeLengthUnitType() {
		return timeLengthUnitType;
	}
	/**
	 * @param timeLengthUnitType The timeLengthUnitType to set.
	 */
	public void setTimeLengthUnitType(String timeLengthUnitType) {
		this.timeLengthUnitType = timeLengthUnitType;
		put("timeLengthUnitType");
	}
	/**
	 * @return Returns the allowAlter.
	 */
	public String getCampaignType() {
		return campaignType;
	}
	/**
	 * @param allowAlter The allowAlter to set.
	 */
	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
		put("campaignType");
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
	public CampaignDTO()
	{
	}


	public void setDescription(String description)
	{
		this.description = description;
		put("description");
	}

	public String getDescription()
	{
		return description;
	}

	public void setDateFrom(Timestamp dateFrom)
	{
		this.dateFrom = dateFrom;
		put("dateFrom");
	}

	public Timestamp getDateFrom()
	{
		return dateFrom;
	}

	public void setDateTo(Timestamp dateTo)
	{
		this.dateTo = dateTo;
		put("dateTo");
	}

	public Timestamp getDateTo()
	{
		return dateTo;
	}

	public void setCustTypeList(String custTypeList)
	{
		this.custTypeList = custTypeList;
		put("custTypeList");
	}

	public String getCustTypeList()
	{
		return custTypeList;
	}
	public void setKeepBilling(String keepBilling)
	{
		this.keepBilling = keepBilling;
		put("keepBilling");
	}

	public String getKeepBilling()
	{
		return keepBilling;
	}

	public void setOpenSourceTypeList(String openSourceTypeList)
	{
		this.openSourceTypeList = openSourceTypeList;
		put("openSourceTypeList");
	}

	public String getOpenSourceTypeList()
	{
		return openSourceTypeList;
	}

	  
	 
	public void setAllowPause(String allowPause)
	{
		this.allowPause = allowPause;
		put("allowPause");
	}

	public String getAllowPause()
	{
		return allowPause;
	}

	public void setAllowTransition(String allowTransition)
	{
		this.allowTransition = allowTransition;
		put("allowTransition");
	}

	public String getAllowTransition()
	{
		return allowTransition;
	}

	public void setAllowTransfer(String allowTransfer)
	{
		this.allowTransfer = allowTransfer;
		put("allowTransfer");
	}

	public String getAllowTransfer()
	{
		return allowTransfer;
	}

	public void setAllowClose(String allowClose)
	{
		this.allowClose = allowClose;
		put("allowClose");
	}

	public String getAllowClose()
	{
		return allowClose;
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


   public void setCampaignID(int campaignID)
	{
		this.campaignID = campaignID;
		//put("campaignID");
	}

	public int getCampaignID()
	{
		return campaignID;
	}

	public void setCampaignName(String campaignName)
	{
		this.campaignName = campaignName;
		put("campaignName");
	}

	public String getCampaignName()
	{
		return campaignName;
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
				CampaignDTO that = (CampaignDTO) obj;
				return
				(((this.getCampaignType() == null) && (that.getCampaignType() == null)) ||
						(this.getCampaignType() != null && this.getCampaignType().equals(that.getCampaignType()))) &&
				(((this.getAllowAlter() == null) && (that.getAllowAlter() == null)) ||
						(this.getAllowAlter() != null && this.getAllowAlter().equals(that.getAllowAlter()))) &&
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					(((this.getKeepBilling() == null) && (that.getKeepBilling() == null)) ||
						(this.getKeepBilling() != null && this.getKeepBilling().equals(that.getKeepBilling()))) &&
					(((this.getDateFrom() == null) && (that.getDateFrom() == null)) ||
						(this.getDateFrom() != null && this.getDateFrom().equals(that.getDateFrom()))) &&
					(((this.getDateTo() == null) && (that.getDateTo() == null)) ||
						(this.getDateTo() != null && this.getDateTo().equals(that.getDateTo()))) &&
					(((this.getCustTypeList() == null) && (that.getCustTypeList() == null)) ||
						(this.getCustTypeList() != null && this.getCustTypeList().equals(that.getCustTypeList()))) &&

					(((this.getOpenSourceTypeList() == null) && (that.getOpenSourceTypeList() == null)) ||
						(this.getOpenSourceTypeList() != null && this.getOpenSourceTypeList().equals(that.getOpenSourceTypeList()))) &&
					this.getTimeLengthUnitNumber() == that.getTimeLengthUnitNumber()  &&
					 
					(((this.getBundlePrePaymentFlag() == null) && (that.getBundlePrePaymentFlag() == null)) ||
						(this.getBundlePrePaymentFlag() != null && this.getBundlePrePaymentFlag().equals(that.getBundlePrePaymentFlag()))) &&
						(((this.getMultiCheckFlag() == null) && (that.getMultiCheckFlag() == null)) ||
								(this.getMultiCheckFlag() != null && this.getMultiCheckFlag().equals(that.getMultiCheckFlag()))) &&	
						(((this.getPaymentAwardFlag() == null) && (that.getPaymentAwardFlag() == null)) ||
								(this.getPaymentAwardFlag() != null && this.getPaymentAwardFlag().equals(that.getPaymentAwardFlag()))) &&
					 
					(((this.getAllowPause() == null) && (that.getAllowPause() == null)) ||
						(this.getAllowPause() != null && this.getAllowPause().equals(that.getAllowPause()))) &&
						(((this.getObligationFlag() == null) && (that.getObligationFlag() == null)) ||
								(this.getObligationFlag() != null && this.getObligationFlag().equals(that.getObligationFlag()))) &&
					(((this.getAllowTransition() == null) && (that.getAllowTransition() == null)) ||
						(this.getAllowTransition() != null && this.getAllowTransition().equals(that.getAllowTransition()))) &&
					(((this.getAllowTransfer() == null) && (that.getAllowTransfer() == null)) ||
						(this.getAllowTransfer() != null && this.getAllowTransfer().equals(that.getAllowTransfer()))) &&
					(((this.getCsiTypeList() == null) && (that.getCsiTypeList() == null)) ||
						(this.getCsiTypeList() != null && this.getCsiTypeList().equals(that.getCsiTypeList()))) &&	
					(((this.getRfBillingFlag() == null) && (that.getRfBillingFlag() == null)) ||
						(this.getRfBillingFlag() != null && this.getRfBillingFlag().equals(that.getRfBillingFlag()))) &&	
					(((this.getAutoExtendFlag() == null) && (that.getAutoExtendFlag() == null)) ||
						(this.getAutoExtendFlag() != null && this.getAutoExtendFlag().equals(that.getAutoExtendFlag()))) &&	
						 ( ( (this.getRfBillingCycleFlag() == null) && (that.getRfBillingCycleFlag() == null)) ||
			                    (this.getRfBillingCycleFlag() != null &&
			                     this.getRfBillingCycleFlag().equals(that.getRfBillingCycleFlag()))) &&
			          	
					(((this.getAllowClose() == null) && (that.getAllowClose() == null)) ||
						(this.getAllowClose() != null && this.getAllowClose().equals(that.getAllowClose()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
						(((this.getTimeLengthUnitType() == null) && (that.getTimeLengthUnitType() == null)) ||
								(this.getTimeLengthUnitType() != null && this.getTimeLengthUnitType().equals(that.getTimeLengthUnitType()))) &&	 
					this.getCampaignID() == that.getCampaignID()  &&
					(((this.getGroupBargainFlag() == null) && (that.getGroupBargainFlag() == null)) ||
						(this.getGroupBargainFlag() != null && this.getGroupBargainFlag().equals(that.getGroupBargainFlag()))) &&
					(((this.getCampaignName() == null) && (that.getCampaignName() == null)) ||
						(this.getCampaignName() != null && this.getCampaignName().equals(that.getCampaignName()))) &&
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
		buf.append(description);
		buf.append(",").append(campaignType);
		buf.append(",").append(allowAlter);
		buf.append(",").append(dateFrom);
		buf.append(",").append(keepBilling);
		buf.append(",").append(dateTo);
		buf.append(",").append(custTypeList);
		buf.append(",").append(obligationFlag);

		buf.append(",").append(openSourceTypeList);
		
		buf.append(",").append(paymentAwardFlag);
		 
		buf.append(",").append(allowPause);
		buf.append(",").append(allowTransition);
		buf.append(",").append(allowTransfer);
		buf.append(",").append(allowClose);
		buf.append(",").append(status);
	 
		buf.append(",").append(timeLengthUnitType);
		buf.append(",").append(timeLengthUnitNumber);
		buf.append(",").append(campaignID);
		buf.append(",").append(campaignName);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(rfBillingCycleFlag);
		buf.append(",").append(bundlePrePaymentFlag);
		buf.append(",").append(multiCheckFlag );
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	/**
	 * @return Returns the groupBargainFlag.
	 */
	public String getGroupBargainFlag() {
		return groupBargainFlag;
	}
	/**
	 * @param groupBargainFlag The groupBargainFlag to set.
	 */
	public void setGroupBargainFlag(String groupBargainFlag) {
		this.groupBargainFlag = groupBargainFlag;
		put("groupBargainFlag");
	}
	
	public int getCampainpriority() {
		return campainpriority;
	}
	public void setCampainpriority(int campainpriority) {
		this.campainpriority = campainpriority;
		put("campainpriority");
	}
	
	
}

