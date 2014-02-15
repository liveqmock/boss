package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * �������ͳ��
 * author     ��Jason.Zhou 
 * date       : 2006-3-14
 * description:
 * @author 250713z
 *
 */
public class SaledPorductPackageStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO��װ����˵��
		//------------------���ڲ�Ʒ���������ͳ��-------
		//SpareStr2:���෽ʽ
		//SpareStr3:��֯
		//SpareStr4:����
		//SpareStr5:�û�����
		//SpareStr9:��Դ����ID
		//SpareTime1:����ʱ��1
		//SpareTime2:����ʱ��2
		//SpareTime3:���ʱ��1
		//SpareTime4:���ʱ��2

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//SpareStr2:���෽ʽ
		if(WebUtil.StringHaveContent(request.getParameter("selSortType")))
			dto.setSpareStr2(request.getParameter("selSortType"));
		else
			throw new WebActionException("ͳ�Ʒ�ʽ�������󣺲�Ʒ��״̬�ֶβ���Ϊ�գ�");

		//SpareStr4:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
		//SpareStr5:�û�����
		if(WebUtil.StringHaveContent(request.getParameter("txtCustType")))
			dto.setSpareStr5(request.getParameter("txtCustType"));
		
		//SpareTime1:����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		//SpareTime3:���ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime3")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime3")));
		//SpareTime4:���ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime4")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime4")));

		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_SALED_PRODUCT_PACKAGE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
