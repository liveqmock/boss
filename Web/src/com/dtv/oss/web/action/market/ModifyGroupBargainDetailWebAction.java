package com.dtv.oss.web.action.market;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.GroupBargainDetailDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.MarketEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2004 </p>
 * <p> Company: Digivision</p>
 * User: Nile.chen
 * Date: 2006-4-25
 * Time: 9:29:31
 * To change this template use File | Settings | File Templates.
 */
public class ModifyGroupBargainDetailWebAction extends GeneralWebAction {

  
    

    protected EJBEvent encapsulateData(HttpServletRequest request) {
    	
    	 MarketEJBEvent event= new MarketEJBEvent();
    	 
         GroupBargainDetailDTO theDTO = new GroupBargainDetailDTO();
         
        
	    if("updateDetail".equals(request.getParameter("Action"))){
	    	theDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(MarketEJBEvent.GROUPBARGAIN_DETAIL_UPDATE);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(MarketEJBEvent.GROUPBARGAIN_DETAIL_DELETE);
	    }
        
        if (WebUtil.StringHaveContent(request.getParameter("vartxtDetailID")))
            theDTO.setId(WebUtil.StringToInt(request.getParameter("vartxtDetailID")));
        if (WebUtil.StringHaveContent(request.getParameter("vartxtGroupBargainID")))
            theDTO.setGroupBargainID(WebUtil.StringToInt(request.getParameter("vartxtGroupBargainID")));
        if (WebUtil.StringHaveContent(request.getParameter("vartxtDetailStatus")))
            theDTO.setStatus(request.getParameter("vartxtDetailStatus"));
        theDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("vartxtDetailDtLastmod")));
         event.setDetailDto(theDTO);
        return event;
    }

}
