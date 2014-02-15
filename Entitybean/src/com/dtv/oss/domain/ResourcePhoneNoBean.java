package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ResourcePhoneNoBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer itemID)
			throws CreateException {
		//setProductID(productID);
		return null;
	}

	public java.lang.Integer ejbCreate(ResourcePhoneNoDTO dto) throws CreateException {
		/** @todo Complete this method */
		setAreaCode(dto.getAreaCode());
		setComments(dto.getComments());
		setStatusTime(dto.getStatusTime());
		setStatus(dto.getStatus());
		setCountryCode(dto.getCountryCode());
		setResourceName(dto.getResourceName());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setPhoneNo(dto.getPhoneNo());
		setDistrictId(dto.getDistrictId());
		setGrade(dto.getGrade());
		setChooseNoFee(dto.getChooseNoFee());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer itemID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ResourcePhoneNoDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setItemID(java.lang.Integer itemID);
	
	public abstract void setDistrictId(int districtId);
	
	public abstract int getDistrictId();

	public abstract void setAreaCode(java.lang.String areaCode);

	public abstract void setComments(java.lang.String comments);

	public abstract void setCountryCode(java.lang.String countryCode);

	public abstract void setStatusTime(java.sql.Timestamp statusTime);

	public abstract void setResourceName(java.lang.String resourceName);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
	
	public abstract void setPhoneNo(java.lang.String phoneNo);

	public abstract java.lang.Integer getItemID();

	public abstract java.lang.String getAreaCode();

	 
	public abstract java.lang.String getComments();

	public abstract java.lang.String getCountryCode();

	public abstract java.sql.Timestamp getStatusTime();

	public abstract java.lang.String getResourceName();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	public abstract java.lang.String getPhoneNo();

	public abstract String getGrade();

	public abstract void setGrade(String grade);

	public abstract double getChooseNoFee();

	public abstract void setChooseNoFee(double chooseNoFee);	 

	 

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

	public int ejbUpdate(ResourcePhoneNoDTO dto) {
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

 

	public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

}