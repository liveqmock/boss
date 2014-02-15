package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.RoleOrganizationDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class RoleOrganizationBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id) throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(RoleOrganizationDTO dto) throws CreateException {
		
		setDistrictId(dto.getDistrictId());
		setServiceOrgId(dto.getServiceOrgId());
        setTroubleType(dto.getTroubleType());
        setTroubleSubType(dto.getTroubleSubType());
        setOrgSubRole(dto.getOrgSubRole());
		setOrgRole(dto.getOrgRole());
		setIsFirst(dto.getIsFirst());
		setDiagnosisResult(dto.getDiagnosisResult());
		setSaProductId(dto.getSaProductId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
       
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(RoleOrganizationDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setDistrictId(int districtId);

	public abstract void setServiceOrgId(int serviceOrgId);

	public abstract void setDiagnosisResult(java.lang.String diagnosisResult);
	public abstract java.lang.String  getDiagnosisResult();
	
	public abstract void setOrgSubRole(java.lang.String orgSubRole);
	public abstract java.lang.String  getOrgSubRole();
	
	public abstract void setOrgRole(java.lang.String orgRole);

	public abstract void setIsFirst(java.lang.String isFirst);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
     
	public abstract java.lang.Integer getId();

	public abstract int getDistrictId();

	public abstract int getServiceOrgId();

	public abstract java.lang.String getOrgRole();

	public abstract java.lang.String getIsFirst();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	public abstract void setTroubleType(java.lang.String troubleType);
	
	public abstract java.lang.String getTroubleType();
	
    public abstract void setTroubleSubType(java.lang.String troubleSubType);
	
	public abstract java.lang.String getTroubleSubType();
	
	public abstract int getSaProductId();
	
	public abstract void setSaProductId(int saProductId);
	
	
     

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


	public int ejbUpdate(RoleOrganizationDTO dto) {
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