package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchJobEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryBatchJobWebAction extends DownloadWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		//DTO��װ����˵��
		//------------------�������������񵥲�ѯ-------
		//SpareStr1:���񵥺�
		//SpareStr2:��������
		//SpareStr3:����״̬
		//SpareStr4:��������
		//SpareStr5:�趨ִ�з�ʽ
		//SpareStr6:������
		//SpareTime1:������ʼʱ��
		//SpareTime2:������ֹʱ��
		//SpareTime3:Ԥ��ִ����ʼʱ��
		//SpareTime4:Ԥ��ִ�н�ֹʱ��
		//---------------�����������������񵥲�ѯ-------

		//------------�������������񵥽�����Ĳ�ѯ-----
		//SpareStr1:���񵥺�
		//---------�����������������񵥽�����Ĳ�ѯ-----

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************�������������񵥲�ѯ****************************************
		//SpareStr2:��������
		if(WebUtil.StringHaveContent(request.getParameter("txtBatchJobName")))
			dto.setSpareStr2(request.getParameter("txtBatchJobName"));
		//SpareStr3:����״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtBatchJobStatus")))
			dto.setSpareStr3(request.getParameter("txtBatchJobStatus"));
		//SpareStr4:��������
		if(WebUtil.StringHaveContent(request.getParameter("txtJobType")))
			dto.setSpareStr4(request.getParameter("txtJobType"));
		//SpareStr5:�趨ִ�з�ʽ
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleType")))
			dto.setSpareStr5(request.getParameter("txtScheduleType"));
		//SpareStr6:������
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateName")))
			dto.setSpareStr6(request.getParameter("txtCreateName"));
		//SpareTime1:������ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
		//SpareTime2:������ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));
		//SpareTime3:Ԥ��ִ����ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtActionStartDate")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtActionStartDate")));
		//SpareTime4:Ԥ��ִ�н�ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtActionEndDate")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtActionEndDate")));

		//****************************�������������񵥽�����Ĳ�ѯ*****************************************
		//SpareStr1:��¼ID
		if(WebUtil.StringHaveContent(request.getParameter("txtBatchID")) && (!"0".equals(request.getParameter("txtBatchID")))){
			//���и�ʽ���
			if(WebUtil.StringToInt(request.getParameter("txtBatchID"))==0)
				throw new WebActionException("��¼ID��ʽ���󣬱���Ϊ������");
			dto.setSpareStr1(request.getParameter("txtBatchID"));
			setSubType(request.getParameter("txtJobType"));
			setQueryParameter(new Integer(request.getParameter("txtBatchID")));
		}

		if("batch".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchJobEJBEvent(dto,pageFrom,pageTo,QueryBatchJobEJBEvent.BATCHJOB_QUERY);
		else if("result".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchJobEJBEvent(dto,pageFrom,pageTo,QueryBatchJobEJBEvent.BATCHJOB_ITEM_QUERY);
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
