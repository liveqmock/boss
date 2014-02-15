package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class PayStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		/**
		 * DTO��װ����˵��
		 * ------------------֧��ͳ��/Ԥ��ͳ��-------
		 * date       : 2007-1-15
		 * description:
		 * SpareStr1:�ͻ�����
		 * SpareStr2:��������
		 * SpareStr3:�ʻ�����
		 * SpareStr4:��������
		 * SpareStr5:ҳ��  ������֧��ͳ��/Ԥ��ͳ�ƹ���
		 * SpareTime1:������ʼʱ��
		 * SpareTime2:��������ʱ��
		 * @author 
		 *
		 */

		CommonQueryConditionDTO	theDTO = new CommonQueryConditionDTO();
		
		//SpareStr1:�ͻ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtCustType")))
            theDTO.setSpareStr1(request.getParameter("txtCustType"));
        //SpareStr2:��������
        if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
            theDTO.setSpareStr2(request.getParameter("txtDistrictID"));
        //SpareStr3:�ʻ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtAcctType")))
            theDTO.setSpareStr3(request.getParameter("txtAcctType"));
        //SpareStr4:��������
        if (WebUtil.StringHaveContent(request.getParameter("txtMOP")))
            theDTO.setSpareStr4(request.getParameter("txtMOP"));
        //SpareStr5:ҳ��
        if (WebUtil.StringHaveContent(request.getParameter("txtPage")))
            theDTO.setSpareStr5(request.getParameter("txtPage"));
        //SpareTime1:����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			theDTO.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));	

		return new StatQueryEJBEvent(theDTO,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_PAY);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
