package com.dtv.oss.domain;


import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OldCustomerInfoDTO;
 
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class OldCustomerInfoBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer id) throws CreateException {
       // setId(id);
        return null;
    }

    public Integer ejbCreate(OldCustomerInfoDTO dto) throws CreateException {
    	setCsiID(dto.getCsiID());
    	setCustID(dto.getCustID());
    	setCatvID(dto.getCatvID());
    	setDigitalCatvID(dto.getDigitalCatvID());
    	setType(dto.getType());
    	setNationality(dto.getNationality());
    	setGender(dto.getGender());
    	setName(dto.getName());
    	 
    	setCardType(dto.getCardType());
    	setTelephone(dto.getTelephone());
		setCardID(dto.getCardID());
		setTimeRangeStart(dto.getTimeRangeStart());
		setTimeRangeEnd(dto.getTimeRangeEnd());
		setEmail(dto.getEmail());
		setOpenSourceType(dto.getOpenSourceType());
		setFibernode(dto.getFibernode());
	 
		setGroupBargainID(dto.getGroupBargainID());
		setMobileNumber(dto.getMobileNumber());
		setFaxNumber(dto.getFaxNumber()); 
		setAddressID(dto.getAddressID());
		setSocialSeccardID(dto.getSocialSeccardID());
		setOpenSourceID(dto.getOpenSourceID());
	 
		setContractNo(dto.getContractNo());
		setCustomerSerialNo(dto.getCustomerSerialNo()); 
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setComments(dto.getComments());
		
		setBirthday(dto.getBirthday());
		setLoginID(dto.getLoginID());
		setLoginPwd(dto.getLoginPwd());
        return null;
    }

    

    public void ejbPostCreate(Integer id) throws CreateException {
    }

    public void ejbPostCreate(OldCustomerInfoDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }
    public abstract void setCustomerSerialNo(java.lang.String customerSerialNo);
	
	public abstract java.lang.String getCustomerSerialNo();
    public  abstract void setComments(java.lang.String comments);
	
	public  abstract java.lang.String getComments();
    public abstract void setId(Integer id);

    public abstract Integer getId();

    public abstract void setCsiID(int csiID);

    public abstract int getCsiID();

    public abstract void setCustID(int custID);

    public abstract int getCustID();

    public abstract void setCatvID(String catvID);

    public abstract String getCatvID();

    public abstract void setDigitalCatvID(String digitalCatvID);

    public abstract String getDigitalCatvID();

    public abstract void setType(String type);

    public abstract String getType();

    public abstract void setNationality(String nationality);

    public abstract String getNationality();

    public abstract void setGender(String gender);

    public abstract String getGender();

    public abstract void setName(String name);

    public abstract String getName();

    public abstract void setCardType(String cardType);

    public abstract String getCardType();

    public abstract void setCardID(String cardID);

    public abstract String getCardID();

    public abstract void setSocialSeccardID(String socialSeccardID);

    public abstract String getSocialSeccardID();

    public abstract void setAddressID(int addressID);

    public abstract int getAddressID();

    public abstract void setFibernode(String fibernode);

    public abstract String getFibernode();

    public abstract void setOpenSourceType(String openSourceType);

    public abstract String getOpenSourceType();

    public abstract void setOpenSourceID(int openSourceID);

    public abstract int getOpenSourceID();

    public abstract void setTelephone(String telephone);

    public abstract String getTelephone();

    public abstract void setMobileNumber(String mobileNumber);

    public abstract String getMobileNumber();

    public abstract void setFaxNumber(String faxNumber);

    public abstract String getFaxNumber();

    public abstract void setEmail(String email);

    public abstract String getEmail();

    public abstract void setGroupBargainID(int groupBargainID);

    public abstract int getGroupBargainID();

    public abstract void setContractNo(String contractNo);

    public abstract String getContractNo();

    public abstract void setTimeRangeStart(Timestamp timeRangeStart);

    public abstract Timestamp getTimeRangeStart();

    public abstract void setTimeRangeEnd(Timestamp timeRangeEnd);

    public abstract Timestamp getTimeRangeEnd();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();
    
    public abstract void setBirthday(Timestamp birthday);

    public abstract Timestamp getBirthday();

    public abstract void setLoginID(String loginID);

    public abstract String getLoginID();
    
    public abstract void setLoginPwd(String loginPwd);

    public abstract String getLoginPwd();
    
    public void ejbLoad() {
    }

    public void ejbStore() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void unsetEntityContext() {
        this.entityContext = null;
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }
    public int ejbUpdate(OldCustomerInfoDTO dto) {
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
