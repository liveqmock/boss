package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryPrepaymentDeductionRecordWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO��װ����˵��
		//------------------����Ԥ��ֿۼ�¼��ѯ-------
		//SpareStr1:���
		//SpareStr2:�ͻ�֤��
		//SpareStr3:�ʻ���
		//SpareStr4:��֯
		//SpareStr5:����
		//SpareStr6:�ۿ�����
		//SpareStr7:��Դ��¼����
		//SpareStr8:��Դ���ݺ�
		//SpareStr9:���ʱ�־
		//SpareStr10:״̬
		//SpareStr11:������Դ
		//SpareStr12:�ʵ���
/*		yangchen 2007/2/11 move
		//SpareTime1:�ֿ�ʱ��1
		//SpareTime2:�ֿ�ʱ��2
*/
		//beginTime :������ʼʱ��
		//endTime   ����������ʱ��
		//SpareStr13:������������
		//SpareStr14:�������ݺ�
		//---------------���ڵ��ʲ�ѯ-------


		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************��������Ԥ��ֿۼ�¼��ѯ****************************************
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
		//SpareStr6:�ۿ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtPrePaymentType")))
			dto.setSpareStr6(request.getParameter("txtPrePaymentType"));
		//SpareStr7:��Դ��¼����
		if(WebUtil.StringHaveContent(request.getParameter("txtReferRecordType")))
			dto.setSpareStr7(request.getParameter("txtReferRecordType"));
		//SpareStr8:��Դ���ݺ�
		if(WebUtil.StringHaveContent(request.getParameter("txtReferRecordID")))
			dto.setSpareStr8(request.getParameter("txtReferRecordID"));
		//SpareStr9:���ʱ�־
		if(WebUtil.StringHaveContent(request.getParameter("txtInvoicedFlag")))
			dto.setSpareStr9(request.getParameter("txtInvoicedFlag"));
		//SpareStr10:״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr10(request.getParameter("txtStatus"));
		//SpareStr11:������Դ
		if(WebUtil.StringHaveContent(request.getParameter("txtCreatingMethod")))
			dto.setSpareStr11(request.getParameter("txtCreatingMethod"));
		//SpareStr12:�ʵ���
		if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceNo")))
			dto.setSpareStr12(request.getParameter("txtInvoiceNo"));
/*		yangchen 2007/2/11 move
		//SpareTime1:�ֿ�ʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:�ֿ�ʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));

*/		
/* yangchen 2007/2/11 add start*/
		//����ʱ��(��ʼ),����ʱ��(����)
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDateDatePart")))
	        dto.setBeginTime(WebUtil.StringToTimestampDefaultDayBegin(
	        		request.getParameter("txtCreateStartDateDatePart"),
					request.getParameter("txtCreateStartDateHourPart")
					));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDateDatePart")))
	        dto.setEndTime(WebUtil.StringToTimestampDefaultDayEnd(
	        		request.getParameter("txtCreateEndDateDatePart"),
					request.getParameter("txtCreateEndDateHourPart")
					));
	    //������������
	    if (WebUtil.StringHaveContent(request.getParameter("txtReferSheetType")))
			dto.setSpareStr13(request.getParameter("txtReferSheetType"));
	    //�������ݺ�
	    if (WebUtil.StringHaveContent(request.getParameter("txtReferSheetID")))
			dto.setSpareStr14(request.getParameter("txtReferSheetID"));
	    
/* yangchen 2007/2/11 add end*/
		//****************************����Ԥ��ֿۼ�¼��ѯ*****************************************
		//SpareStr1:��¼ID
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNO")) && (!"0".equals(request.getParameter("txtSeqNO")))){
			dto.setSpareStr1(request.getParameter("txtSeqNO"));
		}

		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_PREPAYMENT_DEDUCTION_RECORD);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
