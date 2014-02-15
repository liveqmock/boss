package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 * @author Jason.Zhou
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerBillingRuleDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;

/**
 * 客户计费规则维护
 * @author 250713z
 *
 */
public class CustomerBillingRuleWebAction extends GeneralWebAction{
  
	protected int getSepecialAction(String pAction){
        if (pAction==null) return -1;
        if ("create".equalsIgnoreCase(pAction))
          return EJBEvent.CUSTOMER_BILLING_RULE_NEW;
        if("update".equalsIgnoreCase(pAction))
        	return EJBEvent.CUSTOMER_BILLING_RULE_UPDATE;
        return -1;
    }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	CustomerProductEJBEvent event=new CustomerProductEJBEvent();
    	event.setActionType(getSepecialAction(request.getParameter("txtActionType")));
    	if(event.getActionType()==-1)
    		throw new WebActionException("操作类型未知，请检查参数是否完整！");
    	
    	CustomerBillingRuleDTO dto=new CustomerBillingRuleDTO();
    	if(WebUtil.StringHaveContent(request.getParameter("txtCBRID")))
    		dto.setId(WebUtil.StringToInt(request.getParameter("txtCBRID")));
    	if(WebUtil.StringHaveContent(request.getParameter("txtPSID")))
    		dto.setPsID(WebUtil.StringToInt(request.getParameter("txtPSID")));
    	if(WebUtil.StringHaveContent(request.getParameter("txtFeeType")))
    		dto.setFeeType(request.getParameter("txtFeeType"));
    	if(WebUtil.StringHaveContent(request.getParameter("txtAcctItemTypeID")))
    		dto.setAcctItemTypeID(request.getParameter("txtAcctItemTypeID"));
    	if(WebUtil.StringHaveContent(request.getParameter("txtValue")))
    		dto.setValue(WebUtil.StringTodouble(request.getParameter("txtValue")));
    	if(WebUtil.StringHaveContent(request.getParameter("txtStartDate")))
    		dto.setStartDate(WebUtil.StringToTimestamp(request.getParameter("txtStartDate")));
    	if(WebUtil.StringHaveContent(request.getParameter("txtEndDate")))
    		dto.setEndDate(WebUtil.StringToTimestamp(request.getParameter("txtEndDate")));
    	if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    		dto.setStatus(request.getParameter("txtStatus"));              
    	if(WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
    		dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
    	dto.setComments(request.getParameter("txtComments"));
    	event.setCustomerBillingRuleDTO(dto);
        return event;
    }

}