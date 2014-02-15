package com.dtv.oss.dto.wrap;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.dtv.oss.dto.CAQueueDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

public class CAEventWrap implements Serializable {
	private CommonQueryConditionDTO caEventDTO;
	private CAQueueDTO queueDTO; 
	private ArrayList caRecvList;
	private ArrayList caSentList;
	private ArrayList caQueueList;
	
	private int startNO;
	private int endNO;
	private Timestamp startTime;
	private Timestamp endTime;
	
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
	public CAEventWrap() {
		super();
		caEventDTO=new CommonQueryConditionDTO();
		queueDTO=new CAQueueDTO(); 
		caRecvList= new ArrayList();
		caSentList= new ArrayList();
		caQueueList= new ArrayList();
	}
	/**
	 * @return Returns the caEventDTO.
	 */
	public CommonQueryConditionDTO getCaEventDTO() {
		return caEventDTO;
	}
	/**
	 * @param caEventDTO The caEventDTO to set.
	 */
	public void setCaEventDTO(CommonQueryConditionDTO caEventDTO) {
		this.caEventDTO = caEventDTO;
	}
	/**
	 * @return Returns the sysEventDTO.
	 */
	 
	/**
	 * @return Returns the caQueueList.
	 */
	public ArrayList getCaQueueList() {
		return caQueueList;
	}
	/**
	 * @param caQueueList The caQueueList to set.
	 */
	public void setCaQueueList(ArrayList caQueueList) {
		this.caQueueList = caQueueList;
	}
	/**
	 * @return Returns the caRecvList.
	 */
	public ArrayList getCaRecvList() {
		return caRecvList;
	}
	/**
	 * @param caRecvList The caRecvList to set.
	 */
	public void setCaRecvList(ArrayList caRecvList) {
		this.caRecvList = caRecvList;
	}
	/**
	 * @return Returns the caSentList.
	 */
	public ArrayList getCaSentList() {
		return caSentList;
	}
	/**
	 * @param caSentList The caSentList to set.
	 */
	public void setCaSentList(ArrayList caSentList) {
		this.caSentList = caSentList;
	}




	/**
	 * @return Returns the queueDTO.
	 */
	public CAQueueDTO getQueueDTO() {
		return queueDTO;
	}
	/**
	 * @param queueDTO The queueDTO to set.
	 */
	public void setQueueDTO(CAQueueDTO queueDTO) {
		this.queueDTO = queueDTO;
	}
}
