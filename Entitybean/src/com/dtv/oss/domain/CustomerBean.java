package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustomerBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer customerID)
			throws CreateException {
		//setCustomerID(customerID);
		return null;
	}

	public java.lang.Integer ejbCreate(CustomerDTO dto) throws CreateException {
		/** @todo Complete this method */
		setGender(dto.getGender());
		setName(dto.getName());
		setBirthday(dto.getBirthday());
		setNationality(dto.getNationality());
		setCardType(dto.getCardType());
		setCardID(dto.getCardID());

		setLoginID(dto.getLoginID());
		setLoginPwd(dto.getLoginPwd());
		setOrgID(dto.getOrgID());
		setTelephone(dto.getTelephone());
		setTelephoneMobile(dto.getTelephoneMobile());
		setFax(dto.getFax());
		setEmail(dto.getEmail());
		setOpenSourceType(dto.getOpenSourceType());
		setOpenSourceTypeID(dto.getOpenSourceTypeID());

		setGroupBargainID(dto.getGroupBargainID());
		setMarketSegmentID(dto.getMarketSegmentID());
		setAgentName(dto.getAgentName());
		setCustomerSerialNo(dto.getCustomerSerialNo());
		setGroupCustID(dto.getGroupCustID());
		setCatvID(dto.getCatvID());
		setStatus(dto.getStatus());
		setAddressID(dto.getAddressID());
		//setCustomerID(dto.getCustomerID());
		setCustomerStyle(dto.getCustomerStyle());
		setCustomerType(dto.getCustomerType());
		setCreateTime(dto.getCreateTime());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setSocialSecCardID(dto.getSocialSecCardID());
		setContractNo(dto.getContractNo());
		setCurrentAccumulatePoint(dto.getCurrentAccumulatePoint());
        setTotalAccumulatePoint(dto.getTotalAccumulatePoint());
        setComments(dto.getComments());
        
		return null;
	}

	public void ejbPostCreate(java.lang.Integer customerID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustomerDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	public abstract void setComments(String comments);
	public abstract String getComments();
	public abstract void setCustomerID(java.lang.Integer customerID);

	public abstract void setCustomerStyle(java.lang.String customerStyle);

	public abstract void setCustomerType(java.lang.String customerType);
	
	public abstract void setCustomerSerialNo(java.lang.String customerSerialNo);
	
	public abstract java.lang.String getCustomerSerialNo();
	
    public abstract void setContractNo(java.lang.String contractNo);
	
	public abstract java.lang.String getContractNo();

	public abstract void setGender(java.lang.String gender);

	public abstract void setName(java.lang.String name);

	public abstract void setBirthday(java.sql.Timestamp birthday);

	public abstract void setNationality(java.lang.String nationality);

	public abstract void setCardType(java.lang.String cardType);

	public abstract void setCardID(java.lang.String cardID);

	public abstract void setLoginID(java.lang.String loginID);
	
	public abstract void setAgentName(java.lang.String agentName);

	 
	public abstract void setGroupCustID(int groupCustID);
	
	public abstract java.lang.String getAgentName();

	 

	public abstract int getGroupCustID();

	public abstract void setLoginPwd(java.lang.String loginPwd);

	public abstract void setOrgID(int orgID);

	public abstract void setTelephone(java.lang.String telephone);

	public abstract void setTelephoneMobile(java.lang.String telephoneMobile);

	public abstract void setFax(java.lang.String fax);

	public abstract void setEmail(java.lang.String email);

	public abstract void setOpenSourceType(java.lang.String openSourceType);

	public abstract void setOpenSourceTypeID(int openSourceTypeID);

	public abstract void setGroupBargainID(int groupBargainID);

	public abstract void setMarketSegmentID(int marketSegmentID);

	public abstract void setCatvID(java.lang.String catvID);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setAddressID(int addressID);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setSocialSecCardID(java.lang.String socialSecCardID);

	 

	public abstract void setTitle(java.lang.String title);
	
	public abstract void setCurrentAccumulatePoint(int CurrentAccumulatePoint);

	public abstract void setTotalAccumulatePoint(int totalAccumulatePoint);

	 

	public abstract java.lang.Integer getCustomerID();

	public abstract java.lang.String getCustomerStyle();

	public abstract java.lang.String getCustomerType();

	public abstract java.lang.String getGender();

	public abstract java.lang.String getName();

	public abstract java.sql.Timestamp getBirthday();

	public abstract java.lang.String getNationality();

	public abstract java.lang.String getCardType();

	public abstract java.lang.String getCardID();

	public abstract java.lang.String getLoginID();

	public abstract java.lang.String getLoginPwd();

	public abstract int getOrgID();

	public abstract java.lang.String getTelephone();

	public abstract java.lang.String getTelephoneMobile();

	public abstract java.lang.String getFax();

	public abstract java.lang.String getEmail();

	public abstract java.lang.String getOpenSourceType();

	public abstract int getOpenSourceTypeID();

	public abstract int getGroupBargainID();
	
	public abstract int getMarketSegmentID();

	 

	public abstract java.lang.String getCatvID();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getAddressID();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract java.lang.String getSocialSecCardID();

	 
	public abstract java.lang.String getTitle();

	public abstract int getCurrentAccumulatePoint();

	public abstract int getTotalAccumulatePoint();

	 

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

	public int ejbUpdate(CustomerDTO dto) {
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