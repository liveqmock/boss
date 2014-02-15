package com.dtv.oss.web.action.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.CPCampaignMapDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.CustCampaignEJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;

/**
 * 客户促销维护|客户产品促销明细信息维护|人工授予促销活动
 * 
 * @author fiona
 * @version 1.0
 */

public class CustomerCampaignWebAction extends PayFeeWebAction {
	boolean confirmPost = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if (request.getParameter("txtDoPost").equalsIgnoreCase("TRUE")) {
				confirmPost = true;
			}
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		CPCampaignMapDTO cpCampaignMapDTO = new CPCampaignMapDTO();
		CustomerCampaignDTO custCampaignDTO = new CustomerCampaignDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			custCampaignDTO.setCustomerID(WebUtil.StringToInt(request
					.getParameter("txtCustomerID")));
		String actionType = request.getParameter("txtActionType");
		if (!WebUtil.StringHaveContent(actionType)) {
			throw new WebActionException("参数错误，没有操作类型");
		}
		// 客户产品促销维护
		if ("campaignModify".equalsIgnoreCase(actionType)) {
			if (!WebUtil.StringHaveContent(request.getParameter("txtCCID")))
				throw new WebActionException("参数错误，没有客户促销ID");
			custCampaignDTO.setCcID(WebUtil.StringToInt(request
					.getParameter("txtCCID")));
			custCampaignDTO.setAutoExtendFlag(request
					.getParameter("txtAutoExtendFlag"));
			custCampaignDTO
					.setAllowAlter(request.getParameter("txtAllowAlter"));
			custCampaignDTO
					.setAllowPause(request.getParameter("txtAllowPause"));
			custCampaignDTO.setAllowTransition(request
					.getParameter("txtAllowTransition"));
			custCampaignDTO.setAllowTransfer(request
					.getParameter("txtAllowTransfer"));
			custCampaignDTO
					.setAllowClose(request.getParameter("txtAllowClose"));
			custCampaignDTO.setComments(request.getParameter("txtComments"));

			return new CustCampaignEJBEvent(
					SystemEJBEvent.CUST_CAMAPAIGN_MODIFY, custCampaignDTO,
					cpCampaignMapDTO);
		}
		// 客户产品促销维护取消
		else if ("campaignCancle".equalsIgnoreCase(actionType)) {
			if (!WebUtil.StringHaveContent(request.getParameter("txtCCID")))
				throw new WebActionException("参数错误，没有客户促销ID");
			custCampaignDTO.setCcID(WebUtil.StringToInt(request
					.getParameter("txtCCID")));
			// custCampaignDTO.setDateTo(TimestampUtility.getCurrentDateWithoutTime());
			return new CustCampaignEJBEvent(
					SystemEJBEvent.CUST_CAMAPAIGN_CANCEL, custCampaignDTO,
					cpCampaignMapDTO);
		}
		// 手工授予促销活动
		else if ("manualGrantCampaign".equalsIgnoreCase(actionType)) {
			CustCampaignEJBEvent event = new CustCampaignEJBEvent(
					SystemEJBEvent.CUST_CAMAPAIGN_MANUAL_GRANT,
					custCampaignDTO, cpCampaignMapDTO);
			if (!WebUtil.StringHaveContent(request
					.getParameter("txtCampaignID")))
				throw new WebActionException("没有促销活动ID！");
			custCampaignDTO.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
			custCampaignDTO.setAccountID(WebUtil.StringToInt(request
					.getParameter("txtAccountID")));
			custCampaignDTO.setServiceAccountID(WebUtil.StringToInt(request
					.getParameter("txtServiceAccountID")));
			custCampaignDTO.setCustomerID(WebUtil.StringToInt(request
					.getParameter("txtCustID")));
			custCampaignDTO.setDateFrom(WebUtil.StringToTimestamp(request
					.getParameter("txtDateFrom")));
			CampaignDTO campaignDTO = Postern
					.getCampaignDTOByCampaignID(WebUtil.StringToInt(request
							.getParameter("txtCampaignID")));
			if (campaignDTO != null
					&& campaignDTO.getTimeLengthUnitType() != null) {
				custCampaignDTO.setDateTo(TimestampUtility
						.getTimeEndWithoutTime(WebUtil
								.StringToTimestamp(request
										.getParameter("txtDateFrom")),
								campaignDTO.getTimeLengthUnitType(),
								campaignDTO.getTimeLengthUnitNumber()));
			}

			if (confirmPost) {
				event.setFeeList(getSessionFeeList(request));
				int accountID = WebUtil.StringToInt(request.getParameter("txtAccountID"));
				int customerID =WebUtil.StringToInt(request.getParameter("txtCustID"));
			    event.setFeeList(getSessionFeeList(request));
			    getPayAndPrePayList(request, customerID,accountID,
			    	    	event.getPaymentList(),
						    event.getCsiPrePaymentDeductionList());
			}
			// custCampaignDTO.setStatus(request.getParameter("txtStatus"));
			event.setConfirmPost(confirmPost);
			return event;
		}

