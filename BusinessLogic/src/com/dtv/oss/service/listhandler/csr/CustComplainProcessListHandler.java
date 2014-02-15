package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.CustComplainProcessDTO;
import com.dtv.oss.dto.CustomerComplainDTO;
import com.dtv.oss.dto.wrap.customer.ComplainWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CustomerComplainProcessDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class CustComplainProcessListHandler extends ValueListHandler {

	private CustComplainProcessDTO dto=null;
	private CustomerComplainProcessDAO dao=null;
	private final String tableName=" t_custcomplainprocess a ";
	
	public CustComplainProcessListHandler(){
		dao=new CustomerComplainProcessDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
		"in setCriteria begin setCriteria...");
		if (dto1 instanceof CustComplainProcessDTO)
			this.dto = (CustComplainProcessDTO) dto1;
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

	private void constructSelectQueryString(CustComplainProcessDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
		StringBuffer selectStatement = new StringBuffer();
	  	selectStatement.append(" where 1=1 ");
	  	this.makeSQLByIntField("a.custcomplainid",dto.getCustComplainId(),selectStatement);
	  	appendOrderByString(selectStatement);
	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
		
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by id desc ");
	}

}
