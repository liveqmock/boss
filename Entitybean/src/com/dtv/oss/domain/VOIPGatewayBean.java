package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.VOIPConditionDTO;
import com.dtv.oss.dto.VOIPGatewayDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class VOIPGatewayBean implements EntityBean{
	EntityContext entityContext;
	
	public VOIPGatewayPK ejbCreate(String deviceModel,String devsType)throws CreateException{
		return null;
	}
	public VOIPGatewayPK ejbCreate(VOIPGatewayDTO dto)throws CreateException{
		setDeviceModel(dto.getDeviceModel());
		setDevsType(dto.getDevsType());
		setDescription(dto.getDescription());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
		
	}

	public void ejbPostCreate(VOIPGatewayDTO dto)throws CreateException{
	}

	public void ejbPostCreate(String deviceModel,String devsType)throws CreateException{
	}
	public void ejbRemove()throws RemoveException{
	}
	public abstract void setDtLastmod(Timestamp timestamp);
	
	public abstract void setDtCreate(Timestamp timestamp);
	
	public abstract void setDescription(String description);
	
	public abstract void setDevsType(String devsType);
	
	public abstract void setDeviceModel(String deviceModel);
	
	public abstract Timestamp getDtLastmod();
	
	public abstract Timestamp getDtCreate();
	
	public abstract String getDescription();
	
	public abstract String getDevsType();
	
	public abstract String getDeviceModel();
	
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
	public int ejbUpdate(VOIPGatewayDTO dto) {
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