		// 套餐维护
		else if ("modifyCustBundle".equalsIgnoreCase(actionType)) {
			custCampaignDTO.setCcID(WebUtil.StringToInt(request
					.getParameter("txtCcID")));
			custCampaignDTO.setAutoExtendFlag(request
					.getParameter("txtAutoExtendFlag"));
			custCampaignDTO
					.setAllowAlter(request.getParameter("txtAllowAlter"));
			custCampaignDTO
					.setAllowPause(request.getParameter("txtAllowPause"));
			custCampaignDTO.setAllowTransition(request
					.getParameter("txtAllowTransition"));
			custCampaignDTO.setAllowTransfer(request
					.getParameter("txtAllowTransfer"));
			custCampaignDTO
					.setAllowClose(request.getParameter("txtAllowClose"));
			custCampaignDTO.setComments(request.getParameter("txtComments"));

			return new CustCampaignEJBEvent(SystemEJBEvent.CUST_BUNDLE_MODIFY,
					custCampaignDTO, cpCampaignMapDTO);
		}
		// 套餐取消
		else if ("bundleCancel".equalsIgnoreCase(actionType)) {
			CustCampaignEJBEvent event = new CustCampaignEJBEvent(
					SystemEJBEvent.CUST_BUNDLE_CANCEL);
			if (!WebUtil.StringHaveContent(request.getParameter("txtCcID")))
				throw new WebActionException("参数错误，没有客户套餐ID");
			custCampaignDTO.setCcID(WebUtil.StringToInt(request
					.getParameter("txtCcID")));
			String isReturnDevice=request.getParameter("txtIsReturn");
			String deviceFee=request.getParameter("txtDeviceFee");
			event.setIsReturnDevice(isReturnDevice);
			event.setDeviceFee(WebUtil.StringTodouble(deviceFee));
			if (confirmPost) {
				event.setFeeList(getSessionFeeList(request));
				int accountID = WebUtil.StringToInt(request.getParameter("txtAccountID"));
				int customerID =WebUtil.StringToInt(request.getParameter("txtCustID"));
			    event.setFeeList(getSessionFeeList(request));
			    getPayList(request, customerID,accountID,
			    	    	event.getPaymentList(),
						    CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE,
						    event.getCsiPrePaymentDeductionList());
			}
			event.setCampaignDTO(custCampaignDTO);
			event.setCsiDto(getCsiInfo(request));
			event.setConfirmPost(confirmPost);
			return event;
		}
		// 套餐停止
		else if ("stopCustBundle".equalsIgnoreCase(actionType)) {
			if (!WebUtil.StringHaveContent(request.getParameter("txtCcID")))
				throw new WebActionException("参数错误，没有客户套餐ID");
			custCampaignDTO.setCcID(WebUtil.StringToInt(request
					.getParameter("txtCcID")));

			return new CustCampaignEJBEvent(SystemEJBEvent.CUST_BUNDLE_STOP,
					custCampaignDTO, cpCampaignMapDTO);
		}
		//套餐延期
		else if ("bundleDefer".equalsIgnoreCase(actionType)) {
			custCampaignDTO.setAccountID(WebUtil.StringToInt(request
					.getParameter("txtAccountID")));
			custCampaignDTO.setServiceAccountID(WebUtil.StringToInt(request
					.getParameter("txtServiceAccountID")));
			custCampaignDTO.setCcID(WebUtil.StringToInt(request
					.getParameter("txtCcID")));
			custCampaignDTO.setDateFrom(WebUtil.StringToTimestamp(request
					.getParameter("txtDateFrom")));
			custCampaignDTO.setDateTo(WebUtil.StringToTimestamp(request
					.getParameter("txtEndDate")));
			custCampaignDTO.setStatus(request.getParameter("txtStatus"));
			String comments =(request.getParameter("txtOldComments")==null) ? "" :request.getParameter("txtOldComments");
			comments =comments + request.getParameter("txtComments");
			
			custCampaignDTO.setComments(comments);

			CustCampaignEJBEvent event = new CustCampaignEJBEvent(
					SystemEJBEvent.CUST_CAMAPAIGN_DELAY, custCampaignDTO,
					cpCampaignMapDTO);
			event.setDateLength(WebUtil.StringToInt(request
					.getParameter("txtDateLength")));
			return event;
		}
		//套餐转换
		else if ("bundleTransfer".equalsIgnoreCase(actionType)) {
			CustCampaignEJBEvent event = new CustCampaignEJBEvent(
					SystemEJBEvent.CUST_BUNDLE_TRANSFER, custCampaignDTO,
					cpCampaignMapDTO);

			encapsulateDataWithBundleTransfer(request,event);
			if (confirmPost) {
				int accountID = WebUtil.StringToInt(request.getParameter("txtAccoutID"));
				if(accountID==0){throw new WebActionException("无效和帐户信息");}
				int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
				if(accountID==0){throw new WebActionException("无效和客户信息");}
			    event.setFeeList(getSessionFeeList(request));
			    getPayAndPrePayList(request, customerID,accountID,
		    	    	event.getPaymentList(),
					    event.getCsiPrePaymentDeductionList());

			}
			event.setConfirmPost(confirmPost);
			return event;
		}//套餐预付费 
		else if ("BundlePrePayment".equalsIgnoreCase(actionType)) {
			CustCampaignEJBEvent event = new CustCampaignEJBEvent(
					SystemEJBEvent.CUST_BUNDLE_PREPAYMENT);
			if(WebUtil.StringHaveContent(request.getParameter("txtCcID"))){
				custCampaignDTO.setCcID(WebUtil.StringToInt(request
						.getParameter("txtCcID")));
			}else{
				throw new WebActionException("无效的客户套餐");
			}
			event.setRfBillingCycleFlag(request.getParameter("txtMethod"));
			if (confirmPost) {
				int accountID = WebUtil.StringToInt(request.getParameter("txtAccoutID"));
				if(accountID==0){throw new WebActionException("无效和帐户信息");}
				int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
				if(accountID==0){throw new WebActionException("无效和客户信息");}
			    event.setFeeList(getSessionFeeList(request));
			    getPayAndPrePayList(request, customerID,accountID,
			    	    	event.getPaymentList(),
						    event.getCsiPrePaymentDeductionList());

			}
			event.setCampaignDTO(custCampaignDTO);
			event.setCsiDto(getCsiInfo(request));
			event.setConfirmPost(confirmPost);
			return event;			
		} //协议价用户续费
		else if ("ProtoclPrePayment".equals(actionType)){
			CustCampaignEJBEvent event = new CustCampaignEJBEvent(
					SystemEJBEvent.PROTOCOL_BUNDLE_PREPAYMENT);
			String[] saId_indexs =request.getParameterValues("saId_indexs");
			Collection saIDList=new ArrayList();
			String saIdStr ="";
			for (int i=0;i<saId_indexs.length;i++){
				saIDList.add(new Integer(saId_indexs[i]));
				if (saIdStr.equals("")){
					saIdStr =saId_indexs[i];
				}else{
					saIdStr =saIdStr+","+saId_indexs[i];
				}
			}
			event.setSaIDList(saIDList);
			event.setSaId_indexs(saIdStr);
			event.setCsiDto(getProtocolCsiInfo(request));
			String txtUsedMonth =request.getParameter("txtUsedMonth");
			if (txtUsedMonth !=null && !txtUsedMonth.equals("")){
			   event.setDateLength(Integer.parseInt(txtUsedMonth));
			}
			setCsiPackageAndCampaign(event,request);
			if (confirmPost) {
				event.setFeeList(getSessionFeeList(request));
				int accountID = WebUtil.StringToInt(request.getParameter("txtAccountID"));
				int customerID =WebUtil.StringToInt(request.getParameter("txtCustID"));
			    event.setFeeList(getSessionFeeList(request));
			    getPayAndPrePayList(request, customerID,accountID,
			    	    	event.getPaymentList(),
						    event.getCsiPrePaymentDeductionList());
			}
			event.setConfirmPost(confirmPost);
			return event;
		}else
			throw new WebActionException("参数错误，未知的操作类型");
	}
	private void encapsulateDataWithBundleTransfer(HttpServletRequest request,CustCampaignEJBEvent event) throws WebActionException{
		String ccid=request.getParameter("txtCcID");
		String newCampaignID=request.getParameter("txtCampaignID");
		String newBundleTransferDate=request.getParameter("txtTransferDate");
		String newBundleDateTo=request.getParameter("txtCampaignDateTo");

		String []arrCancelCp=request.getParameterValues("cancelCpID");
		String []arrAddP2P=request.getParameterValues("txtProductID2PackageID");
		if(WebUtil.StringHaveContent(ccid)){
			event.setCcid(WebUtil.StringToInt(ccid));
		}else{
			throw new WebActionException("无效的客户套餐!");
		}
		if(WebUtil.StringHaveContent(newCampaignID)){
			event.setNewCampaignID(WebUtil.StringToInt(newCampaignID));
		}else{
			throw new WebActionException("无效的目标套餐!");
		}
		if(WebUtil.StringHaveContent(newBundleTransferDate)){
			event.setNewBundleTransferDate(WebUtil.StringToTimestamp(newBundleTransferDate));
		}else{
			throw new WebActionException("新套餐转换日期未设置!");
		}
		if(WebUtil.StringHaveContent(newBundleDateTo)){
			event.setNewBundleDateTo(WebUtil.StringToTimestamp(newBundleDateTo));
		}else{
			throw new WebActionException("新套餐有效期未设置!");
		}
		ArrayList cancelCpList=new ArrayList();
		for(int i=0;arrCancelCp!=null&&i<arrCancelCp.length;i++){
			cancelCpList.add(new Integer(arrCancelCp[i]));
		}
		event.setCancelProductList(cancelCpList);
		Map product2packageMap=new HashMap();
		for(int i=0;arrAddP2P!=null&&i<arrAddP2P.length;i++){
			String[]tempPP=arrAddP2P[i].split("-");
			Integer productID=new Integer(tempPP[0]!=null?tempPP[0]:"0"); 
			Integer packageID=new Integer(tempPP.length==2&&tempPP[1]!=null?tempPP[1]:"0"); 
			product2packageMap.put(productID, packageID);
		}
		event.setAddProductMap(product2packageMap);
		event.setProductPropertyMap(getProductProperty(request));
		String isReturnDevice=request.getParameter("txtIsReturn");
		String deviceFee=request.getParameter("txtDeviceFee");
		//event.setIsReturnDevice(isReturnDevice);
		//event.setDeviceFee(WebUtil.StringTodouble(deviceFee));
		event.setCsiDto(getCsiInfo(request));
		setDeviceInfo(request,event);
	}

	private Map getProductProperty(HttpServletRequest request) throws WebActionException
	{
		String propertyTagName="txtProductProperty";
		String strPropertyNameList="";
		Map productMap=new LinkedHashMap();

		LogUtility.log(this.getClass(),LogLevel.DEBUG,"strPropertyNameList:"+request.getParameter(propertyTagName));
		String []arrPropertyCodes=request.getParameterValues(propertyTagName);
		if(arrPropertyCodes!=null&&arrPropertyCodes.length>0){
			for(int i=0;i<arrPropertyCodes.length;i++){
				strPropertyNameList+=arrPropertyCodes[i];
			}
		}else{
			strPropertyNameList = request.getParameter(propertyTagName);
		}
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"strPropertyNameList:"+strPropertyNameList);
		
		if (WebUtil.StringHaveContent(strPropertyNameList)) {
			//arrPropertyName是全部的CpConfigProperty控件名称数组(productID_propertyCode)
			String []arrPropertyName=strPropertyNameList.split(";");
//			LogUtility.log(this.getClass(),LogLevel.DEBUG,"从propertyTag得到的控件数:"+arrPropertyName.length);
			for(int i=0; i<arrPropertyName.length;i++){
				String codes=arrPropertyName[i];
				String propertyName=propertyTagName+"_"+codes;
				String productID=codes.split("_")[0];
				String propertyCode=codes.split("_")[1];
				String propertyValue=request.getParameter(propertyName);
//				LogUtility.log(this.getClass(),LogLevel.DEBUG,"从页面request封装得到的DTO:"+dto);
				Map productPropertyMap=null;
				//Integer key=Integer.valueOf(productID);
				if(productMap.containsKey(productID)){
					productPropertyMap=(Map) productMap.get(productID);
					productPropertyMap.put(propertyCode,propertyValue);
				}else{
					productPropertyMap=new LinkedHashMap();
					productPropertyMap.put(propertyCode,propertyValue);
				}
				productMap.put(productID,productPropertyMap);
			}
		}
		return productMap;
	}
	private void setDeviceInfo(HttpServletRequest request, CustCampaignEJBEvent ejbEvent) throws WebActionException
	{
//		硬件设备信息
		HashMap terminalMap =new HashMap();
		
		String  terminalDevice =request.getParameter("deviceModelSerialNo");
		if(terminalDevice==null||terminalDevice.equals(""))return;
		String[] terminalDeviceList =terminalDevice.split(";");
		
		for(int i=0;terminalDeviceList!=null&i<terminalDeviceList.length;i++){
			String[] tempTerminal=terminalDeviceList[i].trim().split("-");
			String deviceClass=tempTerminal[0];
			String terminal=tempTerminal[1];
			if(!terminalMap.containsKey(terminal)){
				terminalMap.put(terminal, deviceClass);
			}
		}
		ejbEvent.setTerminalMap(terminalMap);
	    //虚拟电话号码
		ejbEvent.setPhoneNo(request.getParameter("phoneNo"));
		ejbEvent.setItemID(WebUtil.StringToInt(request.getParameter("itemID")));

	}
	private CustServiceInteractionDTO getCsiInfo(HttpServletRequest request){
		CustServiceInteractionDTO csiDto=new CustServiceInteractionDTO();
		String csiCreateReason=request.getParameter("txtCsiCreateReason");
		csiDto.setCreateReason(csiCreateReason);
		
		//zyg 2007.11.22 需要同时设置 受理单的 statusreason 字段
		csiDto.setStatusReason(csiCreateReason);

		String csiPayMethod=request.getParameter("txtcsiPayOption");
		
		csiDto.setBillCollectionMethod(csiPayMethod);

		return csiDto;
	}
	
	private CustServiceInteractionDTO getProtocolCsiInfo(HttpServletRequest request){
		String txtCustomerID =request.getParameter("txtCustomerID");
		String txtAccount =request.getParameter("txtAccount");
		CustServiceInteractionDTO csiDto=new CustServiceInteractionDTO();
		csiDto.setType(CommonKeys.CUSTSERVICEINTERACTIONTYPE_BDP);
		
		csiDto.setCustomerID(WebUtil.StringToInt(txtCustomerID));
		csiDto.setAccountID(WebUtil.StringToInt(txtAccount));
		String txtUsedMonth =request.getParameter("txtUsedMonth");
		csiDto.setPoint((WebUtil.StringToInt(txtUsedMonth)));

		return csiDto;
	}
	
	private void setCsiPackageAndCampaign(CustCampaignEJBEvent event,HttpServletRequest request){
		String packageList =request.getParameter("ProductList");
		String campaignList =request.getParameter("CampaignList");
		if (packageList !=null && !packageList.equals("")){
		   String[] packageStr  =packageList.split(";");
		   Collection pgCol =new ArrayList();
		   for (int i=0; i<packageStr.length;i++){
			  pgCol.add(new Integer(packageStr[i]));
		   }
		   event.setCsiPackageArray(pgCol);
		}

		if (campaignList !=null && !campaignList.equals("")){ 
		   String[] campaignStr =campaignList.split(";");
		   Collection cpCol =new ArrayList();
		   for (int i=0; i<campaignStr.length;i++){
		  	  cpCol.add(new Integer(campaignStr[i]));
		   }
		   event.setCsiCampaignArray(cpCol);
		}
	}
	
}
