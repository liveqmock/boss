package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ContractDTO implements ReflectionSupport, java.io.Serializable {
    private String contractNo;
    private String name;
    private String description;
    private Timestamp datefrom;
    private Timestamp normaldate;
    private Timestamp dateto;
    private double rentFeeTotal;
    private double oneOffFeeTotal;
    private double prepayAmount;
    private int prepayMopID;
    private Timestamp usedDate;
    private int usedCustomerID;
    private String sheetNo;
    private int userCount;
    private int usedCount;
    private int sourceOrg;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        put("name");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
    }

    public Timestamp getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(Timestamp datefrom) {
        this.datefrom = datefrom;
        put("datefrom");
    }

    public Timestamp getNormaldate() {
        return normaldate;
    }

    public void setNormaldate(Timestamp normaldate) {
        this.normaldate = normaldate;
        put("normaldate");
    }

    public Timestamp getDateto() {
        return dateto;
    }

    public void setDateto(Timestamp dateto) {
        this.dateto = dateto;
        put("dateto");
    }

    public double getRentFeeTotal() {
        return rentFeeTotal;
    }

    public void setRentFeeTotal(double rentFeeTotal) {
        this.rentFeeTotal = rentFeeTotal;
        put("rentFeeTotal");
    }

    public double getOneOffFeeTotal() {
        return oneOffFeeTotal;
    }

    public void setOneOffFeeTotal(double oneOffFeeTotal) {
        this.oneOffFeeTotal = oneOffFeeTotal;
        put("oneOffFeeTotal");
    }

    public double getPrepayAmount() {
        return prepayAmount;
    }

    public void setPrepayAmount(double prepayAmount) {
        this.prepayAmount = prepayAmount;
        put("prepayAmount");
    }

    public int getPrepayMopID() {
        return prepayMopID;
    }

    public void setPrepayMopID(int prepayMopID) {
        this.prepayMopID = prepayMopID;
        put("prepayMopID");
    }

    public Timestamp getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Timestamp usedDate) {
        this.usedDate = usedDate;
        put("usedDate");
    }

    public int getUsedCustomerID() {
        return usedCustomerID;
    }

    public void setUsedCustomerID(int usedCustomerID) {
        this.usedCustomerID = usedCustomerID;
        put("usedCustomerID");
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
        put("sheetNo");
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
        put("userCount");
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
        put("usedCount");
    }

    public int getSourceOrg() {
        return sourceOrg;
    }

    public void setSourceOrg(int sourceOrg) {
        this.sourceOrg = sourceOrg;
        put("sourceOrg");
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

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ContractDTO))
            return false;
        ContractDTO that = (ContractDTO) obj;
        if (!(that.contractNo == null ? this.contractNo == null :
              that.contractNo.equals(this.contractNo)))
            return false;
        if (!(that.name == null ? this.name == null :
              that.name.equals(this.name)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
            return false;
        if (!(that.datefrom == null ? this.datefrom == null :
              that.datefrom.equals(this.datefrom)))
            return false;
        if (!(that.normaldate == null ? this.normaldate == null :
              that.normaldate.equals(this.normaldate)))
            return false;
        if (!(that.dateto == null ? this.dateto == null :
              that.dateto.equals(this.dateto)))
            return false;
        if (Double.doubleToLongBits(that.rentFeeTotal) !=
        	Double.doubleToLongBits(this.rentFeeTotal))
            return false;
        if (Double.doubleToLongBits(that.oneOffFeeTotal) !=
        	Double.doubleToLongBits(this.oneOffFeeTotal))
            return false;
        if (Double.doubleToLongBits(that.prepayAmount) !=
        	Double.doubleToLongBits(this.prepayAmount))
            return false;
        if (that.prepayMopID != this.prepayMopID)
            return false;
        if (!(that.usedDate == null ? this.usedDate == null :
              that.usedDate.equals(this.usedDate)))
            return false;
        if (that.usedCustomerID != this.usedCustomerID)
            return false;
        if (!(that.sheetNo == null ? this.sheetNo == null :
              that.sheetNo.equals(this.sheetNo)))
            return false;
        if (that.userCount != this.userCount)
            return false;
        if (that.usedCount != this.usedCount)
            return false;
        if (that.sourceOrg != this.sourceOrg)
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.contractNo.hashCode();
        result = 37 * result + this.name.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.datefrom.hashCode();
        result = 37 * result + this.normaldate.hashCode();
        result = 37 * result + this.dateto.hashCode();
        result = 37 * result + (int)(this.rentFeeTotal);
        result = 37 * result + (int)(this.oneOffFeeTotal);
        result = 37 * result + (int)(this.prepayAmount);
        result = 37 * result + (int)this.prepayMopID;
        result = 37 * result + this.usedDate.hashCode();
        result = 37 * result + (int)this.usedCustomerID;
        result = 37 * result + this.sheetNo.hashCode();
        result = 37 * result + (int)this.userCount;
        result = 37 * result + (int)this.usedCount;
        result = 37 * result + (int)this.sourceOrg;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(608);
        returnStringBuffer.append("[");
        returnStringBuffer.append("contractNo:").append(contractNo);
        returnStringBuffer.append("name:").append(name);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("datefrom:").append(datefrom);
        returnStringBuffer.append("normaldate:").append(normaldate);
        returnStringBuffer.append("dateto:").append(dateto);
        returnStringBuffer.append("rentFeeTotal:").append(rentFeeTotal);
        returnStringBuffer.append("oneOffFeeTotal:").append(oneOffFeeTotal);
        returnStringBuffer.append("prepayAmount:").append(prepayAmount);
        returnStringBuffer.append("prepayMopID:").append(prepayMopID);
        returnStringBuffer.append("usedDate:").append(usedDate);
        returnStringBuffer.append("usedCustomerID:").append(usedCustomerID);
        returnStringBuffer.append("sheetNo:").append(sheetNo);
        returnStringBuffer.append("userCount:").append(userCount);
        returnStringBuffer.append("usedCount:").append(usedCount);
        returnStringBuffer.append("sourceOrg:").append(sourceOrg);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}
}
