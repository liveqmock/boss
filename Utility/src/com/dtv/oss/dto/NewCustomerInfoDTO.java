package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class NewCustomerInfoDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private int csiID;
	private int custID;
	private String catvID;
	private String digitalCatvID;
	private String type;
	private String nationality;
	private String gender;
	private String name;
	private String cardType;
	private String cardID;
	private int marketSegmentID;
	private String openSourceType;
	private int openSourceID;
	private String telephone;
	private String mobileNumber;
	private String faxNumber;
	private String email;
	private int groupBargainID;
	private String contractNo;
	private Timestamp timeRangeStart;
	private Timestamp timeRangeEnd;
	private int fromAddressID;
	private int toAddressID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private Timestamp birthday;
    private String socialSecCardID;
    private String agentName;
    private int groupCustID;
    private String custStyle; 
    private String loginID;
    private String loginPWD;
    private String comments;
    private String customerSerialNo;
    public String getCustomerSerialNo() {
		return customerSerialNo;
	}
	public void setCustomerSerialNo(String customerSerialNo) {
		this.customerSerialNo = customerSerialNo;
		 put("customerSerialNo");
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
	 * @return Returns the loginID.
	 */
    
	public String getLoginID() {
		return loginID;
	}
	/**
	 * @param loginID The loginID to set.
	 */
	public void setLoginID(String loginID) {
		this.loginID = loginID;
		put("loginID");
	}
	/**
	 * @return Returns the loginPWD.
	 */
	public String getLoginPWD() {
		return loginPWD;
	}
	/**
	 * @param loginPWD The loginPWD to set.
	 */
	public void setLoginPWD(String loginPWD) {
		this.loginPWD = loginPWD;
		put("loginPWD");
	}
    
	 

	/**
	 * @return Returns the agentName.
	 */
	public String getAgentName() {
		return agentName;
	}
	/**
	 * @param agentName The agentName to set.
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
		put("agentName");
	}
	/**
	 * @return Returns the custStyle.
	 */
	public String getCustStyle() {
		return custStyle;
	}
	/**
	 * @param custStyle The custStyle to set.
	 */
	public void setCustStyle(String custStyle) {
		this.custStyle = custStyle;
		put("custStyle");
	}
	/**
	 * @return Returns the groupCustID.
	 */
	public int getGroupCustID() {
		return groupCustID;
	}
	/**
	 * @param groupCustID The groupCustID to set.
	 */
	public void setGroupCustID(int groupCustID) {
		this.groupCustID = groupCustID;
		put("groupCustID");
	}
	

	
	public void setId(int id)
	{
		this.id = id;
		//put("id");
	}

	public int getId()
	{
		return id;
	}

	public void setCsiID(int csiID)
	{
		this.csiID = csiID;
		put("csiID");
	}

	public int getCsiID()
	{
		return csiID;
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

	public void setCatvID(String catvID)
	{
		this.catvID = catvID;
		put("catvID");
	}

	public String getCatvID()
	{
		return catvID;
	}

	public void setDigitalCatvID(String digitalCatvID)
	{
		this.digitalCatvID = digitalCatvID;
		put("digitalCatvID");
	}

	public String getDigitalCatvID()
	{
		return digitalCatvID;
	}

	public void setType(String type)
	{
		this.type = type;
		put("type");
	}

	public String getType()
	{
		return type;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
		put("nationality");
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
		put("gender");
	}

	public String getGender()
	{
		return gender;
	}

	public void setName(String name)
	{
		this.name = name;
		put("name");
	}

	public String getName()
	{
		return name;
	}

	public void setCardType(String cardType)
	{
		this.cardType = cardType;
		put("cardType");
	}

	public String getCardType()
	{
		return cardType;
	}

	public void setCardID(String cardID)
	{
		this.cardID = cardID;
		put("cardID");
	}

	public String getCardID()
	{
		return cardID;
	}

	 

	public void setMarketSegmentID(int marketSegmentID)
	{
		this.marketSegmentID = marketSegmentID;
		put("marketSegmentID");
	}

	public int getMarketSegmentID()
	{
		return marketSegmentID;
	}

	public void setOpenSourceType(String openSourceType)
	{
		this.openSourceType = openSourceType;
		put("openSourceType");
	}

	public String getOpenSourceType()
	{
		return openSourceType;
	}

	public void setOpenSourceID(int openSourceID)
	{
		this.openSourceID = openSourceID;
		put("openSourceID");
	}

	public int getOpenSourceID()
	{
		return openSourceID;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
		put("telephone");
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setMobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
		put("mobileNumber");
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public void setFaxNumber(String faxNumber)
	{
		this.faxNumber = faxNumber;
		put("faxNumber");
	}

	public String getFaxNumber()
	{
		return faxNumber;
	}

	public void setEmail(String email)
	{
		this.email = email;
		put("email");
	}

	public String getEmail()
	{
		return email;
	}

	public void setGroupBargainID(int groupBargainID)
	{
		this.groupBargainID = groupBargainID;
		put("groupBargainID");
	}

	public int getGroupBargainID()
	{
		return groupBargainID;
	}

	public void setContractNo(String contractNo)
	{
		this.contractNo = contractNo;
		put("contractNo");
	}

	public String getContractNo()
	{
		return contractNo;
	}

	public void setTimeRangeStart(Timestamp timeRangeStart)
	{
		this.timeRangeStart = timeRangeStart;
		put("timeRangeStart");
	}

	public Timestamp getTimeRangeStart()
	{
		return timeRangeStart;
	}

	public void setTimeRangeEnd(Timestamp timeRangeEnd)
	{
		this.timeRangeEnd = timeRangeEnd;
		put("timeRangeEnd");
	}

	public Timestamp getTimeRangeEnd()
	{
		return timeRangeEnd;
	}

	 
	 
	 
	 
	public void setFromAddressID(int fromAddressID)
	{
		this.fromAddressID = fromAddressID;
		put("fromAddressID");
	}

	public int getFromAddressID()
	{
		return fromAddressID;
	}

	public void setToAddressID(int toAddressID)
	{
		this.toAddressID = toAddressID;
		put("toAddressID");
	}

	public int getToAddressID()
	{
		return toAddressID;
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

    public String getSocialSecCardID() {
        return socialSecCardID;
    }

    public void setSocialSecCardID(String socialSecCardID) {
        this.socialSecCardID = socialSecCardID;
        put("socialSecCardID");
    }

    public void setBirthday(Timestamp birthday)
	{
		this.birthday = birthday;
		put("birthday");
	}

	public Timestamp getBirthday()
	{
		return birthday;
	}

	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				NewCustomerInfoDTO that = (NewCustomerInfoDTO) obj;
				return
					this.getId() == that.getId()  &&
					this.getCsiID() == that.getCsiID()  &&
					this.getCustID() == that.getCustID()  &&
					(((this.getCatvID() == null) && (that.getCatvID() == null)) ||
						(this.getCatvID() != null && this.getCatvID().equals(that.getCatvID()))) &&
					(((this.getDigitalCatvID() == null) && (that.getDigitalCatvID() == null)) ||
						(this.getDigitalCatvID() != null && this.getDigitalCatvID().equals(that.getDigitalCatvID()))) &&
					(((this.getType() == null) && (that.getType() == null)) ||
						(this.getType() != null && this.getType().equals(that.getType()))) &&
					(((this.getNationality() == null) && (that.getNationality() == null)) ||
						(this.getNationality() != null && this.getNationality().equals(that.getNationality()))) &&
					(((this.getGender() == null) && (that.getGender() == null)) ||
						(this.getGender() != null && this.getGender().equals(that.getGender()))) &&
					(((this.getName() == null) && (that.getName() == null)) ||
						(this.getName() != null && this.getName().equals(that.getName()))) &&
					(((this.getCardType() == null) && (that.getCardType() == null)) ||
						(this.getCardType() != null && this.getCardType().equals(that.getCardType()))) &&
					(((this.getCardID() == null) && (that.getCardID() == null)) ||
						(this.getCardID() != null && this.getCardID().equals(that.getCardID()))) &&
						   (((this.getAgentName() == null) && (that.getAgentName() == null)) || (this
				                  .getAgentName() != null && this.getAgentName().equals(
			                    	that.getAgentName()))) &&
					this.getMarketSegmentID() == that.getMarketSegmentID()  &&
					this.getGroupCustID() == that.getGroupCustID()  &&	 
					(((this.getOpenSourceType() == null) && (that.getOpenSourceType() == null)) ||
						(this.getOpenSourceType() != null && this.getOpenSourceType().equals(that.getOpenSourceType()))) &&
					(((this.getCustStyle() == null) && (that.getCustStyle() == null)) ||
						(this.getCustStyle() != null && this.getCustStyle().equals(that.getCustStyle()))) &&	
					this.getOpenSourceID() == that.getOpenSourceID()  &&
					(((this.getTelephone() == null) && (that.getTelephone() == null)) ||
						(this.getTelephone() != null && this.getTelephone().equals(that.getTelephone()))) &&
					(((this.getMobileNumber() == null) && (that.getMobileNumber() == null)) ||
						(this.getMobileNumber() != null && this.getMobileNumber().equals(that.getMobileNumber()))) &&
					(((this.getFaxNumber() == null) && (that.getFaxNumber() == null)) ||
						(this.getFaxNumber() != null && this.getFaxNumber().equals(that.getFaxNumber()))) &&
					(((this.getEmail() == null) && (that.getEmail() == null)) ||
						(this.getEmail() != null && this.getEmail().equals(that.getEmail()))) &&
                    (((this.getSocialSecCardID() == null) && (that.getSocialSecCardID() == null)) ||
						(this.getSocialSecCardID() != null && this.getSocialSecCardID().equals(that.getSocialSecCardID()))) &&
					this.getGroupBargainID() == that.getGroupBargainID()  &&
					(((this.getContractNo() == null) && (that.getContractNo() == null)) ||
						(this.getContractNo() != null && this.getContractNo().equals(that.getContractNo()))) &&
					(((this.getTimeRangeStart() == null) && (that.getTimeRangeStart() == null)) ||
						(this.getTimeRangeStart() != null && this.getTimeRangeStart().equals(that.getTimeRangeStart()))) &&
					(((this.getTimeRangeEnd() == null) && (that.getTimeRangeEnd() == null)) ||
						(this.getTimeRangeEnd() != null && this.getTimeRangeEnd().equals(that.getTimeRangeEnd()))) &&
						(((this.getComments() == null) && (that.getComments() == null)) ||
								(this.getComments() != null && this.getComments().equals(that.getComments()))) &&		
								  (((this.getCustomerSerialNo() == null) && (that
										.getCustomerSerialNo() == null)) || (this
										.getCustomerSerialNo() != null && this
										.getCustomerSerialNo().equals(that.getCustomerSerialNo()))) &&
					this.getFromAddressID() == that.getFromAddressID()  &&
					this.getToAddressID() == that.getToAddressID()  &&
					(((this.getBirthday() == null) && (that.getBirthday() == null)) ||
							(this.getBirthday() != null && this.getBirthday().equals(that.getBirthday()))) && 
							(((this.getLoginID() == null) && (that.getLoginID() == null)) ||
									(this.getLoginID() != null && this.getLoginID().equals(that.getLoginID()))) &&		
									(((this.getLoginPWD() == null) && (that.getLoginPWD() == null)) ||
											(this.getLoginPWD() != null && this.getLoginPWD().equals(that.getLoginPWD()))) &&					
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
		buf.append(id);
		buf.append(",").append(csiID);
		buf.append(",").append(custID);
		buf.append(",").append(catvID);
		buf.append(",").append(digitalCatvID);
		buf.append(",").append(type);
		buf.append(",").append(nationality);
		buf.append(",").append(gender);
		buf.append(",").append(name);
		buf.append(",").append(cardType);
		buf.append(",").append(cardID);
		buf.append(",").append(birthday); 
		buf.append(",").append(marketSegmentID);
		buf.append(",").append(openSourceType);
		buf.append(",").append(openSourceID);
		buf.append(",").append(telephone);
		buf.append(",").append(mobileNumber);
		buf.append(",").append(faxNumber);
		buf.append(",").append(email);
		buf.append(",").append(groupBargainID);
		buf.append(",").append(contractNo);
		buf.append(",").append(timeRangeStart);
		buf.append(",").append(timeRangeEnd);
		 
		buf.append(",").append(fromAddressID);
		buf.append(",").append(toAddressID);
		buf.append(",").append(loginID);
		buf.append(",").append(loginPWD);
		 
		buf.append(",").append(agentName);
		buf.append(",").append(comments); 
		buf.append(",").append(groupCustID);
		buf.append(",").append(custStyle);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(customerSerialNo);
        buf.append(",").append(socialSecCardID);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

