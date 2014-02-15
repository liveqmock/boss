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
 * 营业应收费统计
 * author     
 * date       
 * description:
 * @author 250713z
 *
 */
public class BusinessAccountItemStatWebAction extends QueryWebAction {
	
	
	/**
	 * date       : 2007-1-19
	 * description:
	 * SpareStr1:客户类型
	 * SpareStr2:所在区域
	 * SpareTime1:创建起始时间
	 * SpareTime2:创建结束时间
	 * @author 
	 *
	 */
	
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        //SpareStr1:客户类型
        if (WebUtil.StringHaveContent(request.getParameter("txtCustType")))
            theDTO.setSpareStr1(request.getParameter("txtCustType"));
        //SpareStr2:所在区域
        if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
            theDTO.setSpareStr2(request.getParameter("txtDistrictID"));
        //SpareStr3:费用类型
        if (WebUtil.StringHaveContent(request.getParameter("txtFeeType")))
            theDTO.setSpareStr3(request.getParameter("txtFeeType"));
        //SpareStr4:帐单标记
        if (WebUtil.StringHaveContent(request.getParameter("txtInvoicedFlag")))
            theDTO.setSpareStr4(request.getParameter("txtInvoicedFlag"));
        //SpareStr5:单据类型
        if (WebUtil.StringHaveContent(request.getParameter("txtReferType")))
            theDTO.setSpareStr5(request.getParameter("txtReferType"));
        //SpareTime1:创建时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:创建时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			theDTO.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));	

        System.out.println(theDTO.toString());
        return new StatQueryEJBEvent(theDTO, pageFrom, pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_BUSINESS_ACCT_ITEM);

        //return null;

    }

}