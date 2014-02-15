
package com.dtv.oss.web.action.work;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.WorkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryJobCardWebAction extends QueryWebAction {


  protected EJBEvent encapsulateData(HttpServletRequest request)
      throws Exception {
    CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
    String actiontype =request.getParameter("actiontype");
    LogUtility.log(QueryJobCardWebAction.class,
			LogLevel.DEBUG, actiontype);
    //  排序方式
    if (WebUtil.StringHaveContent(request.getParameter("txtorderStyle"))){
    	dto.setOrderField(request.getParameter("txtorderStyle"));
    }
    
    if(actiontype!=null&&actiontype.equals("print")){
	    String  id[]=request.getParameterValues("txtCustomerProblemID");
	    LogUtility.log(QueryJobCardWebAction.class,
				LogLevel.DEBUG, id);
	    String contactNo="";
	    if(id!=null){
	    	for(int i=0;i<id.length;i++){
	    		if(i==0){
	    			contactNo=id[i];
	    		}else{
	    			contactNo=contactNo+";"+id[i];
	    		}
	    	}
	    	
	    }
	    LogUtility.log(QueryJobCardWebAction.class,
				LogLevel.DEBUG, contactNo);
	    dto.setNo(contactNo);
	    LogUtility.log(QueryJobCardWebAction.class,
				LogLevel.DEBUG, dto.getNo());
	    return new WorkQueryEJBEvent(dto, pageFrom, 50, WorkQueryEJBEvent.QUERY_TYPE_JOBCARD);
    }else{
	    if (WebUtil.StringHaveContent(request.getParameter("txtJobCardID")))
	        dto.setNo(request.getParameter("txtJobCardID"));
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
	      dto.setStatus(request.getParameter("txtStatus"));
	   //所在分区
	    if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
	        dto.setDistrict(WebUtil.StringToInt(request.getParameter("txtCounty")));
	     
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
	    if(WebUtil.StringHaveContent(request.getParameter("txtPreferedDate"))){
	    	dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPreferedDate")));
	    }
	    
	    //add by liyanchun 2006.12.26 增加按设备型号和设备序列号查询
	    //设备型号
	    if(WebUtil.StringHaveContent(request.getParameter("txtDeviceModel")))
	    	dto.setSpareStr17(request.getParameter("txtDeviceModel"));
	    //设备序列号
	    if(WebUtil.StringHaveContent(request.getParameter("txtSerialNo")))
	    	dto.setSpareStr18(request.getParameter("txtSerialNo"));
	   
	    //维修预约时间精确到日
	    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedStartDate")))
	    	dto.setSpareBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPreferedStartDate")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedEndDate")))
	    	dto.setSpareEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPreferedEndDate")));
	    //维修完成精确到日
	    if (WebUtil.StringHaveContent(request.getParameter("txtFinishedStartDate")))
	    	dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtFinishedStartDate")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtFinishedEndDate")))
	    	dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtFinishedEndDate")));
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
	     
	        
	      
	    if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
	        dto.setSpareStr1(request.getParameter("txtDetailAddress"));
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtWorkResultReason")))
	      dto.setSpareStr2(request.getParameter("txtWorkResultReason"));
	      // 为录入安装信息设置一个flag
	      dto.setSpareStr3(request.getParameter("txtFlag")); 
	    
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
	    if (WebUtil.StringHaveContent(request.getParameter("txtAction")))
	        dto.setSpareStr14(request.getParameter("txtAction"));
	     if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
	        dto.setSpareStr15(request.getParameter("txtCustomerType"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatusReason")))
	        dto.setSpareStr16(request.getParameter("txtStatusReason"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
	    	dto.setSpareStr19(request.getParameter("txtQueryType"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustProblemID")))
	    	dto.setSpareStr20(request.getParameter("txtCustProblemID"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtSubType")))
	    	dto.setSpareStr21(request.getParameter("txtSubType"));
	    //工单处理人员
	    if (WebUtil.StringHaveContent(request.getParameter("txtProcessMan")))
	    	dto.setSpareStr22(request.getParameter("txtProcessMan"));
	    //联系人
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerName")))
	    	dto.setSpareStr23(request.getParameter("txtCustomerName"));
	    //联系电话
	    if (WebUtil.StringHaveContent(request.getParameter("textTelehone")))
	    	dto.setSpareStr24(request.getParameter("textTelehone"));
	   	    
	    
	 if(("contactrep").equals(actiontype)||("registerrepair").equals(actiontype)
			 ||("closerepair").equals(actiontype)||("print").equals(actiontype)
	 		 ||("contactin").equals(actiontype)||("reinstall").equals(actiontype)
	 		 ||("closeinstall").equals(actiontype)|| ("register").equals(actiontype))
		 return new WorkQueryEJBEvent(dto,pageFrom,pageTo,WorkQueryEJBEvent.QUERY_TYPE_JOBCARD_WITHPRIVILEGE);
	 return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_JOBCARD);
    }
  }

}
