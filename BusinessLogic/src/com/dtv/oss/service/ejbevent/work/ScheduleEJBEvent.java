package com.dtv.oss.service.ejbevent.work;

import java.util.Collection;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class ScheduleEJBEvent extends AbstractEJBEvent {

    //排程用：客户产品需要排程的产品ID列表，
    private Collection cpIDList;
    //排程用：客户排程修改、取消的排程ID列表
    private Collection sheduleIDList;
    //排程用：排程记录要素（日期、原因、事件）
    private BatchJobDTO batchJobDTO;
    
    private Collection scheduleItemIDList;
    
    private boolean doPost =false;
  	
	public boolean isDoPost() {
		return doPost;
	}
	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
	}
    
	
	public BatchJobDTO getBatchJobDTO() {
		return batchJobDTO;
	}


	public void setBatchJobDTO(BatchJobDTO batchJobDTO) {
		this.batchJobDTO = batchJobDTO;
	}


	public Collection getCpIDList() {
		return cpIDList;
	}


	public void setCpIDList(Collection cpIDList) {
		this.cpIDList = cpIDList;
	}


	public Collection getSheduleIDList() {
		return sheduleIDList;
	}


	public void setSheduleIDList(Collection sheduleIDList) {
		this.sheduleIDList = sheduleIDList;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public Collection getScheduleItemIDList() {
		return scheduleItemIDList;
	}


	public void setScheduleItemIDList(Collection scheduleItemIDList) {
		this.scheduleItemIDList = scheduleItemIDList;
	}

}
