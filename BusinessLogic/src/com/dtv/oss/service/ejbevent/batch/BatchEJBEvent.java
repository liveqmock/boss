package com.dtv.oss.service.ejbevent.batch;

import java.util.Collection;

import com.dtv.oss.dto.QueryDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class BatchEJBEvent extends AbstractEJBEvent {

	//批处理DTO，用于创建和修改用
	private QueryDTO queryDTO=null;
	//批处理IDList，用于取消、执行
	private Collection queryIDList=null;
	//批处理结果IDList，用于修改结果集记录状态
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
