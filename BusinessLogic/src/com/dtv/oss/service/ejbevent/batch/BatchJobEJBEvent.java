package com.dtv.oss.service.ejbevent.batch;

import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
import com.dtv.oss.dto.BatchJobDTO;

public class BatchJobEJBEvent extends AbstractEJBEvent {
	//������DTO�����ڴ������޸���
	private BatchJobDTO batchJobDTO=null;
	//�趨���ʹ���
	private int scheduleSendNumber=0;
	//�趨����ʱ����
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
