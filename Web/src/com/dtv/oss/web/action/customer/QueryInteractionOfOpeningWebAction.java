package com.dtv.oss.web.action.customer;

/**
 *david.Yang
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;

public class QueryInteractionOfOpeningWebAction extends QueryWebAction {	
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		//ֻ��ѯOS	�ŵ꿪��
        //OB	ԤԼ����
        String txtType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_OS+";"+CommonKeys.CUSTSERVICEINTERACTIONTYPE_OB+";"+CommonKeys.CUSTSERVICEINTERACTIONTYPE_CO;
        theDTO.setType(txtType);
        // ԤԼ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtID")))
           theDTO.setNo(request.getParameter("txtID"));
        
        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTSERVICEINTERACTION);
	}
    
}