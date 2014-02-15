package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.ServiceResourceDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ServiceResourceObjectBriefListHandler extends ValueListHandler {
	private ServiceResourceDAO dao = null;

	public ServiceResourceObjectBriefListHandler() {
		this.dao = new ServiceResourceDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto == null || !(dto instanceof ServiceResourceDTO)) {
			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		constructSelectQueryString((ServiceResourceDTO) dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(ServiceResourceDTO dto) {
		String tableName = "T_SERVICERESOURCE";

		StringBuffer select = new StringBuffer(" select ");
		StringBuffer where = new StringBuffer(" where 1=1 ");
		

		select
				.append(" RESOURCENAME,RESOURCEDESC,RESOURCETYPE,STATUS,DT_CREATE,DT_LASTMOD ");
		
		// 设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(where);
		// 设置当前数据查询sql
		select.append(" from ").append(tableName).append(where).append(
				" order by RESOURCENAME Asc ");
		setRecordDataQueryBuffer(select);
	}
}
