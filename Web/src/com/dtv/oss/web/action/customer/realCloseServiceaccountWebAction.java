package com.dtv.oss.web.action.customer;
import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;
public class realCloseServiceaccountWebAction extends GeneralWebAction {
	boolean confirmPost = false;

	boolean cleanSideBarInfo = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if (request.getParameter("txtDoPost").equalsIgnoreCase("TRUE"))
				confirmPost = true;
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		try {
			String txtActionType = request.getParameter("txtActionType");
			ServiceAccountEJBEvent ejbEvent = new ServiceAccountEJBEvent();
			setServiceAccountInfo(request, ejbEvent);
			setCustomerInfo(request, ejbEvent);
			setAccountInfo(request, ejbEvent);
			int eJbActionType = 0;
			switch (WebUtil.StringToInt(txtActionType)) {
			case CommonKeys.SERVICE_ACCOUNT_REAL_CLOSE:
				eJbActionType = ServiceAccountEJBEvent.REAL_CLOSE;
				ejbEvent.setDoPost(confirmPost);
				ejbEvent.setIsSendBack(request.getParameter("txtIsSendBack"));
				setCSIInfo(request, ejbEvent,CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_RC);
				if (confirmPost)
					cleanSideBarInfo = true;
				break;
			default:
				break;
			}

			ejbEvent.setActionType(eJbActionType);
			return ejbEvent;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}

	}
	private void setServiceAccountInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent) throws WebActionException {
		String saID = request.getParameter("txtServiceAccountID");
		
		if (WebUtil.StringHaveContent(saID)) {
			ejbEvent.setServiceAccountID(WebUtil.StringToInt(saID.trim()));
		} else {
			throw new WebActionException("请指定需要暂停的业务帐户！");
		}
	}

	private void setCustomerInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent) throws WebActionException {
		String customer = request.getParameter("txtCustomerID");
		if (WebUtil.StringHaveContent(customer)) {
			CustomerDTO custDTO = new CustomerDTO();
			custDTO.setCustomerID(WebUtil.StringToInt(customer));
			ejbEvent.setCustomerDTO(custDTO);
			ejbEvent.setCustomerID(WebUtil.StringToInt(customer));
		} else {
			throw new WebActionException("请先做客户查询，定位客户！");
		}
	}

	private void setAccountInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent) throws WebActionException {
		String account = request.getParameter("txtAccount");
		if (WebUtil.StringHaveContent(account)) {
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setAccountID(WebUtil.StringToInt(account));
			ejbEvent.setAccountDTO(accountDTO);
			ejbEvent.setAccountID(WebUtil.StringToInt(account));
		} 

	}

	private void setCSIInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent, String csiType) {
		// 受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setType(csiType);
		if (WebUtil.StringHaveContent(request.getParameter("txtPreferedTime")))
			csiDTO.setPreferedTime(request.getParameter("txtPreferedTime"));
		if (WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))
			csiDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
		if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
			csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
	
		if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
            csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason")))
		{    
			csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));		
			csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtComments")))
            csiDTO.setComments(request.getParameter("txtComments"));
		
		ejbEvent.setCsiDto(csiDTO);
	}	

	protected void afterSuccessfulResponse(HttpServletRequest request,
			CommandResponse cmdResponse) {
		if (cleanSideBarInfo) {
			request.getSession().removeAttribute(WebKeys.SIDERBARMENUID);
			request.getSession().removeAttribute(WebKeys.SIDERBARTDID);
		}
	}
}
