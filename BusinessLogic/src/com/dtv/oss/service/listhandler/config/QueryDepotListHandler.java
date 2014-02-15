package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.DepotDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.DepotDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * 仓库查询
 * @author 260327h
 *
 */
public class QueryDepotListHandler extends ValueListHandler {
	private DepotDAO dao = null;

	private final String tableName = "t_depot t ";

	private DepotDTO dto = null;

	public QueryDepotListHandler() {
		this.dao = new DepotDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof DepotDTO)
			this.dto = (DepotDTO) dto1;
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

	private void constructSelectQueryString(DepotDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//设置构造取得当前查询总纪录数的sql
	  	selectStatement.append(" where 1=1 ");
    	makeSQLByIntField("t.depotID",dto.getDepotID(),selectStatement);
    	makeSQLByStringField("t.depotName",dto.getDepotName(),selectStatement,"like");
    	makeSQLByStringField("t.status",dto.getStatus(),selectStatement);
        makeSQLByIntField("t.OWNERID",dto.getOwnerID(),selectStatement);
		
		selectStatement.append(" order by t.depotID desc");
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
