package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.work.ScheduleDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CommonUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * 查找客户业务帐户下产品的排程
 * author     ：zhouxushun 
 * date       : 2005-12-30
 * description:
 * @author 250713z
 *
 */
public class ScheduleListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private ScheduleDAO dao = null;
	private static final Class clazz = ScheduleListHandler.class;
	final private String tableName = " T_BATCHJOB batch,T_BATCHJOBITEM item ";
	
	public ScheduleListHandler(){
		dao=new ScheduleDAO();
	}
	
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(JobCardListHandler.class, LogLevel.DEBUG,"the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);

	}

	/**
	 * 
	 * @param dto2
	 */
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {
		

		StringBuffer begin = new StringBuffer();
		
		begin.append(" select distinct batch.*,"+CommonUtility.getSelectExpress4BatchJobItemInfo("item.")
				   +" from " + tableName);
	
		
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 and batch.BATCHID=item.BATCHID  ");
		selectStatement.append(" and ( batch.JOBTYPE='"+CommonConstDefinition.BATCH_JOB_TYPE_PC +"' or ");
		selectStatement.append("  batch.JOBTYPE='"+CommonConstDefinition.BATCH_JOB_TYPE_PR +"' or ");
		selectStatement.append("  batch.JOBTYPE='"+CommonConstDefinition.BATCH_JOB_TYPE_PS +"' ) ");
		
		//只查询单个客户创建的排程 .
		makeSQLByStringField("refertype",CommonConstDefinition.BATCH_JOB_REFER_TYPE_C,selectStatement);
		//排程批处理ID
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			makeSQLByIntField("batch.BatchID", Integer.valueOf(dto.getSpareStr1()).intValue(), selectStatement);
				
		//排程任务状态
		makeSQLByStringField("batch.Status",dto.getSpareStr7(),selectStatement);
		//事件原因
		makeSQLByStringField("batch.ReasonCode",dto.getSpareStr4(),selectStatement);
		//事件类型
		makeSQLByStringField("batch.JobType",dto.getSpareStr3(),selectStatement);
		//客户ID
		makeSQLByIntField("CUSTOMERID",Integer.parseInt(dto.getSpareStr5()),selectStatement);

		//排程执行时间
		if (dto.getSpareTime1()!=null)
			selectStatement.append(" and batch.ScheduleTime>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		if (dto.getSpareTime2() != null)
			selectStatement.append(" and batch.ScheduleTime<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		//排程创建时间
		if (dto.getSpareTime3()!=null)
			selectStatement.append(" and batch.CreateTime>=to_timestamp('").append(dto.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		if (dto.getSpareTime4() != null)
			selectStatement.append(" and batch.CreateTime<=to_timestamp('").append(dto.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		appendOrderByString(selectStatement);
	    setRecordCountQueryTable(tableName);
	    setRecordCountSuffixBuffer(selectStatement);
	    
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		 
		selectStatement.append(" order by batch.batchid desc,item.itemno desc");

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
