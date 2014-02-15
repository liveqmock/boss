package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryPaymentRecordAllWebAction extends DownloadWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO��װ����˵��
		//------------------����֧����ѯ-------
		//SpareStr1:֧�����
		//SpareStr2:�ͻ�֤��
		//SpareStr3:�ʻ���
		//SpareStr4:��֯
		//SpareStr5:����
		//SpareStr6:���ѷ�ʽ
		//SpareStr7:֧�����
		//SpareStr8:����ȯ����
		//SpareStr9:ȯ��
		//SpareStr10:������������
		//SpareStr11:�������ݺ�
		//SpareStr12:��Դ
		//SpareStr13:��Դ���ݺ�
		//SpareStr14:״̬
		//SpareStr15:���ʱ�־
		//SpareStr16:�ʵ���
/*	2007/2/11 yangchen move	
		//SpareTime1:֧��ʱ��1
		//SpareTime2:֧��ʱ��2
*/
		//beginTime :������ʼʱ��
		//endTime   ����������ʱ��
		
		//SpareStr17:������Դ
		//SpareStr18:Ԥ�������
		//SpareStr19:�շ���operatorid
		//---------------����֧����ѯ-------


		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************��������֧����¼��ѯ****************************************
		//SpareStr2:�ͻ�֤��(����ʹ����������ͬ����Դ����,һ���Ǹ���������µ�֧����¼��ѯ�õ�(txtCustID,txtAcctID),һ���Ǹ��ͻ��µ��õ�(txtCustomerID,txtAccountID) add by �)
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID")))
			dto.setSpareStr2(request.getParameter("txtCustID"));
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setSpareStr2(request.getParameter("txtCustomerID"));
		//SpareStr3:�ʻ���
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID")))
			dto.setSpareStr3(request.getParameter("txtAcctID"));
		if(WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
			dto.setSpareStr3(request.getParameter("txtAccountID"));
		//SpareStr4:��֯���տ���֯��
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr4(request.getParameter("txtOrgID"));
		//SpareStr5:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr5(request.getParameter("txtDistrictID"));
		//SpareStr6:���ѷ�ʽ
		if(WebUtil.StringHaveContent(request.getParameter("txtMOPID")))
			dto.setSpareStr6(request.getParameter("txtMOPID"));
		//SpareStr7:֧�����
		if(WebUtil.StringHaveContent(request.getParameter("txtPayType")))
			dto.setSpareStr7(request.getParameter("txtPayType"));
		//SpareStr8:����ȯ����
		if(WebUtil.StringHaveContent(request.getParameter("txtTicketType")))
			dto.setSpareStr8(request.getParameter("txtTicketType"));
		//SpareStr9:ȯ��
		if(WebUtil.StringHaveContent(request.getParameter("txtTicketNo")))
			dto.setSpareStr9(request.getParameter("txtTicketNo"));
		//SpareStr10:������������
		if(WebUtil.StringHaveContent(request.getParameter("txtReferType")))
			dto.setSpareStr10(request.getParameter("txtReferType"));
		//SpareStr11:�������ݺ�
		if(WebUtil.StringHaveContent(request.getParameter("txtReferID")))
			dto.setSpareStr11(request.getParameter("txtReferID"));
		//SpareStr12:��Դ
		if(WebUtil.StringHaveContent(request.getParameter("txtSourceType")))
			dto.setSpareStr12(request.getParameter("txtSourceType"));
		//SpareStr13:��Դ���ݺ�
		if(WebUtil.StringHaveContent(request.getParameter("txtSourceRecordID")))
			dto.setSpareStr13(request.getParameter("txtSourceRecordID"));
		//SpareStr14:״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr14(request.getParameter("txtStatus"));
		//SpareStr15:���ʱ�־
		if(WebUtil.StringHaveContent(request.getParameter("txtInvoicedFlag")))
			dto.setSpareStr15(request.getParameter("txtInvoicedFlag"));
        //SpareStr16:�ʵ���
		if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceNo")))
			dto.setSpareStr16(request.getParameter("txtInvoiceNo"));
/*  2007/2/11 yangchen move	
		//SpareTime1:������ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtPaymentTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPaymentTime1")));
		//SpareTime2:������ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtPaymentTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPaymentTime2")));
*/
/*   yangchen 2007/2/11  add  start */		
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
	    //������Դ
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreatingMethod")))
			dto.setSpareStr17(request.getParameter("txtCreatingMethod"));
	    //Ԥ�������
	    if (WebUtil.StringHaveContent(request.getParameter("txtPrePaymentType")))
			dto.setSpareStr18(request.getParameter("txtPrePaymentType"));
	    //�տ���operatorid
	    if (WebUtil.StringHaveContent(request.getParameter("txtCollectorID")))
			dto.setSpareStr19(request.getParameter("txtCollectorID"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtAddress")))
	    	dto.setSpareStr20(request.getParameter("txtAddress"));
/*   yangchen 2007/2/11  add  end */			
		//****************************������ϸ֧����¼��ѯ*****************************************
		//SpareStr1:��¼ID
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNO")) && (!"0".equals(request.getParameter("txtSeqNO")))){
			dto.setSpareStr1(request.getParameter("txtSeqNO"));
		}

		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_PAMENTT_RECORD);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
