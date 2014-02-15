package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.VOIPConditionDTO;
import com.dtv.oss.dto.VOIPProductDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class VOIPProductBean implements EntityBean{
	EntityContext entityContext;
	public VOIPProductPK ejbCreate(int SMSID,String sssrvCode)throws CreateException{
		return null;
	}
	
	public VOIPProductPK ejbCreate(VOIPProductDTO dto)throws CreateException{
		setProductID(dto.getProductID());
		setPropertyName(dto.getPropertyName());
		setSssrvCode(dto.getSssrvCode());
		setSssrvType(dto.getSssrvType());
		setDescription(dto.getDescription());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}
	
	public void ejbPostCreate(int SMSID,String sssrvCode)throws CreateException{
	}
	
	public void ejbPostCreate(VOIPProductDTO dto)throws CreateException{
	}
	
	public void ejbRemove()throws RemoveException{
	}
	
	public abstract void setDtLastmod(Timestamp dtLastmod) ;
	public abstract Timestamp getDtLastmod();

	public abstract void setDtCreate(Timestamp dtCreate);
	public abstract Timestamp getDtCreate();
	
	public abstract void setDescription(String description);
	public abstract String getDescription();

	public abstract void setSssrvType(String serviceType);
	public abstract String getSssrvType();
	
	public abstract void setSssrvCode(String sssrvCode);
	public abstract String getSssrvCode();
	
	public abstract void setPropertyName(String name) ;
	public abstract String getPropertyName();

	public abstract void setProductID(int SMSID);
	public abstract int getProductID();
	
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
	public int ejbUpdate(VOIPProductDTO dto) {
		/** @todo Complete this method */
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
