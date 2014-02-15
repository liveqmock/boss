/*
 * Created on 2007-12-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.web.action.customer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.CurrentOperator;
import com.dtv.oss.web.util.CurrentLogonOperator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author 260904l
 *
 * 手工收费WebAction
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AccountManualFeeWebAction extends GeneralWebAction{

	/* (non-Javadoc)
	 * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException, SAXException, IOException, FactoryConfigurationError{
		CustServiceInteractionDTO csiDto = new CustServiceInteractionDTO();
		AccountEJBEvent ejbEvent = new AccountEJBEvent(EJBEvent.ACCOUNT_MANUAL_FEE);
				
		if(WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
			csiDto.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			csiDto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		csiDto.setType(CommonKeys.CUSTSERVICEINTERACTIONTYPE_MB);
		try{
		setFeeAndPayInfo(request,ejbEvent);
		}catch(Exception e){
			e.printStackTrace();
		}
		ejbEvent.setCsiDto(csiDto);		
		
		return ejbEvent;
	}

	/**
	 * @param request
	 * @return
	 */
	private String readXMLFormRequestBody(HttpServletRequest request) throws WebActionException {
		StringBuffer xml = new StringBuffer();
		String line = null;
		try{
			BufferedReader reader = request.getReader();
			while( (line = reader.readLine()) != null){
				xml.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new WebActionException("读取费用和支付列表出错!");
		}
	
		return xml.toString();
	}
	/**
	 * 解析xml文档中的费用和支付
	 * 然后封装成传统的费用和支付集合
	 * @param request
	 */
	private void setFeeAndPayInfo(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException, SAXException, IOException, FactoryConfigurationError{
		Collection feelist = new ArrayList();
		Collection paylist = new ArrayList();
		Collection preDuctionlist = new ArrayList();
		String xml = request.getParameter("xml");		
		Document xmlDoc = null;
		try{
			xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(xml.getBytes()));
		}catch(ParserConfigurationException e){
			System.out.println("ParserConfigurationException : " + e);
		}catch(SAXException e){
			System.out.println("SAXException : " + e);
		}
		if(xmlDoc == null ){
			System.out.println("xml文件为空!");
			return ;
		}
		System.out.println("xml文件:"+xmlDoc.toString());	
		NodeList feeListXml = xmlDoc.getElementsByTagName("feelist");		
		for(int i = 0;i < feeListXml.getLength(); i ++){
			AccountItemDTO acctItemDto = new AccountItemDTO();
			acctItemDto.setFeeType(feeListXml.item(i).getFirstChild().getFirstChild().getNodeValue());
			acctItemDto.setAcctItemTypeID(feeListXml.item(i).getFirstChild().getNextSibling().getFirstChild().getNodeValue());
			String strProductID ="";
			if(feeListXml.item(i).getFirstChild().getNextSibling().getNextSibling() != null && feeListXml.item(i).getFirstChild().getNextSibling().getNextSibling().getFirstChild() != null)
				strProductID = feeListXml.item(i).getFirstChild().getNextSibling().getNextSibling().getFirstChild().getNodeValue();
			if(strProductID.length() >0 ){
				acctItemDto.setProductID(WebUtil.StringToInt(strProductID.substring(0,strProductID.indexOf(";")-1)));
				acctItemDto.setPsID(WebUtil.StringToInt(strProductID.substring(strProductID.indexOf(";")+1)));
			}
				
			acctItemDto.setValue(WebUtil.StringTodouble(feeListXml.item(i).getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue()));
			acctItemDto.setAcctID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
			acctItemDto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
			acctItemDto.setStatus(CommonKeys.SET_F_FTSTATUS_V);
			acctItemDto.setInvoiceFlag(CommonKeys.INVOICE_STATUS_PAID);
			acctItemDto.setSetOffFlag(CommonKeys.SETOFFFLAG_D);
			acctItemDto.setOperatorID(CurrentOperator.GetCurrentOperatorID(request));
			acctItemDto.setOrgID(CurrentLogonOperator.getCurrentOperatorOrgID(request));
			acctItemDto.setReferType(CommonKeys.AIREFERTYPE_M);
			acctItemDto.setCreatingMethod(CommonKeys.FTCREATINGMETHOD_M);
			acctItemDto.setInvoiceFlag(CommonKeys.FORCEDDEPOSITFLAG_N);
			if(CommonKeys.BRFEETYPE_PREPAY.equals(acctItemDto.getFeeType())){
				acctItemDto.setForcedDepositFlag(CommonKeys.FORCEDDEPOSITFLAG_Y);
			}else
				acctItemDto.setForcedDepositFlag(CommonKeys.FORCEDDEPOSITFLAG_N);			
			
			feelist.add(acctItemDto);
		}
		NodeList payListXml = xmlDoc.getElementsByTagName("paylist");
		for(int i = 0; i < payListXml.getLength(); i ++){			
			String realMop = payListXml.item(i).getFirstChild().getFirstChild().getNodeValue();
			String mopID = realMop.substring(0,realMop.indexOf("-"));
			String ticketType = realMop.substring(realMop.indexOf("-")+1,realMop.indexOf("_"));
			String paymentType = realMop.substring(realMop.indexOf("_")+1);
			String prePaymentType ="";
			if ("N".equals(paymentType))
				 prePaymentType =CommonKeys.PREPAYMENTTYPE_TRANSLUNARY;
			else
				 prePaymentType =CommonKeys.PREPAYMENTTYPE_CASH;
			double amount = WebUtil.StringTodouble(payListXml.item(i).getFirstChild().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
			double preductionValue = WebUtil.StringTodouble(payListXml.item(i).getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
			if(amount != 0){
				PaymentRecordDTO payRecordDto = new PaymentRecordDTO();
				payRecordDto.setMopID(WebUtil.StringToInt(mopID));
				if(payListXml.item(i).getFirstChild().getNextSibling() != null && payListXml.item(i).getFirstChild().getNextSibling().getFirstChild() != null)
					payRecordDto.setTicketNo(payListXml.item(i).getFirstChild().getNextSibling().getFirstChild().getNodeValue());
				payRecordDto.setAmount(amount);
				payRecordDto.setTicketType(ticketType);
				payRecordDto.setPayType(CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE);
				payRecordDto.setPrepaymentType(prePaymentType);
				payRecordDto.setAcctID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
				payRecordDto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
				payRecordDto.setStatus(CommonKeys.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
				payRecordDto.setInvoicedFlag(CommonKeys.INVOICE_STATUS_PAID);
				payRecordDto.setOpID(CurrentOperator.GetCurrentOperatorID(request));
				payRecordDto.setOrgID(CurrentLogonOperator.getCurrentOperatorOrgID(request));
				payRecordDto.setPaymentTime(new java.sql.Timestamp(System.currentTimeMillis()));
				payRecordDto.setReferType(CommonKeys.AIREFERTYPE_M);
				payRecordDto.setSourceType(CommonKeys.PAYMENTRECORDSOURCETYPE_ACCEPT);
				payRecordDto.setInvoicedFlag(CommonKeys.FORCEDDEPOSITFLAG_N);
				payRecordDto.setStatus(CommonKeys.SET_F_FTSTATUS_V);
				
				paylist.add(payRecordDto);				
			}
			if(preductionValue !=0 ){
				PaymentRecordDTO payRecordDto = new PaymentRecordDTO();
				payRecordDto.setMopID(WebUtil.StringToInt(mopID));
				if(payListXml.item(i).getFirstChild().getNextSibling() != null && payListXml.item(i).getFirstChild().getNextSibling().getFirstChild() != null)
				payRecordDto.setTicketNo(payListXml.item(i).getFirstChild().getNextSibling().getFirstChild().getNodeValue());
				payRecordDto.setAmount(preductionValue);
				payRecordDto.setTicketType(ticketType);
				payRecordDto.setPayType(CommonKeys.PAYMENTRECORD_TYPE_PRESAVE);
				payRecordDto.setPrepaymentType(prePaymentType);
				payRecordDto.setAcctID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
				payRecordDto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
				payRecordDto.setStatus(CommonKeys.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
				payRecordDto.setInvoicedFlag(CommonKeys.INVOICE_STATUS_PAID);
				payRecordDto.setOpID(CurrentOperator.GetCurrentOperatorID(request));
				payRecordDto.setOrgID(CurrentLogonOperator.getCurrentOperatorOrgID(request));
				payRecordDto.setPaymentTime(new java.sql.Timestamp(System.currentTimeMillis()));
				payRecordDto.setReferType(CommonKeys.AIREFERTYPE_M);
				payRecordDto.setSourceType(CommonKeys.PAYMENTRECORDSOURCETYPE_ACCEPT);
				payRecordDto.setInvoicedFlag(CommonKeys.FORCEDDEPOSITFLAG_N);
				payRecordDto.setStatus(CommonKeys.SET_F_FTSTATUS_V);
				
				paylist.add(payRecordDto);	
			}
		}
		ejbEvent.setCsiFeeList(feelist);
		ejbEvent.setCsiPaymentList(paylist);
	}
}
