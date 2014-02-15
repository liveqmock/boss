package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CAProductDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CAProductBean implements EntityBean {
	EntityContext entityContext;

	public CAProductPK ejbCreate(int productId, int conditionId)
			throws CreateException {
		// setProductId(productId);
		//  setConditionId(conditionId);
		return null;
	}

	public CAProductPK ejbCreate(CAProductDTO dto) throws CreateException {
		setProductId(dto.getProductId());
		setConditionId(dto.getConditionId());
		setEntitlement(dto.getEntitlement());
		setDescription(dto.getDescription());
		setOpiID(dto.getOpiID());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
       return null;
	}

	public void ejbPostCreate(int productId, int conditionId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CAProductDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setProductId(int productId);
	
	 public abstract void setOpiID(int opiID);

	 public abstract int getOpiID();


	public abstract void setConditionId(int conditionId);

	public abstract void setEntitlement(java.lang.String entitlement);

	public abstract void setDescription(java.lang.String description);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getProductId();

	public abstract int getConditionId();

	public abstract java.lang.String getEntitlement();

	public abstract java.lang.String getDescription();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

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

	public int ejbUpdate(CAProductDTO dto) {
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