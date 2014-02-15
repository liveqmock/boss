/*
 * Created on 2006-4-29 by Stone Liang
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.OrganizationDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class OrganizationListHandler extends ValueListHandler {
	private OrganizationDAO dao = null;

	private String selectCriteria = "";

	final private String tableName = "t_organization org";

	/**
	 * use DTO as a template to determine
	 * search criteria
	 */
	private OrganizationDTO dto = null;

	public OrganizationListHandler() {
		this.dao = new OrganizationDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(DistrictSettingListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof OrganizationDTO)
			this.dto = (OrganizationDTO) dto1;
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

	private void constructSelectQueryString(OrganizationDTO dto) {
//		LogUtility.log(DistrictSettingListHandler.class, LogLevel.DEBUG,"dto.getBelongTo():\n"+dto.getBelongTo());
//		LogUtility.log(DistrictSettingListHandler.class, LogLevel.DEBUG,"dto.getId():\n"+dto.getId());
		StringBuffer begin = new StringBuffer();
		begin.append("select *  from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		if(dto.getOrgID()>0)
			makeSQLByIntField("orgid", dto.getOrgID(), selectStatement);
		else{
			//belongto����0����������,
			selectStatement.append(" and ParentOrgID =");
			selectStatement.append(dto.getParentOrgID());
		}
		//���÷�������
		makeSQLByStringField("orgType",dto.getOrgType(),selectStatement);
//		makeSQLByIntField("belongto", dto.getBelongTo(), selectStatement);

		appendOrderByString(selectStatement);

		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by orgid asc");
	}
}
