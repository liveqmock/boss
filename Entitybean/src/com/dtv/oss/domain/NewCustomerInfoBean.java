package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class NewCustomerInfoBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(NewCustomerInfoDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		//setId(dto.getId());
		setCsiID(dto.getCsiID());
		setCustID(dto.getCustID());
		setCatvID(dto.getCatvID());
		setDigitalCatvID(dto.getDigitalCatvID());
		setType(dto.getType());
		setNationality(dto.getNationality());
		setGender(dto.getGender());
		setName(dto.getName());
		setCardType(dto.getCardType());
		setCardID(dto.getCardID());
        setFromAddressID(dto.getFromAddressID());
        setToAddressID(dto.getToAddressID());
		setMarketSegmentID(dto.getMarketSegmentID());
		setOpenSourceType(dto.getOpenSourceType());
		setOpenSourceID(dto.getOpenSourceID());
		setTelephone(dto.getTelephone());
		setMobileNumber(dto.getMobileNumber());
		setFaxNumber(dto.getFaxNumber());
		setEmail(dto.getEmail());
		setGroupBargainID(dto.getGroupBargainID());
		setContractNo(dto.getContractNo());
		setTimeRangeStart(dto.getTimeRangeStart());
		setTimeRangeEnd(dto.getTimeRangeEnd());
		setAgentName(dto.getAgentName());
		setCustStyle(dto.getCustStyle());
		setGroupCustID(dto.getGroupCustID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setSocialSecCardID(dto.getSocialSecCardID());
		setBirthday(dto.getBirthday());
		setLoginID(dto.getLoginID());
		setLoginPWD(dto.getLoginPWD());
		setComments(dto.getComments());
		setCustomerSerialNo(dto.getCustomerSerialNo());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(NewCustomerInfoDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
    public abstract void setCustomerSerialNo(java.lang.String customerSerialNo);
	
	public abstract java.lang.String getCustomerSerialNo();
	public abstract void setComments(java.lang.String comments);
	
	public abstract java.lang.String getComments();
	
	public abstract void setLoginID(java.lang.String loginID);

	public abstract void setLoginPWD(java.lang.String loginPWD);
	
	public abstract java.lang.String getLoginID();

	public abstract java.lang.String getLoginPWD();
	
	public abstract void setId(java.lang.Integer id);

	public abstract void setCsiID(int csiID);

	public abstract void setCustID(int custID);

	public abstract void setCatvID(java.lang.String catvID);

	public abstract void setDigitalCatvID(java.lang.String digitalCatvID);

	public abstract void setType(java.lang.String type);

	public abstract void setNationality(java.lang.String nationality);

	public abstract void setGender(java.lang.String gender);

	public abstract void setName(java.lang.String name);

	public abstract void setCardType(java.lang.String cardType);

	public abstract void setCardID(java.lang.String cardID);
	
	public abstract void setFromAddressID(int fromAddressID);
	
	public abstract void setToAddressID(int toAddressID);

	public abstract void setMarketSegmentID(int marketSegmentID);

	public abstract void setOpenSourceType(java.lang.String openSourceType);

	public abstract void setOpenSourceID(int openSourceID);

	public abstract void setTelephone(java.lang.String telephone);

	public abstract void setMobileNumber(java.lang.String mobileNumber);

	public abstract void setFaxNumber(java.lang.String faxNumber);

	public abstract void setEmail(java.lang.String email);

	public abstract void setGroupBargainID(int groupBargainID);

	public abstract void setContractNo(java.lang.String contractNo);
	
	public abstract void setAgentName(java.lang.String agentName);

	public abstract void setCustStyle(String custStyle);

	public abstract void setGroupCustID(int groupCustID);
	
	public abstract java.lang.String getAgentName();

	public abstract String getCustStyle();

	public abstract int getGroupCustID();
	
	public abstract void setBirthday(java.sql.Timestamp birthday);
	
	public abstract java.sql.Timestamp getBirthday();

	public abstract void setTimeRangeStart(java.sql.Timestamp timeRangeStart);

	public abstract void setTimeRangeEnd(java.sql.Timestamp timeRangeEnd);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setSocialSecCardID(java.lang.String socialSecCardID);

	public abstract java.lang.Integer getId();

	public abstract int getCsiID();

	public abstract int getCustID();

	public abstract java.lang.String getCatvID();

	public abstract java.lang.String getDigitalCatvID();

	public abstract java.lang.String getType();

	public abstract java.lang.String getNationality();

	public abstract java.lang.String getGender();

	public abstract java.lang.String getName();

	public abstract java.lang.String getCardType();

	public abstract java.lang.String getCardID();

	public abstract int getMarketSegmentID();

	public abstract java.lang.String getOpenSourceType();

	public abstract int getOpenSourceID();

	public abstract java.lang.String getTelephone();

	public abstract java.lang.String getMobileNumber();

	public abstract java.lang.String getFaxNumber();

	public abstract java.lang.String getEmail();

	public abstract int getGroupBargainID();

	public abstract java.lang.String getContractNo();

	public abstract java.sql.Timestamp getTimeRangeStart();

	public abstract java.sql.Timestamp getTimeRangeEnd();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	public abstract  int getFromAddressID();
	
	public abstract int getToAddressID();

	public abstract java.lang.String getSocialSecCardID();

	public void ejbLoad() {
		/** @todo Complete this method */
	}

	public void ejbStore() {
		/** @todo Complete this method */
	}

	public void ejbActivate() {
		/** @todo Complete this method */
	}

	public void ejbPassivate() {
		/** @todo Complete this method */
	}

	public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	public int ejbUpdate(NewCustomerInfoDTO dto) {
		/** @todo Complete this method */
		if (dto.getDtLastmod() == null) {
			return -1;
		}

		if (!dto.getDtLastmod().equals(getDtLastmod())) {

			return -1;
		} else {
			try {
				EntityBeanAutoUpdate.update(dto, this);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			return 0;
		}
	}

}