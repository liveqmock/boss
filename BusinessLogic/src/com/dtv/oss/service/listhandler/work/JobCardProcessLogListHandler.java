/*
 * Created on 2005-9-28
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.work.JobCardProcessLogDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
 

public class JobCardProcessLogListHandler extends ValueListHandler {
	private JobCardProcessLogDAO dao = null;

	 

	final private String tableName = "T_jobCardProcess jcp";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private JobCardProcessDTO dto = null;

	public JobCardProcessLogListHandler() {
		this.dao = new JobCardProcessLogDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(JobCardProcessLogListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof JobCardProcessDTO)
			this.dto = (JobCardProcessDTO) dto1;
		else {
			LogUtility.log(JobCardProcessLogListHandler.class, LogLevel.DEBUG,
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
	 
	private void constructSelectQueryString(JobCardProcessDTO dto) {
		 
		StringBuffer begin = new StringBuffer();
		begin.append("select jcp.* from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		 
		
		 
		/*
		 * Search Condition:
		 * 
		 */
		makeSQLByIntField("jcp.jcid", dto.getJcId(), selectStatement); 
		 

		selectStatement.append(" order by jcp.seqno desc");   
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