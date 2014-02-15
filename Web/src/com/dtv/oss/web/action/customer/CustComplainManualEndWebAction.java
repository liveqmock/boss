package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustComplainProcessDTO;
import com.dtv.oss.dto.wrap.customer.ComplainWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerComplainEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class CustComplainManualEndWebAction extends GeneralWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ComplainWrap wrap=new ComplainWrap();
				
		if(WebUtil.StringHaveContent(request.getParameter("txtCustComplainId"))){
			wrap.getDto().setCustComplainId(WebUtil.StringToInt(request.getParameter("txtCustComplainId")));
			wrap.getPdto().setCustComplainId(WebUtil.StringToInt(request.getParameter("txtCustComplainId")));
		}else throw new WebActionException("没有投诉受理单ID");
		
		
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerId")))
			wrap.getDto().setCustomerId(WebUtil.StringToInt(request.getParameter("txtCustomerId")));
		
		if(WebUtil.StringHaveContent(request.getParameter("txtDescriptionManual")))
			wrap.getPdto().setDescription(request.getParameter("txtDescriptionManual"));
		
		if(WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
			wrap.getDto().setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));

		return new CustomerComplainEJBEvent(wrap,CustomerComplainEJBEvent.COMPLAIN_MANUALEND);
	}
}
