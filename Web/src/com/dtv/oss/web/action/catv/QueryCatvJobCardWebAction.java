/*
 * 创建日期 2005-10-12
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dtv.oss.web.action.catv;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.catv.CatvJobCardEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * @author chen jiang
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class QueryCatvJobCardWebAction extends QueryWebAction {


  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request)
      throws Exception {
    CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
    //工单编号
    if (WebUtil.StringHaveContent(request.getParameter("txtJobCardID")))
        dto.setNo(request.getParameter("txtJobCardID"));
    //客户号
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
    //工单子类型
    if (WebUtil.StringHaveContent(request.getParameter("txtSubType")))
        dto.setSpareStr1(request.getParameter("txtSubType"));
    //  工单状态类型
    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
      dto.setStatus(request.getParameter("txtStatus"));
    //客户类型
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
        dto.setSpareStr2(request.getParameter("txtCustomerType"));
    //  所在区域
    if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
        dto.setSpareStr3(request.getParameter("txtCounty"));
    //创建时间
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDateDatePart")))
        dto.setBeginTime(WebUtil.StringToTimestampDefaultDayBegin(
        		request.getParameter("txtCreateStartDateDatePart"),
				request.getParameter("txtCreateStartDateHourPart")));
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDateDatePart")))
        dto.setEndTime(WebUtil.StringToTimestampDefaultDayEnd(
        		request.getParameter("txtCreateEndDateDatePart"),
				request.getParameter("txtCreateEndDateHourPart")));
    /**
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
        dto.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
        dto.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));
    **/
	if (WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
		dto.setSpareStr19(request.getParameter("txtQueryType"));
    //  预约时间精确到日
    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedStartDate")))
    	dto.setSpareBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPreferedStartDate")));
    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedEndDate")))
    	dto.setSpareEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPreferedEndDate")));
    //  完成时间
    if (WebUtil.StringHaveContent(request.getParameter("txtFinishedStartDate")))
    	dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtFinishedStartDate")));
    if (WebUtil.StringHaveContent(request.getParameter("txtFinishedEndDate")))
    	dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtFinishedEndDate")));
    //处理失败原因
    if (WebUtil.StringHaveContent(request.getParameter("txtWorkResultReason")))
        dto.setSpareStr4(request.getParameter("txtWorkResultReason"));
    //工单类型
    if (WebUtil.StringHaveContent(request.getParameter("txtJobType")))
        dto.setSpareStr5(request.getParameter("txtJobType"));
    //工单处理人员
    if (WebUtil.StringHaveContent(request.getParameter("txtProcessMan")))
    	dto.setSpareStr22(request.getParameter("txtProcessMan"));
    //--add by david.Yang
    //联系人
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerName")))
    	dto.setSpareStr23(request.getParameter("txtCustomerName"));
    //联系电话
    if (WebUtil.StringHaveContent(request.getParameter("textTelehone")))
    	dto.setSpareStr24(request.getParameter("textTelehone"));
    //排序方式
    if (WebUtil.StringHaveContent(request.getParameter("txtorderStyle"))){
    	dto.setOrderField(request.getParameter("txtorderStyle"));
    }


    return new CsrQueryEJBEvent(dto, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_JOBCARD_CATV);
  }

}
