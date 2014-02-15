package com.dtv.oss.dto.wrap;

import java.io.Serializable;
import java.sql.Timestamp;
import com.dtv.oss.dto.SystemEventDTO;

public class VOIPEventWrap implements Serializable {
	private SystemEventDTO sysEventDTO;
	private int startNO;
	private int endNO;
	private Timestamp startTime;
	private Timestamp endTime;
	private String serviceCode;
	
	/**
	 * @return Returns the endNO.
	 */
	public int getEndNO() {
		return endNO;
	}
	/**
	 * @param endNO The endNO to set.
	 */
	public void setEndNO(int endNO) {
		this.endNO = endNO;
	}
	/**
	 * @return Returns the endTime.
	 */
	public Timestamp getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime The endTime to set.
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return Returns the startNO.
	 */
	public int getStartNO() {
		return startNO;
	}
	/**
	 * @param startNO The startNO to set.
	 */
	public void setStartNO(int startNO) {
		this.startNO = startNO;
	}
	/**
	 * @return Returns the startTime.
	 */
	public Timestamp getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime The startTime to set.
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public VOIPEventWrap() {
		super();
		sysEventDTO=new SystemEventDTO();
	}
	
	/**
	 * @return Returns the sysEventDTO.
	 */
	public SystemEventDTO getSysEventDTO() {
		return sysEventDTO;
	}
	/**
	 * @param sysEventDTO The sysEventDTO to set.
	 */
	public void setSysEventDTO(SystemEventDTO sysEventDTO) {
		this.sysEventDTO = sysEventDTO;
	}
	/**
	 * @return Returns the serviceCode.
	 */
	public String getServiceCode() {
		return serviceCode;
	}
	/**
	 * @param serviceCode The serviceCode to set.
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
}
