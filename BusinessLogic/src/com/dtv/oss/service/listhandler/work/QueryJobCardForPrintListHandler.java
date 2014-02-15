/*
 * Created on 2005-9-28
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.work.JobCardDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonUtility;

public class QueryJobCardForPrintListHandler extends ValueListHandler {
	private JobCardDAO dao = null;

	final private String tableName = "T_JobCard jc";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CommonQueryConditionDTO dto = null;

	public QueryJobCardForPrintListHandler() {
		this.dao = new JobCardDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		
		
		LogUtility.log(JobCardListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(JobCardListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		//ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}

	/**
	 * �����ѯ���� ������ jobcardid 
	 */
	 
	private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException{
		 
		StringBuffer begin = new StringBuffer();
		begin.append("select jc.* from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		
		if(dto.getNo()!=null&&dto.getNo().indexOf(";") > -1){			
		    makeSQLByIntFieldIn("jc.jobCardId", dto.getNo(), selectStatement);		    
		}else{
			makeSQLByStringField("jc.jobCardId", dto.getNo(), selectStatement);
		}
		selectStatement.append(" order by jc.JobCardID");
		//selectStatement.append(" order by jc.JobCardID desc");
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		StringBuffer querySql =  begin.append(selectStatement);
		//System.out.println("____querySql="+querySql);
		setRecordDataQueryBuffer(querySql);
	}
	
}