package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.UserPointsExchangeCondDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigActivityEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2006-4-14
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyConfigCondWebAction extends GeneralWebAction {

	boolean doPost = false;

	protected boolean needCheckToken() {
		return doPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm"))) {
			if (request.getParameter("confirm").equalsIgnoreCase("true")) {
				doPost = true;

			}
		}

	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		UserPointsExchangeCondDTO dto = new UserPointsExchangeCondDTO();

		ConfigActivityEJBEvent event = new ConfigActivityEJBEvent();

		if (WebUtil.StringHaveContent(request.getParameter("txtActionType"))) {
			String actionType = "";
			actionType = request.getParameter("txtActionType");

			if ("cond_create".equals(actionType))
				event.setActionType(EJBEvent.CONFIG_COND_CREATE);
			else if ("cond_update".equals(actionType)) {
				dto.setDtLastmod(WebUtil.StringToTimestamp(request
						.getParameter("txtDtLastmod")));
				event.setActionType(EJBEvent.CONFIG_COND_EDIT);
			} else if ("cond_delete".equals(actionType)) {
				event.setActionType(EJBEvent.CONFIG_COND_DELETE);
			} else {
				throw new WebActionException("操作不明.");
			}
		} else {
			throw new WebActionException("操作标志丢失.");
		}
		 
		
		if (WebUtil.StringHaveContent(request.getParameter("txtActivityID")))
			dto.setActivityId(WebUtil.StringToInt(request
					.getParameter("txtActivityID")));
		else{
			throw new WebActionException("积分活动编号丢失.");
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtCondID")))
			dto.setCondId(WebUtil
					.StringToInt(request.getParameter("txtCondID")));
		else if(event.getActionType()!=EJBEvent.CONFIG_COND_CREATE){
			throw new WebActionException("条件编号丢失.");
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtGoodTypeID")))
			dto.setExchangeGoodsTypeID(WebUtil.StringToInt(request
					.getParameter("txtGoodTypeID")));
		 
			dto.setMopIdList(request.getParameter("txtMopIDList"));
	 
			dto.setAccountClassList(request.getParameter("txtAccountClassList"));
		 
			dto.setPointRange1(WebUtil.StringToInt(request
					.getParameter("txtPointRange1")));
		 
			dto.setPointRange2(WebUtil.StringToInt(request
					.getParameter("txtPointRange2")));

		if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));

		 
			dto.setCustTypeList(request.getParameter("txtCustTypeList"));
		
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "dto.getActivityID"+dto.getActivityId());
		
		event.setUserPointsExchCondDto(dto);
		return event;
	}
}
