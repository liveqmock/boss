package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.PalletDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * 货架查询
 * @author 260327h
 *
 */
public class QueryPalletListHandler extends ValueListHandler {
	private PalletDAO dao = null;

	private final String tableName = "t_pallet t ";

	private PalletDTO dto = null;

	public QueryPalletListHandler() {
		this.dao = new PalletDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof PalletDTO)
			this.dto = (PalletDTO) dto1;
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

	private void constructSelectQueryString(PalletDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//设置构造取得当前查询总纪录数的sql
	  	selectStatement.append(" where 1=1 ");
	  	//设置货架ID
    	makeSQLByIntField("t.palletID",dto.getPalletID(),selectStatement);
    	//设置货架名称
    	makeSQLByStringField("t.palletName",dto.getPalletName(),selectStatement,"like");
    	//设置货架状态
    	makeSQLByStringField("t.status",dto.getStatus(),selectStatement);
    	//设置货架所属仓库
        makeSQLByIntField("t.depotid",dto.getDepotID(),selectStatement);
		
		selectStatement.append(" order by t.palletID asc");
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
