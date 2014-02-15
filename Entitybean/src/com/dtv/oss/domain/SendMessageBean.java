package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.SendMessageDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class SendMessageBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer messageId)
			throws CreateException {
		// setMessageId(messageId);
		return null;
	}

	public java.lang.Integer ejbCreate(SendMessageDTO dto)
			throws CreateException {
		setOrgId(dto.getOrgId());
		setOperatorId(dto.getOperatorId());
		setCreateTime(dto.getCreateTime());
		setProcessTime(dto.getProcessTime());
		setSendType(dto.getSendType());
		setSourceType(dto.getSourceType());
		setModuleName(dto.getModuleName());
		setCustomerId(dto.getCustomerId());
		setServiceAccountId(dto.getServiceAccountId());
		setContent(dto.getContent());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer messageId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(SendMessageDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setMessageId(java.lang.Integer messageId);

	public abstract void setOrgId(int orgId);

	public abstract void setOperatorId(int operatorId);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setProcessTime(java.sql.Timestamp processTime);

	public abstract void setSendType(java.lang.String sendType);

	public abstract void setSourceType(java.lang.String sourceType);

	public abstract void setModuleName(java.lang.String moduleName);

	public abstract void setCustomerId(int customerId);

	public abstract void setServiceAccountId(int serviceAccountId);

	public abstract void setContent(java.lang.String content);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getMessageId();

	public abstract int getOrgId();

	public abstract int getOperatorId();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract java.sql.Timestamp getProcessTime();

	public abstract java.lang.String getSendType();

	public abstract java.lang.String getSourceType();

	public abstract java.lang.String getModuleName();

	public abstract int getCustomerId();

	public abstract int getServiceAccountId();

	public abstract java.lang.String getContent();

	public abstract java.lang.String getStatus();

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

	public int ejbUpdate(SendMessageDTO dto) {
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