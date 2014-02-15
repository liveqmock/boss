package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.ResourcePhoneNoDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ServiceResourceDetaiObjectBriefListHandler extends
		ValueListHandler {
	private ResourcePhoneNoDAO dao = null;

	public ServiceResourceDetaiObjectBriefListHandler() {
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
		// ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(ResourcePhoneNoDTO dto) {
		String tableName = "T_RESOURCE_PHONENO";

		StringBuffer select = new StringBuffer(" select ");
		StringBuffer where = new StringBuffer(" where 1=1 ");

		// 9-7���
		select.append(" * ");
		// select
		// .append("
		// ITEMID,RESOURCENAME,PHONENO,AREACODE,DISTRICTID,COUNTRYCODE,COMMENTS,STATUS,STATUSTIME,DT_CREATE,DT_LASTMOD
		// ");
		if (dto != null) {
			makeSQLByStringField("RESOURCENAME", dto.getResourceName(), where);
			makeSQLByStringField("STATUS", dto.getStatus(), where);
			makeSQLByStringField("COUNTRYCODE", dto.getCountryCode(), where);
			makeSQLByStringField("AREACODE", dto.getAreaCode(), where);
			makeSQLByStringField("grade", dto.getGrade(), where);
			// makeSQLByIntField("DistrictId", dto.getDistrictId(), where);
			//����Ե����Ĳ�ѯ
			if (dto.getDistrictId() != 0) {
				where.append(" and DISTRICTID in ");
				where
						.append("(Select id  From T_DISTRICTSETTING bbb Where ((Select Count(*) from t_resource_phoneno aa Where aa.districtid=bbb.Id)>0) connect by prior Id = belongto start with ID=");
				where.append(dto.getDistrictId());
				where.append(")");
			}
			// ��������,end��begin������,�����end��begin֮��ĺ���,
			if (dto.getPhoneNoEnd() != null
					&& dto.getPhoneNoEnd().trim().length() != 0
					&& dto.getPhoneNoBegin() != null
					&& dto.getPhoneNoBegin().trim().length() != 0) {

				where.append(" and PHONENO<='").append(dto.getPhoneNoEnd())
						.append("'");

				where.append(" and PHONENO>='").append(dto.getPhoneNoBegin())
						.append("'");
			}
			// ���end��,begin����,��begin��ȷ��ѯ
			if ((dto.getPhoneNoEnd() == null || dto.getPhoneNoEnd().trim()
					.length() == 0)
					&& dto.getPhoneNoBegin() != null
					&& dto.getPhoneNoBegin().trim().length() != 0) {

				where.append(" and PHONENO='").append(dto.getPhoneNoBegin())
						.append("'");
			}

		}
		// ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(where);
		// ���õ�ǰ���ݲ�ѯsql
		select.append(" from ").append(tableName).append(where).append(
				" order by COUNTRYCODE,AREACODE,PHONENO Asc ");
		setRecordDataQueryBuffer(select);
	}
}