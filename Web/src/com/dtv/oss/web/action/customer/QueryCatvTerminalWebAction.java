package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Yangyong
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.listhandler.csr.CatvTerminalListHandler;

public class QueryCatvTerminalWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		//�ն�ID
		if (WebUtil.StringHaveContent(request.getParameter("id")))
	        theDTO.setSpareStr1(request.getParameter("id"));
		//�ն˱��
		//if (WebUtil.StringHaveContent(request.getParameter("txtCatvCode")))
		//	theDTO.setSpareStr2(request.getParameter("txtCatvCode"));
		//�ն˵�ַ
		if (WebUtil.StringHaveContent(request.getParameter("txtDetailedAddress")))
			theDTO.setSpareStr3(request.getParameter("txtDetailedAddress"));
		//�˿���
		if (WebUtil.StringHaveContent(request.getParameter("txtPortNo")))
			theDTO.setSpareStr4(request.getParameter("txtPortNo").trim());
		//��������
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			theDTO.setSpareStr5(request.getParameter("txtDistrictID"));
		//��ڵ�
		if (WebUtil.StringHaveContent(request.getParameter("txtFiberNodeID")))
			theDTO.setSpareStr6(request.getParameter("txtFiberNodeID"));
		//�ն�״̬
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			theDTO.setSpareStr7(request.getParameter("txtStatus"));
		//��������
		if (WebUtil.StringHaveContent(request.getParameter("txtCableType")))
			theDTO.setSpareStr8(request.getParameter("txtCableType"));
		//�ն���Դ
		if (WebUtil.StringHaveContent(request.getParameter("txtRecordSource")))
			theDTO.setSpareStr9(request.getParameter("txtRecordSource"));
		//˫�������
		if (WebUtil.StringHaveContent(request.getParameter("txtBiDirectionFlag")))
			theDTO.setSpareStr10(request.getParameter("txtBiDirectionFlag"));
		
		//��������(��)
		if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeStart")))
			theDTO.setSpareStr11(request.getParameter("txtCreateTimeStart"));
		//��������(ֹ)
		if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeEnd")))
			theDTO.setSpareStr12(request.getParameter("txtCreateTimeEnd"));
		
		//�ض�����(��)
		if (WebUtil.StringHaveContent(request.getParameter("txtPauseTimeStart")))
			theDTO.setSpareStr13(request.getParameter("txtPauseTimeStart"));
		//�ض�����(ֹ)
		if (WebUtil.StringHaveContent(request.getParameter("txtPauseTimeEnd")))
			theDTO.setSpareStr14(request.getParameter("txtPauseTimeEnd"));
		
		//��ͨ����(��)
		if (WebUtil.StringHaveContent(request.getParameter("txtActiveTimeStart")))
			theDTO.setSpareStr15(request.getParameter("txtActiveTimeStart"));
		//��ͨ����(ֹ)
		if (WebUtil.StringHaveContent(request.getParameter("txtActiveTimeEnd")))
			theDTO.setSpareStr16(request.getParameter("txtActiveTimeEnd"));
		
		//��������(��)
		if (WebUtil.StringHaveContent(request.getParameter("txtCancelTimeStart")))
			theDTO.setSpareStr17(request.getParameter("txtCancelTimeStart"));
		//��������(ֹ)
		if (WebUtil.StringHaveContent(request.getParameter("txtCancelTimeEnd")))
			theDTO.setSpareStr18(request.getParameter("txtCancelTimeEnd"));
		
		//�û�����
		if (WebUtil.StringHaveContent(request.getParameter("txtUserName")))
			theDTO.setSpareStr19(request.getParameter("txtUserName"));
		
		//����ʽ
		if (WebUtil.StringHaveContent(request.getParameter("txtOrderBy")))
			theDTO.setSpareStr20(request.getParameter("txtOrderBy"));
		
		//ֻ��ѯ�ն˱�־ ,�ͻ�����ʱѡ���նˣ������Ի����õ���
		if (WebUtil.StringHaveContent(request.getParameter("select")))
			theDTO.setSpareStr21(request.getParameter("select"));
		
		//ֻ��ѯ�ն˱�־
		if (WebUtil.StringHaveContent(request.getParameter("txtCatvTermType")))
			theDTO.setSpareStr22(request.getParameter("txtCatvTermType"));
		
		return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo,
				CsrQueryEJBEvent.QUERY_TYPE_CatvTerminal);

	}

}