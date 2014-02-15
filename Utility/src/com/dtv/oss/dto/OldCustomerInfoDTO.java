package com.dtv.oss.dto;
import java.sql.Timestamp;

public class OldCustomerInfoDTO implements ReflectionSupport, java.io.Serializable {
    private int ID;
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
    private String socialSeccardID;
    private int addressID;
    private String fibernode;
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
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private String comments;
    private String customerSerialNo;
    private Timestamp birthday;
    private String loginID;
	private String loginPwd;
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
    public int getID() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }

    public int getCsiID() {
        return csiID;
    }

    public void setCsiID(int csiID) {
        this.csiID = csiID;
        put("csiID");
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
        put("custID");
    }

    public String getCatvID() {
        return catvID;
    }

    public void setCatvID(String catvID) {
        this.catvID = catvID;
        put("catvID");
    }

    public String getDigitalCatvID() {
        return digitalCatvID;
    }

    public void setDigitalCatvID(String digitalCatvID) {
        this.digitalCatvID = digitalCatvID;
        put("digitalCatvID");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        put("type");
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
        put("nationality");
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        put("gender");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        put("name");
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
        put("cardType");
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
        put("cardID");
    }

    public String getSocialSeccardID() {
        return socialSeccardID;
    }

    public void setSocialSeccardID(String socialSeccardID) {
        this.socialSeccardID = socialSeccardID;
        put("socialSeccardID");
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
        put("addressID");
    }

    public String getFibernode() {
        return fibernode;
    }

    public void setFibernode(String fibernode) {
        this.fibernode = fibernode;
        put("fibernode");
    }

    public String getOpenSourceType() {
        return openSourceType;
    }

    public void setOpenSourceType(String openSourceType) {
        this.openSourceType = openSourceType;
        put("openSourceType");
    }

    public int getOpenSourceID() {
        return openSourceID;
    }

    public void setOpenSourceID(int openSourceID) {
        this.openSourceID = openSourceID;
        put("openSourceID");
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
        put("telephone");
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        put("mobileNumber");
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
        put("faxNumber");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        put("email");
    }

    public int getGroupBargainID() {
        return groupBargainID;
    }

    public void setGroupBargainID(int groupBargainID) {
        this.groupBargainID = groupBargainID;
        put("groupBargainID");
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
        put("contractNo");
    }

    public Timestamp getTimeRangeStart() {
        return timeRangeStart;
    }

    public void setTimeRangeStart(Timestamp timeRangeStart) {
        this.timeRangeStart = timeRangeStart;
        put("timeRangeStart");
    }

    public Timestamp getTimeRangeEnd() {
        return timeRangeEnd;
    }

    public void setTimeRangeEnd(Timestamp timeRangeEnd) {
        this.timeRangeEnd = timeRangeEnd;
        put("timeRangeEnd");
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
        if (!(obj instanceof OldCustomerInfoDTO))
            return false;
        OldCustomerInfoDTO that = (OldCustomerInfoDTO) obj;
        if (that.ID != this.ID)
            return false;
        if (that.csiID != this.csiID)
            return false;
        if (that.custID != this.custID)
            return false;
        if (!(that.catvID == null ? this.catvID == null :
              that.catvID.equals(this.catvID)))
            return false;
        if (!(that.digitalCatvID == null ? this.digitalCatvID == null :
              that.digitalCatvID.equals(this.digitalCatvID)))
            return false;
        if (!(that.type == null ? this.type == null :
              that.type.equals(this.type)))
            return false;
        if (!(that.nationality == null ? this.nationality == null :
              that.nationality.equals(this.nationality)))
            return false;
        if (!(that.gender == null ? this.gender == null :
              that.gender.equals(this.gender)))
            return false;
        if (!(that.name == null ? this.name == null :
              that.name.equals(this.name)))
            return false;
        if (!(that.cardType == null ? this.cardType == null :
              that.cardType.equals(this.cardType)))
            return false;
        if (!(that.cardID == null ? this.cardID == null :
              that.cardID.equals(this.cardID)))
            return false;
        if (!(that.socialSeccardID == null ? this.socialSeccardID == null :
              that.socialSeccardID.equals(this.socialSeccardID)))
            return false;
        if (that.addressID != this.addressID)
            return false;
        if (!(that.fibernode == null ? this.fibernode == null :
              that.fibernode.equals(this.fibernode)))
            return false;
        if (!(that.openSourceType == null ? this.openSourceType == null :
              that.openSourceType.equals(this.openSourceType)))
            return false;
        if (that.openSourceID != this.openSourceID)
            return false;
        if (!(that.telephone == null ? this.telephone == null :
              that.telephone.equals(this.telephone)))
            return false;
        if (!(that.mobileNumber == null ? this.mobileNumber == null :
              that.mobileNumber.equals(this.mobileNumber)))
            return false;
        if (!(that.faxNumber == null ? this.faxNumber == null :
              that.faxNumber.equals(this.faxNumber)))
            return false;
        if (!(that.email == null ? this.email == null :
              that.email.equals(this.email)))
            return false;
        if (that.groupBargainID != this.groupBargainID)
            return false;
        if (!(that.contractNo == null ? this.contractNo == null :
              that.contractNo.equals(this.contractNo)))
            return false;
        if (!(that.timeRangeStart == null ? this.timeRangeStart == null :
              that.timeRangeStart.equals(this.timeRangeStart)))
            return false;
        if (!(that.customerSerialNo == null ? this.customerSerialNo == null :
            that.customerSerialNo.equals(this.customerSerialNo)))
          return false;
        if (!(that.timeRangeEnd == null ? this.timeRangeEnd == null :
              that.timeRangeEnd.equals(this.timeRangeEnd)))
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
        result = 37 * result + (int)this.ID;
        result = 37 * result + (int)this.csiID;
        result = 37 * result + (int)this.custID;
        result = 37 * result + this.catvID.hashCode();
        result = 37 * result + this.digitalCatvID.hashCode();
        result = 37 * result + this.type.hashCode();
        result = 37 * result + this.nationality.hashCode();
        result = 37 * result + this.gender.hashCode();
        result = 37 * result + this.name.hashCode();
        result = 37 * result + this.cardType.hashCode();
        result = 37 * result + this.cardID.hashCode();
        result = 37 * result + this.socialSeccardID.hashCode();
        result = 37 * result + (int)this.addressID;
        result = 37 * result + this.fibernode.hashCode();
        result = 37 * result + this.openSourceType.hashCode();
        result = 37 * result + (int)this.openSourceID;
        result = 37 * result + this.telephone.hashCode();
        result = 37 * result + this.mobileNumber.hashCode();
        result = 37 * result + this.faxNumber.hashCode();
        result = 37 * result + this.email.hashCode();
        result = 37 * result + (int)this.groupBargainID;
        result = 37 * result + this.contractNo.hashCode();
        result = 37 * result + this.timeRangeStart.hashCode();
        result = 37 * result + this.timeRangeEnd.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        result = 37 * result + this.customerSerialNo.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(832);
        returnStringBuffer.append("[");
        returnStringBuffer.append("id:").append(ID);
        returnStringBuffer.append("csiID:").append(csiID);
        returnStringBuffer.append("custID:").append(custID);
        returnStringBuffer.append("catvID:").append(catvID);
        returnStringBuffer.append("digitalCatvID:").append(digitalCatvID);
        returnStringBuffer.append("type:").append(type);
        returnStringBuffer.append("nationality:").append(nationality);
        returnStringBuffer.append("gender:").append(gender);
        returnStringBuffer.append("name:").append(name);
        returnStringBuffer.append("cardType:").append(cardType);
        returnStringBuffer.append("cardID:").append(cardID);
        returnStringBuffer.append("socialSeccardID:").append(socialSeccardID);
        returnStringBuffer.append("addressID:").append(addressID);
        returnStringBuffer.append("fibernode:").append(fibernode);
        returnStringBuffer.append("openSourceType:").append(openSourceType);
        returnStringBuffer.append("openSourceID:").append(openSourceID);
        returnStringBuffer.append("telephone:").append(telephone);
        returnStringBuffer.append("mobileNumber:").append(mobileNumber);
        returnStringBuffer.append("faxNumber:").append(faxNumber);
        returnStringBuffer.append("email:").append(email);
        returnStringBuffer.append("groupBargainID:").append(groupBargainID);
        returnStringBuffer.append("contractNo:").append(contractNo);
        returnStringBuffer.append("timeRangeStart:").append(timeRangeStart);
        returnStringBuffer.append("timeRangeEnd:").append(timeRangeEnd);
        returnStringBuffer.append("comments:").append(comments);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("CustomerSerialNo :").append(customerSerialNo );
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
	public Timestamp getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
		put("birthday");
	}
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
		put("loginID");
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
		put("loginPwd");
	}
}
