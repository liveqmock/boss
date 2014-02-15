package com.dtv.oss.web.action.market;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DtvMigrationAreaDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.MarketEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>
 * Title: BOSS P5
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Digivision
 * </p>
 * User: chen jiang Date: 2006-3-20 Time: 15:22:51 To change this template use
 * File | Settings | File Templates.
 */
public class ModifyDtvMigrationWebAction extends GeneralWebAction {

	boolean confirmPost = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {

		super.doStart(request);

		confirmPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}

	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		
		
		DtvMigrationAreaDTO dto = new DtvMigrationAreaDTO();
		
		MarketEJBEvent event = new MarketEJBEvent();
		if ("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.DTV_MGT_CREATE);
		if ("update".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.DTV_MGT_UPDATE);
		/*
		 * if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
		 * event.setSeqNo(WebUtil.StringToInt(request
		 * .getParameter("txtSeqNo")));
		 */

		
		if (WebUtil.StringHaveContent(request.getParameter("id")))
			dto.setId(Integer.valueOf(request.getParameter("id")).intValue());
		
		if (WebUtil.StringHaveContent(request.getParameter("areaName")))
			dto.setAreaName(request.getParameter("areaName"));
		if (WebUtil.StringHaveContent(request.getParameter("areaCode")))
			dto.setAreaCode(request.getParameter("areaCode"));
		if (WebUtil
				.StringHaveContent(request.getParameter("txtRegionalAreaId")))
			dto.setRegionalAreaId(Integer.valueOf(
					request.getParameter("txtRegionalAreaId")).intValue());
		if (WebUtil
				.StringHaveContent(request.getParameter("migrationTeamName")))
			dto.setMigrationTeamName(request.getParameter("migrationTeamName"));

		if (WebUtil.StringHaveContent(request.getParameter("batchStartDate")))
			dto.setBatchStartDate(WebUtil.StringToTimestamp(request
					.getParameter("batchStartDate")));

		if (WebUtil.StringHaveContent(request.getParameter("batchEndDate")))
			dto.setBatchEndDate(WebUtil.StringToTimestamp(request
					.getParameter("batchEndDate")));

		if (WebUtil.StringHaveContent(request
				.getParameter("planMigrationRoomNum")))
			dto.setPlanMigrationRoomNum(Integer.valueOf(
					request.getParameter("planMigrationRoomNum")).intValue());

		if (WebUtil.StringHaveContent(request.getParameter("description")))
			dto.setDescription(request.getParameter("description"));

		if (WebUtil.StringHaveContent(request.getParameter("dtLastmod")))
			dto.setDtLastmod(WebUtil.StringToTimestamp(request
					.getParameter("dtLastmod")));

		if ("create".equals(request.getParameter("Action"))) {

			// 平移小区状态设置为创建
			dto.setStatus("N");
			// 设置记录创建日期
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); // 格式化当前系统日期
			dto.setCreateDate(WebUtil.StringToTimestamp(dateFm
					.format(new java.util.Date())));

		}

		if ("update".equals(request.getParameter("Action"))) {

			if (WebUtil.StringHaveContent(request.getParameter("status")))
				dto.setStatus(request.getParameter("status"));

			if (WebUtil.StringHaveContent(request.getParameter("createDate")))
				dto.setCreateDate(WebUtil.StringToTimestamp(request
						.getParameter("createDate")));

		}

		event.setDoPost(confirmPost);
		event.setDtvMigrationDto(dto);

		System.out.println("Action-----------------\n" + dto);

		return event;
	}
}
