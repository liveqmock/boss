/*
 * 创建日期 2005-10-12
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.WorkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * @author chen jiang
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class QueryJobCardWebAction extends QueryWebAction {


  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request)
      throws Exception {
    CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
    if (WebUtil.StringHaveContent(request.getParameter("txtJobCardID")))
        dto.setNo(request.getParameter("txtJobCardID"));
    
    
    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
      dto.setStatus(request.getParameter("txtStatus"));
    if (WebUtil.StringHaveContent(request.getParameter("txtType")))
      dto.setType(request.getParameter("txtType"));
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateOperatorName")))
		dto.setOperator(request.getParameter("txtCreateOperatorName"));
    
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
    //CustOrgID
    if (WebUtil.StringHaveContent(request.getParameter("txtCustOrgID")))
        dto.setFiliale(WebUtil.StringToInt(request.getParameter("txtCustOrgID")));
    //CustStreetStationID 
    if (WebUtil.StringHaveContent(request.getParameter("txtCustStreetStationID")))
        dto.setStreet(WebUtil.StringToInt(request.getParameter("txtCustStreetStationID")));

    
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDateDatePart")))
        dto.setBeginTime(WebUtil.StringToTimestampDefaultDayBegin(
        		request.getParameter("txtCreateStartDateDatePart"),
				request.getParameter("txtCreateStartDateHourPart")));
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDateDatePart")))
        dto.setEndTime(WebUtil.StringToTimestampDefaultDayEnd(
        		request.getParameter("txtCreateEndDateDatePart"),
				request.getParameter("txtCreateEndDateHourPart")));
    
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
        dto.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
        dto.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));
        
    //维修预约时间精确到日
    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedStartDate")))
    	dto.setSpareBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPreferedStartDate")));
    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedEndDate")))
    	dto.setSpareEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPreferedEndDate")));
    
    if (WebUtil.StringHaveContent(request.getParameter("txtCloseStartDateDatePart")))
    	dto.setSpareTime1(WebUtil.StringToTimestampDefaultDayBegin(
    			request.getParameter("txtCloseStartDateDatePart"),
    			request.getParameter("txtCloseStartDateHourPart")));     
    if (WebUtil.StringHaveContent(request.getParameter("txtCloseEndDateDatePart")))
    	dto.setSpareTime2(WebUtil.StringToTimestampDefaultDayEnd(
    			request.getParameter("txtCloseEndDateDatePart"),
    			request.getParameter("txtCloseEndDateHourPart")
    			));
      
    if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
        dto.setSpareStr1(request.getParameter("txtDetailAddress"));
    
    if (WebUtil.StringHaveContent(request.getParameter("txtWorkResultReason")))
      dto.setSpareStr2(request.getParameter("txtWorkResultReason"));
    if (WebUtil.StringHaveContent(request.getParameter("txtInstallFailedReason")))
    {
    	//如果选择了安装失败原因只查安装单
        dto.setSpareStr2(request.getParameter("txtInstallFailedReason"));
        dto.setType("I");
    }
    if (WebUtil.StringHaveContent(request.getParameter("txtRepairFailedReason")))
    {
    	// 如果选择了安装失败原因只查维修单
        dto.setSpareStr2(request.getParameter("txtRepairFailedReason"));
        dto.setType("R");
    }
    
    if (WebUtil.StringHaveContent(request.getParameter("txtContactResult")))
      dto.setSpareStr3(request.getParameter("txtContactResult"));
    if (WebUtil.StringHaveContent(request.getParameter("txtWorkResult")))
        dto.setSpareStr4(request.getParameter("txtWorkResult"));
    if (WebUtil.StringHaveContent(request.getParameter("txtReferSheetID")))
        dto.setSpareStr5(request.getParameter("txtReferSheetID"));
    if (WebUtil.StringHaveContent(request.getParameter("txtCSIStatus")))
        dto.setSpareStr6(request.getParameter("txtCSIStatus"));
       
    if (WebUtil.StringHaveContent(request.getParameter("txtOutOfDateReason")))
        dto.setSpareStr7(request.getParameter("txtOutOfDateReason"));
    
    //----add 2005 /01/29   start----------------------
    if (WebUtil.StringHaveContent(request.getParameter("txtTroubleSubType")))
        dto.setSpareStr8(request.getParameter("txtTroubleSubType"));
    if (WebUtil.StringHaveContent(request.getParameter("txtTroubleType")))
        dto.setSpareStr9(request.getParameter("txtTroubleType"));
    if (WebUtil.StringHaveContent(request.getParameter("txtWorkComments")))
        dto.setSpareStr10(request.getParameter("txtWorkComments"));   
    //  ----add 2005 /01/29   end----------------------
    if (WebUtil.StringHaveContent(request.getParameter("txtResolutionType")))
        dto.setSpareStr11(request.getParameter("txtResolutionType"));
    if (WebUtil.StringHaveContent(request.getParameter("txtProblemCategory")))
        dto.setSpareStr12(request.getParameter("txtProblemCategory"));
    //  ----add 2005 /03/09   start----------------------
    if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
        dto.setSpareStr13(request.getParameter("txtOpenSourceType"));
    if (WebUtil.StringHaveContent(request.getParameter("txtFeeClass")))
        dto.setSpareStr14(request.getParameter("txtFeeClass"));
 
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
        dto.setSpareStr15(request.getParameter("txtCustomerType"));
    if (WebUtil.StringHaveContent(request.getParameter("txtStatusReason")))
        dto.setSpareStr16(request.getParameter("txtStatusReason"));
 
 return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_JOBCARD);
  }

}
