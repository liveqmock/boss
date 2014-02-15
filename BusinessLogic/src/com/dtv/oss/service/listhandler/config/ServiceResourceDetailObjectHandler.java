package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.ResourcePhoneNoDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ServiceResourceDetailObjectHandler extends ValueListHandler {
	private ResourcePhoneNoDAO dao = null;

	public ServiceResourceDetailObjectHandler() {
		this.dao = new ResourcePhoneNoDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto == null || !(dto instanceof ResourcePhoneNoDTO)) {
			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		constructSelectQueryString((ResourcePhoneNoDTO) dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(ResourcePhoneNoDTO dto) {
		String tableName = "T_RESOURCE_PHONENO";

		StringBuffer select = new StringBuffer(" select ");
		StringBuffer where = new StringBuffer(" where 1=1 ");

		select
				.append(" ITEMID,RESOURCENAME,PHONENO,AREACODE,COUNTRYCODE,COMMENTS,STATUS,STATUSTIME,DT_CREATE,DT_LASTMOD ");
		if (dto != null) {
			makeSQLByStringField("RESOURCENAME", dto.getResourceName(), where);
			makeSQLByStringField("STATUS", dto.getStatus(), where);
			makeSQLByStringField("COUNTRYCODE", dto.getCountryCode(), where);
			makeSQLByStringField("AREACODE", dto.getAreaCode(), where);
			if (dto.getPhoneNoEnd() != null
					&& dto.getPhoneNoEnd().trim().length() != 0) {
				where.append(" and PHONENO>='").append(dto.getPhoneNoBegin())
						.append("'");
				where.append(" and PHONENO<='").append(dto.getPhoneNoEnd())
						.append("'");
			} else {
				where.append(" and PHONENO='").append(dto.getPhoneNoBegin())
						.append("'");
			}
		}
		// 设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(where);
		// 设置当前数据查询sql
		select.append(" from ").append(tableName).append(where).append(
				" order by PHONENO Asc ");
		setRecordDataQueryBuffer(select);
	}
}
