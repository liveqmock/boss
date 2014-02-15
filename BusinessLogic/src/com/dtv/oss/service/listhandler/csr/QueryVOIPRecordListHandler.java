package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.VOIPRecordDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.VOIPRecordDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class QueryVOIPRecordListHandler extends ValueListHandler {
	private VOIPRecordDTO dto=null;
	private VOIPRecordDAO dao=null;
	private final String tableName=" t_accumulateaccount a, c_bill_result b ";
	
	public QueryVOIPRecordListHandler(){
		dao=new VOIPRecordDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
		"in setCriteria begin setCriteria...");
		if (dto1 instanceof VOIPRecordDTO)
			this.dto = (VOIPRecordDTO) dto1;
		else {
			LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
		
	}

	private void constructSelectQueryString(VOIPRecordDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select a.aa_no AA_NO,a.date1 DATE1,a.date2 DATE2,a.value VALUE,a.AcctItemTypeID AcctItemTypeID,b.point_origin POINT_ORIGIN,b.point_target POINT_TARGET,b.units UNITS,b.rate_amount/b.shift_ier RATE_AMOUNT,b.discount/b.shift_ier DISCOUNT,b.unit_credited UNIT_CREDITED from " + tableName);
		StringBuffer selectStatement = new StringBuffer();
	  	selectStatement.append(" where a.aa_no=b.aa_no ");
	  	this.makeSQLByIntField("a.custid",dto.getCustID(),selectStatement);
	  	this.makeSQLByIntField("a.acctid",dto.getAcctID(),selectStatement);
	  	this.makeSQLByStringField("b.POINT_ORIGIN",dto.getPointOrigin(),selectStatement,"like");
	  	this.makeSQLByStringFieldIn("a.acctitemtypeid",dto.getAcctItemTypeID(),selectStatement);
	  	this.makeSQLByIntField("a.billingcycleid",dto.getBillingCycleID(),selectStatement);
	  	this.makeSQLByStringFieldIn("a.sourcetype",dto.getSourceType(),selectStatement);
	  	appendOrderByString(selectStatement);
	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
		
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by b.seq_no ");
	}
}
