/**
 * 
 */
package com.dtv.oss.service.ejbevent.batch;
import java.util.Collection;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.custom.GeHuaUploadCustBatchHeaderDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

/**
 * @author 240910y
 *
 */
public class GehuaBatchMessageEJBEvent extends AbstractEJBEvent {
	//设定发送次数
	private int scheduleSendNumber=0;
	//设定发送时间间隔
	private int scheduleSendTimeInterval=0;
	/**
	 * 
	 */
	public GehuaBatchMessageEJBEvent () {
		// TODO Auto-generated constructor stub
	}
	private  Collection  custInfoCol;
	
	private GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO;
	
	private BatchJobDTO batchJobDTO;
	/**
	 * @return the custInfoCol
	 */
	public Collection getCustInfoCol() {
		return custInfoCol;
	}
	/**
	 * @param custInfoCol the custInfoCol to set
	 */
	public void setCustInfoCol(Collection custInfoCol) {
		this.custInfoCol = custInfoCol;
	}
	/**
	 * @return the custBatchHeaderDTO
	 */
	public GeHuaUploadCustBatchHeaderDTO getCustBatchHeaderDTO() {
		return custBatchHeaderDTO;
	}
	/**
	 * @param custBatchHeaderDTO the custBatchHeaderDTO to set
	 */
	public void setCustBatchHeaderDTO(
			GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO) {
		this.custBatchHeaderDTO = custBatchHeaderDTO;
	}
	/**
	 * @return the batchJobDTO
	 */
	public BatchJobDTO getBatchJobDTO() {
		return batchJobDTO;
	}
	/**
	 * @param batchJobDTO the batchJobDTO to set
	 */
	public void setBatchJobDTO(BatchJobDTO batchJobDTO) {
		this.batchJobDTO = batchJobDTO;
	}
	/**
	 * @return the scheduleSendNumber
	 */
	public int getScheduleSendNumber() {
		return scheduleSendNumber;
	}
	/**
	 * @param scheduleSendNumber the scheduleSendNumber to set
	 */
	public void setScheduleSendNumber(int scheduleSendNumber) {
		this.scheduleSendNumber = scheduleSendNumber;
	}
	/**
	 * @return the scheduleSendTimeInterval
	 */
	public int getScheduleSendTimeInterval() {
		return scheduleSendTimeInterval;
	}
	/**
	 * @param scheduleSendTimeInterval the scheduleSendTimeInterval to set
	 */
	public void setScheduleSendTimeInterval(int scheduleSendTimeInterval) {
		this.scheduleSendTimeInterval = scheduleSendTimeInterval;
	}

}
