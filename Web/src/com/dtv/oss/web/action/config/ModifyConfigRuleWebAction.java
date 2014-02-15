package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.UserPointsExchangeRuleDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigActivityEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2006-4-14
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyConfigRuleWebAction extends GeneralWebAction {

	boolean doPost = false;
	 
	protected boolean needCheckToken()
	{
		return doPost;
	}
	 
	 
	
   public void doStart(HttpServletRequest request)
	{
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm")))
		{
			if (request.getParameter("confirm").equalsIgnoreCase("true")) 
			{
				doPost = true;
				 
			 
			}
		}
		 
	 }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
        UserPointsExchangeRuleDTO dto = new UserPointsExchangeRuleDTO();
        
        ConfigActivityEJBEvent event= new ConfigActivityEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_RULE_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.CONFIG_RULE_EDIT);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.CONFIG_RULE_DELETE);
	    }
	    if (WebUtil.StringHaveContent(request.getParameter("txtID")))
	      dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtVarID")))
		      dto.setId(WebUtil.StringToInt(request.getParameter("txtVarID"))); 
	    if (WebUtil.StringHaveContent(request.getParameter("txtActivityID")))
		    dto.setActivityId(WebUtil.StringToInt(request.getParameter("txtActivityID")));   
	    if (WebUtil.StringHaveContent(request.getParameter("vartxtActivityID")))
		    dto.setActivityId(WebUtil.StringToInt(request.getParameter("vartxtActivityID")));   
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	   dto.setStatus(request.getParameter("txtStatus"));
    	 
    	 
            dto.setCustTypeList(request.getParameter("txtCustTypeList"));
    	if (WebUtil.StringHaveContent(request.getParameter("txtExchangeGoodsValue")))
            dto.setExchangePointsValue(WebUtil.StringToInt(request.getParameter("txtExchangeGoodsValue")));
    	if (WebUtil.StringHaveContent(request.getParameter("txtGoodTypeID")))
            dto.setExchangeGoodsTypeId(WebUtil.StringToInt(request.getParameter("txtGoodTypeID")));
    	if (WebUtil.StringHaveContent(request.getParameter("txtExchangeGoodsAmount")))
    		dto.setExchangeGoodsAmount(WebUtil.StringToInt(request.getParameter("txtExchangeGoodsAmount")));
           event.setUserPointsExchRuleDto(dto);
        
        return event;
    }
}
