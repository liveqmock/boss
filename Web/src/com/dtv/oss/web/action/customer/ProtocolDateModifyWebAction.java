package com.dtv.oss.web.action.customer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ProtocolDateModifyWebAction  extends GeneralWebAction {

	 protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
		 CustomerProductEJBEvent ejbEvent = new CustomerProductEJBEvent();
		 ejbEvent.setActionType(CustomerProductEJBEvent.PROTOCOL_DATE_MODIFY);
		 String[] cpID =request.getParameterValues("txtcpID");
		 ArrayList cpDtoList = new ArrayList();
		 java.sql.Timestamp currentTime = TimestampUtility.getCurrentDateWithoutTime();
		 for (int i=0;i <cpID.length;i++){
			 CustomerProductDTO dto =new CustomerProductDTO();
			 dto.setPsID(WebUtil.StringToInt(cpID[i]));
			 String txtEndDateName ="txtEndDate"+i;
			 java.sql.Timestamp endDateTime =  WebUtil.StringToTimestamp(request.getParameter(txtEndDateName));
			 if (currentTime.after(endDateTime)){
				 throw new WebActionException(WebUtil.TimestampToString(endDateTime,"yyyy-MM-dd")+ "小于当天日期，不能用做协议维护！");
			 }
			 dto.setEndTime(endDateTime);
			 String txtOldEndDateName ="txtOldEndDate" +i;
			 dto.setStartTime(WebUtil.StringToTimestamp(request.getParameter(txtOldEndDateName)));
			 cpDtoList.add(dto);
		 }
		 ejbEvent.setColPsid(cpDtoList);
		 ejbEvent.setSaID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
		 ejbEvent.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		 return ejbEvent;
	 }
}
