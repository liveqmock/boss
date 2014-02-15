package com.dtv.oss.web.action.voice;


import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.voice.VoiceQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author fiona
 * @version 1.0
 */

public class QueryFriendPhoneNoWebAction extends QueryWebAction {
    
	protected EJBEvent encapsulateData(HttpServletRequest request)
            throws Exception {    	
		
		VoiceFriendPhoneNoDTO dto=new VoiceFriendPhoneNoDTO();
    	
    	if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
            dto.setServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));

    	return new VoiceQueryEJBEvent(dto, pageFrom, pageTo, VoiceQueryEJBEvent.QUERY_FRIEND_PHONENO);

    }
}
