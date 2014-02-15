package com.dtv.oss.service.ejbevent.work;

import java.util.Collection;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class ScheduleEJBEvent extends AbstractEJBEvent {

    //�ų��ã��ͻ���Ʒ��Ҫ�ų̵Ĳ�ƷID�б�
    private Collection cpIDList;
    //�ų��ã��ͻ��ų��޸ġ�ȡ�����ų�ID�б�
    private Collection sheduleIDList;
    //�ų��ã��ų̼�¼Ҫ�أ����ڡ�ԭ���¼���
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
