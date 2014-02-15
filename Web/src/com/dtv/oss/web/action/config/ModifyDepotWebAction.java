package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DepotDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.DepotEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
/*
 * �ֿ����
 */
public class ModifyDepotWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		DepotDTO dto = new DepotDTO();
		DepotEJBEvent event = new DepotEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "������־ dto" + aType);

		//����ҳ��txtActionType������Event�Ĳ�������,�ò����κβ�����־ʱ�׳��쳣.
		if ("CREATE".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new DepotEJBEvent(dto, EJBEvent.DEPOT_CREATE);
		} else if ("MODIFY".equalsIgnoreCase(aType)) {
			event = new DepotEJBEvent(dto, EJBEvent.DEPOT_UPDATE);
		} else if ("DELETE".equalsIgnoreCase(aType)) {
			event = new DepotEJBEvent(dto, EJBEvent.DEPOT_DELETE);
			event.setList(request.getParameterValues("ListID"));
			return event;
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "�Ƿ�����");
			throw new WebActionException("�Ƿ�����");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtDepotID")))
			dto.setDepotID(WebUtil.StringToInt(request
					.getParameter("txtDepotID")));
		//�Ӹ���֤,���Ʋ���
		if (WebUtil.StringHaveContent(request.getParameter("txtDepotName"))) {
			dto.setDepotName(request.getParameter("txtDepotName"));
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "ȱ�ٲֿ�����");
			throw new WebActionException("ȱ�ٲֿ�����");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
			dto.setDetailAddress(request.getParameter("txtDetailAddress"));
		if (WebUtil.StringHaveContent(request.getParameter("txtDepotStatus")))
			dto.setStatus(request.getParameter("txtDepotStatus"));
		if (WebUtil.StringHaveContent(request.getParameter("txtOwner")))
			dto.setOwnerID(WebUtil
					.StringToInt(request.getParameter("txtOwner")));

		dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());


		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�޸Ĳֿ� dto"
				+ dto.toString());

		return event;
	}
	protected void afterSuccessfulResponse(HttpServletRequest request,
			CommandResponse cmdResponse) {
		//�����²ֿ⻺��
		Postern.getDepots(true);
	}
}
