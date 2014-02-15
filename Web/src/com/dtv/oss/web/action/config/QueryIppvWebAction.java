/*
 * �������� 2006-1-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CAWalletDefineDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author chen jiang
 * 
 * ��������������ע�͵�ģ��Ϊ ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class QueryIppvWebAction extends QueryWebAction {

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		// TODO �Զ����ɷ������

		CAWalletDefineDTO dto = new CAWalletDefineDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txt_ippv_code")))
			dto.setCaWalletCode(request.getParameter("txt_ippv_code"));

		if (WebUtil.StringHaveContent(request.getParameter("txt_ippv_name")))
			dto.setCaWalletName(request.getParameter("txt_ippv_name"));

		if (WebUtil.StringHaveContent(request
				.getParameter("txt_ippv_dependent_eqp")))
			dto.setDeviceModelList(request
					.getParameter("txt_ippv_dependent_eqp"));

		if (WebUtil.StringHaveContent(request.getParameter("txt_require_y_n")))
			dto.setRequired(request.getParameter("txt_require_y_n"));

		return new ConfigQueryEJBEvent(dto, pageFrom, pageTo,
				ConfigQueryEJBEvent.IPPV_QUERY);

	}

}
