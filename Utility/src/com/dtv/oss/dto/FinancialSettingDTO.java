package com.dtv.oss.dto;

import java.sql.Timestamp;

public class FinancialSettingDTO implements ReflectionSupport,
		java.io.Serializable {
	private String name;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private String unifiedCycleFlag;

	private Timestamp billingStartDate;

	private int invoiceDueDate;

	private String calculatePunishmentFlag;

	private int punishmentStartDate;

	private double punishmenTrate;

	private String smallchangeProcessMode;

	private String prepaymentDeductionMode;

	private String invoiceAccumulateMode;

	private String setOffLevel;

	private String autoResumeCpFlag;

	private String delayOneMonthInarrearFlag;

	private String punishmentAcctItemTypeID;

	private String forcedDepositAcctItemTypeID;

	private String returnDeviceAcctItemTypeID;

	/**
	 * @return Returns the forcedDepositAcctItemTypeID.
	 */
	public String getForcedDepositAcctItemTypeID() {
		return forcedDepositAcctItemTypeID;
	}

	/**
	 * @param forcedDepositAcctItemTypeID
	 *            The forcedDepositAcctItemTypeID to set.
	 */
	public void setForcedDepositAcctItemTypeID(
			String forcedDepositAcctItemTypeID) {
		this.forcedDepositAcctItemTypeID = forcedDepositAcctItemTypeID;
		put("forcedDepositAcctItemTypeID");
	}

	/**
	 * @return Returns the returnDeviceAcctItemTypeID.
	 */
	public String getReturnDeviceAcctItemTypeID() {
		return returnDeviceAcctItemTypeID;
	}

	/**
	 * @param returnDeviceAcctItemTypeID
	 *            The returnDeviceAcctItemTypeID to set.
	 */
	public void setReturnDeviceAcctItemTypeID(String returnDeviceAcctItemTypeID) {
		this.returnDeviceAcctItemTypeID = returnDeviceAcctItemTypeID;
		put("returnDeviceAcctItemTypeID");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getUnifiedCycleFlag() {
		return unifiedCycleFlag;
	}

	public void setUnifiedCycleFlag(String unifiedCycleFlag) {
		this.unifiedCycleFlag = unifiedCycleFlag;
		put("unifiedCycleFlag");
	}

	public Timestamp getBillingStartDate() {
		return billingStartDate;
	}

	public void setBillingStartDate(Timestamp billingStartDate) {
		this.billingStartDate = billingStartDate;
		put("billingStartDate");
	}

	public int getInvoiceDueDate() {
		return invoiceDueDate;
	}

	public void setInvoiceDueDate(int invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
		put("invoiceDueDate");
	}

	public String getCalculatePunishmentFlag() {
		return calculatePunishmentFlag;
	}

	public void setCalculatePunishmentFlag(String calculatePunishmentFlag) {
		this.calculatePunishmentFlag = calculatePunishmentFlag;
		put("calculatePunishmentFlag");
	}

	public int getPunishmentStartDate() {
		return punishmentStartDate;
	}

	public void setPunishmentStartDate(int punishmentStartDate) {
		this.punishmentStartDate = punishmentStartDate;
		put("punishmentStartDate");
	}

	public double getPunishmenTrate() {
		return punishmenTrate;
	}

	public void setPunishmenTrate(double punishmenTrate) {
		this.punishmenTrate = punishmenTrate;
		put("punishmenTrate");
	}

	public String getSmallchangeProcessMode() {
		return smallchangeProcessMode;
	}

	public void setSmallchangeProcessMode(String smallchangeProcessMode) {
		this.smallchangeProcessMode = smallchangeProcessMode;
		put("smallchangeProcessMode");
	}

	public String getPrepaymentDeductionMode() {
		return prepaymentDeductionMode;
	}

	public void setPrepaymentDeductionMode(String prepaymentDeductionMode) {
		this.prepaymentDeductionMode = prepaymentDeductionMode;
		put("prepaymentDeductionMode");
	}

	public String getInvoiceAccumulateMode() {
		return invoiceAccumulateMode;
	}

	public void setInvoiceAccumulateMode(String invoiceAccumulateMode) {
		this.invoiceAccumulateMode = invoiceAccumulateMode;
		put("invoiceAccumulateMode");
	}

	public String getSetOffLevel() {
		return setOffLevel;
	}

	public void setSetOffLevel(String setOffLevel) {
		this.setOffLevel = setOffLevel;
		put("setOffLevel");

	}

	public String getAutoResumeCpFlag() {
		return autoResumeCpFlag;
	}

	public void setAutoResumeCpFlag(String autoResumeCpFlag) {
		this.autoResumeCpFlag = autoResumeCpFlag;
		put("autoResumeCpFlag");
	}

	public String getDelayOneMonthInarrearFlag() {
		return delayOneMonthInarrearFlag;
	}

	public void setDelayOneMonthInarrearFlag(String delayOneMonthInarrearFlag) {
		this.delayOneMonthInarrearFlag = delayOneMonthInarrearFlag;
		put("delayOneMonthInarrearFlag");
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				FinancialSettingDTO that = (FinancialSettingDTO) obj;
				return (((this.getName() == null) && (that.getName() == null)) || (this
						.getName() != null && this.getName().equals(
						that.getName())))
						&& (((this.getDtCreate() == null) && (that
								.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())))
						&& (((this.getForcedDepositAcctItemTypeID() == null) && (that
								.getForcedDepositAcctItemTypeID() == null)) || (this
								.getForcedDepositAcctItemTypeID() != null && this
								.getForcedDepositAcctItemTypeID().equals(
										that.getForcedDepositAcctItemTypeID())))
						&& (((this.getReturnDeviceAcctItemTypeID() == null) && (that
								.getReturnDeviceAcctItemTypeID() == null)) || (this
								.getReturnDeviceAcctItemTypeID() != null && this
								.getReturnDeviceAcctItemTypeID().equals(
										that.getReturnDeviceAcctItemTypeID())))
						&& (((this.getUnifiedCycleFlag() == null) && (that
								.getUnifiedCycleFlag() == null)) || (this
								.getUnifiedCycleFlag() != null && this
								.getUnifiedCycleFlag().equals(
										that.getUnifiedCycleFlag())))
						&& (((this.getBillingStartDate() == null) && (that
								.getBillingStartDate() == null)) || (this
								.getBillingStartDate() != null && this
								.getBillingStartDate().equals(
										that.getBillingStartDate())))
						&& this.getInvoiceDueDate() == that.getInvoiceDueDate()
						&& (((this.getCalculatePunishmentFlag() == null) && (that
								.getCalculatePunishmentFlag() == null)) || (this
								.getCalculatePunishmentFlag() != null && this
								.getCalculatePunishmentFlag().equals(
										that.getCalculatePunishmentFlag())))
						&& this.getPunishmentStartDate() == that
								.getPunishmentStartDate()
						&& this.getPunishmenTrate() == that.getPunishmenTrate()
						&& (((this.getSmallchangeProcessMode() == null) && (that
								.getSmallchangeProcessMode() == null)) || (this
								.getSmallchangeProcessMode() != null && this
								.getSmallchangeProcessMode().equals(
										that.getSmallchangeProcessMode())))
						&& (((this.getPrepaymentDeductionMode() == null) && (that
								.getPrepaymentDeductionMode() == null)) || (this
								.getPrepaymentDeductionMode() != null && this
								.getPrepaymentDeductionMode().equals(
										that.getPrepaymentDeductionMode())))
						&& (((this.getInvoiceAccumulateMode() == null) && (that
								.getInvoiceAccumulateMode() == null)) || (this
								.getInvoiceAccumulateMode() != null && this
								.getInvoiceAccumulateMode().equals(
										that.getInvoiceAccumulateMode())))
						&& (((this.getSetOffLevel() == null) && (that
								.getSetOffLevel() == null)) || (this
								.getSetOffLevel() != null && this
								.getSetOffLevel().equals(that.getSetOffLevel())))
						&& (((this.getAutoResumeCpFlag() == null) && (that
								.getAutoResumeCpFlag() == null)) || (this
								.getAutoResumeCpFlag() != null && this
								.getAutoResumeCpFlag().equals(
										that.getAutoResumeCpFlag())))
						&& (((this.getDelayOneMonthInarrearFlag() == null) && (that
								.getDelayOneMonthInarrearFlag() == null)) || (this
								.getDelayOneMonthInarrearFlag() != null && this
								.getDelayOneMonthInarrearFlag().equals(
										that.getDelayOneMonthInarrearFlag())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append("name=").append(name);
		buf.append(",").append("unifiedCycleFlag=").append(unifiedCycleFlag);
		buf.append(",").append("billingStartDate=").append(billingStartDate);
		buf.append(",").append("invoiceDueDate=").append(invoiceDueDate);
		buf.append(",").append("calculatePunishmentFlag=").append(
				calculatePunishmentFlag);
		buf.append(",").append("punishmentStartDate=").append(
				punishmentStartDate);
		buf.append(",").append("punishmenTrate=").append(punishmenTrate);
		buf.append(",").append("smallchangeProcessMode=").append(
				smallchangeProcessMode);
		buf.append(",").append("prepaymentDeductionMode=").append(
				prepaymentDeductionMode);
		buf.append(",").append("invoiceAccumulateMode=").append(
				invoiceAccumulateMode);
		buf.append(",").append("setOffLevel=").append(setOffLevel);
		buf.append(",").append("autoResumeCpFlag=").append(autoResumeCpFlag);
		buf.append(",").append("delayOneMonthInarrearFlag=").append(
				delayOneMonthInarrearFlag);
		buf.append(",").append("punishmentAcctItemTypeID=").append(
				punishmentAcctItemTypeID);

		buf.append(",").append("dtCreate=").append(dtCreate);
		buf.append(",").append("dtLastmod=").append(dtLastmod);
		buf.append(",").append("dtCreate=").append(dtCreate);
		buf.append(",").append("forcedDepositAcctItemTypeID=").append(
				forcedDepositAcctItemTypeID);
		buf.append(",").append("returnDeviceAcctItemTypeID=").append(
				returnDeviceAcctItemTypeID);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

	/**
	 * @return Returns the punishmentAcctItemTypeID.
	 */
	public String getPunishmentAcctItemTypeID() {
		return punishmentAcctItemTypeID;
	}

	/**
	 * @param punishmentAcctItemTypeID
	 *            The punishmentAcctItemTypeID to set.
	 */
	public void setPunishmentAcctItemTypeID(String punishmentAcctItemTypeID) {
		this.punishmentAcctItemTypeID = punishmentAcctItemTypeID;
		put("punishmentAcctItemTypeID");
	}
}
