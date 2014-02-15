/*
 * Created on 2006-4-29 by Stone Liang
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.DistrictSettingDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class DistrictSettingListHandler extends ValueListHandler {
	private DistrictSettingDAO dao = null;

	private String selectCriteria = "";

	final private String tableName = "T_DISTRICTSETTING dist";

	/**
	 * use DTO as a template to determine
	 * search criteria
	 */
	private DistrictSettingDTO dto = null;

	public DistrictSettingListHandler() {
		this.dao = new DistrictSettingDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(DistrictSettingListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof DistrictSettingDTO)
			this.dto = (DistrictSettingDTO) dto1;
		else {
			LogUtility.log(DistrictSettingListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		//ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(DistrictSettingDTO dto) {
//		LogUtility.log(DistrictSettingListHandler.class, LogLevel.DEBUG,"dto.getBelongTo():\n"+dto.getBelongTo());
//		LogUtility.log(DistrictSettingListHandler.class, LogLevel.DEBUG,"dto.getId():\n"+dto.getId());
		StringBuffer begin = new StringBuffer();
		begin.append("select *  from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		if(dto.getId()>0)
			makeSQLByIntField("ID", dto.getId(), selectStatement);
		else{
			//belongto����0����������,
			selectStatement.append(" and belongto =");
			selectStatement.append(dto.getBelongTo());
		}
//		makeSQLByIntField("belongto", dto.getBelongTo(), selectStatement);

		appendOrderByString(selectStatement);

		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by districtcode");
	}
}
