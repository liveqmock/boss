package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustComplainProcessDTO;
import com.dtv.oss.dto.CustomerComplainDTO;
import com.dtv.oss.dto.wrap.customer.ComplainWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerComplainEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class CreateCustComplainWebAction extends GeneralWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ComplainWrap wrap=new ComplainWrap();
		CustomerComplainDTO dto=new CustomerComplainDTO();
		CustComplainProcessDTO pdto=new CustComplainProcessDTO();
				
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))){
			dto.setCustomerId(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtType"))){
			dto.setType(request.getParameter("txtType"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtComplainedOrgID"))){
			dto.setComplainedOrgId(WebUtil.StringToInt(request.getParameter("txtComplainedOrgID")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtComplainedPerson"))){
			dto.setComplainedPerson(request.getParameter("txtComplainedPerson"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtContent"))){
			dto.setContent(request.getParameter("txtContent").trim());
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtRequest"))){
			dto.setRequest(request.getParameter("txtRequest").trim());
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtAction"))){
			pdto.setAction(request.getParameter("txtAction"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtMemo"))){
			pdto.setDescription(request.getParameter("txtMemo").trim());
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtContactPerson"))){
			dto.setContactPerson(request.getParameter("txtContactPerson"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtContactPhone"))){
			dto.setContactPhone(request.getParameter("txtContactPhone"));
		}		
		wrap.setDto(dto);
		wrap.setPdto(pdto);
		if(request.getParameter("pre").equals("y"))
			return new CustomerComplainEJBEvent(wrap,CustomerComplainEJBEvent.COMPLAIN_CREATE_PRE);
		else{
			if (WebUtil.StringHaveContent(request.getParameter("transferType"))){
						if(("auto").equalsIgnoreCase(request.getParameter("transferType"))){
							wrap.getPdto().setNextOrgId(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
						}else if(("manual").equalsIgnoreCase(request.getParameter("transferType"))){
							wrap.getPdto().setNextOrgId(WebUtil.StringToInt(request.getParameter("txtNextOrgID")));
						}
			}
			return new CustomerComplainEJBEvent(wrap,CustomerComplainEJBEvent.COMPLAIN_CREATE_NOPRE);
		}
	}

}
