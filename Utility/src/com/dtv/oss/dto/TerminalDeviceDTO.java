package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class TerminalDeviceDTO implements ReflectionSupport, java.io.Serializable
{
	private String status;
	private String addressType;
	private int addressID;
	private int batchID;
	private String leaseBuy;
	private int guaranteeLength;
	private Timestamp dateFrom;
	private Timestamp dateTo;
	private int depotID;
	private int palletID;
	private String useFlag;
	private String matchFlag;
	private int matchDeviceID;
	private int deviceID;
	private String deviceClass;
	private String deviceModel;
	private String serialNo;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
    private String caSetFlag;
    private Timestamp caSetDate;
    private String mACAddress;
	private String interMACAddress;
	private String preAuthorization;
	
	private String okNumber;
	private String ownerType;
	private int ownerID;
	private String purposeStrList;
	private String comments;
	
	

	public TerminalDeviceDTO()
	{
	}

	public void setInterMACAddress(String interMACAddress) {
		this.interMACAddress = interMACAddress;
		put("interMACAddress");
	}

	/**
	 * @return Returns the preAuthorization.
	 */
	public String getPreAuthorization() {
		return preAuthorization;
	}
	/**
	 * @param preAuthorization The preAuthorization to set.
	 */
	public void setPreAuthorization(String preAuthorization) {
		this.preAuthorization = preAuthorization;
		put("preAuthorization");
	}
	public String getInterMACAddress() {
		return interMACAddress;
	}	 
	public void setMACAddress(String mACAddress) {
		this.mACAddress = mACAddress;
		put("mACAddress");
	}

	public String getMACAddress() {
		return mACAddress;
	}	 
    public void setCaSetFlag(String caSetFlag) {
		this.caSetFlag = caSetFlag;
		put("caSetFlag");
	}

	public String getCaSetFlag() {
		return caSetFlag;
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

	public void setAddressType(String addressType)
	{
		this.addressType = addressType;
		put("addressType");
	}

	public String getAddressType()
	{
		return addressType;
	}

	public void setAddressID(int addressID)
	{
		this.addressID = addressID;
		put("addressID");
	}

	public int getAddressID()
	{
		return addressID;
	}

	public void setBatchID(int batchID)
	{
		this.batchID = batchID;
		put("batchID");
	}

	public int getBatchID()
	{
		return batchID;
	}

	public void setLeaseBuy(String leaseBuy)
	{
		this.leaseBuy = leaseBuy;
		put("leaseBuy");
	}

	public String getLeaseBuy()
	{
		return leaseBuy;
	}

	public void setGuaranteeLength(int guaranteeLength)
	{
		this.guaranteeLength = guaranteeLength;
		put("guaranteeLength");
	}

	public int getGuaranteeLength()
	{
		return guaranteeLength;
	}

	public void setDateFrom(Timestamp  dateFrom)
	{
		this.dateFrom = dateFrom;
		put("dateFrom");
	}

	public Timestamp getDateFrom()
	{
		return dateFrom;
	}

	public void setDateTo(Timestamp  dateTo)
	{
		this.dateTo = dateTo;
		put("dateTo");
	}

	public Timestamp getDateTo()
	{
		return dateTo;
	}

	public void setDepotID(int depotID)
	{
		this.depotID = depotID;
		put("depotID");
	}

	public int getDepotID()
	{
		return depotID;
	}

	public void setPalletID(int palletID)
	{
		this.palletID = palletID;
		put("palletID");
	}

	public int getPalletID()
	{
		return palletID;
	}

	public void setUseFlag(String useFlag)
	{
		this.useFlag = useFlag;
		put("useFlag");
	}

	public String getUseFlag()
	{
		return useFlag;
	}

	public void setMatchFlag(String matchFlag)
	{
		this.matchFlag = matchFlag;
		put("matchFlag");
	}

	public String getMatchFlag()
	{
		return matchFlag;
	}

	public void setMatchDeviceID(int matchDeviceID)
	{
		this.matchDeviceID = matchDeviceID;
		put("matchDeviceID");
	}

	public int getMatchDeviceID()
	{
		return matchDeviceID;
	}

	public void setDeviceID(int deviceID)
	{
		this.deviceID = deviceID;
		//put("deviceID");
	}

	public int getDeviceID()
	{
		return deviceID;
	}

	public void setDeviceClass(String deviceClass)
	{
		this.deviceClass = deviceClass;
		put("deviceClass");
	}

	public String getDeviceClass()
	{
		return deviceClass;
	}

	public void setDeviceModel(String deviceModel)
	{
		this.deviceModel = deviceModel;
		put("deviceModel");
	}

	public String getDeviceModel()
	{
		return deviceModel;
	}

	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
		put("serialNo");
	}

	public String getSerialNo()
	{
		return serialNo;
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

        public Timestamp getCaSetDate()
          {
                  return caSetDate;
          }

      public void setCaSetDate(Timestamp caSetDate)
          {
                  this.caSetDate = caSetDate;
                  put("caSetDate");
          }
      
      
    
  	public void setOwnerType(String ownerType)
	{
		this.ownerType = ownerType;
		put("ownerType");
	}

	public String getOwnerType()
	{
		return ownerType;
	}
	public void setOwnerID(int ownerID)
	{
		this.ownerID = ownerID;
		put("ownerID");
	}

	public int getOwnerID()
	{
		return ownerID;
	}
	
  	public void setPurposeStrList(String purposeStrList)
	{
		this.purposeStrList = purposeStrList;
		put("purposeStrList");
	}

	public String getPurposeStrList()
	{
		return purposeStrList;
	}
	
	public void setComments(String comments)
	{
		this.comments = comments;
		put("comments");
	}

	public String getComments()
	{
		return comments;
	}
	public void setOkNumber(String okNumber)
	{
		this.okNumber = okNumber;
		put("okNumber");
	}

	public String getOkNumber()
	{
		return okNumber;
	}

	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				TerminalDeviceDTO that = (TerminalDeviceDTO) obj;
				return
				(((this.getMACAddress() == null) && (that.getMACAddress() == null)) ||
						(this.getMACAddress() != null && this.getMACAddress().equals(that.getMACAddress()))) &&
					(((this.getInterMACAddress() == null) && (that.getInterMACAddress() == null)) ||
						(this.getInterMACAddress() != null && this.getInterMACAddress().equals(that.getInterMACAddress()))) &&
                    (((this.getCaSetFlag() == null) && (that.getCaSetFlag() == null)) ||
						(this.getCaSetFlag() != null && this.getCaSetFlag().equals(that.getCaSetFlag()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					(((this.getAddressType() == null) && (that.getAddressType() == null)) ||
						(this.getAddressType() != null && this.getAddressType().equals(that.getAddressType()))) &&
					this.getAddressID() == that.getAddressID()  &&
					this.getBatchID() == that.getBatchID()  &&
					(((this.getLeaseBuy() == null) && (that.getLeaseBuy() == null)) ||
						(this.getLeaseBuy() != null && this.getLeaseBuy().equals(that.getLeaseBuy()))) &&
					this.getGuaranteeLength() == that.getGuaranteeLength()  &&
					(((this.getDateFrom() == null) && (that.getDateFrom() == null)) ||
						(this.getDateFrom() != null && this.getDateFrom().equals(that.getDateFrom()))) &&
					(((this.getDateTo() == null) && (that.getDateTo() == null)) ||
						(this.getDateTo() != null && this.getDateTo().equals(that.getDateTo()))) &&
					this.getDepotID() == that.getDepotID()  &&
					this.getPalletID() == that.getPalletID()  &&
					(((this.getUseFlag() == null) && (that.getUseFlag() == null)) ||
						(this.getUseFlag() != null && this.getUseFlag().equals(that.getUseFlag()))) &&
					(((this.getMatchFlag() == null) && (that.getMatchFlag() == null)) ||
						(this.getMatchFlag() != null && this.getMatchFlag().equals(that.getMatchFlag()))) &&
					this.getMatchDeviceID() == that.getMatchDeviceID()  &&
					this.getDeviceID() == that.getDeviceID()  &&
					(((this.getDeviceClass() == null) && (that.getDeviceClass() == null)) ||
						(this.getDeviceClass() != null && this.getDeviceClass().equals(that.getDeviceClass()))) &&
					(((this.getDeviceModel() == null) && (that.getDeviceModel() == null)) ||
						(this.getDeviceModel() != null && this.getDeviceModel().equals(that.getDeviceModel()))) &&
					(((this.getSerialNo() == null) && (that.getSerialNo() == null)) ||
						(this.getSerialNo() != null && this.getSerialNo().equals(that.getSerialNo()))) &&
					(((this.getCaSetDate() == null) && (that.getCaSetDate() == null)) ||
						(this.getCaSetDate() != null && this.getCaSetDate().equals(that.getCaSetDate()))) &&
                                        (((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod()))) &&
						
						(((this.getOwnerType() == null) && (that.getOwnerType() == null)) ||
								(this.getOwnerType() != null && this.getOwnerType().equals(that.getOwnerType()))) &&
						this.getOwnerID() == that.getOwnerID()  &&
						(((this.getPurposeStrList() == null) && (that.getPurposeStrList() == null)) ||
								(this.getPurposeStrList() != null && this.getPurposeStrList().equals(that.getPurposeStrList()))) &&
						(((this.getComments() == null) && (that.getComments() == null)) ||
								(this.getComments() != null && this.getComments().equals(that.getComments())))&&
						(((this.getOkNumber() == null) && (that.getOkNumber() == null)) ||
								(this.getOkNumber() != null && this.getOkNumber().equals(that.getOkNumber())))
						
						;
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
		buf.append(status);
		buf.append(",").append(addressType);
		buf.append(",").append(addressID);
		buf.append(",").append(batchID);
		buf.append(",").append(leaseBuy);
		buf.append(",").append(guaranteeLength);
		buf.append(",").append(dateFrom);
		buf.append(",").append(dateTo);
		buf.append(",").append(depotID);
		buf.append(",").append(palletID);
		buf.append(",").append(useFlag);
		buf.append(",").append(matchFlag);
		buf.append(",").append(matchDeviceID);
		buf.append(",").append(deviceID);
		buf.append(",").append(deviceClass);
		buf.append(",").append(deviceModel);
		buf.append(",").append(serialNo);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
        buf.append(",").append(caSetFlag);
		buf.append(",").append(caSetDate);
		buf.append(",").append(mACAddress);
		buf.append(",").append(interMACAddress);
		buf.append(",").append(preAuthorization);
		buf.append(",").append(ownerType);
		buf.append(",").append(ownerID);
		buf.append(",").append(purposeStrList);
		buf.append(",").append(comments);
		buf.append(",").append(okNumber);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

