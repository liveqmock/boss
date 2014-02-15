/*
 * 创建日期 2005-10-16
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dtv.oss.web.action.work;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.WorkQueryEJBEvent;
import com.dtv.oss.web.util.WebUtil;
  
/**
 * @author chenjiang
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class QueryCustomerProblemAndProcessWebAction extends QueryWebAction {


  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request)
      throws Exception {
//状态,类型,ID,报修用户ID,目前流转部门ID,
//目前流转到部门时间(起始),目前流转到部门时间(结束),上门处理的截止时间(起始),上门处理的截止时间(结束)
  	 CommonQueryConditionDTO	dto = new  CommonQueryConditionDTO();
   
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerProblemID")))
        dto.setNo(request.getParameter("txtCustomerProblemID"));
    if(WebUtil.StringHaveContent(request.getParameter("fflag")) && ("frombutton").equals(request.getParameter("fflag"))){
    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM2CUSTOMER_PROBLEM_PROCESS);
    }
    
    //创建时间(起始),创建时间(结束)
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDateDatePart")))
        dto.setBeginTime(WebUtil.StringToTimestampDefaultDayBegin(
        		request.getParameter("txtCreateStartDateDatePart"),
				request.getParameter("txtCreateStartDateHourPart")
				));
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDateDatePart")))
        dto.setEndTime(WebUtil.StringToTimestampDefaultDayEnd(
        		request.getParameter("txtCreateEndDateDatePart"),
				request.getParameter("txtCreateEndDateHourPart")
				));
    if(request.getParameter("txtCustID")!=null && !"".equals(request.getParameter("txtCustID"))){
    	// request.getSession().setAttribute("CUSTOMERID",request.getParameter("txtCustID"));
    	request.setAttribute("CUSTOMERID",request.getParameter("txtCustID"));
    }
   //安装地址
    if (WebUtil.StringHaveContent(request.getParameter("txtAddress")))
        dto.setSpareStr4(request.getParameter("txtAddress"));  
//  服务号码
    if (WebUtil.StringHaveContent(request.getParameter("txtServiceCode")))
        dto.setSpareStr5(request.getParameter("txtServiceCode"));  
//  设备类型
    if (WebUtil.StringHaveContent(request.getParameter("txtDeviceClass")))
        dto.setSpareStr8(request.getParameter("txtDeviceClass"));  
//  设备型号
    if (WebUtil.StringHaveContent(request.getParameter("txtDeviceModel")))
        dto.setSpareStr6(request.getParameter("txtDeviceModel"));  
    
    //所属组织
    if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
        dto.setSpareStr7(request.getParameter("txtOrgID"));
   
   
    //报修类别
    if (WebUtil.StringHaveContent(request.getParameter("txtProblemCategory")))
    	dto.setType(request.getParameter("txtProblemCategory"));
    //收费类别
   if (WebUtil.StringHaveContent(request.getParameter("txtFeeClass")))
      dto.setSpareStr1(request.getParameter("txtFeeClass"));  
   //status
   if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
       dto.setStatus(request.getParameter("txtStatus"));  
   //用户证号
   if (WebUtil.StringHaveContent(request.getParameter("txtCustID")))
       dto.setSpareStr2(request.getParameter("txtCustID"));   
// 所在分区
   if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
       dto.setDistrict(WebUtil.StringToInt(request.getParameter("txtCounty")));
   //回访标志
   if (WebUtil.StringHaveContent(request.getParameter("txtCallBackFlag")))
    dto.setSpareStr3(request.getParameter("txtCallBackFlag")); 
   if (WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
	   dto.setSpareStr9(request.getParameter("txtQueryType"));
   if (WebUtil.StringHaveContent(request.getParameter("txtIsManualTransfer")))
	   dto.setSpareStr10(request.getParameter("txtIsManualTransfer"));
    return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM2CUSTOMER_PROBLEM_PROCESS);
  }

}
