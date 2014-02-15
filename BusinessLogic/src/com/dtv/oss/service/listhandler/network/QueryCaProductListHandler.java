package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.CAProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.CaProductDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * CA主机查询
 * @author 260327h
 *
 */
public class QueryCaProductListHandler extends ValueListHandler {
	private CaProductDAO dao = null;

	private final String tableName = "t_caproduct a,t_product b";

	private CAProductDTO dto = null;

	public QueryCaProductListHandler() {
		this.dao = new CaProductDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CAProductDTO)
			this.dto = (CAProductDTO) dto1;
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

	private void constructSelectQueryString(CAProductDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select a.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//设置构造取得当前查询总纪录数的sql
	  	selectStatement.append(" where 1=1 and a.productid=b.productid(+) and  b.productclass='S' ");
	    makeSQLByIntField("a.productid",dto.getProductId(),selectStatement);
		makeSQLByIntField("a.conditionid",dto.getConditionId(),selectStatement);
	  //	this.makeSQLByIntField("conditionid",dto.getConditionId(),selectStatement);
	  	selectStatement.append(" order by a.productid ,a.conditionid desc");
	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
