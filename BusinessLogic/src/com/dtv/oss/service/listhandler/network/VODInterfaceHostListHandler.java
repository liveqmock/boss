package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.VODInterfaceHostDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.VODInterfaceHostDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class VODInterfaceHostListHandler extends ValueListHandler {
	private VODInterfaceHostDAO dao = null;

	private VODInterfaceHostDTO dto = null;

	public VODInterfaceHostListHandler() {
		this.dao = new VODInterfaceHostDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof VODInterfaceHostDTO)
			this.dto = (VODInterfaceHostDTO) dto1;
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

	private void constructSelectQueryString(VODInterfaceHostDTO dto) {
		String tableName = " t_vodhost ";
		// select column
		// HOSTID, HOSTNAME, VODTYPE, IP, PORT, IPBACK,
		// PORTBACK, PROTOCOLTYPE, LOOPSIZE, LOOPINTERVAL,
		// TRYTIME, VALIDSTARTTIME, VALIDENDTIME,
		// LASTRUNTIME, LASTSTOPTIME, STATUS, DT_CREATE, DT_LASTMOD
		StringBuffer select = new StringBuffer();
		select.append(" select HOSTID, HOSTNAME, VODTYPE, IP, PORT, IPBACK,");
		select.append(" PORTBACK, PROTOCOLTYPE, LOOPSIZE, LOOPINTERVAL,");
		select.append(" TRYTIME, VALIDSTARTTIME, VALIDENDTIME,");
		select
				.append(" LASTRUNTIME, LASTSTOPTIME, STATUS, DT_CREATE, DT_LASTMOD");

		// query constraints
		StringBuffer where = new StringBuffer(" where 1=1 ");

		if (dto != null) {
			if (dto.getHostID() != 0) {
				where.append(" and HOSTID=").append(dto.getHostID()).append(" ");
			}

		}
		// 设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(where);
		// 设置当前数据查询sql
		select.append(" from ").append(tableName).append(where).append(" order by hostID ");
		setRecordDataQueryBuffer(select);
	}

}