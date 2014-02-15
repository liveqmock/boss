package com.dtv.oss.web.util;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.dtv.oss.dto.*;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.Postern;
import com.dtv.oss.log.LogUtility;

/***
 *You can use the static methods of this class to update current customer or clear curren customer.
 *The current user id stored in an attribute of session which is named with CURRENT_CUSTOMER_SESSION_NAME.
 *Author: lhd
 */

public class CurrentCustomer {
	
	public static final int COLLECTION_ACCOUNT = 1;
	public static final int COLLECTION_SERVICEACCOUNT = 5;
	
	public static boolean UpdateCurrentCustomer(HttpServletRequest request, int pID) {
		CustInfoTree qrycurcust=new CustInfoTree();
		CustInfoWrap currentCustomer=null;
		
		int showNumberSA = 0;			 //客户树上并列显示业务帐户的最大数。
		boolean isShowNumberSA = true;   //是否在客户树上并列显示业务帐户。默认是显示。
		int allNumberSA = 0;			 //客户下所有的业务帐户数量
		
		int selectSAID = WebUtil.StringToInt(request.getParameter("txtServiceAccountID"));
		
		try
		{
			//设置isShowNumberSA
			showNumberSA = getShowNumberSA();
			allNumberSA = getSACountByCustomerID(pID);
			if(allNumberSA > showNumberSA) isShowNumberSA = false;
			qrycurcust.setIsShowNumberSA(isShowNumberSA);
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "UpdateCurrentCustomer showNumberSA="+String.valueOf(showNumberSA));
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "UpdateCurrentCustomer getSACountByCustomerID(pID)="+String.valueOf(getSACountByCustomerID(pID)));
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "UpdateCurrentCustomer isShowNumberSA="+String.valueOf(isShowNumberSA));
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "getCurrentCustomerSubCollection CURRENT_CUSTOMER_SACOUNT_SESSION_NAME="+String.valueOf(request.getSession().getAttribute(CommonKeys.CURRENT_CUSTOMER_SACOUNT_SESSION_NAME)));
			
			//设置session
			selectSAID = setSASession(request.getSession(),pID,selectSAID,isShowNumberSA,allNumberSA);
			//System.out.println("selectSAID============"+selectSAID);
			currentCustomer = qrycurcust.getByID(pID,selectSAID);
		}
		catch(Throwable e) {
			//WebPrint.SetRequestExceptionErrorInfo("CurrentCustomer",request , e);
			WebPrint.PrintErrorInfo("CurrentCustomer", e.getMessage());
			request.getSession().removeAttribute(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME);
			return false;
		}
		
		if (currentCustomer!=null)
		{
			/*if (currentCustomer.getErrorCode()!=0)
			 {
			 ErrorProcess(request, currentCustomer.getErrorCode());
			 currentCustomer=null;
			 }*/
			request.getSession().setAttribute(CommonKeys.CURRENT_CUSTOMER_SACOUNT_SESSION_NAME,new Integer(getSACountByCustomerID(pID)));
			request.getSession().setAttribute(CommonKeys.CURRENT_CUSTOMER_ISSHOWALLSA_SESSION_NAME, new Boolean(isShowNumberSA));
			request.getSession().setAttribute(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME, currentCustomer);
			return true;
		}
		else
			request.getSession().removeAttribute(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME);
		
		return false;
		
	}
	
	public static boolean UpdateCurrentCustomer(HttpServletRequest request) {
		
		return UpdateCurrentCustomerByName(request, "txtCustomerID");
	}
	
	public static boolean UpdateCurrentCustomerByName(HttpServletRequest request, String RequestIDName) {
		int iID=0;
		String strCustomerID = request.getParameter(RequestIDName);
		
		if (WebUtil.StringHaveContent(strCustomerID))
			iID = WebUtil.StringToInt(strCustomerID);
		
		return UpdateCurrentCustomer(request, iID);
	}
	
	public static boolean ClearCurrentCustomer(HttpServletRequest request) {
		request.getSession().removeAttribute(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME);
		request.getSession().removeAttribute(WebKeys.SIDERBARMENUID);
		request.getSession().removeAttribute(WebKeys.SIDERBARTDID);
		request.getSession().removeAttribute(WebKeys.FILTER_BOOKMARK);
		request.getSession().removeAttribute(CommonKeys.FEE_SESSION_NAME);
		request.getSession().removeAttribute(CommonKeys.FILE_UPLOAD_PAGE_CONTEXT);
		return true;
	}
	
	public static CustInfoWrap getCurrentCustomer(HttpSession session) {
		CustInfoWrap custWrap = null;
		
		try
		{
			custWrap = (CustInfoWrap)session.getAttribute(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME);
		}
		catch(Exception e)
		{
			WebPrint.PrintErrorInfo("CurrentCustomer", "get Current user Session :"+e.getMessage());
		}
		
		return custWrap;
	}
	
	public static CustomerDTO getCurrentCustomerDTO(HttpSession session) {
		CustInfoWrap custWrap = getCurrentCustomer(session);
		
		if (custWrap==null) return null;
		
		return custWrap.getBaseInfo();
	}
	
	
	public static AddressDTO getCurrentCustomerAddressDTO(HttpSession session) {
		CustInfoWrap custWrap = getCurrentCustomer(session);
		
		if (custWrap==null) return null;
		
		return custWrap.getAddrInfo();
	}
	
	public static boolean isNormalCustomer(HttpSession session) {
		CustomerDTO dto = getCurrentCustomerDTO(session);
		if (dto==null) return false;
		if (dto.getStatus()==null) return false;
		return dto.getStatus().equals("N");
	}
	
	/**
	 * SubCollectionType is defined as COLLECTION_ACCOUNT...
	 */
  //	public static Collection getCurrentCustomerSubCollection(HttpSession session, int subCollectionType) {
	
	public static Collection getCurrentCustomerSubCollection(HttpSession session,int subCollectionType,int selectSAID){
	    CustInfoWrap currentCustomer = getCurrentCustomer(session);
	    CustInfoTree qrycurcust=new CustInfoTree();
	    
	    int showNumberSA = 0;			 //客户树上并列显示业务帐户的最大数。
		boolean isShowNumberSA = true;  //是否在客户树上并列显示业务帐户。默认是显示。
		int allNumberSA = 0;			 //客户下所有的业务帐户数量
		
	    if (currentCustomer==null) return null;
	    int pID =currentCustomer.getBaseInfo().getCustomerID();
	    try
		{
			//设置isShowNumberSA
			showNumberSA = getShowNumberSA();
			allNumberSA = getSACountByCustomerID(pID);
			if(allNumberSA > showNumberSA) isShowNumberSA = false;
			qrycurcust.setIsShowNumberSA(isShowNumberSA);
			
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "getCurrentCustomerSubCollection showNumberSA="+String.valueOf(showNumberSA));
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "getCurrentCustomerSubCollection getSACountByCustomerID(pID)="+String.valueOf(getSACountByCustomerID(pID)));
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "getCurrentCustomerSubCollection isShowNumberSA="+String.valueOf(isShowNumberSA));
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "getCurrentCustomerSubCollection CURRENT_CUSTOMER_SACOUNT_SESSION_NAME="+String.valueOf(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_SACOUNT_SESSION_NAME)));
			
			
			//设置session
			selectSAID = setSASession(session,pID,selectSAID,isShowNumberSA,allNumberSA);
			
			session.setAttribute(CommonKeys.CURRENT_CUSTOMER_SACOUNT_SESSION_NAME,new Integer(getSACountByCustomerID(pID)));
			session.setAttribute(CommonKeys.CURRENT_CUSTOMER_ISSHOWALLSA_SESSION_NAME, new Boolean(isShowNumberSA));
			
			//System.out.println("selectSAID============"+selectSAID);
			currentCustomer = qrycurcust.getByID(pID,selectSAID);
		}
		catch(Throwable e) {
			//WebPrint.SetRequestExceptionErrorInfo("CurrentCustomer",request , e);
			WebPrint.PrintErrorInfo("CurrentCustomer", e.getMessage());
		}
	//	if (custWrap==null) return null;
		
	//	CustInfoWrap custWrap =new CustInfoWrap();
		
		
		switch (subCollectionType){
		     case COLLECTION_ACCOUNT: 
			
			return currentCustomer.getAccountList();
		
		    case COLLECTION_SERVICEACCOUNT: return currentCustomer.getServiceAccountList();
		}
		
		
		return null;
	}
	
	public static Collection getCurrentCustomerSubCollection(HttpSession session,int subCollectionType){
		return getCurrentCustomerSubCollection(session,subCollectionType,0);
	}
	
	//客户树上并列显示业务帐户的最大数,从t_bossconfiguration表取得,如果没有设置，则默认值为5
	private static int getShowNumberSA() {
		int showNumberSA = Postern.getTreeShowSaMaxNum();
		if(showNumberSA==0) showNumberSA=5;                  //设置默认值
		return showNumberSA;
	}
	
	//根据客户ID取得客户下业务帐户的个数
	private static int getSACountByCustomerID(int customerID) {
		int SACount = Postern.getSACountByCustomerID(customerID);
		return SACount;
	}
	
	//根据客户信息ID，取得客户下最新的一个业务帐户
	private static int getLatestSAIDByCustomerID(int customerID) {
		int SACount = Postern.getLatestSAIDByCustomerID(customerID);
		return SACount;
	}
	
	//根据customerID,selectSAID,isShowNumberSA设置session
	private static int setSASession(HttpSession session,int customerID,int selectSAID,boolean isShowNumberSA,int allNumberSA) {
		//设置session的属性selectSAID
		LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,参数isShowNumberSA="+String.valueOf(isShowNumberSA));
		LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,参数customerID="+String.valueOf(customerID));
		LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,参数selectSAID="+String.valueOf(selectSAID));
		if(!isShowNumberSA){
			int SAIDSession = 0;
			if(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_SAID_SESSION_NAME) != null)
				SAIDSession = WebUtil.StringToInt(String.valueOf(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_SAID_SESSION_NAME)));
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,SAIDSession="+String.valueOf(SAIDSession));
			
			if(selectSAID!=0 && SAIDSession==0)    //第一次设置SAIDSession
				session.setAttribute(CommonKeys.CURRENT_CUSTOMER_SAID_SESSION_NAME, new Integer(selectSAID));
			if(selectSAID != SAIDSession && SAIDSession!=0 && selectSAID!=0 )  //选择别的业务帐户时,更新SAIDSession
				session.setAttribute(CommonKeys.CURRENT_CUSTOMER_SAID_SESSION_NAME, new Integer(selectSAID));  //没有txtServiceAccountID参数传进来，就用SAIDSession，做查询参数
			
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,赋值selectSAID="+String.valueOf(selectSAID));
			if(selectSAID==0 && SAIDSession!=0 && session.getAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME) != null) 
				selectSAID = SAIDSession;
			
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,session.getAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME)="+session.getAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME));
			
			//新客户时，取得客户下最新的一个业务帐户
			if(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME) != null){
				if(WebUtil.StringToInt(String.valueOf(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME))) != customerID){
					selectSAID = getLatestSAIDByCustomerID(customerID);
					LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,新客户时selectSAID="+String.valueOf(selectSAID));
					session.setAttribute(CommonKeys.CURRENT_CUSTOMER_SAID_SESSION_NAME, new Integer(selectSAID));
				}
			}
			
			//首次客户时，取得客户下最新的一个业务帐户
			if(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME) == null){
				selectSAID = getLatestSAIDByCustomerID(customerID);
				LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,首次客户时selectSAID="+String.valueOf(selectSAID));
				session.setAttribute(CommonKeys.CURRENT_CUSTOMER_SAID_SESSION_NAME, new Integer(selectSAID));
			}
			
			//新增业务帐户时，取得客户下最新的一个业务帐户
			if(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME) != null){
				if(WebUtil.StringToInt(String.valueOf(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME))) == customerID){
					if(allNumberSA == ((Integer)session.getAttribute(CommonKeys.CURRENT_CUSTOMER_SACOUNT_SESSION_NAME)).intValue() + 1 ){
						selectSAID = getLatestSAIDByCustomerID(customerID);
						LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,新增业务帐户时selectSAID="+String.valueOf(selectSAID));
						session.setAttribute(CommonKeys.CURRENT_CUSTOMER_SAID_SESSION_NAME, new Integer(selectSAID));
					}
				}
			}	

			session.setAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME, new Integer(customerID));
			LogUtility.log(CurrentCustomer.class, LogLevel.DEBUG, "方法名setSASession,最终的selectSAID="+String.valueOf(selectSAID));
		}
		else if(isShowNumberSA){
				session.removeAttribute(CommonKeys.CURRENT_CUSTOMER_SAID_SESSION_NAME);
				session.removeAttribute(CommonKeys.CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME);
		}
		return selectSAID;
	}
}