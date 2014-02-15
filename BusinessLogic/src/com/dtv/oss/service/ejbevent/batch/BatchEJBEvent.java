package com.dtv.oss.service.ejbevent.batch;

import java.util.Collection;

import com.dtv.oss.dto.QueryDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class BatchEJBEvent extends AbstractEJBEvent {

	//������DTO�����ڴ������޸���
	private QueryDTO queryDTO=null;
	//������IDList������ȡ����ִ��
	private Collection queryIDList=null;
	//��������IDList�������޸Ľ������¼״̬
	private Collection resultItemIDList=null;
	
	
	public QueryDTO getQueryDTO() {
		return queryDTO;
	}


	public void setQueryDTO(QueryDTO queryDTO) {
		this.queryDTO = queryDTO;
	}


	public Collection getQueryIDList() {
		return queryIDList;
	}


	public void setQueryIDList(Collection queryIDList) {
		this.queryIDList = queryIDList;
	}


	public Collection getResultItemIDList() {
		return resultItemIDList;
	}


	public void setResultItemIDList(Collection resultItemIDList) {
		this.resultItemIDList = resultItemIDList;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
