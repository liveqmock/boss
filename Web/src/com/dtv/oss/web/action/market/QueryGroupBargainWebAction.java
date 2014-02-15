package com.dtv.oss.web.action.market;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.*; 
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 *
 * @author chen jiang
 * @version 1.0
 */

public class QueryGroupBargainWebAction extends QueryWebAction {
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        if (WebUtil.StringHaveContent(request.getParameter("txtBargainNo")))
            theDTO.setNo(request.getParameter("txtBargainNo"));
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
            theDTO.setStatus(request.getParameter("txtStatus"));
        if (WebUtil.StringHaveContent(request.getParameter("txtDetailNo"))) {
            theDTO.setSpareStr1(request.getParameter("txtDetailNo"));
        }
       
        //代理商
        if (WebUtil.StringHaveContent(request.getParameter("txtAgentId"))) {
            theDTO.setSpareStr2(request.getParameter("txtAgentId"));
        }
        //是否优惠
        if (WebUtil.StringHaveContent(request.getParameter("txtIsCampaign"))) {
            theDTO.setSpareStr3(request.getParameter("txtIsCampaign"));
        }
        //促销活动
        if (WebUtil.StringHaveContent(request.getParameter("txtCampaignID"))) {
        	theDTO.setSpareStr4(request.getParameter("txtCampaignID"));
        }
         
        if (WebUtil.StringHaveContent(request.getParameter("txtOrgID"))) {
            theDTO.setFiliale(WebUtil.StringToInt(request.getParameter("txtOrgID")));
        }
        if (WebUtil.StringHaveContent(request.getParameter("txtStartDate")) && WebUtil.IsDate(request.getParameter("txtStartDate"))) {
            theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartDate")));
        }
        if (WebUtil.StringHaveContent(request.getParameter("txtEndDate")) && WebUtil.IsDate(request.getParameter("txtEndDate"))) {
            theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndDate")));
        }

        return new MarketQueryEJBEvent(theDTO, pageFrom, pageTo, MarketQueryEJBEvent.QUERY_TYPE_GROUPBARGAIN);
    }
}