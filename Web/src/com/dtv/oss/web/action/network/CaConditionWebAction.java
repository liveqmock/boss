package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CAConditionDTO;
import com.dtv.oss.dto.CAHostDTO;
import com.dtv.oss.dto.CAProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.CaEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/*
 * CA����
 */

public class CaConditionWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CAConditionDTO dto = new CAConditionDTO();
		CaEJBEvent event = new CaEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "������־" + aType);

		// ����ҳ��txtActionType������Event�Ĳ�������,�ò����κβ�����־ʱ�׳��쳣.
		if ("CA_CONDITION_MODIFY".equalsIgnoreCase(aType)) {
			event = new CaEJBEvent(dto, CaEJBEvent.CA_CONDITION_MODIFY);
		} else if ("CA_CONDITION_CREATE".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new CaEJBEvent(dto, CaEJBEvent.CA_CONDITION_CREATE);
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "�Ƿ�����");
			throw new WebActionException("�Ƿ�����");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtCondID")))
			dto.setCondID(WebUtil.StringToInt(request.getParameter("txtCondID")));

		 
			dto.setCondName(request.getParameter("txtCondName"));
		 
			dto.setHostID(WebUtil.StringToInt(request.getParameter("txtHostID")));
		 
			dto.setCondString(request.getParameter("txtCondString"));
		 
			dto.setDescription(request.getParameter("txtDesc"));

		   dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA��Ʒ���� dto"
				+ dto.toString());

		return event;
	}

}
