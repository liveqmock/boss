package com.dtv.oss.dto;

import java.sql.Timestamp;

/**@todo Complete package & import here*/

public class CatvTerminalDTO implements ReflectionSupport, java.io.Serializable
{
	private String id;
	 
	private String catvTermType;
	 
	private int conBatchID;
	private String comments;
	private String status;
	private String recordSource;
	private String statusReason;
 
	private String detailedAddress;
	private String postcode;
	 
	private int districtID;
	
	private int portNo;
	private int fiberNodeID;
	private String cableType;
	private String biDirectionFlag;
	private Timestamp createDate;
	private Timestamp activeDate;
	 
	private Timestamp pauseDate;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private Timestamp cancelDate;
	

	public CatvTerminalDTO()
	{
	}

	 
	public void setId(String id)
	{
		this.id = id;
		//put("id");
	}

	public String getId()
	{
		return id;
	}
   
	 
	public Timestamp getCancelDate() {
		return cancelDate;
	}


	public void setCancelDate(Timestamp cancelDate) {
		this.cancelDate = cancelDate;
		put("cancelDate");
	}
	
	public Timestamp getActiveDate() {
		return activeDate;
	}


	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
		put("activeDate");
	}


	 


	public String getCatvTermType() {
		return catvTermType;
	}


	public void setCatvTermType(String catvTermType) {
		this.catvTermType = catvTermType;
		put("catvTermType");
	}


	public Timestamp getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
		put("createDate");
	}


	public int getFiberNodeID() {
		return fiberNodeID;
	}


	public void setFiberNodeID(int fiberNodeID) {
		this.fiberNodeID = fiberNodeID;
		put("fiberNodeID");
	}


	public Timestamp getPauseDate() {
		return pauseDate;
	}


	public void setPauseDate(Timestamp pauseDate) {
		this.pauseDate = pauseDate;
		put("pauseDate");
	}


	public int getPortNo() {
		return portNo;
	}


	public void setPortNo(int portNo) {
		this.portNo = portNo;
		put("portNo");
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

	public void setConBatchID(int conBatchID)
	{
		this.conBatchID = conBatchID;
		put("conBatchID");
	}

	public int getConBatchID()
	{
		return conBatchID;
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

	public void setRecordSource(String recordSource)
	{
		this.recordSource = recordSource;
		put("recordSource");
	}

	public String getRecordSource()
	{
		return recordSource;
	}

	public void setStatusReason(String statusReason)
	{
		this.statusReason = statusReason;
		put("statusReason");
	}

	public String getStatusReason()
	{
		return statusReason;
	}

	 

	public void setDetailedAddress(String detailedAddress)
	{
		this.detailedAddress = detailedAddress;
		put("detailedAddress");
	}

	public String getDetailedAddress()
	{
		return detailedAddress;
	}

	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
		put("postcode");
	}

	public String getPostcode()
	{
		return postcode;
	}

	 
	 
	public void setCableType(String cableType)
	{
		this.cableType = cableType;
		put("cableType");
	}

	public String getCableType()
	{
		return cableType;
	}

	public void setBiDirectionFlag(String biDirectionFlag)
	{
		this.biDirectionFlag = biDirectionFlag;
		put("biDirectionFlag");
	}

	public String getBiDirectionFlag()
	{
		return biDirectionFlag;
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
				CatvTerminalDTO that = (CatvTerminalDTO) obj;
				return
				 
					(((this.getId() == null) && (that.getId() == null)) ||
							(this.getId() != null && this.getId().equals(that.getId()))) &&
					this.getConBatchID() == that.getConBatchID()  &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
						(((this.getComments() == null) && (that.getComments() == null)) ||
								(this.getComments() != null && this.getComments().equals(that.getComments()))) &&
					(((this.getRecordSource() == null) && (that.getRecordSource() == null)) ||
						(this.getRecordSource() != null && this.getRecordSource().equals(that.getRecordSource()))) &&
					(((this.getStatusReason() == null) && (that.getStatusReason() == null)) ||
						(this.getStatusReason() != null && this.getStatusReason().equals(that.getStatusReason()))) &&
					this.getDistrictID() == that.getDistrictID()  &&
					(((this.getDetailedAddress() == null) && (that.getDetailedAddress() == null)) ||
						(this.getDetailedAddress() != null && this.getDetailedAddress().equals(that.getDetailedAddress()))) &&
					(((this.getPostcode() == null) && (that.getPostcode() == null)) ||
						(this.getPostcode() != null && this.getPostcode().equals(that.getPostcode()))) &&
					 this.getPortNo() == that.getPortNo()  &&
					 this.getFiberNodeID() == that.getFiberNodeID()  &&
					 
					(((this.getCableType() == null) && (that.getCableType() == null)) ||
						(this.getCableType() != null && this.getCableType().equals(that.getCableType()))) &&
						(((this.getCancelDate() == null) && (that.getCancelDate() == null)) ||
								(this.getCancelDate() != null && this.getCancelDate().equals(that.getCancelDate()))) &&
					(((this.getBiDirectionFlag() == null) && (that.getBiDirectionFlag() == null)) ||
						(this.getBiDirectionFlag() != null && this.getBiDirectionFlag().equals(that.getBiDirectionFlag()))) &&
					(((this.getActiveDate() == null) && (that.getActiveDate() == null)) ||
						(this.getActiveDate() != null && this.getActiveDate().equals(that.getActiveDate()))) &&	 
					(((this.getPauseDate() == null) && (that.getPauseDate() == null)) ||
						(this.getPauseDate() != null && this.getPauseDate().equals(that.getPauseDate()))) &&
						(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
								(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
						(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
										(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod()))) &&	
						(((this.getPauseDate() == null) && (that.getPauseDate() == null)) ||
									(this.getPauseDate() != null && this.getPauseDate().equals(that.getPauseDate()))) &&			
							 				
						(((this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
											(this.getCreateDate() != null && this.getCreateDate().equals(that.getCreateDate())));			
						 
						 
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
		buf.append(id);
	//	buf.append(",").append(catvCode);
		buf.append(",").append(catvTermType);
		buf.append(",").append(conBatchID);
		buf.append(",").append(status);
		buf.append(",").append(recordSource);
		buf.append(",").append(statusReason);
	 
		buf.append(",").append(detailedAddress);
		buf.append(",").append(postcode);
		buf.append(",").append(districtID);
		buf.append(",").append(cancelDate); 
		 
		buf.append(",").append(portNo);
		buf.append(",").append(fiberNodeID);
		buf.append(",").append(cableType);
		buf.append(",").append(biDirectionFlag);
		buf.append(",").append(createDate);
		buf.append(",").append(activeDate);
		buf.append(",").append(pauseDate);
		buf.append(",").append(comments);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }


	 


	public int getDistrictID() {
		return districtID;
	}


	public void setDistrictID(int districtID) {
		this.districtID = districtID;
		put("districtID");
	}


}

