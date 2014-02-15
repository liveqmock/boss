package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.VOIPConditionDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class VOIPConditionBean implements EntityBean{
	EntityContext entityContext;
	
	public Integer ejbCreate(Integer conditionID)
	        throws CreateException{
		    return null;
	}
	
	public Integer ejbCreate(VOIPConditionDTO dto)
	   throws CreateException{
		setConditionID(new Integer(dto.getConditionID()));
		setConditionName(dto.getConditionName());
		setConditionString(dto.getConditionString());
		setDescription(dto.getDescription());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}
	
	public void ejbPostCreate(VOIPConditionDTO dto)throws CreateException{
	}

	public void ejbPostCreate(Integer conditionID)throws CreateException{
	}
	
	public void ejbRemove()throws RemoveException{
	}
	public abstract void setDtLastmod(Timestamp dtCreate);
	public abstract Timestamp getDtLastmod();

	public abstract void setDtCreate(Timestamp dtCreate);
	public abstract Timestamp getDtCreate();

	public  abstract void setDescription(String description) ;
	public abstract String getDescription();

	public  abstract void setConditionString(String conditionString);
	public abstract String getConditionString();
	
	public abstract void setConditionName(String conditionName);
	public abstract String getConditionName();
	
	public abstract void setConditionID(Integer conditionID);
	public abstract Integer getConditionID();
	
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
	public int ejbUpdate(VOIPConditionDTO dto) {
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
