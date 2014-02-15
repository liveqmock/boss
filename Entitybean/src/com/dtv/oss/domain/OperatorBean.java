package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class OperatorBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer operatorID)
			throws CreateException {
		//setOperatorID(operatorID);
		return null;
	}

	public java.lang.Integer ejbCreate(OperatorDTO dto) throws CreateException {
		/** @todo Complete this method */
		setStatus(dto.getStatus());
		//setOperatorID(dto.getOperatorID());
		setOperatorName(dto.getOperatorName());
		setOperatorDesc(dto.getOperatorDesc());
		setLoginID(dto.getLoginID());
		setLoginPwd(dto.getLoginPwd());
		setOrgID(dto.getOrgID());
		setInternalUserFlag(dto.getInternalUserFlag()); 
		setLevelID(dto.getLevelID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer operatorID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(OperatorDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setOperatorID(java.lang.Integer operatorID);

	public abstract void setOperatorName(java.lang.String operatorName);
	
	public abstract void setInternalUserFlag(java.lang.String internalUserFlag);
	
	public abstract java.lang.String getInternalUserFlag();

	public abstract void setOperatorDesc(java.lang.String operatorDesc);

	public abstract void setLoginID(java.lang.String loginID);

	public abstract void setLoginPwd(java.lang.String loginPwd);

	public abstract void setOrgID(int orgID);

	 

	public abstract void setLevelID(int levelID);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setOpGroup_R(java.util.Collection opGroup_R);

	public abstract java.lang.Integer getOperatorID();

	public abstract java.lang.String getOperatorName();

	public abstract java.lang.String getOperatorDesc();

	public abstract java.lang.String getLoginID();

	public abstract java.lang.String getLoginPwd();

	public abstract int getOrgID();

	 

	public abstract int getLevelID();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.util.Collection getOpGroup_R();

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

	public int ejbUpdate(OperatorDTO dto) {
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