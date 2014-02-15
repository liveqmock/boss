package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * ����ͳ��
 * date       : 2007-1-17
 * description:
 * @author  
 *
 */
public class AdjustAccountStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO��װ����˵��
		//------------------���� ����ͳ��-------
		//SpareStr1:ͳ�Ʒ�ʽ
		//SpareStr2:������֯
		//SpareStr3:��������
		//SpareStr4:�ͻ�����
		//SpareStr5:���ʷ�ʽ
		//SpareStr6:�ʻ�����
		//SpareStr7:����ԭ��
		//SpareTime1:����ʱ��1
		//SpareTime2:����ʱ��2
		
		//��֯������ͬͬʱ��ѡ��
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("ͳ�Ʒ�ʽ����������֯��������ͬʱ��ѡ��");
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		//SpareStr1:ͳ�Ʒ�ʽ
		if(WebUtil.StringHaveContent(request.getParameter("selStatType")))
			dto.setSpareStr1(request.getParameter("selStatType"));
		else
			throw new WebActionException("ͳ�Ʒ�ʽ��������:ͳ�Ʒ�ʽ�ֶβ���Ϊ�գ�");
		//SpareStr2:������֯
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr2(request.getParameter("txtOrgID"));	
		//SpareStr3:��������
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr3(request.getParameter("txtDistrictID"));
		//SpareStr4:�ͻ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtCustType")))
			dto.setSpareStr4(request.getParameter("txtCustType"));
		//SpareStr5:���ʷ�ʽ
		if(WebUtil.StringHaveContent(request.getParameter("txtAdjustType")))
			dto.setSpareStr5(request.getParameter("txtAdjustType"));
		//�ʻ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctType")))
			dto.setSpareStr6(request.getParameter("txtAcctType"));
		//����ԭ��
		if(WebUtil.StringHaveContent(request.getParameter("txtAdjustReason")))
			dto.setSpareStr7(request.getParameter("txtAdjustReason"));
		//SpareTime1:����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		
		
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_ADJUSTACCOUNT);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
