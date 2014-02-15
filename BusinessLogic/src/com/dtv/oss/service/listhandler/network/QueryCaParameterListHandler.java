package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.CAParameterDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.CaParameterDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * CA主机查询
 * @author 260327h
 *
 */
public class QueryCaParameterListHandler extends ValueListHandler {
	private CaParameterDAO dao = null;

	private final String tableName = "t_caParameter t ";

	private CAParameterDTO dto = null;

	public QueryCaParameterListHandler() {
		this.dao = new CaParameterDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CAParameterDTO)
			this.dto = (CAParameterDTO) dto1;
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

	private void constructSelectQueryString(CAParameterDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//设置构造取得当前查询总纪录数的sql
	  	selectStatement.append(" where 1=1 ");
	  	this.makeSQLByStringField("PMNAME",dto.getPmName(),selectStatement);
		appendOrderByString(selectStatement);

	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by PMNAME");
	}
}
