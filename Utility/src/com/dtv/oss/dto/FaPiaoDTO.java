package com.dtv.oss.dto;
import java.sql.Timestamp;

public class FaPiaoDTO implements ReflectionSupport, java.io.Serializable
{
	private int seqNo;
	
    private String type;
    private String moduleName;
    private String serailNo;
    private String sysSerialNo;
    private int volumnSeqno;
    
    private int creatorOpID;
    private int creatorOrgID;
    private int recipientOpID;
    private int recipientOrgID;
    private int drawOpID;
    
    private int drawOrgID;
    private String title;
    private int accountID;
    private int sumAmount;
    private String isAmtFixed;
    
    private String authBy;
    private String addressType;
    private int addressID;
    private String status;
    private Timestamp statusTime;
    
    private String description;
    private Timestamp createTime;
    private String discardReason;
    private int printTimes;
    private Timestamp lastPrintDate;
    
    private String printCode;
    private String machineCode;
    private String printNumber;
    private String taxControlCode;
    private String majorTaxAgentCode;
    
    private String isZongBaoRen;
    private String isFenBaoRen;
    private String payerName;
    private String payerType;
    private String payerID;
    
    private String payeeName;
    private String payeeType;
    private String payeeID;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    
    private Timestamp recipientTime;
    private Timestamp drawTime;
    private String isPatchPrint;
    private String sourceType;
    private int sourceID;
    private int invoiceCycleID;
    
    
    public int getInvoiceCycleID() {
		return invoiceCycleID;
	}

	public void setInvoiceCycleID(int invoiceCycleID) {
		this.invoiceCycleID = invoiceCycleID;
		put("invoiceCycleID");
	}

