package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.VODInterfaceHostDTO;
 
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class VodHostBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer hostID) throws CreateException {

       // setHostID(hostID);
        return null;
    }

    public Integer ejbCreate(VODInterfaceHostDTO dto) throws CreateException {
    	setHostName(dto.getHostName());
    	setVodType(dto.getVodType());
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
    	setLastRunTime(dto.getLastRunTime());
    	setLastStopTime(dto.getLastStopTime());
    	setStatus(dto.getStatus());
    	
    	setDtCreate(new Timestamp(System.currentTimeMillis()));
        setDtLastmod(new Timestamp(System.currentTimeMillis()));
        return null;
    }

     

    

    public void ejbPostCreate(Integer hostID) throws CreateException {
    }

    

    public void ejbPostCreate(VODInterfaceHostDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setHostID(Integer hostID);

    public abstract Integer getHostID();

    public abstract void setHostName(String hostName);

    public abstract String getHostName();

    public abstract void setVodType(String vodType);

    public abstract String getVodType();

    public abstract void setIp(String ip);

    public abstract String getIp();

    public abstract void setPort(String port);

    public abstract String getPort();

    public abstract void setIpBack(String ipBack);

    public abstract String getIpBack();

    public abstract void setPortBack(String portBack);

    public abstract String getPortBack();

    public abstract void setProtocolType(String protocolType);

    public abstract String getProtocolType();

    public abstract void setLoopSize(int loopSize);

    public abstract int getLoopSize();

    public abstract void setLoopInterval(int loopInterval);

    public abstract int getLoopInterval();

    public abstract void setTryTime(int tryTime);

    public abstract int getTryTime();

    public abstract void setValidStartTime(Timestamp validStartTime);

    public abstract Timestamp getValidStartTime();

    public abstract void setValidEndTime(Timestamp validEndTime);

    public abstract Timestamp getValidEndTime();

    public abstract void setLastRunTime(Timestamp lastRunTime);

    public abstract Timestamp getLastRunTime();

    public abstract void setLastStopTime(Timestamp lastStopTime);

    public abstract Timestamp getLastStopTime();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();

    public void ejbLoad() {
    }

    public void ejbStore() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void unsetEntityContext() {
        this.entityContext = null;
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }
    public int ejbUpdate(VODInterfaceHostDTO dto) {
    	/** @todo Complete this method */
    	if (dto.getDtLastMod() == null) {
    		return -1;
    	}

    	if (!dto.getDtLastMod().equals(getDtLastmod())) {

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
