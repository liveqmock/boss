package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillingRuleDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigBillingEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2006-4-24
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyBillingRuleWebAction extends GeneralWebAction {

	 
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	BillingRuleDTO dto = new BillingRuleDTO();
        ConfigBillingEJBEvent event= new ConfigBillingEJBEvent();
         
        dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.BILLING_RULE_CREATE);
	     else if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.BILLING_RULE_MODIFY);
	    }
	      else if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.BILLING_RULE_DELE);
	    }
	      else {
	      	event.setActionType(EJBEvent.RECACULATE_RULE);
	      }
 
	        dto.setBrName(request.getParameter("txtBrName"));
	//    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	     dto.setStatus(request.getParameter("txtStatus"));
	//    if (WebUtil.StringHaveContent(request.getParameter("txtBrDesc")))
	    	   dto.setBrDesc(request.getParameter("txtBrDesc"));
	//    if (WebUtil.StringHaveContent(request.getParameter("txtEventClass")))
            dto.setEventClass(WebUtil.StringToInt(request.getParameter("txtEventClass")));
    //	if (WebUtil.StringHaveContent(request.getParameter("txtEventReason")))
            dto.setEventReason(request.getParameter("txtEventReason"));
    //	if (WebUtil.StringHaveContent(request.getParameter("txtProductID")))
            dto.setProductId(WebUtil.StringToInt(request.getParameter("txtProductID")));
    //	if (WebUtil.StringHaveContent(request.getParameter("txtProductPackageID")))
            dto.setPackageId(WebUtil.StringToInt(request.getParameter("txtProductPackageID")));
    //	if (WebUtil.StringHaveContent(request.getParameter("txtDestProductID")))
            dto.setDestProductId(WebUtil.StringToInt(request.getParameter("txtDestProductID")));
            dto.setDestPackageId(WebUtil.StringToInt(request.getParameter("txtDestPackageID")));
    //	if (WebUtil.StringHaveContent(request.getParameter("txtCustType")))
            dto.setBrcntIdCustType(WebUtil.StringToInt(request.getParameter("txtCustType")));
    //	if (WebUtil.StringHaveContent(request.getParameter("txtCampaign")))
            dto.setBrcntIdCampaign(WebUtil.StringToInt(request.getParameter("txtCampaign")));
    //	if (WebUtil.StringHaveContent(request.getParameter("txtAcctItem")))
            dto.setBrcntIdAcctType(WebUtil.StringToInt(request.getParameter("txtAcctItem"))); 
    //	if (WebUtil.StringHaveContent(request.getParameter("txtValue")))
            dto.setValue(WebUtil.StringTodouble(request.getParameter("txtValue"))); 
    //	if (WebUtil.StringHaveContent(request.getParameter("txtAcctItemTypeID")))
            dto.setAcctItemTypeId(request.getParameter("txtAcctItemTypeID")); 
   //	if (WebUtil.StringHaveContent(request.getParameter("txtAllowFlag")))
            dto.setAllowReturn(request.getParameter("txtAllowFlag")); 
   // 	if (WebUtil.StringHaveContent(request.getParameter("txtValidFrom")))
            dto.setValidFrom(WebUtil.StringToTimestamp(request.getParameter("txtValidFrom"))); 
    //	if (WebUtil.StringHaveContent(request.getParameter("txtValidTo")))
            dto.setValidTo(WebUtil.StringToTimestamp(request.getParameter("txtValidTo"))); 
   //   	if (WebUtil.StringHaveContent(request.getParameter("txtAllowFlag")))
            dto.setAllowReturn(request.getParameter("txtAllowFlag")); 
            dto.setRfBillingCycleFlag(request.getParameter("txtRfBillingCycleFlag")); 
            dto.setFeeSplitPlanID(WebUtil.StringToInt(request.getParameter("txtFeeSplitPlanID"))); 
            dto.setBrCntIDCATVTermType(WebUtil.StringToInt(request.getParameter("txtCatvTermType")));
    	    
    	    dto.setBrCntIDCableType(WebUtil.StringToInt(request.getParameter("txtCableType")));
    	  
    	    dto.setBrCntIDBiDrectionFlag(WebUtil.StringToInt(request.getParameter("txtDoubleFlag")));
    	    
    	    dto.setOldPortNo(WebUtil.StringToInt(request.getParameter("txtOldPortNo")));
      	  
    	    dto.setNewPortNo(WebUtil.StringToInt(request.getParameter("txtNewPortNo")));
    	    
    	    dto.setBrCntIDUserType(WebUtil.StringToInt(request.getParameter("txtUserType")));
    	    
    	    dto.setBrCntIDMarketSegmentID(WebUtil.StringToInt(request.getParameter("txtMarketSegmentID")));
    	    
           event.setBillingRuleDto(dto);
            
        return event;
    }
}
