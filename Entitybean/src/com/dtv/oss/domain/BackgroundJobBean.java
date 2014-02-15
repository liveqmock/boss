package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BackgroundJobDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BackgroundJobBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(BackgroundJobDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		//setId(dto.getId());
		setAction(dto.getAction());
		setActionType(dto.getActionType());
		setJobName(dto.getJobName());
		setJobGroup(dto.getJobGroup());
		setDescr(dto.getDescr());
		setJobClassName(dto.getJobClassName());
		setQueryCriteriaID(dto.getQueryCriteriaID());
		setEvent(dto.getEvent());
		setStartTime(dto.getStartTime());
		setSpanTime(dto.getSpanTime());
		setRepeatTime(dto.getRepeatTime());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setCreateOperatorID(dto.getCreateOperatorID());
		setComments(dto.getComments());
		setBeginTime(dto.getBeginTime());
		setEndTime(dto.getEndTime());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BackgroundJobDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setAction(int action);

	public abstract void setCreateOperatorID(int createOperatorID);

	public abstract void setComments(String comments);

	public abstract void setActionType(int actionType);

	public abstract void setJobName(java.lang.String jobName);

	public abstract void setJobGroup(java.lang.String jobGroup);

	public abstract void setDescr(java.lang.String descr);

	public abstract void setJobClassName(java.lang.String jobClassName);

	public abstract void setQueryCriteriaID(int queryCriteriaID);

	public abstract void setEvent(int event);

	public abstract void setStartTime(java.sql.Timestamp startTime);

	public abstract void setSpanTime(long spanTime);

	public abstract void setRepeatTime(int repeatTime);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setBeginTime(java.sql.Timestamp beginTime);

	public abstract void setEndTime(java.sql.Timestamp endTime);

	public abstract java.lang.Integer getId();

	public abstract int getAction();

	public abstract int getActionType();

	public abstract java.lang.String getJobName();

	public abstract java.lang.String getJobGroup();

	public abstract java.lang.String getDescr();

	public abstract java.lang.String getJobClassName();

	public abstract int getQueryCriteriaID();

	public abstract int getEvent();

	public abstract int getCreateOperatorID();

	public abstract String getComments();

	public abstract java.sql.Timestamp getStartTime();

	public abstract long getSpanTime();

	public abstract int getRepeatTime();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.sql.Timestamp getBeginTime();

	public abstract java.sql.Timestamp getEndTime();

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

	public int ejbUpdate(BackgroundJobDTO dto) {
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