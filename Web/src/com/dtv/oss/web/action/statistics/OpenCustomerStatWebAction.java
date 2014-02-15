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
public class OpenCustomerStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO��װ����˵��
		//------------------���ڿ���������ͳ��-------
		//SpareStr1:ͳ�Ʒ�ʽ
		//SpareStr2:���෽ʽ
		//SpareStr3:��֯
		//SpareStr4:����
		//SpareStr5:�ͻ�����
		//SpareStr6:��װ��ʽ
		//SpareStr7:����״̬
		//SpareStr8:��Դ����
		//SpareStr9:��Դ����ID
		//SpareStr10:ͳ������
		//SpareTime1:����ʱ��1
		//SpareTime2:����ʱ��2
		//SpareTime3:���ʱ��1
		//SpareTime4:���ʱ��2

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
		//SpareStr2:���෽ʽ
		if(WebUtil.StringHaveContent(request.getParameter("selSortType")))
			dto.setSpareStr2(request.getParameter("selSortType"));
		else
			throw new WebActionException("ͳ�Ʒ�ʽ�������󣺷��෽ʽ�ֶβ���Ϊ�գ�");
		//SpareStr3:��֯
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr3(request.getParameter("txtOrgID"));
		//SpareStr4:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
		//SpareStr5:�ͻ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtCustType")))
			dto.setSpareStr5(request.getParameter("txtCustType"));
		//SpareStr6:��װ��ʽ
		if(WebUtil.StringHaveContent(request.getParameter("txtInstallationType")))
			dto.setSpareStr6(request.getParameter("txtInstallationType"));
		//SpareStr7:����״̬
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr7(request.getParameter("txtStatus"));
		//SpareStr8:��Դ����
		if(WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
			dto.setSpareStr8(request.getParameter("txtOpenSourceType"));
		//SpareStr9:��Դ����ID
		if(WebUtil.StringHaveContent(request.getParameter("txtOpenSourceID")))
			dto.setSpareStr9(request.getParameter("txtOpenSourceID"));
		//SpareStr10:ͳ������
		if(WebUtil.StringHaveContent(request.getParameter("txtOpenType")))
			dto.setSpareStr10(request.getParameter("txtOpenType"));
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

		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_OPEN_CUSTOMER);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
