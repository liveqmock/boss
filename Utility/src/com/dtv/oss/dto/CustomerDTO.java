package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class CustomerDTO implements ReflectionSupport, java.io.Serializable {
	private String gender;

	private String name;

	private Timestamp birthday;

	private String nationality;

	private String cardType;

	private String cardID;

	private String loginID;

	private String loginPwd;

	private int orgID;

	private String telephone;

	private String telephoneMobile;

	private String fax;

	private String email;

	private String openSourceType;

	private int openSourceTypeID;

	private int groupBargainID;

	 

	private int marketSegmentID; 

	private String catvID;

	private String status;

	private int addressID;

	private int customerID;

	private String customerStyle;

	private String customerType;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private java.sql.Timestamp createTime;

	private String socialSecCardID;

	private String title;

	private int currentAccumulatePoint;

	private int totalAccumulatePoint;
	
	private String agentName;
	
	private String customerSerialNo;
	
	private String contractNo;
	 private String comments;
	 
	 
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
	 * @return Returns the contractNo.
	 */
	public String getContractNo() {
		return contractNo;
	}
	/**
	 * @param contractNo The contractNo to set.
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
		put("contractNo");
	}
	private int groupCustID;
	
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
	

	 

	public CustomerDTO() {
	}

	public void setGender(String gender) {
		this.gender = gender;
		put("gender");
	}

	public String getGender() {
		return gender;
	}
	 

	public void setName(String name) {
		this.name = name;
		put("name");
	}

	public String getName() {
		return name;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
		put("birthday");
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
		put("nationality");
	}

	public String getNationality() {
		return nationality;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
		put("cardType");
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
		put("cardID");
	}

	public String getCardID() {
		return cardID;
	}

	public void setMarketSegmentID(int marketSegmentID) {
		this.marketSegmentID = marketSegmentID;
		put("marketSegmentID");
	}

	public int getMarketSegmentID() {
		return marketSegmentID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
		put("loginID");
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
		put("loginPwd");
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setOrgID(int orgID) {
		this.orgID = orgID;
		put("orgID");
	}

	public int getOrgID() {
		return orgID;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
		put("telephone");
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephoneMobile(String telephoneMobile) {
		this.telephoneMobile = telephoneMobile;
		put("telephoneMobile");
	}

	public String getTelephoneMobile() {
		return telephoneMobile;
	}

	public void setFax(String fax) {
		this.fax = fax;
		put("fax");
	}

	public String getFax() {
		return fax;
	}

	public void setEmail(String email) {
		this.email = email;
		put("email");
	}

	public String getEmail() {
		return email;
	}

	public void setOpenSourceType(String openSourceType) {
		this.openSourceType = openSourceType;
		put("openSourceType");
	}

	public String getOpenSourceType() {
		return openSourceType;
	}

	public void setOpenSourceTypeID(int openSourceTypeID) {
		this.openSourceTypeID = openSourceTypeID;
		put("openSourceTypeID");
	}

	public int getOpenSourceTypeID() {
		return openSourceTypeID;
	}

	public void setGroupBargainID(int groupBargainID) {
		this.groupBargainID = groupBargainID;
		put("groupBargainID");
	}

	public int getGroupBargainID() {
		return groupBargainID;
	}

	 

	 
	public void setCatvID(String catvID) {
		this.catvID = catvID;
		put("catvID");
	}

	public String getCatvID() {
		return catvID;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getStatus() {
		return status;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
		put("addressID");
	}

	public int getAddressID() {
		return addressID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
		//put("customerID");
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerStyle(String customerStyle) {
		this.customerStyle = customerStyle;
		put("customerStyle");
	}

	public String getCustomerStyle() {
		return customerStyle;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
		put("customerType");
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}

	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public String getSocialSecCardID() {
		return socialSecCardID;
	}

	public void setSocialSecCardID(String socialSecCardID) {
		this.socialSecCardID = socialSecCardID;
		put("socialSecCardID");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setCurrentAccumulatePoint(int currentAccumulatePoint) {
		this.currentAccumulatePoint = currentAccumulatePoint;
		put("currentAccumulatePoint");
	}

	public int getCurrentAccumulatePoint() {
		return currentAccumulatePoint;
	}

	public void setTotalAccumulatePoint(int totalAccumulatePoint) {
		this.totalAccumulatePoint = totalAccumulatePoint;
		put("totalAccumulatePoint");
	}

	public int getTotalAccumulatePoint() {
		return totalAccumulatePoint;
	}

	 

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CustomerDTO that = (CustomerDTO) obj;
				return (((this.getGender() == null) && (that.getGender() == null)) || (this
						.getGender() != null && this.getGender().equals(
						that.getGender())))
						&& (((this.getName() == null) && (that.getName() == null)) || (this
								.getName() != null && this.getName().equals(
								that.getName())))
	                   && (((this.getAgentName() == null) && (that.getAgentName() == null)) || (this
			                  .getAgentName() != null && this.getAgentName().equals(
		                    	that.getAgentName())))
						&& (((this.getBirthday() == null) && (that
								.getBirthday() == null)) || (this.getBirthday() != null && this
								.getBirthday().equals(that.getBirthday())))
						&& (((this.getNationality() == null) && (that
								.getNationality() == null)) || (this
								.getNationality() != null && this
								.getNationality().equals(that.getNationality())))
								&& (((this.getCustomerSerialNo() == null) && (that
								.getCustomerSerialNo() == null)) || (this
								.getCustomerSerialNo() != null && this
								.getCustomerSerialNo().equals(that.getCustomerSerialNo())))
&& (((this.getContractNo() == null) && (that
		.getContractNo() == null)) || (this
		.getContractNo() != null && this
		.getContractNo().equals(that.getContractNo())))			
						&& (((this.getCardType() == null) && (that
								.getCardType() == null)) || (this.getCardType() != null && this
								.getCardType().equals(that.getCardType())))
						&& (((this.getCardID() == null) && (that.getCardID() == null)) || (this
								.getCardID() != null && this.getCardID()
								.equals(that.getCardID())))
						&& (((this.getTitle() == null) && (that.getTitle() == null)) || (this
								.getTitle() != null && this.getTitle().equals(
								that.getTitle())))
						&& (((this.getLoginID() == null) && (that.getLoginID() == null)) || (this
								.getLoginID() != null && this.getLoginID()
								.equals(that.getLoginID())))
						&& (((this.getLoginPwd() == null) && (that
								.getLoginPwd() == null)) || (this.getLoginPwd() != null && this
								.getLoginPwd().equals(that.getLoginPwd())))
								 
						&& this.getOrgID() == that.getOrgID()
	                    && this.getGroupCustID() == that.getGroupCustID()
                        && this.getMarketSegmentID() == that.getMarketSegmentID()
						&& (((this.getTelephone() == null) && (that
								.getTelephone() == null)) || (this
								.getTelephone() != null && this.getTelephone()
								.equals(that.getTelephone())))
						&& (((this.getTelephoneMobile() == null) && (that
								.getTelephoneMobile() == null)) || (this
								.getTelephoneMobile() != null && this
								.getTelephoneMobile().equals(
										that.getTelephoneMobile())))
						&& (((this.getFax() == null) && (that.getFax() == null)) || (this
								.getFax() != null && this.getFax().equals(
								that.getFax())))
						&& (((this.getEmail() == null) && (that.getEmail() == null)) || (this
								.getEmail() != null && this.getEmail().equals(
								that.getEmail())))
						&& (((this.getOpenSourceType() == null) && (that
								.getOpenSourceType() == null)) || (this
								.getOpenSourceType() != null && this
								.getOpenSourceType().equals(
										that.getOpenSourceType())))
						&& this.getOpenSourceTypeID() == that
								.getOpenSourceTypeID()
						&& this.getCurrentAccumulatePoint() == that
								.getCurrentAccumulatePoint()
						&& this.getTotalAccumulatePoint() == that
								.getTotalAccumulatePoint()
						 
						&& (((this.getSocialSecCardID() == null) && (that
								.getSocialSecCardID() == null)) || (this
								.getSocialSecCardID() != null && this
								.getSocialSecCardID().equals(
										that.getSocialSecCardID())))
						&&

						this.getGroupBargainID() == that.getGroupBargainID()
						 
						&& (((this.getCatvID() == null) && (that.getCatvID() == null)) || (this
								.getCatvID() != null && this.getCatvID()
								.equals(that.getCatvID())))
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
						&& this.getAddressID() == that.getAddressID()
						&& this.getCustomerID() == that.getCustomerID()
						&& (((this.getCreateTime() == null) && (that
								.getCreateTime() == null)) || (this
								.getCreateTime() != null && this
								.getCreateTime().equals(that.getCreateTime())))
						&& (((this.getCustomerStyle() == null) && (that
								.getCustomerStyle() == null)) || (this
								.getCustomerStyle() != null && this
								.getCustomerStyle().equals(
										that.getCustomerStyle())))
						&& (((this.getCustomerType() == null) && (that
								.getCustomerType() == null)) || (this
								.getCustomerType() != null && this
								.getCustomerType().equals(
										that.getCustomerType())))
						&& (((this.getDtCreate() == null) && (that
								.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append(gender);
		buf.append(",").append(name);
		buf.append(",").append(birthday);
		buf.append(",").append(nationality);
		buf.append(",").append(cardType);
		buf.append(",").append(cardID);
		buf.append(",").append(title);
		buf.append(",").append(loginID);
		buf.append(",").append(loginPwd);
		buf.append(",").append(orgID);
		buf.append(",").append(telephone);
		buf.append(",").append(telephoneMobile);
		buf.append(",").append(fax);
		buf.append(",").append(email);
		buf.append(",").append(openSourceType);
		buf.append(",").append(openSourceTypeID);
		buf.append(",").append(currentAccumulatePoint);
		buf.append(",").append(totalAccumulatePoint);
		buf.append(",").append(groupBargainID);
		buf.append(",").append(marketSegmentID);
		buf.append(",").append(catvID);
		buf.append(",").append(status);
		buf.append(",").append(addressID);
		buf.append(",").append(customerID);
		buf.append(",").append(customerStyle);
		buf.append(",").append(customerType);
		buf.append(",").append(createTime);
		buf.append(",").append(groupCustID);
		buf.append(",").append(agentName);
		buf.append(",").append(contractNo);
		buf.append(",").append(comments);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(socialSecCardID);
		buf.append(",").append(customerSerialNo);
		return buf.toString();

	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

}

