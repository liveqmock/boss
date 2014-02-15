/*
 * Created on 2005-9-28
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.groupcustomer;

import com.dtv.oss.dto.ContractProcessLogDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.groupcustomer.ContractProcLogDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
 

public class ContractProcessLogListHandler extends ValueListHandler {
	private ContractProcLogDAO dao = null;

	 

	final private String tableName = "t_contractprocesslog c";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private ContractProcessLogDTO dto = null;

	public ContractProcessLogListHandler() {
		this.dao = new ContractProcLogDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(ContractProcessLogListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof ContractProcessLogDTO)
			this.dto = (ContractProcessLogDTO) dto1;
		else {
			LogUtility.log(ContractProcessLogListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// added by Horace 2004-11-3
	//	if (fillDTOWithPrivilege(dto) == false)
	//		return;
		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		//ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}

	/**
	 * �����ѯ���� 1. ������ jobcardid 2.�û����� CustomerType 3.�û�֤�� custid
	 *  4.״̬ status 5.����ʱ�� actionTime 6.�������� District
	 */
	 
	private void constructSelectQueryString(ContractProcessLogDTO dto) {
		 
		StringBuffer begin = new StringBuffer();
		begin.append("select c.* from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		 
		
		 
		/*
		 * Search Condition:
		 * 
		 */
		makeSQLByStringField("c.contractno", dto.getContractNo(), selectStatement); 
		 

		selectStatement.append(" order by c.seqno desc");   
		//appendOrderByString(end);
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		  
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	/**
	 * Use fillDTOWithPrivilege() method to filter by operator's privilege
	 * 
	 * @return
	
	private boolean fillDTOWithPrivilege(CommonQueryConditionDTO dto)
			throws ListHandlerException {

		switch (getBelongTo()) {
		case OPERATOR_BELONG_TO_GENERAL_COMPANY:
			return true;
		case OPERATOR_BELONG_TO_SUBCOMPANY:
			dto.setFiliale(getOperator().getOrgID());
			return true;
		case OPERATOR_BELONG_TO_STREET_STATION:
			dto.setStreet(getOperator().getOrgID());
			return true;
		default:
			return false;
		}
	}
	 */

	
	 
	 
}