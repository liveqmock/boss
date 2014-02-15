package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryBankDeductionWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		//DTO��װ����˵��
		//------------------��������ͷ��¼��ѯ-------
		//SpareStr1��������
		//SpareStr2:����״̬
		//SpareStr3:֧����ʽ
		//SpareStr4:��������
		//SpareTime1:��¼������ʼʱ��
		//SpareTime2:��¼������ֹʱ��
		//---------------��������ͷ��¼��ѯ-------
		
		//------------����������ϸ�Ĳ�ѯ-----
		//SpareStr5:�ͻ�֤��
		//SpareStr6:�ͻ�����
		//SpareStr7:�ͻ���������
		//---------����������ϸ�Ĳ�ѯ-----
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//*************************************����ͷ��¼��ѯ****************************************
		//SpareStr1��������
		if(WebUtil.StringHaveContent(request.getParameter("txtBatchNo")))
			dto.setSpareStr1(request.getParameter("txtBatchNo"));
		//SpareStr2:����״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtProcessStatus")))
			dto.setSpareStr2(request.getParameter("txtProcessStatus"));
		//SpareStr3:֧����ʽ
		if(WebUtil.StringHaveContent(request.getParameter("txtMopID")))
			dto.setSpareStr3(request.getParameter("txtMopID"));
		//SpareStr4:��������
		if(WebUtil.StringHaveContent(request.getParameter("txtBillingCycleID")))
			dto.setSpareStr4(request.getParameter("txtBillingCycleID"));
		//SpareTime1:��¼������ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:��¼������ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		
		
		//****************************����������ϸ�Ĳ�ѯ*****************************************
		//SpareStr5:�ͻ�֤��
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")) && (!"0".equals(request.getParameter("txtCustomerID")))){
			//���и�ʽ���
			if(WebUtil.StringToInt(request.getParameter("txtCustomerID"))==0)
				throw new WebActionException("�ͻ�֤�ţ�����Ϊ������");
			dto.setSpareStr5(request.getParameter("txtCustomerID"));
		}
		//SpareStr6:�ͻ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
			dto.setSpareStr6(request.getParameter("txtCustomerType"));
		//SpareStr7:�ͻ���������
		if(WebUtil.StringHaveContent(request.getParameter("txtCustDistrictID")))
			dto.setSpareStr7(request.getParameter("txtCustDistrictID"));
		//SpareStr7:�ͻ���������
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			dto.setSpareStr8(request.getParameter("txtSeqNo"));
		
		if("header".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BANK_DEDUCTION_HEADER);
		else if("detail".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BANK_DEDUCTION_DETAIL);
		else 
			throw new WebActionException("��ѯ����δ֪���޷���ɴ˲�����");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
