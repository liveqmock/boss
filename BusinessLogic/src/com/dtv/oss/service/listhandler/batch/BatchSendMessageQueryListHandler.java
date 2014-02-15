package com.dtv.oss.service.listhandler.batch;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.batch.GeHuaUploadCustBatchHeaderDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class BatchSendMessageQueryListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private GeHuaUploadCustBatchHeaderDAO dao = null;
	private static final Class clazz = BatchListHandler.class;
	final private String tableName = " GeHua_UploadCust_BatchHeader ";
	public BatchSendMessageQueryListHandler(int operatorID){
		this.dao=new GeHuaUploadCustBatchHeaderDAO();
		this.operatorID = operatorID;
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
		//导入客户信息的操作员的所属组织是当前操作员的所属组织或者其下属组织
		selectStatement.append(" where CreateOrgID in(select orgid from t_organization connect by prior orgid=parentorgid start with orgid = (select orgid from t_operator where operatorid = "+operatorID+"))");
		//任务类型
		if(dto.getSpareStr1()!=null)
			selectStatement.append(" and JobType='").append(dto.getSpareStr1()).append("' ");
		//SpareTime1:记录创建起始时间
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and CREATETIME>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:记录创建截止时间
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and CREATETIME<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
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
			selectStatement.append(" order by BATCHNO desc");
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
