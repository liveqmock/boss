package com.dtv.oss.web.action.system;


import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.LogQueryEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 * @author chen jiang
 * @version 1.0
 */

public class QuerySystemEventWebAction extends QueryWebAction {
    protected EJBEvent encapsulateData(HttpServletRequest request)
            throws Exception {

          SystemEventDTO dto = new SystemEventDTO();
          
          if (WebUtil.StringHaveContent(request.getParameter("txtSequenceNo")))
                  dto.setSequenceNo(WebUtil.StringToInt(request.getParameter("txtSequenceNo")));
          if (WebUtil.StringHaveContent(request.getParameter("txtOperatorID")))
            dto.setOperatorID(WebUtil.StringToInt(request.getParameter("txtOperatorID")));
          if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
                  dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
          
          //事件类型
          if (WebUtil.StringHaveContent(request.getParameter("txtEventClass")))
                  dto.setEventClass(WebUtil.StringToInt(request.getParameter("txtEventClass")));
          //事件原因
          if (WebUtil.StringHaveContent(request.getParameter("txtEventReason")))
                  dto.setEventReason(request.getParameter("txtEventReason"));
          //操作员
          if (WebUtil.StringHaveContent(request.getParameter("txtOperatorName")))
                  dto.setOperatorID(Postern.getOperatorIDByLoginID(request.getParameter("txtOperatorName")));
          //用户证号
          if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
                  dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
          //资金账户号
          if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
                  dto.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
          //业务账户号
          if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
                  dto.setServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
          //客户产品号
          if (WebUtil.StringHaveContent(request.getParameter("txtPSID")))
                  dto.setPsID(WebUtil.StringToInt(request.getParameter("txtPSID")));
          //运营商产品
          if (WebUtil.StringHaveContent(request.getParameter("txtproductID")))
                 dto.setProductID(WebUtil.StringToInt(request.getParameter("txtproductID")));
          //受理单号
          if (WebUtil.StringHaveContent(request.getParameter("txtCSIID")))
            dto.setCsiID(WebUtil.StringToInt(request.getParameter("txtCSIID")));
          //设备ID
          if (WebUtil.StringHaveContent(request.getParameter("txtDeviceID")))
            dto.setScDeviceID(WebUtil.StringToInt(request.getParameter("txtDeviceID")));
          //设备序列号
          if (WebUtil.StringHaveContent(request.getParameter("txtSerialNo")))
            dto.setScSerialNo(request.getParameter("txtSerialNo"));
          //创建时间(起始),创建时间(结束)
          if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDateDatePart")))
              dto.setDtCreate(WebUtil.StringToTimestampDefaultDayBegin(
              		request.getParameter("txtCreateStartDateDatePart"),
      				request.getParameter("txtCreateStartDateHourPart")
      				));
          if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDateDatePart")))
              dto.setDtLastmod(WebUtil.StringToTimestampDefaultDayEnd(
              		request.getParameter("txtCreateEndDateDatePart"),
      				request.getParameter("txtCreateEndDateHourPart")
      				));
          //if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
          //        dto.setDtLastmod(new java.sql.Timestamp(convertDateTimeStrToLng(request.getParameter("txtEndTime"))));
          //dto.setDtCreate(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateStartDate")));
          //if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
          //dto.setDtLastmod(new java.sql.Timestamp(convertDateTimeStrToLng(request.getParameter("txtEndTime"))));
         //dto.setDtLastmod(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));
           return new LogQueryEJBEvent(dto, pageFrom, pageTo, LogQueryEJBEvent.QUERY_SYSTEM_EVENT);
    }
    

}
