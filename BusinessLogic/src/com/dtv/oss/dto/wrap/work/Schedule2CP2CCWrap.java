package com.dtv.oss.dto.wrap.work;

import java.io.Serializable;
import java.util.Collection;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.BatchJobItemDTO;
import com.dtv.oss.dto.CPCampaignMapDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProductDTO;

public class Schedule2CP2CCWrap implements Serializable {
	
	//�ͻ���Ʒ��Ϣ��¼
	private CustomerProductDTO custProductDTO=null;
	//�ͻ���Ϣ
	private CustomerDTO customerDTO=null;
	
	//�ͻ���Ʒ��Ӧ���Ż���Ϣ
	private CPCampaignMapDTO cpCamaignMapDTO=null;
	
	//�ͻ���Ʒ��Ӧ���ų�
	private BatchJobDTO batchJobDTO=null;
	
	//�ͻ���Ʒ��Ӧ���ų�
	private Collection batchJobItemDTOList=null;
	
	//�ͻ���Ʒ��Ӧ���ų̵���־�б�
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
