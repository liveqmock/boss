package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryAdjustAccountWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO��װ����˵��
		//------------------���ڵ��ʲ�ѯ-------
		//SpareStr1:�������
		//SpareStr2:�ͻ�֤��
		//SpareStr3:�ʻ���
		//SpareStr4:����ԭ��
		//SpareStr5:��������
		//SpareStr6:������֯
		//SpareStr7:����
		//SpareStr8:���ʶ���
		//SpareStr9:���ʶ���ID
		//SpareStr10:״̬
		//SpareStr11:Ԥ��������
		
		//SpareStr12:����ԱID
		//SpareStr13:���˷���

		//SpareTime1:������ʼʱ��
		//SpareTime2:������ֹʱ��
		//---------------���ڵ��ʲ�ѯ-------


		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************�������е��ʲ�ѯ****************************************
		//SpareStr2:�ͻ�֤��
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID")))
			dto.setSpareStr2(request.getParameter("txtCustID"));
		//SpareStr3:�ʻ���
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID")))
			dto.setSpareStr3(request.getParameter("txtAcctID"));
		//SpareStr4:����ԭ��
		if(WebUtil.StringHaveContent(request.getParameter("txtReason")))
			dto.setSpareStr4(request.getParameter("txtReason"));
		//SpareStr5:��������
		if(WebUtil.StringHaveContent(request.getParameter("txtAdjustmentType")))
			dto.setSpareStr5(request.getParameter("txtAdjustmentType"));
		//SpareStr6:������֯
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr6(request.getParameter("txtOrgID"));
		//SpareStr7:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr7(request.getParameter("txtDistrictID"));
		//SpareStr8:���ʶ���
		if(WebUtil.StringHaveContent(request.getParameter("txtReferRecordType")))
			dto.setSpareStr8(request.getParameter("txtReferRecordType"));
		//SpareStr9:���ʶ���ID
		if(WebUtil.StringHaveContent(request.getParameter("txtReferRecordID")))
			dto.setSpareStr9(request.getParameter("txtReferRecordID"));
		//SpareStr10:״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr10(request.getParameter("txtStatus"));
		//SpareStr11:Ԥ��������
		if(WebUtil.StringHaveContent(request.getParameter("txtAdjustmentPrepaymentFlag")))
			dto.setSpareStr11(request.getParameter("txtAdjustmentPrepaymentFlag"));
		
		//SpareTime1:������ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1DatePart")))
			dto.setSpareTime1(WebUtil.StringToTimestampDefaultDayBegin(
	        		request.getParameter("txtCreateTime1DatePart"),
					request.getParameter("txtCreateTime1HourPart")
					));
		//SpareTime2:������ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2DatePart")))
			dto.setSpareTime2(WebUtil.StringToTimestampDefaultDayBegin(
	        		request.getParameter("txtCreateTime2DatePart"),
					request.getParameter("txtCreateTime2HourPart")
					));

		//****************************���ڵ��ʾ����ѯ*****************************************
		//SpareStr1:��¼ID
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNO")) && (!"0".equals(request.getParameter("txtSeqNO")))){
			dto.setSpareStr1(request.getParameter("txtSeqNO"));
		}
		//SpareStr12:����ԱID
		if(WebUtil.StringHaveContent(request.getParameter("txtOpID"))){
			dto.setSpareStr12(request.getParameter("txtOpID"));
		}
		//SpareStr13:���˷���
		if(WebUtil.StringHaveContent(request.getParameter("txtAdjustmentDirection"))){
			dto.setSpareStr13(request.getParameter("txtAdjustmentDirection"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtAddress")))
			dto.setSpareStr14(request.getParameter("txtAddress"));
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_ADJUST_ACCOUNT);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
