/*
 * Created on 2006-3-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.web.action.market;

 

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.GroupBargainDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.MarketEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class CreateGroupBargainWebAction extends GeneralWebAction{
	
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
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
  	   MarketEJBEvent event= new MarketEJBEvent();
  	   
  	  GroupBargainDTO  theDTO = new GroupBargainDTO();
  	   
      
	   theDTO.setCampaignId(WebUtil.StringToInt(request.getParameter("txtClassID")));
       
     
     if (WebUtil.StringHaveContent(request.getParameter("txtBargainNo")))
     	theDTO.setBargainNo(request.getParameter("txtBargainNo"));
     
     if (WebUtil.StringHaveContent(request.getParameter("txtPrepayImmediateFee")))
     	theDTO.setPrepayImmediateFee(WebUtil.StringTodouble(request.getParameter("txtPrepayImmediateFee")));
      if (WebUtil.StringHaveContent(request.getParameter("txtPrepayDepositFee"))){
     	 theDTO.setPrepayDepositFee(WebUtil.StringTodouble(request.getParameter("txtPrepayDepositFee")));
     }
     if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
 	    theDTO.setOrgId(WebUtil.StringToInt(request.getParameter("txtOrgID")));
     
     if (WebUtil.StringHaveContent(request.getParameter("txtAgentId")))
 	    theDTO.setAgentId(WebUtil.StringToInt(request.getParameter("txtAgentId")));
     
     if (WebUtil.StringHaveContent(request.getParameter("txtDateFrom")))
 	    theDTO.setDataFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
     
     if (WebUtil.StringHaveContent(request.getParameter("txtDateTo")))
 	    theDTO.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
     
     if (WebUtil.StringHaveContent(request.getParameter("txtNormalTimeTo")))
 	    theDTO.setNormalTimeTo(WebUtil.StringToTimestamp(request.getParameter("txtNormalTimeTo")));
     
     if (WebUtil.StringHaveContent(request.getParameter("txtTotalSheets")))
 	    theDTO.setTotalSheets(WebUtil.StringToInt(request.getParameter("txtTotalSheets")));
     
     if (WebUtil.StringHaveContent(request.getParameter("txtStartFrom")))
     	event.setFromBegin(WebUtil.StringToInt(request.getParameter("txtStartFrom")));
     if (WebUtil.StringHaveContent(request.getParameter("txtFormat")))
     	event.setFormat(request.getParameter("txtFormat")); 
     if (WebUtil.StringHaveContent(request.getParameter("txtMopID")))
     	theDTO.setMopId(WebUtil.StringToInt(request.getParameter("txtMopID")));
     if (WebUtil.StringHaveContent(request.getParameter("txtIsCampaign")))
     	theDTO.setIsCampaign(request.getParameter("txtIsCampaign")); 
     if (WebUtil.StringHaveContent(request.getParameter("txtDescription")))
     	theDTO.setDescription(request.getParameter("txtDescription")); 
        
        event.setActionType(EJBEvent.GROUPBARGAIN_CREATE);
        event.setGroupBargainDto(theDTO);
        event.setDoPost(doPost);
        return event;
      
    }
}

