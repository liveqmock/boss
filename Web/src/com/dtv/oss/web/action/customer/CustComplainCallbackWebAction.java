package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustComplainProcessDTO;
import com.dtv.oss.dto.wrap.customer.ComplainWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CallBackInfoEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerComplainEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class CustComplainCallbackWebAction extends GeneralWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ComplainWrap wrap=new ComplainWrap();
				
		if(WebUtil.StringHaveContent(request.getParameter("txtCustComplainId"))){
			wrap.getDto().setCustComplainId(WebUtil.StringToInt(request.getParameter("txtCustComplainId")));
			wrap.getPdto().setCustComplainId(WebUtil.StringToInt(request.getParameter("txtCustComplainId")));
		}else throw new WebActionException("没有投诉受理单ID");
		
		String paramName ="txtDynamicServey";
    	String sourceType =request.getParameter("txtTypeShow");
    	int custComplainId = WebUtil.StringToInt(request.getParameter("txtCustComplainId"));
        Collection	callBackInfoList =DynamicServey.getCallBackInfo(request,paramName,sourceType,custComplainId);
        
		wrap.setCallBackInfoList(callBackInfoList);
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerId")))
			wrap.getDto().setCustomerId(WebUtil.StringToInt(request.getParameter("txtCustomerId")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
			wrap.getDto().setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
		if(WebUtil.StringHaveContent(request.getParameter("callback"))&&request.getParameter("callback").equals("wait"))
			wrap.getPdto().setStatus("D");
		
		return new CustomerComplainEJBEvent(wrap,CustomerComplainEJBEvent.COMPLAIN_CALLBACK);
	}
}
