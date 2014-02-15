package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryGroupLeafCustWebAction extends QueryWebAction {

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        
        //客户证号
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID") ) );
        	//theDTO.setNo(request.getParameter("txtCustomerID"));
        //客户姓名
        if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            theDTO.setSpareStr1(request.getParameter("txtName"));
        //安装地址
        if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
            theDTO.setSpareStr3(request.getParameter("txtDetailAddress"));
        //来源渠道
        if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
            theDTO.setSpareStr13(request.getParameter("txtOpenSourceType"));
        //证件号
        if (WebUtil.StringHaveContent(request.getParameter("txtCardID")))
            theDTO.setSpareStr5(request.getParameter("txtCardID"));
        //社保卡号
        if (WebUtil.StringHaveContent(request.getParameter("txtSocialSecCardID")))
            theDTO.setSpareStr6(request.getParameter("txtSocialSecCardID"));
        //固定电话
        if (WebUtil.StringHaveContent(request.getParameter("txtTelephone")))
            theDTO.setSpareStr7(request.getParameter("txtTelephone"));
        //移动电话
        if (WebUtil.StringHaveContent(request.getParameter("txtTelephoneMobile")))
            theDTO.setSpareStr8(request.getParameter("txtTelephoneMobile"));
        //付费方式
        if (WebUtil.StringHaveContent(request.getParameter("txtMopID")))
            theDTO.setSpareStr9(request.getParameter("txtMopID"));
        //银行账号
        if (WebUtil.StringHaveContent(request.getParameter("txtBankAccount")))
            theDTO.setSpareStr10(request.getParameter("txtBankAccount"));
        
        //add by jason 2006-7-3 
        //Mac地址
        if (WebUtil.StringHaveContent(request.getParameter("txtMacAddress")))
            theDTO.setSpareStr11(request.getParameter("txtMacAddress"));
        //内部Mac地址 
        if (WebUtil.StringHaveContent(request.getParameter("txtInterMacAddress")))
            theDTO.setSpareStr12(request.getParameter("txtInterMacAddress"));
        //end 
        
        // 服务号
        if (WebUtil.StringHaveContent(request.getParameter("txtServiceCode")))
        	theDTO.setSpareStr14(request.getParameter("txtServiceCode"));
        //区域
        if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
        	theDTO.setSpareStr15(request.getParameter("txtDistrictID"));
        //组织
        if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
        	theDTO.setSpareStr16(request.getParameter("txtOrgID"));
        
         //客户类型
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
            theDTO.setType(request.getParameter("txtCustomerType"));
        //客户状态
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
            theDTO.setStatus(request.getParameter("txtStatus"));
        //创建日期
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
            theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
            theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));

        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerStyle")))
        	theDTO.setSpareStr2(request.getParameter("txtCustomerStyle"));
        if (WebUtil.StringHaveContent(request.getParameter("txtGroupCustID")))
        	theDTO.setSpareStr4(request.getParameter("txtGroupCustID"));
        if (WebUtil.StringHaveContent(request.getParameter("txtContractNo")))
        	theDTO.setSpareStr17(request.getParameter("txtContractNo"));
                
        theDTO.setOrderField(orderByFieldName);
        theDTO.setIsAsc(!orderDescFlag);
        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTOMER);
        //return null;

    }
    
}