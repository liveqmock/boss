package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

public class BatchPrepayWebAction extends PayFeeWebAction {
	boolean confirmPost = false;
	protected boolean needCheckToken()
	{
		return confirmPost;
	}
	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if(request.getParameter("txtDoPost").equalsIgnoreCase( "TRUE")){
				confirmPost = true;
			}
		}
	}	
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
//		 TODO Auto-generated method stub
		AccountEJBEvent ejbEvent = new AccountEJBEvent(EJBEvent.BATCHPRESAVE);
		String txtAccountIDs = request.getParameter("txtAccountIDs");
		String txtCustomerIDs = request.getParameter("txtCustIDs");
		if(txtAccountIDs == null || txtCustomerIDs==null)
		{
			System.out.print("__传递参数:txtAccountIDs="+txtAccountIDs+";__txtCustIDs="+txtCustomerIDs);
			throw new WebActionException("传递参数有错误！");
		}
		String [] txtAccountID = txtAccountIDs.split(",");
		String [] txtCustomerID = txtCustomerIDs.split(",");
		if(txtAccountID.length != txtCustomerID.length)
		{
			System.out.print("__传递参数:txtAccountIDs="+txtAccountIDs+";__txtCustIDs="+txtCustomerIDs);
			throw new WebActionException("传递参数长度有错误！");
		}
		List txtAccountIDList = new ArrayList();
		List txtCustomerIDList = new ArrayList();
		List PayList = new ArrayList();
		List batchPreSaveParaList = new ArrayList();
		for(int i=0;i<txtAccountID.length;i++)
		{
			txtAccountIDList.add(txtAccountID[i]);
			txtCustomerIDList.add(txtCustomerID[i]);
			PayList.add(getPayInfo(request,txtAccountID[i],txtCustomerID[i]));
		}
		batchPreSaveParaList.add(txtAccountIDList);
		batchPreSaveParaList.add(txtCustomerIDList);
		batchPreSaveParaList.add(PayList);
		
		//System.out.println("___batchPreSaveParaList="+batchPreSaveParaList);
    	ejbEvent.setDoPost (confirmPost);
    	ejbEvent.setBatchPreSaveParaList(batchPreSaveParaList);
		return ejbEvent;
	}
	
	//该方法可以不动
	private List getPayInfo(HttpServletRequest request, String txtAccountID,String txtCustomerID) 
	   throws WebActionException{	
		List eachPayList = new ArrayList();
		  getPayList(request, WebUtil.StringToInt(txtCustomerID)
				            , WebUtil.StringToInt(txtAccountID)
				            , eachPayList
				            , CommonKeys.PAYMENTRECORD_TYPE_PRESAVE
				            , null);
		  return eachPayList;
	}
	
//	private List getEachCsiList(String txtAccountID, String txtCustomerID)
//	{
//		//受理单信息
//		List eachCsiList = new ArrayList();
//		eachCsiList.add(new Integer(WebUtil.StringToInt(txtAccountID))); //Integer
//		eachCsiList.add(new Integer(WebUtil.StringToInt(txtCustomerID)));//Integer
//		eachCsiList.add(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_AD);//String
//		eachCsiList.add(CommonKeys.CSI_PAYMENT_OPTION_IM);//String
//		return eachCsiList;
//	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String txtAccountIDs = "111;222";
		String [] txtAccountID = txtAccountIDs.split(";");
	}

}
