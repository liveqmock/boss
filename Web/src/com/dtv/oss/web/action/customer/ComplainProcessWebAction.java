package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustComplainProcessDTO;
import com.dtv.oss.dto.CustomerComplainDTO;
import com.dtv.oss.dto.wrap.customer.ComplainWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerComplainEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class ComplainProcessWebAction extends GeneralWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ComplainWrap wrap=new ComplainWrap();
		CustomerComplainDTO dto=new CustomerComplainDTO();
		CustComplainProcessDTO pdto=new CustComplainProcessDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtCustComplainId"))){
			pdto.setCustComplainId(WebUtil.StringToInt(request.getParameter("txtCustComplainId")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtAction"))){
			pdto.setAction(request.getParameter("txtAction"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtMemo"))){
			pdto.setDescription(request.getParameter("txtMemo"));
		}
		wrap.setPdto(pdto);
		wrap.setDto(dto);
		if(request.getParameter("txtProResult").equals("S")){
			return new CustomerComplainEJBEvent(wrap,CustomerComplainEJBEvent.COMPLAIN_PROCESS_SUCCESS);
		}else if(request.getParameter("txtProResult").equals("F")){
			return new CustomerComplainEJBEvent(wrap,CustomerComplainEJBEvent.COMPLAIN_PROCESS_FAILURE);
		}
		return null;
	}
}
