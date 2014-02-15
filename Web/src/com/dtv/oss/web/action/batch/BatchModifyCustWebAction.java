package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.BatchModifyCustEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class BatchModifyCustWebAction extends GeneralWebAction {
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
		BatchModifyCustEJBEvent batchModifyCustEvent =new BatchModifyCustEJBEvent();
		if (!WebUtil.StringHaveContent(request.getParameter("strCustIds"))){
			throw new WebActionException("没有客户证号不能更新!");
		}
		batchModifyCustEvent.setStrCustIds(request.getParameter("strCustIds"));
		batchModifyCustEvent.setActionType(EJBEvent.BATCH_MODIFY_CUST);
		batchModifyCustEvent.setObject_DistrictID(Integer.parseInt(request.getParameter("txtObject_DistrictID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtObject_CustomerType"))){
			batchModifyCustEvent.setObject_CustomerType(request.getParameter("txtObject_CustomerType"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtObject_DetailAddress"))){
			batchModifyCustEvent.setObject_DetailAddress(request.getParameter("txtObject_DetailAddress"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtaddrOption"))){
			batchModifyCustEvent.setAddrOption(request.getParameter("txtaddrOption"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtSource_DetailAddress"))){
			batchModifyCustEvent.setSource_DetailAddress(request.getParameter("txtSource_DetailAddress"));
		}
			
		return batchModifyCustEvent;
	}

}
