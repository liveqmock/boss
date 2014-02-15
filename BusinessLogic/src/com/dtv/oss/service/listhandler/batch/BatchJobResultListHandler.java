package com.dtv.oss.service.listhandler.batch;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.batch.BatchJobResultDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class BatchJobResultListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private BatchJobResultDAO dao = null;
	private static final Class clazz = BatchJobResultListHandler.class;
	final private String tableName = " T_BatchJobItem ";

	public BatchJobResultListHandler(){
		this.dao=new BatchJobResultDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");

		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
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

		//SpareStr1:记录ID
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			makeSQLByIntField("BatchID",Integer.valueOf(dto.getSpareStr1()).intValue(),selectStatement);
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
			selectStatement.append(" order by ItemNO desc");
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
