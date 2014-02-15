package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryGeneralCustomerWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author Yangyong
 */
public class QueryInteractionOfBookingWebAction extends QueryGeneralCustomerWebAction {

	 protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        
        //预约单编号
        if (WebUtil.StringHaveContent(request.getParameter("txtID")))
            theDTO.setNo(request.getParameter("txtID"));
        
        //客户证号 --注意对于预约的查询txtCustomerID在页面上的传值是0
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	theDTO.setSpareStr1(request.getParameter("txtCustomerID"));
        //创建时间
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
        	theDTO.setBeginTime(WebUtil.StringToTimestamp("txtCreateStartDate"));
        
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
        	theDTO.setEndTime(WebUtil.StringToTimestamp("txtCreateEndDate"));
        
        //联系人姓名
        if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            theDTO.setSpareStr11(request.getParameter("txtName"));
        
        //联系电话
        if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
        	theDTO.setSpareStr12(request.getParameter("txtContactPhone"));
        
        //来源渠道
        if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
            theDTO.setSpareStr13(request.getParameter("txtOpenSourceType"));
        
        //来源渠道ID
        if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceID")))
           theDTO.setSpareStr14(request.getParameter("txtOpenSourceID"));
        
        //所在区
        if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
           theDTO.setStreet(WebUtil.StringToInt(request.getParameter("txtCounty")));
        
        //工单编号
        if (WebUtil.StringHaveContent(request.getParameter("txtJobCardId")))
           theDTO.setSpareStr15(request.getParameter("txtJobCardId"));
        
        //详细地址
        if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
           theDTO.setSpareStr16(request.getParameter("txtDetailAddress"));
        
        //受理单状态
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus"))){
          theDTO.setStatus(request.getParameter("txtStatus"));
        }
        //受理单类型
       	theDTO.setType(CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK);
        
        theDTO.setOrderField(orderByFieldName);
        theDTO.setIsAsc(!orderDescFlag);
        
        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTSERVICEINTERACTION);
	 }
	
}
