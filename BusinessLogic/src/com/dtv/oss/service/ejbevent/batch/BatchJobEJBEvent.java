package com.dtv.oss.service.ejbevent.batch;

import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
import com.dtv.oss.dto.BatchJobDTO;

public class BatchJobEJBEvent extends AbstractEJBEvent {
	//批处理DTO，用于创建和修改用
	private BatchJobDTO batchJobDTO=null;
	//设定发送次数
	private int scheduleSendNumber=0;
	//设定发送时间间隔
	private int scheduleSendTimeInterval=0;
	
	public BatchJobDTO getBatchJobDTO() {
		return batchJobDTO;
	}
	public void setBatchJobDTO(BatchJobDTO batchJobDTO) {
		this.batchJobDTO = batchJobDTO;
	}
	
	public int getScheduleSendNumber() {
		return scheduleSendNumber;
	}
	public void setScheduleSendNumber(int scheduleSendNumber) {
		this.scheduleSendNumber = scheduleSendNumber;
	}
	public int getScheduleSendTimeInterval() {
		return scheduleSendTimeInterval;
	}
	public void setScheduleSendTimeInterval(int scheduleSendTimeInterval) {
		this.scheduleSendTimeInterval = scheduleSendTimeInterval;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
}
