package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.FaPiaoEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/*
 * ��Ʊ�ؿ��ѯ ����������ѯ��Ʊ���к�
 */
public class FapiaoBackQueryWebAction extends GeneralWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		//if (!WebUtil.StringHaveContent(request.getParameter("txtVolumnSN")))
		//	throw new WebActionException("û�и�����Ʊ�����к�!");
		if (!WebUtil.StringHaveContent(request.getParameter("txtType")))
			throw new WebActionException("��������:��Ʊ����δ֪!");
//		if (!WebUtil.StringHaveContent(request.getParameter("txtFromAddressID")))
//			throw new WebActionException("��������:��Ʊԭ��ַδ֪!");

		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		// ��Ʊ�����к�
		//theDTO.setSpareStr1(request.getParameter("txtVolumnSN"));
		// ��Ʊ����
		theDTO.setSpareStr2(request.getParameter("txtType"));
		// ��ʼ��Ʊ�ֹ����
		theDTO.setSpareStr3(request.getParameter("txtSerialBegin"));
		// ��ֹ��Ʊ�ֹ����
		theDTO.setSpareStr4(request.getParameter("txtSerialEnd"));
		//��Ʊԭ��ַ����
		theDTO.setSpareStr5(request.getParameter("txtFromAddressType"));
		//��Ʊԭ��ַ
		theDTO.setSpareStr6(request.getParameter("txtFromAddressID"));
		FaPiaoEJBEvent theEvent = new FaPiaoEJBEvent();
		theEvent.setActionType(LogisticsEJBEvent.FAPIAO_BACK_QUERY);
		theEvent.setCommDTO(theDTO);
		return theEvent;
	}
}
