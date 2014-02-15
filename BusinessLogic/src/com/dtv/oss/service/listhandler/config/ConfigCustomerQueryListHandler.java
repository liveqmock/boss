package com.dtv.oss.service.listhandler.config;

import java.util.List;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.dao.config.ConfigCustomerValueDao;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ConfigCustomerQueryListHandler extends ValueListHandler {
	private GenericDAO dao = null;

	public ConfigCustomerQueryListHandler() {
		dao = new ConfigCustomerValueDao();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(ConfigCustomerQueryListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria..." + dto);

		if (dto == null) {
			throw new ListHandlerException();
		}
		try {
			StringBuffer querySql = constructSQLQueryString((CommonSettingDataDTO) dto);
			getDataBySQL(querySql);
		} catch (Exception e) {
			throw new ListHandlerException(e.toString());
		}

	}

	private StringBuffer constructSQLQueryString(CommonSettingDataDTO dto) {
		StringBuffer sb = new StringBuffer(
				"select * from t_commonsettingdata where ");
		sb.append(" name='").append(dto.getName()).append("'");
		String key = dto.getKey();
		if (key != null && key.trim().length() > 0) {
			sb.append(" and key='").append(key).append("'");
		}
		
		sb.append(" order by name,key ");
		
		return sb;

	}

	private void getDataBySQL(StringBuffer sql) throws ListHandlerException {
		LogUtility.log(ConfigCustomerQueryListHandler.class, LogLevel.DEBUG,
				"in getDataBySQL begin setCriteria...\nthe sql:" + sql);
		setRecordDataQueryBuffer(sql);
		executeSearch(dao, false, false);
		List list = getList();
		setTotalRecordSize(list != null ? list.size() : 0);

	}

}
