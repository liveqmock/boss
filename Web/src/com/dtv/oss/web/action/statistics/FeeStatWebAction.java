package com.dtv.oss.web.action.statistics;

/**
 * <p>Title: BOSS P5</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Digivision</p>
 * @author Stone Liang
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * 
 * author     
 * date       
 * description:
 * @author 250713z
 *
 */
public class FeeStatWebAction extends QueryWebAction {
	
	
	/**
	 * date       : 2007-1-15
	 * description:
	 * SpareStr1:�ͻ�����
	 * SpareStr2:��������
	 * SpareStr3:�ʻ�����
	 * SpareStr4:��������
	 * SpareTime1:������ʼʱ��
	 * SpareTime2:��������ʱ��
	 * @author 
	 *
	 */
	
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
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
        if (WebUtil.StringHaveContent(request.getParameter("txtFeeType")))
            theDTO.setSpareStr4(request.getParameter("txtFeeType"));
        //SpareTime1:����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			theDTO.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));	

        System.out.println(theDTO.toString());
        return new StatQueryEJBEvent(theDTO, pageFrom, pageTo,
                                     StatQueryEJBEvent.
                                     QUERY_TYPE_STAT_FEE);

        //return null;

    }

}