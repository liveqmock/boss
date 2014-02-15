package com.dtv.oss.web.action.system;

import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.FutureRightDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.FutureRightEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.CurrentCustomer;

public class FutureRightMantainWebAction extends GeneralWebAction {
	boolean confirmPost = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);

		confirmPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}

	}
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
		String operatFlag =request.getParameter("operatFlag");
		FutureRightEJBEvent event =null;
		FutureRightDTO dto =new FutureRightDTO();
		if (operatFlag ==null || operatFlag.equals("")){
			return null;
		}else if (operatFlag.equalsIgnoreCase("Cancel")){
			dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
			dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
			dto.setStatus(request.getParameter("txtstatus"));
			event =new 	FutureRightEJBEvent(FutureRightEJBEvent.FUTURERIGHT_CANCEL,dto);	
			return event;
		}else if (operatFlag.equalsIgnoreCase("Encash")){
			dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
			dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
			dto.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountId")));
			event =new 	FutureRightEJBEvent(FutureRightEJBEvent.FUTURERIGHT_ENCASH,dto);
		    return event;
		}
		return null;
	}
	
	protected  void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse)
	{
	    super.afterSuccessfulResponse(request, cmdResponse);

	    CurrentCustomer.UpdateCurrentCustomer(request);
    }

}
