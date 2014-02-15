package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryBatchWebAction extends DownloadWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		//DTO��װ����˵��
		//------------------����������ѯ-------
		//SpareStr1:��ѯ��������
		//SpareStr2:���������
		//SpareStr3:ִ�з�ʽ
		//SpareStr4:״̬
		//SpareStr5:״̬2
		//SpareTime1:��¼������ʼʱ��
		//SpareTime2:��¼������ֹʱ��
		//SpareTime3:Ԥ��ִ����ʼʱ��
		//SpareTime4:Ԥ��ִ�н�ֹʱ��
		//SpareTime5:ʵ��ִ����ʼʱ��
		//SpareTime6:ʵ��ִ�н�ֹʱ��
		//---------------��������������ѯ-------
		
		//------------����������ѯ����Ĳ�ѯ-----
		//SpareStr8:��¼ID
		//SpareStr9:״̬
		//SpareStr10:�û�֤��
		//SpareStr11:�˻���
		//SpareStr12:ҵ���˻���
		//SpareStr13:��Ӫ�̲�Ʒ
		//SpareStr14:��Ӫ�̲�Ʒ��
		//SpareStr15:�����
		//---------��������������ѯ����Ĳ�ѯ-----
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//*************************************����������ѯ****************************************
		//�ݽ���ѯ��ҳ������ ���������Ƿ�ģ���ѯ
		if(WebUtil.StringHaveContent(request.getParameter("txtTemplateFlag")))
			dto.setSpareStr6(request.getParameter("txtTemplateFlag"));
		//SpareStr1:��ѯ��������
		if(WebUtil.StringHaveContent(request.getParameter("txtQueryName")))
			dto.setSpareStr1(request.getParameter("txtQueryName"));
		//SpareStr2:���������
		if(WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
			dto.setSpareStr2(request.getParameter("txtQueryType"));
		//SpareStr3:ִ�з�ʽ
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleType")))
			dto.setSpareStr3(request.getParameter("txtScheduleType"));
		//SpareStr4:״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr4(request.getParameter("txtStatus"));
		//SpareStr5:״̬2
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus1")))
			dto.setSpareStr5(request.getParameter("txtStatus1"));
		//SpareTime1:��¼������ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:��¼������ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		//SpareTime3:Ԥ��ִ����ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleTime1")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtScheduleTime1")));
		//SpareTime4:Ԥ��ִ�н�ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleTime2")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtScheduleTime2")));
		//SpareTime5:ʵ��ִ����ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtPerformTime1")))
			dto.setSpareTime5(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPerformTime1")));
		//SpareTime6:ʵ��ִ�н�ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtPerformTime2")))
			dto.setSpareTime6(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPerformTime2")));
		
		
		//****************************����������ѯ����Ĳ�ѯ*****************************************
		
		//SpareStr8:��¼ID
		if(WebUtil.StringHaveContent(request.getParameter("txtQueryID")) && (!"0".equals(request.getParameter("txtQueryID")))){
			//���и�ʽ���
			if(WebUtil.StringToInt(request.getParameter("txtQueryID"))==0)
				throw new WebActionException("��¼ID��ʽ���󣬱���Ϊ������");
			dto.setSpareStr8(request.getParameter("txtQueryID"));
			setSubType(request.getParameter("txtQueryType"));
			setQueryParameter(new Integer(request.getParameter("txtQueryID")));
		}
		//SpareStr9:״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtRusultItemStatus")))
			dto.setSpareStr9(request.getParameter("txtRusultItemStatus"));
		//SpareStr10:�û�֤��
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setSpareStr10(request.getParameter("txtCustomerID"));
		//SpareStr11:�˻���
		if(WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
			dto.setSpareStr11(request.getParameter("txtAccountID"));
		//SpareStr12:ҵ���˻���
		if(WebUtil.StringHaveContent(request.getParameter("txtUserID")))
			dto.setSpareStr12(request.getParameter("txtUserID"));
		//SpareStr13:��Ӫ�̲�Ʒ
		if(WebUtil.StringHaveContent(request.getParameter("txtCPProductIDList")))
			dto.setSpareStr13(request.getParameter("txtCPProductIDList"));
		//SpareStr14:��Ӫ�̲�Ʒ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCPProductPackageIDList")))
			dto.setSpareStr14(request.getParameter("txtCPProductPackageIDList"));
		//SpareStr15:�����
		if(WebUtil.StringHaveContent(request.getParameter("txtCPCampaignIDList")))
			dto.setSpareStr15(request.getParameter("txtCPCampaignIDList"));
		
		if("batch".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BATCH_QUERY);
		else if("result".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BATCH_RESULT_ITEM);
		else 
			throw new WebActionException("���Ҳ�������");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
