package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class BatchSendMessageQueryWebAction extends DownloadWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		//DTO��װ����˵��
		//------------------��������������Ϣ��Ϣ��ѯ------
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//*************************************��������������Ϣ��Ϣ��ѯ****************************************
		if(WebUtil.StringHaveContent(request.getParameter("txtJobType")))
			dto.setSpareStr1(request.getParameter("txtJobType"));
		
		//SpareTime1:�������ڿ�ʼ��
		if(WebUtil.StringHaveContent(request.getParameter("txtImportDateFrom")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtImportDateFrom")));
		//SpareTime2:�������ڽ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtImportDateTo")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtImportDateTo")));
		//SpareStr1:��¼ID
		if(WebUtil.StringHaveContent(request.getParameter("txtNo")) && (!"0".equals(request.getParameter("txtNo")))){
			//���и�ʽ���
			if(WebUtil.StringToInt(request.getParameter("txtNo"))==0)
				throw new WebActionException("��¼ID��ʽ���󣬱���Ϊ������");
			dto.setSpareStr1(request.getParameter("txtNo"));
			setSubType(request.getParameter("txtSubType"));
			setQueryParameter(new Integer(request.getParameter("txtNo")));
		}
		return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BATCH_SEND_MESSAGE);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
