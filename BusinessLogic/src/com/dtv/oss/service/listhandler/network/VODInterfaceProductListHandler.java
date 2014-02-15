package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.VODInterfaceProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.VODInterfaceProductDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class VODInterfaceProductListHandler extends ValueListHandler {
	private VODInterfaceProductDAO dao = null;

	private VODInterfaceProductDTO dto = null;

	public VODInterfaceProductListHandler() {
		this.dao = new VODInterfaceProductDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof VODInterfaceProductDTO)
			this.dto = (VODInterfaceProductDTO) dto1;
		else {
			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// added
		// if (fillDTOWithPrivilege(dto) == false) return;
		// 构造查询字符串
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(VODInterfaceProductDTO dto) {
		String tableName = " t_vodproduct ";
		// select column
		StringBuffer select = new StringBuffer();
		// SMSPRODUCTID, VODPRODUCTIDLIST, VODSERVICEIDLIST, BILLINGCODELIST,
		// NEWSAFLAG, STATUS, DT_CREATE, DT_LASTMOD, ACCTITEMTYPEID
		select
				.append(" select SMSPRODUCTID, VODPRODUCTIDLIST, VODSERVICEIDLIST, BILLINGCODELIST,");
		select
				.append("NEWSAFLAG, STATUS, DT_CREATE, DT_LASTMOD, ACCTITEMTYPEID ");
		// query constraints
		StringBuffer where = new StringBuffer(" where 1=1 ");

		if (dto != null) {
			if (dto.getSmsProductID() != 0) {
				where.append(" and SMSPRODUCTID=").append(dto.getSmsProductID()).append(" ");
			}
		}
		// 设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(where);
		// 设置当前数据查询sql
		select.append(" from ").append(tableName).append(where).append(" order by smsproductid ");
		setRecordDataQueryBuffer(select);
	}

}