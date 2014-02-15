package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.CasubentitlementDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.CASubentitlementDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * CA
 * @author 260710h
 *
 */
public class QueryCaSubentitlementListHandler extends ValueListHandler {
	private CASubentitlementDAO dao = null;

	private final String tableName = " t_casubentitlement t ";

	private CasubentitlementDTO dto = null;

	public QueryCaSubentitlementListHandler() {
		this.dao = new CASubentitlementDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		if (dto1 instanceof CasubentitlementDTO)
			this.dto = (CasubentitlementDTO) dto1;
		else {
			throw new ListHandlerException("the dto type is not proper...");
		}

		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
		
	}

	private void constructSelectQueryString(CasubentitlementDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
				
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

	  	selectStatement.append(" where 1=1 ");
	  	this.makeSQLByStringField("CARDSN",dto.getCardsn(),selectStatement);
	  	
		appendOrderByString(selectStatement);

	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by caproductid ASC");
	}

}
