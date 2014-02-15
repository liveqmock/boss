package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ProductPackageBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer packageID)
			throws CreateException {
		//setPackageID(packageID);
		return null;
	}

	public java.lang.Integer ejbCreate(ProductPackageDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setDescription(dto.getDescription());
		setDateFrom(dto.getDateFrom());
		setDateTo(dto.getDateTo());
		setStatus(dto.getStatus());
		setPackageClass(dto.getPackageClass());
		setPackagePriority(dto.getPackagePriority());
		
		//setPackageID(dto.getPackageID());
		setPackageName(dto.getPackageName());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setMinSelProductNum(dto.getMinSelProductNum());
		setMaxSelProductNum(dto.getMaxSelProductNum());
		setHasOptionProductFlag(dto.getHasOptionProductFlag());
		setGrade(dto.getGrade());
		setCaptureFlag(dto.getCaptureFlag());
		setCsiTypeList(dto.getCsiTypeList());
		setCustTypeList(dto.getCustTypeList());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer packageID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ProductPackageDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	public abstract void setCaptureFlag(String captureFlag);
	
	public abstract void setCsiTypeList(String csiTypeList);
	
	public abstract void setCustTypeList(String custTypeList);
	
	public abstract String getCaptureFlag();
	
	public abstract String getCsiTypeList();
	
	public abstract String getCustTypeList();
	
	public abstract void setMinSelProductNum(int minSelProdtuctNum);
	
	public abstract void setMaxSelProductNum(int maxSelProdtuctNum);
	
	public abstract void setHasOptionProductFlag(String hasOptionProductFlag);
	
	public abstract void setPackageClass(java.lang.String packageClass);
	
	public abstract void setPackagePriority(int packagePriority);
	
	public abstract void setPackageID(java.lang.Integer packageID);

	public abstract void setPackageName(java.lang.String packageName);

	public abstract void setDescription(java.lang.String description);

	public abstract void setDateFrom(java.sql.Timestamp dateFrom);

	public abstract void setDateTo(java.sql.Timestamp dateTo);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getPackageID();

	public abstract java.lang.String getPackageName();

	public abstract java.lang.String getDescription();

	public abstract java.sql.Timestamp getDateFrom();

	public abstract java.sql.Timestamp getDateTo();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	public abstract java.lang.String getPackageClass();
	
	public abstract int getPackagePriority();
	
	public abstract int getMinSelProductNum();
	
	public abstract int getMaxSelProductNum();
	
	public abstract String getHasOptionProductFlag();

	public abstract void setGrade(int grade);

	public abstract int getGrade();

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

	public int ejbUpdate(ProductPackageDTO dto) {
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