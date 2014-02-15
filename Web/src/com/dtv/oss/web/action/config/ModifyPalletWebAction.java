package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.PalletEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
/**
 * ���ܲ���
 * @author 260327h
 *
 */
public class ModifyPalletWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		PalletDTO dto = new PalletDTO();
		PalletEJBEvent event = new PalletEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "������־ dto" + aType);

		//����ҳ��txtActionType������Event�Ĳ�������,�ò����κβ�����־ʱ�׳��쳣.
		if ("CREATE".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new PalletEJBEvent(dto, PalletEJBEvent.PALLET_CREATE);
		} else if ("MODIFY".equalsIgnoreCase(aType)) {
			event = new PalletEJBEvent(dto, PalletEJBEvent.PALLET_UPDATE);
		} else if ("DELETE".equalsIgnoreCase(aType)) {
			event = new PalletEJBEvent(dto, PalletEJBEvent.PALLET_DELETE);
			event.setList(request.getParameterValues("ListID"));
			return event;
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "�Ƿ�����");
			throw new WebActionException("�Ƿ�����");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtPalletID")))
			dto.setPalletID(WebUtil.StringToInt(request
					.getParameter("txtPalletID")));
		//�Ӹ���֤,���Ʋ���
		if (WebUtil.StringHaveContent(request.getParameter("txtPalletName"))) {
			dto.setPalletName(request.getParameter("txtPalletName"));
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "ȱ�ٲֿ�����");
			throw new WebActionException("ȱ�ٲֿ�����");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtPalletStatus")))
			dto.setStatus(request.getParameter("txtPalletStatus"));
		if (WebUtil.StringHaveContent(request.getParameter("txtMaxNumber")))
			dto.setMaxNumberAllowed(WebUtil.StringToInt(request
					.getParameter("txtMaxNumber")));
		if (WebUtil.StringHaveContent(request.getParameter("txtOwner")))
			dto.setDepotID(WebUtil
					.StringToInt(request.getParameter("txtOwner")));
		if (WebUtil.StringHaveContent(request.getParameter("txtPalletDesc")))
			dto.setPalletDesc(request.getParameter("txtPalletDesc"));

		dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�޸Ĳֿ� dto"
				+ dto.toString());

		return event;
	}

}
