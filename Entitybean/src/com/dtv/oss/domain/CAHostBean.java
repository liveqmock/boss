package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CAHostDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CAHostBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer hostID)
			throws CreateException {
		//setHostID(hostID);
		return null;
	}

	public java.lang.Integer ejbCreate(CAHostDTO dto) throws CreateException {
		setHostName(dto.getHostName());
		setCaType(dto.getCaType());
		setIp(dto.getIp());
		setPort(dto.getPort());
		setIpBack(dto.getIpBack());
		setPortBack(dto.getPortBack());
		setProtocolType(dto.getProtocolType());
		setLoopSize(dto.getLoopSize());
		setLoopInterval(dto.getLoopInterval());
		setTryTime(dto.getTryTime());
		setValidStartTime(dto.getValidStartTime());
		setValidEndTime(dto.getValidEndTime());
		setLastRuntime(dto.getLastRunTime());
		setLastStopTime(dto.getLastStopTime());
		setStatus(dto.getStatus());
		setDescription(dto.getDescription());
		setOperatorID(dto.getOperatorID());
		setMd5key(dto.getMd5key());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer hostID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CAHostDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setHostID(java.lang.Integer hostID);

	public abstract void setHostName(java.lang.String hostName);

	public abstract void setCaType(java.lang.String caType);

	public abstract void setIp(java.lang.String ip);

	public abstract void setPort(int port);

	public abstract void setIpBack(java.lang.String ipBack);

	public abstract void setPortBack(int portBack);

	public abstract void setProtocolType(java.lang.String protocolType);

	public abstract void setLoopSize(int loopSize);

	public abstract void setLoopInterval(int loopInterval);

	public abstract void setTryTime(int tryTime);
 
	public abstract void setValidStartTime(java.sql.Timestamp validStartTime);

	public abstract void setValidEndTime(java.sql.Timestamp validEndTime);

	public abstract void setLastRuntime(java.sql.Timestamp lastRuntime);

	public abstract void setLastStopTime(java.sql.Timestamp lastStopTime);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDescription(java.lang.String description);

	public abstract void setOperatorID(int operatorID);

	public abstract void setMd5key(java.lang.String md5key);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getHostID();

	public abstract java.lang.String getHostName();

	public abstract java.lang.String getCaType();

	public abstract java.lang.String getIp();

	public abstract int getPort();

	public abstract java.lang.String getIpBack();

	public abstract int getPortBack();

	public abstract java.lang.String getProtocolType();

	public abstract int getLoopSize();

	public abstract int getLoopInterval();

	public abstract int getTryTime();

	public abstract java.sql.Timestamp getValidStartTime();

	public abstract java.sql.Timestamp getValidEndTime();

	public abstract java.sql.Timestamp getLastRuntime();

	public abstract java.sql.Timestamp getLastStopTime();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getDescription();

	public abstract int getOperatorID();

	public abstract java.lang.String getMd5key();

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

	public int ejbUpdate(CAHostDTO dto) {
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