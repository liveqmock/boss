package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustDepositEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.util.Postern;

/**
 * <p>Title: 客户押金退还、没收</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: dvb</p>
 * @author wesley
 * @version 1.0
 */
public class CustDepositWebAction extends GeneralWebAction {
	boolean doPost = false;
	protected boolean needCheckToken()
	{
		return doPost;
	}
	public void doStart(HttpServletRequest request){
		super.doStart(request);
	}
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		/**
		 * 动作定义（txtActionType）
		 * 客户押金退还:      return
		 * 客户押金没收:      confiscate
		 */
		CustDepositEJBEvent event=new CustDepositEJBEvent();
		if("return".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.WITHDRAW_FORCED_DEPOSIT);
		else if("confiscate".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.WITHDRAW_CONFISCATE_DEPOSIT);
		else
			throw new WebActionException("客户押金管理动作类型未知！");

		AccountItemDTO acctItemDto =new AccountItemDTO();
		PaymentRecordDTO paymentDto =new PaymentRecordDTO();
		CustServiceInteractionDTO csiDto =new CustServiceInteractionDTO();

		csiDto.setAccountID(WebUtil.StringToInt(request.getParameter("txtAcctID")));
		csiDto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        
		acctItemDto.setAcctID(WebUtil.StringToInt(request.getParameter("txtAcctID")));
		acctItemDto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		acctItemDto.setValue(-WebUtil.StringTodouble(request.getParameter("txtPay")));
		acctItemDto.setAcctItemTypeID(Postern.getAcctItemType());
		acctItemDto.setForcedDepositFlag(CommonKeys.FORCEDDEPOSITFLAG_N);

		paymentDto.setAcctID(WebUtil.StringToInt(request.getParameter("txtAcctID")));
		paymentDto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		paymentDto.setMopID(WebUtil.StringToInt(request.getParameter("mopid")));
		paymentDto.setPayType(CommonKeys.PAYMENTRECORD_TYPE_RETURN_FEE);
		paymentDto.setAmount(-WebUtil.StringTodouble(request.getParameter("txtPay")));
		paymentDto.setTicketType(request.getParameter("tickettype"));
		if(CommonKeys.MOPPAYTYPE_DK.equals(request.getParameter("tickettype")))
		{
			paymentDto.setTicketNo(request.getParameter("billNo"));
		}

		if (WebUtil.StringHaveContent(request.getParameter("confirm_post")))
		{
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
			{
				doPost = true;
			}
		}

		request.setAttribute("tickettype",request.getParameter("tickettype"));

		event.setAiNO(WebUtil.StringToInt(request.getParameter("txtAiNO")));
		event.setCsiDTO(csiDto);
		event.setAcctItemDTO(acctItemDto);
		event.setPaymentDTO(paymentDto);
		event.setDoPost(doPost);
		return event;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
