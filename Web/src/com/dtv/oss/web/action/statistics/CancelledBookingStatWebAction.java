package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * ��װʧ��ͳ��
 * author     ��Jason.Zhou 
 * date       : 2006-3-14
 * description:
 * @author 250713z
 *
 */
public class CancelledBookingStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO��װ����˵��
		//------------------���ڰ�װʧ��ͳ��-------
		//SpareStr1:ͳ�Ʒ�ʽ
		//SpareStr2:��Դ����
		//SpareStr3:��֯
		//SpareStr4:����
		//SpareTime1:����ʱ��1
		//SpareTime2:����ʱ��2
		//SpareTime3:ԤԼ����ʱ��1
		//SpareTime4:ԤԼ����ʱ��2
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//��֯������ͬͬʱ��ѡ��
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("ͳ�Ʒ�ʽ����������֯��������ͬʱ��ѡ��");
			
		//SpareStr1:ͳ�Ʒ�ʽ
		if(WebUtil.StringHaveContent(request.getParameter("selStatType")))
			dto.setSpareStr1(request.getParameter("selStatType"));
		else
			throw new WebActionException("ͳ�Ʒ�ʽ��������:ͳ�Ʒ�ʽ�ֶβ���Ϊ�գ�");
		
		//SpareStr2:��Դ����
		if(WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
			dto.setSpareStr2(request.getParameter("txtOpenSourceType"));
		
		//SpareStr3:��֯
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr3(request.getParameter("txtOrgID"));
		//SpareStr4:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
	
		//SpareTime1:����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		//SpareTime3:ԤԼ����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime3")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime3")));
		//SpareTime4:ԤԼ����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime4")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime4")));
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_CANCELED_BOOKING);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
