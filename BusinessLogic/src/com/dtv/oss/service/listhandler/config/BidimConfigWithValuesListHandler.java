package com.dtv.oss.service.listhandler.config;

import java.sql.SQLException;
import java.util.List;

import com.dtv.oss.dto.BidimConfigSettingDTO;
import com.dtv.oss.dto.BidimConfigSettingWithValueDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.dao.config.BidimConfigDAO;
import com.dtv.oss.service.dao.config.BidimConfigValueDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class BidimConfigWithValuesListHandler extends ValueListHandler {
	private BidimConfigDAO bidimConfigDAO = null;

	private BidimConfigValueDAO bidimConfigValueDAO = new BidimConfigValueDAO();

	final private String tableName = "t_bidimconfigsetting t";

	private BidimConfigSettingDTO dto = null;

	public BidimConfigWithValuesListHandler() {
		this.bidimConfigDAO = new BidimConfigDAO();
		this.bidimConfigValueDAO = new BidimConfigValueDAO();
	}

	protected void executeSearch(GenericDAO dao, boolean isWrap, boolean isCount)
			throws ListHandlerException {
		// getConfig //executeSelect
		super.executeSearch(bidimConfigDAO, isWrap, isCount);
		try {
			List list = getList();
			if (list.size() < 1) {
				return;
			}
			BidimConfigSettingDTO dto = (BidimConfigSettingDTO) list.get(0);
			BidimConfigSettingWithValueDTO withValuesDTO = new BidimConfigSettingWithValueDTO(
					dto);
			getList().set(0, withValuesDTO);

			List valueList = getValuesList(dto);
			withValuesDTO.addValues(valueList);
			System.out.println(this.getClass() + "dto:" + dto + "\n values:"
					+ valueList);
		} catch (Exception e) {
			LogUtility.log(getClass(), LogLevel.WARN, "executeSearch", e);
			throw new ListHandlerException(e.getMessage());
		}

	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof BidimConfigSettingDTO)
			this.dto = (BidimConfigSettingDTO) dto1;
		else {
			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// added
		// if (fillDTOWithPrivilege(dto) == false) return;
		// 构造查询字符串
		constructConfigSettingQueryString(dto);
		// 执行数据查询
		executeSearch(bidimConfigDAO, true, true);
	}

	private void constructConfigSettingQueryString(BidimConfigSettingDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		makeSQLByStringField("t.configType", dto.getConfigType(),
				selectStatement);
		makeSQLByStringField("t.configSubType", dto.getConfigSubType(),
				selectStatement);
		makeSQLByStringField("t.valueType", dto.getValueType(), selectStatement);
		makeSQLByStringField("t.status", dto.getStatus(), selectStatement);
		makeSQLByIntField("t.id", dto.getId(), selectStatement);

		selectStatement.append(" order by t.id desc");

		// 设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private List getValuesList(BidimConfigSettingDTO dto) throws SQLException {
		String sql = "select * from T_BiDimConfigSettingValue where settingid="
				+ dto.getId()+" order by id ";
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in executeSearch method data query start");
		List valuesList = bidimConfigValueDAO.executeSelect(sql);

		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in executeSearch method data query end");
		return valuesList;
	}
}