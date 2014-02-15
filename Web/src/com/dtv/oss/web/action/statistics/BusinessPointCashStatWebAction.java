package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class BusinessPointCashStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		/**
		 * DTO��װ����˵��
		 * ------------------Ӫҵ���շ�ͳ��-------
		 * date       : 2007-1-15
		 * description:
		 * SpareStr4:�ͻ�����
		 * SpareStr3:���ڻ���
		 * SpareTime1:֧��ʱ��1
		 * SpareTime2:֧��ʱ��2
		 * @author 
		 *
		 */

		CommonQueryConditionDTO	theDTO = new CommonQueryConditionDTO();
//		��֯������ͬͬʱ��ѡ��
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			throw new WebActionException("ͳ�Ʒ�ʽ����������֯��������ͬʱ��ѡ��");
		//SpareStr1:ͳ�Ʒ�ʽ
		if(WebUtil.StringHaveContent(request.getParameter("selStatType")))
			theDTO.setSpareStr1(request.getParameter("selStatType"));
		else
			throw new WebActionException("ͳ�Ʒ�ʽ��������:ͳ�Ʒ�ʽ�ֶβ���Ϊ�գ�");
		//SpareStr2:��������
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			theDTO.setSpareStr2(request.getParameter("txtDistrictID"));	
		//SpareStr3:�����շѻ���
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			theDTO.setSpareStr3(request.getParameter("txtOrgID"));
		//SpareStr4:�ͻ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtCustType")))
            theDTO.setSpareStr4(request.getParameter("txtCustType"));
        
        //SpareStr5:�ʻ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtAccountType")))
            theDTO.setSpareStr5(request.getParameter("txtAccountType"));
        
        //SpareStr6:�շ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtPayType")))
            theDTO.setSpareStr6(request.getParameter("txtPayType"));
        //SpareStr7:֧����ʽ
        if (WebUtil.StringHaveContent(request.getParameter("txtMOPId")))
            theDTO.setSpareStr7(request.getParameter("txtMOPId"));
        
        
        //SpareTime1:֧��ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:֧��ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			theDTO.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));	

		return new StatQueryEJBEvent(theDTO,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_BUSINESS_POINT_CASH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
