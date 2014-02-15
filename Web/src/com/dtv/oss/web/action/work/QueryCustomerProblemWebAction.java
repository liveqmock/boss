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
public class QueryCustomerProblemWebAction extends QueryWebAction {


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
    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM);
    }
    //创建时间(起始),创建时间(结束)
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
        dto.setBeginTime(WebUtil.StringToTimestampWithDayBegin(
        		request.getParameter("txtCreateStartDate"))) ;
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
        dto.setEndTime(WebUtil.StringToTimestampWithDayEnd(
        		request.getParameter("txtCreateEndDate")));
    //报修类别
    if (WebUtil.StringHaveContent(request.getParameter("txtProblemCategory")))
    	dto.setSpareStr13(request.getParameter("txtProblemCategory"));
    //收费类别
   if (WebUtil.StringHaveContent(request.getParameter("txtFeeClass")))
      dto.setSpareStr14(request.getParameter("txtFeeClass"));  
   //status
   if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
       dto.setSpareStr1(request.getParameter("txtStatus")); 
   if (WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
   		dto.setSpareStr15(request.getParameter("txtQueryType"));
    return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM);
  }

}
