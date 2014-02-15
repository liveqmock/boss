package com.dtv.oss.dto;
import java.sql.Timestamp;

import com.dtv.oss.dto.ReflectionSupport;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class PaymentRecordDTO implements ReflectionSupport, java.io.Serializable
{
	private double amount;
	private Timestamp createTime;
	private int opID;
	private int orgID;
	private Timestamp paymentTime;
	private String payType;
	private int paidTo;
	private int mopID;
	private int sourceRecordID;
	private int invoiceNo;
	private String status;
	private String invoicedFlag;
	private int custID;
	private int acctID;
	private int seqNo;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
    private String sourceType;
    private String ticketType;
    private String ticketNo;
    private String prepaymentType;
    private String referType;
    private int referID;
    private String creatingMethod;
    private String adjustmentFlag;
    private int adjustmentNo;
    private String comments;
    private String faPiaoNo;
    /**
	 * @return the faPiaoNo
	 */
	public String getFaPiaoNo() {
		return faPiaoNo;
	}
	/**
	 * @param faPiaoNo the faPiaoNo to set
	 */
	public void setFaPiaoNo(String faPiaoNo) {
		this.faPiaoNo = faPiaoNo;
		put("faPiaoNo");
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
	 public void setSourceType(String sourceType)
               {
                       this.sourceType = sourceType;
                       put("sourceType");
               }

       public String getSourceType()
       {
       return sourceType;
         }
      
	public void setAmount(double amount)
	{
		this.amount = amount;
		put("amount");
	}

	public double getAmount()
	{
		return amount;
	}

	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
		put("createTime");
	}

	public Timestamp getCreateTime()
	{
		return createTime;
	}

	 

	public void setOrgID(int orgID)
	{
		this.orgID = orgID;
		put("orgID");
	}

	public int getOrgID()
	{
		return orgID;
	}

	public void setPaymentTime(Timestamp paymentTime)
	{
		this.paymentTime = paymentTime;
		put("paymentTime");
	}

	public Timestamp getPaymentTime()
	{
		return paymentTime;
	}

	 

	public void setPaidTo(int paidTo)
	{
		this.paidTo = paidTo;
		put("paidTo");
	}

	public int getPaidTo()
	{
		return paidTo;
	}

	public void setMopID(int mopID)
	{
		this.mopID = mopID;
		put("mopID");
	}

	public int getMopID()
	{
		return mopID;
	}

	public void setSourceRecordID(int sourceRecordID)
	{
		this.sourceRecordID = sourceRecordID;
		put("sourceRecordID");
	}

	public int getSourceRecordID()
	{
		return sourceRecordID;
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

	public void setSeqNo(int seqNo)
	{
		this.seqNo = seqNo;
		//put("seqNo");
	}

	public int getSeqNo()
	{
		return seqNo;
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
				PaymentRecordDTO that = (PaymentRecordDTO) obj;
				return
					this.getAmount() == that.getAmount()  &&
					(((this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
						(this.getCreateTime() != null && this.getCreateTime().equals(that.getCreateTime()))) &&
					this.getOpID() == that.getOpID()  &&
					this.getOrgID() == that.getOrgID()  &&
					(((this.getPaymentTime() == null) && (that.getPaymentTime() == null)) ||
						(this.getPaymentTime() != null && this.getPaymentTime().equals(that.getPaymentTime()))) &&
					(((this.getPayType() == null) && (that.getPayType() == null)) ||
						(this.getPayType() != null && this.getPayType().equals(that.getPayType()))) &&
					this.getPaidTo() == that.getPaidTo()  &&
					this.getMopID() == that.getMopID()  &&
					this.getSourceRecordID() == that.getSourceRecordID()  &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getCustID() == that.getCustID()  &&
					this.getAcctID() == that.getAcctID()  &&
					this.getAdjustmentNo() == that.getAdjustmentNo()  &&
					 ( ( (this.getReferType() == null) && (that.getReferType() == null)) ||
		                    (this.getReferType() != null &&
		                     this.getReferType().equals(that.getReferType()))) &&
		            ( ( (this.getAdjustmentFlag() == null) && (that.getAdjustmentFlag() == null)) ||
				            (this.getAdjustmentFlag() != null &&
				            this.getAdjustmentFlag().equals(that.getAdjustmentFlag()))) &&	
				   ( ( (this.getCreatingMethod() == null) && (that.getCreatingMethod() == null)) ||
						  (this.getCreatingMethod() != null &&
						       this.getCreatingMethod().equals(that.getCreatingMethod()))) &&			
					(((this.getTicketNo() == null) && (that.getTicketNo() == null)) ||
					  (this.getTicketNo() != null && this.getTicketNo().equals(that.getTicketNo()))) &&
					(((this.getTicketType() == null) && (that.getTicketType() == null)) ||
					  (this.getTicketType() != null && this.getTicketType().equals(that.getTicketType()))) &&	
					(((this.getInvoicedFlag() == null) && (that.getInvoicedFlag() == null)) ||
					(this.getInvoicedFlag() != null && this.getInvoicedFlag().equals(that.getInvoicedFlag()))) &&	  
					  (((this.getPrepaymentType() == null) && (that.getPrepaymentType() == null)) ||
					(this.getPrepaymentType() != null && this.getPrepaymentType().equals(that.getPrepaymentType()))) &&  
					(((this.getFaPiaoNo() == null) && (that.getFaPiaoNo() == null)) ||
							(this.getFaPiaoNo() != null && this.getFaPiaoNo().equals(that.getFaPiaoNo()))) &&  
					this.getSeqNo() == that.getSeqNo()  &&
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
		buf.append(amount);
		buf.append(",").append(createTime);
		buf.append(",").append(opID);
		buf.append(",").append(orgID);
		buf.append(",").append(paymentTime);
		buf.append(",").append(payType);
		buf.append(",").append(paidTo);
		buf.append(",").append(mopID);
		buf.append(",").append(sourceRecordID);
		buf.append(",").append(invoiceNo);
		buf.append(",").append(referType);
		buf.append(",").append(referID);
		buf.append(",").append(status);
		buf.append(",").append(custID);
		buf.append(",").append(acctID);
		buf.append(",").append(seqNo);
		buf.append(",").append(sourceType);
		buf.append(",").append(prepaymentType);
		buf.append(",").append(ticketType);
		buf.append(",").append(ticketNo);
		buf.append(",").append(invoicedFlag);
		buf.append(",").append(faPiaoNo);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
       
	    
		return buf.toString();
	}




	private java.util.Map map = new java.util.HashMap();


        public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }




	/**
	 * @return Returns the invoicedFlag.
	 */
	public String getInvoicedFlag() {
		return invoicedFlag;
	}
	/**
	 * @param invoicedFlag The invoicedFlag to set.
	 */
	public void setInvoicedFlag(String invoicedFlag) {
		this.invoicedFlag = invoicedFlag;
		put("invoicedFlag");
	}
	/**
	 * @return Returns the invoiceNo.
	 */
	public int getInvoiceNo() {
		return invoiceNo;
	}
	/**
	 * @param invoiceNo The invoiceNo to set.
	 */
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
		put("invoiceNo");
	}
	/**
	 * @return Returns the opID.
	 */
	public int getOpID() {
		return opID;
	}
	/**
	 * @param opID The opID to set.
	 */
	public void setOpID(int opID) {
		this.opID = opID;
		put("opID");
	}
	/**
	 * @return Returns the payType.
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * @param payType The payType to set.
	 */
	public void setPayType(String payType) {
		this.payType = payType;
		put("payType");
	}
	/**
	 * @return Returns the prepaymentType.
	 */
	public String getPrepaymentType() {
		return prepaymentType;
	}
	/**
	 * @param prepaymentType The prepaymentType to set.
	 */
	public void setPrepaymentType(String prepaymentType) {
		this.prepaymentType = prepaymentType;
		put("prepaymentType");
	}
	/**
	 * @return Returns the ticketNo.
	 */
	public String getTicketNo() {
		return ticketNo;
	}
	/**
	 * @param ticketNo The ticketNo to set.
	 */
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
		put("ticketNo");
	}
	/**
	 * @return Returns the ticketType.
	 */
	public String getTicketType() {
		return ticketType;
	}
	/**
	 * @param ticketType The ticketType to set.
	 */
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
		put("ticketType");
	}
	 
	/**
	 * @return Returns the referID.
	 */
	public int getReferID() {
		return referID;
	}
	/**
	 * @param referID The referID to set.
	 */
	public void setReferID(int referID) {
		this.referID = referID;
		put("referID");
	}
	/**
	 * @return Returns the referType.
	 */
	public String getReferType() {
		return referType;
	}
	/**
	 * @param referType The referType to set.
	 */
	public void setReferType(String referType) {
		this.referType = referType;
		put("referType");
	}
}

