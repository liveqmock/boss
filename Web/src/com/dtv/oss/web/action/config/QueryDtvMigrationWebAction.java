/*
 * 创建日期 2006-1-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class QueryDtvMigrationWebAction extends QueryWebAction {

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		// TODO 自动生成方法存根

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
