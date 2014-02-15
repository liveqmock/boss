package com.dtv.oss.dto.wrap.work;

import java.io.Serializable;
import java.util.Collection;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.BatchJobItemDTO;
import com.dtv.oss.dto.CPCampaignMapDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProductDTO;

public class Schedule2CP2CCWrap implements Serializable {
	
	//客户产品信息记录
	private CustomerProductDTO custProductDTO=null;
	//客户信息
	private CustomerDTO customerDTO=null;
	
	//客户产品对应的优惠信息
	private CPCampaignMapDTO cpCamaignMapDTO=null;
	
	//客户产品对应的排程
	private BatchJobDTO batchJobDTO=null;
	
	//客户产品对应的排程
	private Collection batchJobItemDTOList=null;
	
	//客户产品对应的排程的日志列表
	private Collection batchJobProcessLogDTOList=null;
	
	
	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public BatchJobDTO getBatchJobDTO() {
		return batchJobDTO;
	}

	public void setBatchJobDTO(BatchJobDTO batchJobDTO) {
		this.batchJobDTO = batchJobDTO;
	}

	public Collection getBatchJobItemDTOList() {
		return batchJobItemDTOList;
	}

	public void setBatchJobItemDTOList(Collection batchJobItemDTOList) {
		this.batchJobItemDTOList = batchJobItemDTOList;
	}

	public Collection getBatchJobProcessLogDTOList() {
		return batchJobProcessLogDTOList;
	}

	public void setBatchJobProcessLogDTOList(Collection batchJobProcessLogDTOList) {
		this.batchJobProcessLogDTOList = batchJobProcessLogDTOList;
	}

	public CPCampaignMapDTO getCpCamaignMapDTO() {
		return cpCamaignMapDTO;
	}

	public void setCpCamaignMapDTO(CPCampaignMapDTO cpCamaignMapDTO) {
		this.cpCamaignMapDTO = cpCamaignMapDTO;
	}

	public CustomerProductDTO getCustProductDTO() {
		return custProductDTO;
	}

	public void setCustProductDTO(CustomerProductDTO custProductDTO) {
		this.custProductDTO = custProductDTO;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