	public int getSourceID() {
		return sourceID;
	}

	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
		put("sourceID");
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
		put("sourceType");
	}

	public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
       // put("seqNo");  主键不能标示修改
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        put("type");
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
        put("moduleName");
    }

    public String getSerailNo() {
        return serailNo;
    }

    public void setSerailNo(String serailNo) {
        this.serailNo = serailNo;
        put("serailNo");
    }

    public String getSysSerialNo() {
        return sysSerialNo;
    }

    public void setSysSerialNo(String sysSerialNo) {
        this.sysSerialNo = sysSerialNo;
        put("sysSerialNo");
    }

    public int getVolumnSeqno() {
        return volumnSeqno;
    }

    public void setVolumnSeqno(int volumnSeqno) {
        this.volumnSeqno = volumnSeqno;
        put("volumnSeqno");
    }

    public int getCreatorOpID() {
        return creatorOpID;
    }

    public void setCreatorOpID(int creatorOpID) {
        this.creatorOpID = creatorOpID;
        put("creatorOpID");
    }

    public int getCreatorOrgID() {
        return creatorOrgID;
    }

    public void setCreatorOrgID(int creatorOrgID) {
        this.creatorOrgID = creatorOrgID;
        put("creatorOrgID");
    }

    public int getRecipientOpID() {
        return recipientOpID;
    }

    public void setRecipientOpID(int recipientOpID) {
        this.recipientOpID = recipientOpID;
        put("recipientOpID");
    }

    public int getRecipientOrgID() {
        return recipientOrgID;
    }

    public void setRecipientOrgID(int recipientOrgID) {
        this.recipientOrgID = recipientOrgID;
        put("recipientOrgID");
    }

    public int getDrawOpID() {
        return drawOpID;
    }

    public void setDrawOpID(int drawOpID) {
        this.drawOpID = drawOpID;
        put("drawOpID");
    }

    public int getDrawOrgID() {
        return drawOrgID;
    }

    public void setDrawOrgID(int drawOrgID) {
        this.drawOrgID = drawOrgID;
        put("drawOrgID");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        put("title");
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
        put("accountID");
    }

    public int getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(int sumAmount) {
        this.sumAmount = sumAmount;
        put("sumAmount");
    }

    public String getIsAmtFixed() {
        return isAmtFixed;
    }

    public void setIsAmtFixed(String isAmtFixed) {
        this.isAmtFixed = isAmtFixed;
        put("isAmtFixed");
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
        put("authBy");
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
        put("addressType");
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
        put("addressID");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public Timestamp getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Timestamp statusTime) {
        this.statusTime = statusTime;
        put("statusTime");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        put("createTime");
    }

    public String getDiscardReason() {
        return discardReason;
    }

    public void setDiscardReason(String discardReason) {
        this.discardReason = discardReason;
        put("discardReason");
    }

    public int getPrintTimes() {
        return printTimes;
    }

    public void setPrintTimes(int printTimes) {
        this.printTimes = printTimes;
        put("printTimes");
    }

    public Timestamp getLastPrintDate() {
        return lastPrintDate;
    }

    public void setLastPrintDate(Timestamp lastPrintDate) {
        this.lastPrintDate = lastPrintDate;
        put("lastPrintDate");
    }

    public String getPrintCode() {
        return printCode;
    }

    public void setPrintCode(String printCode) {
        this.printCode = printCode;
        put("printCode");
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
        put("machineCode");
    }

    public String getPrintNumber() {
        return printNumber;
    }

    public void setPrintNumber(String printNumber) {
        this.printNumber = printNumber;
        put("printNumber");
    }

    public String getTaxControlCode() {
        return taxControlCode;
    }

    public void setTaxControlCode(String taxControlCode) {
        this.taxControlCode = taxControlCode;
        put("taxControlCode");
    }

    public String getMajorTaxAgentCode() {
        return majorTaxAgentCode;
    }

    public void setMajorTaxAgentCode(String majorTaxAgentCode) {
        this.majorTaxAgentCode = majorTaxAgentCode;
        put("majorTaxAgentCode");
    }

    public String getIsZongBaoRen() {
        return isZongBaoRen;
    }

    public void setIsZongBaoRen(String isZongBaoRen) {
        this.isZongBaoRen = isZongBaoRen;
        put("isZongBaoRen");
    }

    public String getIsFenBaoRen() {
        return isFenBaoRen;
    }

    public void setIsFenBaoRen(String isFenBaoRen) {
        this.isFenBaoRen = isFenBaoRen;
        put("isFenBaoRen");
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
        put("payerName");
    }

    public String getPayerType() {
        return payerType;
    }

    public void setPayerType(String payerType) {
        this.payerType = payerType;
        put("payerType");
    }

    public String getPayerID() {
        return payerID;
    }

    public void setPayerID(String payerID) {
        this.payerID = payerID;
        put("payerID");
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
        put("payeeName");
    }

    public String getPayeeType() {
        return payeeType;
    }

    public void setPayeeType(String payeeType) {
        this.payeeType = payeeType;
        put("payeeType");
    }

    public String getPayeeID() {
        return payeeID;
    }

    public void setPayeeID(String payeeID) {
        this.payeeID = payeeID;
        put("payeeID");
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
        put("dtCreate");
    }

    public Timestamp getDtLastmod() {
        return dtLastmod;
    }

    public void setDtLastmod(Timestamp dtLastmod) {
        this.dtLastmod = dtLastmod;
        put("dtLastmod");
    }
    
    public Timestamp getRecipientTime() {
        return recipientTime;
    }

    public void setRecipientTime(Timestamp recipientTime) {
        this.recipientTime = recipientTime;
        put("recipientTime");
    }
    
    public Timestamp getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(Timestamp drawTime) {
        this.drawTime = drawTime;
        put("drawTime");
    }
    
    public String getIsPatchPrint() {
        return isPatchPrint;
    }

    public void setIsPatchPrint(String isPatchPrint) {
        this.isPatchPrint = isPatchPrint;
        put("IsPatchPrint");
    }
    
    
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof FaPiaoDTO))
            return false;
        FaPiaoDTO that = (FaPiaoDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (!(that.type == null ? this.type == null :
              that.type.equals(this.type)))
            return false;
        if (!(that.moduleName == null ? this.moduleName == null :
              that.moduleName.equals(this.moduleName)))
            return false;
        if (!(that.serailNo == null ? this.serailNo == null :
              that.serailNo.equals(this.serailNo)))
            return false;
        if (!(that.sysSerialNo == null ? this.sysSerialNo == null :
              that.sysSerialNo.equals(this.sysSerialNo)))
            return false;
        if (that.volumnSeqno != this.volumnSeqno)
            return false;
        if (that.creatorOpID != this.creatorOpID)
            return false;
        if (that.creatorOrgID != this.creatorOrgID)
            return false;
        if (that.recipientOpID != this.recipientOpID)
            return false;
        if (that.recipientOrgID != this.recipientOrgID)
            return false;
        if (that.drawOpID != this.drawOpID)
            return false;
        if (that.drawOrgID != this.drawOrgID)
            return false;
        if (!(that.title == null ? this.title == null :
              that.title.equals(this.title)))
            return false;
        if (that.accountID != this.accountID)
            return false;
        if (that.sumAmount != this.sumAmount)
            return false;
        if (!(that.isAmtFixed == null ? this.isAmtFixed == null :
              that.isAmtFixed.equals(this.isAmtFixed)))
            return false;
        if (!(that.authBy == null ? this.authBy == null :
              that.authBy.equals(this.authBy)))
            return false;
        if (!(that.addressType == null ? this.addressType == null :
              that.addressType.equals(this.addressType)))
            return false;
        if (that.addressID != this.addressID)
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.statusTime == null ? this.statusTime == null :
              that.statusTime.equals(this.statusTime)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
            return false;
        if (!(that.discardReason == null ? this.discardReason == null :
              that.discardReason.equals(this.discardReason)))
            return false;
        if (that.printTimes != this.printTimes)
            return false;
        if (!(that.lastPrintDate == null ? this.lastPrintDate == null :
              that.lastPrintDate.equals(this.lastPrintDate)))
            return false;
        if (!(that.printCode == null ? this.printCode == null :
              that.printCode.equals(this.printCode)))
            return false;
        if (!(that.machineCode == null ? this.machineCode == null :
              that.machineCode.equals(this.machineCode)))
            return false;
        if (!(that.printNumber == null ? this.printNumber == null :
              that.printNumber.equals(this.printNumber)))
            return false;
        if (!(that.taxControlCode == null ? this.taxControlCode == null :
              that.taxControlCode.equals(this.taxControlCode)))
            return false;
        if (!(that.majorTaxAgentCode == null ? this.majorTaxAgentCode == null :
              that.majorTaxAgentCode.equals(this.majorTaxAgentCode)))
            return false;
        if (!(that.isZongBaoRen == null ? this.isZongBaoRen == null :
              that.isZongBaoRen.equals(this.isZongBaoRen)))
            return false;
        if (!(that.isFenBaoRen == null ? this.isFenBaoRen == null :
              that.isFenBaoRen.equals(this.isFenBaoRen)))
            return false;
        if (!(that.payerName == null ? this.payerName == null :
              that.payerName.equals(this.payerName)))
            return false;
        if (!(that.payerType == null ? this.payerType == null :
              that.payerType.equals(this.payerType)))
            return false;
        if (!(that.payerID == null ? this.payerID == null :
              that.payerID.equals(this.payerID)))
            return false;
        if (!(that.payeeName == null ? this.payeeName == null :
              that.payeeName.equals(this.payeeName)))
            return false;
        if (!(that.payeeType == null ? this.payeeType == null :
              that.payeeType.equals(this.payeeType)))
            return false;
        if (!(that.payeeID == null ? this.payeeID == null :
              that.payeeID.equals(this.payeeID)))
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        if (!(that.recipientTime == null ? this.recipientTime == null :
            that.recipientTime.equals(this.recipientTime)))
          return false;
        if (!(that.drawTime == null ? this.drawTime == null :
            that.drawTime.equals(this.drawTime)))
          return false;
        if (!(that.isPatchPrint == null ? this.isPatchPrint == null :
            that.isPatchPrint.equals(this.isPatchPrint)))
          return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.seqNo;
        result = 37 * result + this.type.hashCode();
        result = 37 * result + this.moduleName.hashCode();
        result = 37 * result + this.serailNo.hashCode();
        result = 37 * result + this.sysSerialNo.hashCode();
        result = 37 * result + (int)this.volumnSeqno;
        result = 37 * result + (int)this.creatorOpID;
        result = 37 * result + (int)this.creatorOrgID;
        result = 37 * result + (int)this.recipientOpID;
        result = 37 * result + (int)this.recipientOrgID;
        result = 37 * result + (int)this.drawOpID;
        result = 37 * result + (int)this.drawOrgID;
        result = 37 * result + this.title.hashCode();
        result = 37 * result + (int)this.accountID;
        result = 37 * result + (int)this.sumAmount;
        result = 37 * result + this.isAmtFixed.hashCode();
        result = 37 * result + this.authBy.hashCode();
        result = 37 * result + this.addressType.hashCode();
        result = 37 * result + (int)this.addressID;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.statusTime.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + this.discardReason.hashCode();
        result = 37 * result + (int)this.printTimes;
        result = 37 * result + this.lastPrintDate.hashCode();
        result = 37 * result + this.printCode.hashCode();
        result = 37 * result + this.machineCode.hashCode();
        result = 37 * result + this.printNumber.hashCode();
        result = 37 * result + this.taxControlCode.hashCode();
        result = 37 * result + this.majorTaxAgentCode.hashCode();
        result = 37 * result + this.isZongBaoRen.hashCode();
        result = 37 * result + this.isFenBaoRen.hashCode();
        result = 37 * result + this.payerName.hashCode();
        result = 37 * result + this.payerType.hashCode();
        result = 37 * result + this.payerID.hashCode();
        result = 37 * result + this.payeeName.hashCode();
        result = 37 * result + this.payeeType.hashCode();
        result = 37 * result + this.payeeID.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        result = 37 * result + this.recipientTime.hashCode();
        result = 37 * result + this.drawTime.hashCode();
        result = 37 * result + this.isPatchPrint.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(1312);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("type:").append(type);
        returnStringBuffer.append("moduleName:").append(moduleName);
        returnStringBuffer.append("serailNo:").append(serailNo);
        returnStringBuffer.append("sysSerialNo:").append(sysSerialNo);
        returnStringBuffer.append("volumnSeqno:").append(volumnSeqno);
        returnStringBuffer.append("creatorOpID:").append(creatorOpID);
        returnStringBuffer.append("creatorOrgID:").append(creatorOrgID);
        returnStringBuffer.append("recipientOpID:").append(recipientOpID);
        returnStringBuffer.append("recipientOrgID:").append(recipientOrgID);
        returnStringBuffer.append("drawOpID:").append(drawOpID);
        returnStringBuffer.append("drawOrgID:").append(drawOrgID);
        returnStringBuffer.append("title:").append(title);
        returnStringBuffer.append("accountID:").append(accountID);
        returnStringBuffer.append("sumAmount:").append(sumAmount);
        returnStringBuffer.append("isAmtFixed:").append(isAmtFixed);
        returnStringBuffer.append("authBy:").append(authBy);
        returnStringBuffer.append("addressType:").append(addressType);
        returnStringBuffer.append("addressID:").append(addressID);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("statusTime:").append(statusTime);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("discardReason:").append(discardReason);
        returnStringBuffer.append("printTimes:").append(printTimes);
        returnStringBuffer.append("lastPrintDate:").append(lastPrintDate);
        returnStringBuffer.append("printCode:").append(printCode);
        returnStringBuffer.append("machineCode:").append(machineCode);
        returnStringBuffer.append("printNumber:").append(printNumber);
        returnStringBuffer.append("taxControlCode:").append(taxControlCode);
        returnStringBuffer.append("majorTaxAgentCode:").append(majorTaxAgentCode);
        returnStringBuffer.append("isZongBaoRen:").append(isZongBaoRen);
        returnStringBuffer.append("isFenBaoRen:").append(isFenBaoRen);
        returnStringBuffer.append("payerName:").append(payerName);
        returnStringBuffer.append("payerType:").append(payerType);
        returnStringBuffer.append("payerID:").append(payerID);
        returnStringBuffer.append("payeeName:").append(payeeName);
        returnStringBuffer.append("payeeType:").append(payeeType);
        returnStringBuffer.append("sourceType:").append(sourceType);
        returnStringBuffer.append("sourceID:").append(sourceID);
        returnStringBuffer.append("invoiceCycleID:").append(invoiceCycleID);
        returnStringBuffer.append("payeeID:").append(payeeID);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
	
	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
