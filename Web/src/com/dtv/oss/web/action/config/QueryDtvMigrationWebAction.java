/*
 * �������� 2006-1-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DtvMigrationAreaDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author chen jiang
 * 
 * ��������������ע�͵�ģ��Ϊ ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class QueryDtvMigrationWebAction extends QueryWebAction {

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		// TODO �Զ����ɷ������

		DtvMigrationAreaDTO dto = new DtvMigrationAreaDTO();
		if (WebUtil.StringHaveContent(request.getParameter("id")))
			dto.setId(Integer.valueOf(request.getParameter("id")).intValue());

		if (WebUtil.StringHaveContent(request.getParameter("areaCode")))
			dto.setAreaCode(request.getParameter("areaCode"));

		if (WebUtil.StringHaveContent(request.getParameter("areaName")))
			dto.setAreaName(request.getParameter("areaName"));

		if (WebUtil.StringHaveContent(request.getParameter("createDate")))
			dto.setCreateDate(WebUtil.StringToTimestamp(request
					.getParameter("createDate")));

		if (WebUtil
				.StringHaveContent(request.getParameter("txtRegionalAreaId")))
			dto.setRegionalAreaId(Integer.valueOf(
					request.getParameter("txtRegionalAreaId")).intValue());

		if (WebUtil
				.StringHaveContent(request.getParameter("migrationTeamName")))
			dto
					.setMigrationTeamName((request
							.getParameter("migrationTeamName")));

		if (WebUtil.StringHaveContent(request.getParameter("status")))
			dto.setStatus(request.getParameter("status"));

		return new ConfigQueryEJBEvent(dto, pageFrom, pageTo,
				ConfigQueryEJBEvent.DTV_MIGRATION_QUERY);

	}

}
