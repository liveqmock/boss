package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.VODInterfaceHostDTO;

public interface VodHost extends EJBLocalObject {
    public void setIp(String ip);

    public String getIp();

    public void setPort(String port);

    public String getPort();

    public void setIpBack(String ipBack);

    public String getIpBack();

    public void setPortBack(String portBack);

    public String getPortBack();

    public void setProtocolType(String protocolType);

    public String getProtocolType();

    public void setLoopSize(int loopSize);

    public int getLoopSize();

    public void setLoopInterval(int loopInterval);

    public int getLoopInterval();

    public void setTryTime(int tryTime);

    public int getTryTime();

    public void setValidStartTime(Timestamp validStartTime);

    public Timestamp getValidStartTime();

    public void setValidEndTime(Timestamp validEndTime);

    public Timestamp getValidEndTime();

    public void setLastRunTime(Timestamp lastRunTime);

    public Timestamp getLastRunTime();

    public void setLastStopTime(Timestamp lastStopTime);

    public Timestamp getLastStopTime();

    

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getHostID();

    public void setHostName(String hostName);

    public String getHostName();

    public void setVodType(String vodType);

    public String getVodType();
    public int ejbUpdate(VODInterfaceHostDTO dto);
}
