package com.dtv.oss.web.action.catv;

/**
 * <p>
 * Title: BOSS
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Digivision
 * </p>
 * 
 * @author Horace Lin
 * @version 1.0
 */
 

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.catv.CatvJobCardEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ContactUserForCatvWebAction extends GeneralWebAction {
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
        
		CatvJobCardEJBEvent jcEvent = new CatvJobCardEJBEvent();
		//批量预约 begin
		if("batch".equals(request.getParameter("txtActionType")))
		{
			
			CommonQueryConditionDTO commonConditionDto = new CommonQueryConditionDTO();
			commonConditionDto.setSpareStr1(request.getParameter("txtJobCardIDList"));
			commonConditionDto.setSpareStr2(request.getParameter("txtJobCardDtLastmodList"));
			commonConditionDto.setSpareStr3(request.getParameter("txtPreferedDate"));
			commonConditionDto.setSpareStr4(request.getParameter("txtPreferedTime"));
			commonConditionDto.setSpareStr5(request.getParameter("txtMemo"));
			jcEvent.setActionType(EJBEvent.BATCH_CATV_CONTACT_USER);
			jcEvent.setCommonConditionDto(commonConditionDto);
			return jcEvent;
		}
		//批量预约 end
		JobCardDTO jobCardDto = new JobCardDTO();
		JobCardProcessDTO jcpDto = new JobCardProcessDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtJobCardID"))){
			jobCardDto.setJobCardId(WebUtil.StringToInt(request
					.getParameter("txtJobCardID")));
			jcpDto.setJcId(WebUtil.StringToInt(request
					.getParameter("txtJobCardID")));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			jobCardDto.setStatus(request
					.getParameter("txtStatus"));
		jobCardDto.setPreferedDate(WebUtil.StringToTimestamp(request
				.getParameter("txtPreferedDate")));
	    
		 //为了判断是否update
		jobCardDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtJobCardDtLastmod")));
		//jcpDto.setWorkTime(request.getParameter("txtWorkTime"));
		jobCardDto.setPreferedTime(request.getParameter("txtPreferedTime"));
		jobCardDto.setContactPerson(request.getParameter("txtContactPerson"));
		jobCardDto.setContactPhone(request.getParameter("txtContactPhone"));
		jcpDto.setMemo(request.getParameter("txtMemo"));
		jcpDto.setWorkResultReason(request.getParameter("txtWorkResultReason"));
		 
		 
		jcEvent.setActionType(EJBEvent.CATV_CONTACT_USER_FOR_CONSTRUCTION);
		jcEvent.setJobCardDto(jobCardDto);
		jcEvent.setJobcardProcessDto(jcpDto);
		return jcEvent;

	}

}