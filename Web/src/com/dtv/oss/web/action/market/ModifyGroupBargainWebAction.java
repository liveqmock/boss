package com.dtv.oss.web.action.market;

import com.dtv.oss.web.action.MultipleWebAction;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.market.MarketEJBEvent;
 
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.dto.GroupBargainDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2004 </p>
 * <p> Company: Digivision</p>
 * User: Chen Jiang
 * Date: 2006-4-3
 * Time: 17:15:46
 * To change this template use File | Settings | File Templates.
 */
public class ModifyGroupBargainWebAction extends MultipleWebAction {
    protected int getSepecialAction(String pAction) {
        if (pAction == null) return -1;

        if (pAction.equals("update"))
            return MarketEJBEvent.GROUPBARGAIN_UPDATE;
        if (pAction.equals("delete"))
            return MarketEJBEvent.GROUPBARGAIN_DELETE;
        if (pAction.equals("sale"))
            return MarketEJBEvent.GROUPBARGAIN_SALE;

        return -1;
    }

    protected boolean isAllowedAction() {
        switch (actionType) {
            case MarketEJBEvent.GROUPBARGAIN_UPDATE:
            case MarketEJBEvent.GROUPBARGAIN_DELETE:
            case MarketEJBEvent.GROUPBARGAIN_SALE:
                return true;
        }
        return false;
    }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {

       

        GroupBargainDTO theDTO = new GroupBargainDTO();
        MarketEJBEvent event = new MarketEJBEvent();
        theDTO.setId(WebUtil.StringToInt(request.getParameter("txtID")));
        theDTO.setBargainNo(request.getParameter("txtBargainNo"));
        theDTO.setCreateTime(WebUtil.StringToTimestamp(request.getParameter("txtCreateTime")));
        if (WebUtil.StringHaveContent(request.getParameter("txtDateFrom")))
            theDTO.setDataFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
        if (WebUtil.StringHaveContent(request.getParameter("txtDateTo")))
            theDTO.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
        if (WebUtil.StringHaveContent(request.getParameter("txtNormalTimeTo")))
            theDTO.setNormalTimeTo(WebUtil.StringToTimestamp(request.getParameter("txtNormalTimeTo")));
            theDTO.setTotalSheets(WebUtil.StringToInt(request.getParameter("txtTotalSheets")));
        
      //  theDTO.setStatus(request.getParameter("txtStatus"));
        theDTO.setDescription(request.getParameter("txtDescription"));
        theDTO.setPrepayImmediateFee(WebUtil.StringTodouble(request.getParameter("txtPrepayImmediateFee")));
        theDTO.setPrepayDepositFee(WebUtil.StringTodouble(request.getParameter("txtPrepayDepositFee")));
        theDTO.setMopId(WebUtil.StringToInt(request.getParameter("txtMopID")));
        theDTO.setOrgId(WebUtil.StringToInt(request.getParameter("txtOrgID")));
        theDTO.setIsCampaign(request.getParameter("txtIsCampaign"));
        theDTO.setAgentId(WebUtil.StringToInt(request.getParameter("txtAgentId")));
        theDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("dtLastmod")));
        event.setActionType(actionType);
        event.setGroupBargainDto(theDTO);
        return event;
    }
}
