package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CAEventCmdMapDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.CaEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/*
 * CA�¼�����ӳ��
 */

public class CaEventCmdMapWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CAEventCmdMapDTO dto = new CAEventCmdMapDTO();
		CaEJBEvent event = new CaEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "������־" + aType);

		// ����ҳ��txtActionType������Event�Ĳ�������,�ò����κβ�����־ʱ�׳��쳣.
		if ("CA_EVENTCMDMAP_MODIFY".equalsIgnoreCase(aType)) {
			event = new CaEJBEvent(dto, CaEJBEvent.CA_EVENTCMDMAP_MODIFY);
		} else if ("CA_EVENTCMDMAP_CREATE".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new CaEJBEvent(dto, CaEJBEvent.CA_EVENTCMDMAP_CREATE);
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "�Ƿ�����");
			throw new WebActionException("�Ƿ�����");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtMapID")))
			dto.setMapID(WebUtil.StringToInt(request.getParameter("txtMapID")));

		 
			dto.setMapEventID(WebUtil.StringToInt(request.getParameter("txtEventID")));
		 
			dto.setMapConditionID(WebUtil.StringToInt(request.getParameter("txtCondID")));
		 
			dto.setMapCmdID(WebUtil.StringToInt(request.getParameter("txtCmdID")));
		 
			dto.setPriority(WebUtil.StringToInt(request.getParameter("txtPriority")));
		 
			dto.setStatus(request.getParameter("txtStatus"));
		 
			dto.setDescription(request.getParameter("txtDesc"));

		dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA��Ʒ���� dto"
				+ dto.toString());

		return event;
	}

}
