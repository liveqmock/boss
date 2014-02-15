package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author david.Yang
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;

public class QueryInteractionWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		//�����������ڴ�ͻ�������ѯ,
		String systemPlatform = (String) request.getSession().getAttribute(
				"SET_G_SYSTEMPLATFORM");
		if (WebUtil.StringHaveContent(systemPlatform)&&"F".equals(systemPlatform)) {
			System.out
					.println("SET_G_SYSTEMPLATFORM============="
							+ systemPlatform);
			theDTO.setSpareStr12(CommonConstDefinition.CUSTOMERSTYLE_GROUP);
		}else{
			theDTO.setSpareStr12(CommonConstDefinition.CUSTOMERSTYLE_SINGLE);
		}

		//�������
		if (WebUtil.StringHaveContent(request.getParameter("txtID")))
			theDTO.setNo(request.getParameter("txtID"));

		//��������
		if (WebUtil.StringHaveContent(request.getParameter("txtType"))){
			theDTO.setType(request.getParameter("txtType"));
		}else if (WebUtil.StringHaveContent(request.getParameter("txtCsiType"))){
			theDTO.setType(request.getParameter("txtCsiType"));
		}
		
		
		
		//����״̬
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			theDTO.setStatus(request.getParameter("txtStatus"));

		//����Ա��Ϣ
		if (WebUtil.StringHaveContent(request
				.getParameter("txtCreateOperatorName")))
			theDTO.setOperator(request.getParameter("txtCreateOperatorName"));

		//�û�������
		if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
			theDTO.setStreet(WebUtil.StringToInt(request
					.getParameter("txtCounty")));

		//�طñ�־
		if (WebUtil.StringHaveContent(request.getParameter("txtCallBackFlag")))
			theDTO.setSpareStr1(request.getParameter("txtCallBackFlag"));

		//���ѱ�־
		if (WebUtil.StringHaveContent(request.getParameter("txtPaymentStatus")))
			theDTO.setSpareStr2(request.getParameter("txtPaymentStatus"));

		//�û�֤��
		if (WebUtil.StringHaveContent(request.getParameter("txtUserID")))
			theDTO.setSpareStr5(request.getParameter("txtUserID"));

		if (request.getParameter("txtUserID") == null
				|| "".equals(request.getParameter("txtUserID").trim())) {
			if (WebUtil
					.StringHaveContent(request.getParameter("txtCustomerID")))
				theDTO.setSpareStr5(request.getParameter("txtCustomerID"));
		}

		//�ͻ�����
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerName")))
			theDTO.setSpareStr6(request.getParameter("txtCustomerName"));

		//��װ��ַ
		if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
			theDTO.setSpareStr7(request.getParameter("txtDetailAddress"));

		//��ϵ�绰
		if (WebUtil.StringHaveContent(request.getParameter("txtTelephone")))
			theDTO.setSpareStr8(request.getParameter("txtTelephone"));

		//ȡ��ԭ��
		if (WebUtil.StringHaveContent(request.getParameter("txtCancleReason")))
			theDTO.setSpareStr9(request.getParameter("txtCancleReason"));
         
	    //����ԭ��
		if (WebUtil.StringHaveContent(request.getParameter("txtCsiReason")))
			theDTO.setSpareStr14(request.getParameter("txtCsiReason"));
		
		//������֯
		if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			theDTO.setFiliale(WebUtil.StringToInt(request
					.getParameter("txtOrgID")));

		//����ʱ��
		if (WebUtil.StringHaveContent(request
				.getParameter("txtCreateStartDate")))
			theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request
					.getParameter("txtCreateStartDate")));
		if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
			theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request
					.getParameter("txtCreateEndDate")));

		//ԤԼ����ʱ��
		if (WebUtil.StringHaveContent(request
				.getParameter("txtPreferedStartDate")))
			theDTO.setSpareBeginTime(WebUtil
					.StringToTimestampWithDayBegin(request
							.getParameter("txtPreferedStartDate")));
		if (WebUtil.StringHaveContent(request
				.getParameter("txtPreferedEndDate")))
			theDTO.setSpareEndTime(WebUtil.StringToTimestampWithDayEnd(request
					.getParameter("txtPreferedEndDate")));

		//�������
		if (WebUtil
				.StringHaveContent(request.getParameter("txtReferjobcardid")))
			theDTO.setSpareStr11(request.getParameter("txtReferjobcardid"));
		
		//������־(���������ŵ갲װ�˷��õ�)
		if (WebUtil.StringHaveContent(request.getParameter("txtCondition")))
			theDTO.setSpareStr13(request.getParameter("txtCondition"));
		
		//������ѯ����ͼ���Ż�
		if(request.getParameter("txtActionType")!=null&&"select".equals(request.getParameter("txtActionType")))
			return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo,
				CsrQueryEJBEvent.QUERY_TYPE_SIMPLECUSTSERVICEINTERACTIONVIEW);
		else
			return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo,
					CsrQueryEJBEvent.QUERY_TYPE_SIMPLECUSTSERVICEINTERACTION);

	}

}