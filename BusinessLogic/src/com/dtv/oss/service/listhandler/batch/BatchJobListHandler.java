package com.dtv.oss.service.listhandler.batch;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.batch.BatchJobDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class BatchJobListHandler extends ValueListHandler{
	private CommonQueryConditionDTO dto = null;
	private BatchJobDAO dao = null;
	private static final Class clazz = BatchJobListHandler.class;
	final private String tableName = " T_BatchJob ";

	public BatchJobListHandler(){
		this.dao=new BatchJobDAO();
	}
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO)dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
	}
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {
		StringBuffer begin = new StringBuffer();
		StringBuffer selectStatement = new StringBuffer(128);
		begin.append("select * from " + tableName);
		selectStatement.append(" where 1=1 ");

		
		makeSQLByStringFieldIn("refertype",CommonConstDefinition.BATCH_JOB_REFER_TYPE_Q+"','"+CommonConstDefinition.BATCH_JOB_REFER_TYPE_U,selectStatement);
		//SpareStr1:任务
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			makeSQLByIntField("BatchID",Integer.valueOf(dto.getSpareStr1()).intValue(),selectStatement);
		//SpareStr2:任务名称
		if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			selectStatement.append(" and Name like '%" + dto.getSpareStr2() +"%'");
		//SpareStr3:任务状态
		makeSQLByStringField("status",dto.getSpareStr3(),selectStatement);
		//SpareStr4:任务类型
		makeSQLByStringField("JobType",dto.getSpareStr4(),selectStatement);
		//SpareStr5:设定执行方式
		makeSQLByStringField("ScheduleType",dto.getSpareStr5(),selectStatement);
		//SpareStr6:创建人
		if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6()))){
			selectStatement.append(" and createopid in (select operatorid from t_operator where loginid like " +
					"'" + dto.getSpareStr6() + "%' and status='" + CommonConstDefinition.GENERALSTATUS_VALIDATE +"') ");
		}
			//makeSQLByIntField("createopid",Integer.valueOf(dto.getSpareStr6()).intValue(),selectStatement);
		//SpareTime1:创建起始时间
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and CreateTime>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:记录创建截止时间
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and CreateTime<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime3:预定执行起始时间
		if(dto.getSpareTime3()!=null)
			selectStatement.append(" and ScheduleTime>=to_timestamp('").append(dto.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime4:预定执行截止时间
		if(dto.getSpareTime4()!=null)
			selectStatement.append(" and ScheduleTime<=to_timestamp('").append(dto.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		appendOrderByString(selectStatement);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by BatchID desc");
		else {
			selectStatement.append(" order by " + dto.getOrderField()
					+ orderByAscend);
		}
		orderByAscend = null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
