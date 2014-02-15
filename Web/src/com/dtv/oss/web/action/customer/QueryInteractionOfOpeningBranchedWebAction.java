package com.dtv.oss.web.action.customer;

/**
 * david.Yang
 */

import javax.servlet.http.HttpServletRequest;


import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;


public class QueryInteractionOfOpeningBranchedWebAction extends QueryInteractionNewCustWebAction {

    protected void specialSetDTO(HttpServletRequest request, CommonQueryConditionDTO theDTO) {
        theDTO.setType(null);
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus"))) {
            theDTO.setStatus(request.getParameter("txtStatus"));
        }
        if (WebUtil.StringHaveContent(request.getParameter("txtPaymentStatus"))) {
            theDTO.setSpareStr5(request.getParameter("txtPaymentStatus"));
        }
        if (WebUtil.StringHaveContent(request.getParameter("txtType")))
            theDTO.setType(request.getParameter("txtType"));
        
    }
}