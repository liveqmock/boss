package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * ���ʼ�¼��ѯ
 * author     ��Jason.Zhou 
 * date       : 2006-2-15
 * description:
 * @author 250713z
 *
 */
public class QuerySetoffRecordWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO��װ����˵��
		//------------------�������ʲ�ѯ-------
		//SpareStr1:���
		//SpareStr2:�ͻ�֤��
		//SpareStr3:�ʻ���
		//SpareStr4:��֯
		//SpareStr5:����
		//SpareStr6:ʵ�չ�������
		//SpareStr7:��������
		//SpareStr8:֧����¼ID
		//SpareStr9:��Ŀ����
		//SpareStr10:���ü�¼ID
		
		//SpareTime1:����ʱ��1
		//SpareTime2:����ʱ��2
		//SpareTime3:֧��ʱ��1
		//SpareTime4:֧��ʱ��2
		//SpareTime5:���ô���ʱ��1
		//SpareTime6:���ô���ʱ��2

		//---------------�������ʲ�ѯ-------

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************�����������ʲ�ѯ****************************************
		//SpareStr2:�ͻ�֤��
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID")))
			dto.setSpareStr2(request.getParameter("txtCustID"));
		//SpareStr3:�ʻ���
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID")))
			dto.setSpareStr3(request.getParameter("txtAcctID"));
		//SpareStr4:��֯
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr4(request.getParameter("txtOrgID"));
		//SpareStr5:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr5(request.getParameter("txtDistrictID"));
		//SpareStr6:ʵ�չ�������
		if(WebUtil.StringHaveContent(request.getParameter("txtPlusReferType")))
			dto.setSpareStr6(request.getParameter("txtPlusReferType"));
		
		//SpareStr7:��������
    	if(WebUtil.StringHaveContent(request.getParameter("txtMinusReferType")))
		    dto.setSpareStr7(request.getParameter("txtMinusReferType"));
		
		//SpareStr8:֧����¼ID
		if(WebUtil.StringHaveContent(request.getParameter("txtPlusReferID")))
			dto.setSpareStr8(request.getParameter("txtPlusReferID"));
		//SpareStr9:��Ŀ����
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctItemTypeID")))
			dto.setSpareStr9(request.getParameter("txtAcctItemTypeID"));
		//SpareStr10:���ü�¼ID
		if(WebUtil.StringHaveContent(request.getParameter("txtMinusReferID")))
			dto.setSpareStr10(request.getParameter("txtMinusReferID"));

		//SpareTime1:����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		//SpareTime3:֧��ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtPaymentTime1")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPaymentTime1")));
		//SpareTime4:֧��ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtPaymentTime2")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPaymentTime2")));
		//SpareTime5:���ô���ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtFeeTime1")))
			dto.setSpareTime5(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtFeeTime1")));
		//SpareTime6:���ô���ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtFeeTime2")))
			dto.setSpareTime6(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtFeeTime2")));
		

		//****************************���ڵ��ʲ�ѯ*****************************************
		//SpareStr1:��¼ID
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")) && (!"0".equals(request.getParameter("txtSeqNo")))){
			dto.setSpareStr1(request.getParameter("txtSeqNo"));
		}

		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_SETOFF_RECORD);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
