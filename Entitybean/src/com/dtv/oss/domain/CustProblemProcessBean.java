package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustProblemProcessDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustProblemProcessBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(CustProblemProcessDTO dto)
			throws CreateException {
		setCustProblemId(dto.getCustProblemId());
		setCurrentorgId(dto.getCurrentorgId());
		setCurrentOperatorId(dto.getCurrentOperatorId());
		setAction(dto.getAction());
		setCreateDate(dto.getCreateDate());
		setWorkResult(dto.getWorkResult());
		setWorkResultReason(dto.getWorkResultReason());
		setNextOrgId(dto.getNextOrgId());
		setMemo(dto.getMemo());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustProblemProcessDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setMemo(String memo);
	
	public abstract String getMemo();
	
	public abstract void setId(java.lang.Integer id);

	public abstract void setCustProblemId(int custProblemId);

	public abstract void setCurrentorgId(int currentorgId);

	public abstract void setCurrentOperatorId(int currentOperatorId);

	public abstract void setAction(java.lang.String action);

	public abstract void setCreateDate(java.sql.Timestamp createDate);

	public abstract void setWorkResult(java.lang.String workResult);

	public abstract void setWorkResultReason(java.lang.String workResultReason);

	public abstract void setNextOrgId(int nextOrgId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getCustProblemId();

	public abstract int getCurrentorgId();

	public abstract int getCurrentOperatorId();

	public abstract java.lang.String getAction();

	public abstract java.sql.Timestamp getCreateDate();

	public abstract java.lang.String getWorkResult();

	public abstract java.lang.String getWorkResultReason();

	public abstract int getNextOrgId();

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

	public int ejbUpdate(CustProblemProcessDTO dto) {
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