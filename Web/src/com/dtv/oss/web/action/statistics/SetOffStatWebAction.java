package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class SetOffStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO��װ����˵��
		//------------------�����������ͳ��-------
		//SpareStr1:ͳ�Ʒ�ʽ
		//SpareStr2:��֯
		//SpareStr3:����
		//SpareStr4:ʵ�չ�������
		//SpareTime1:����ʱ��1
		//SpareTime2:����ʱ��2

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//��֯������ͬͬʱ��ѡ��
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("ͳ�Ʒ�ʽ����������֯��������ͬʱ��ѡ��");
			
		//SpareStr2:��֯
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr2(request.getParameter("txtOrgID"));
		//SpareStr3:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr3(request.getParameter("txtDistrictID"));
		////SpareStr4:ʵ�չ�������
		if(WebUtil.StringHaveContent(request.getParameter("txtPlusReferType")))
			dto.setSpareStr4(request.getParameter("txtPlusReferType"));
		else
			throw new WebActionException("ͳ�Ʒ�ʽ��������");
		//SpareTime1:������ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:������ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));

		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_SETOFF);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
