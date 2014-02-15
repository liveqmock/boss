package com.dtv.oss.web.action.market;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.GroupBargainDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.dto.wrap.CreateGroupBargainResult;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.MarketQueryEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P4</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2004 </p>
 * <p> Company: Digivision</p>
 * User: thurm zhang
 * Date: 2004-9-23
 * Time: 11:28:53
 * To change this template use File | Settings | File Templates.
 */
public class QueryGroupBargainDetailWebAction extends QueryWebAction {

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        theDTO.setNo(request.getParameter("txtID"));
        if (WebUtil.StringHaveContent(request.getParameter("vartxtGroupBargainID")))
        	 theDTO.setNo(request.getParameter("vartxtGroupBargainID"));

        return new MarketQueryEJBEvent(theDTO, pageFrom, pageTo, MarketQueryEJBEvent.QUERY_TYPE_GROUPBARGAIN_DETAIL);
    }


    public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
        super.doEnd(request, cmdResponse);

        if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE) == null) {
            GroupBargainDTO groupBargainDTO = new GroupBargainDTO();
            groupBargainDTO = Postern.getGroupBargainByID(WebUtil.StringToInt(request.getParameter("txtID")));
            if (WebUtil.StringHaveContent(request.getParameter("vartxtGroupBargainID")))
             groupBargainDTO = Postern.getGroupBargainByID(WebUtil.StringToInt(request.getParameter("vartxtGroupBargainID")));
            if (cmdResponse != null) {
                CreateGroupBargainResult resultWrap = new CreateGroupBargainResult();
                resultWrap.setDetailList((Collection) cmdResponse.getPayload());
                resultWrap.setGroupBargain(groupBargainDTO);
                if (cmdResponse != null) {
                    request.setAttribute(getResponseAttributeName(),  new CommandResponseImp(resultWrap));
                    afterSuccessfulResponse(request, cmdResponse);
                }
            }
        }
    }

    protected  void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse) {
        super.updateBookmarkPool(request);
    }


    protected String getResponseAttributeName() {
        return "ResponseFromEJBEvent";
    }

}
