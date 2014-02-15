package com.dtv.oss.web.action.logistics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.FaPiaoEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.WebUtil;

/*
 * 发票运入
 */
public class FapiaoAbandonWebAction extends GeneralWebAction {
	boolean doPost = false;

	protected boolean needCheckToken() {
		return doPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true")) {
				doPost = true;

			}
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		// 发票册序列号
		theDTO.setSpareStr4(request.getParameter("volumnSetting"));
		


		theDTO.setSpareStr1(request.getParameter("txtSerailnos"));
		theDTO.setSpareStr2(request.getParameter("txtType"));
		if(request.getParameter("volumnSetting").equals("Y")){
			theDTO.setSpareStr3(request.getParameter("txtVolumnSN"));
		}
		
		FaPiaoEJBEvent theEvent = new FaPiaoEJBEvent();
		
		
		theEvent.setDtLastmods(request.getParameter("dtLastmods"));
		theEvent.setActionType(LogisticsEJBEvent.FAPIAO_ABANDON);
		theEvent.setDoPost(doPost);
		theEvent.setCommDTO(theDTO);
		
		return theEvent;

	}
}
