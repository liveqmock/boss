package com.dtv.oss.web.action.groupcustomer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;

public class OpenChildCustomerWebAction extends PayFeeWebAction {
	boolean confirmPost = false;
	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {	
		
		String actionType=request.getParameter("txtActionType");
		if (actionType ==null){
			return null;
		}
		GroupCustomerEJBEvent event=new GroupCustomerEJBEvent();
		if (WebUtil.StringHaveContent(request.getParameter("txtContractNo")))
			event.setContractNO(request.getParameter("txtContractNo"));
		switch (Integer.parseInt(actionType)){
			case CommonKeys.GET_DEVICE_LIST :
				event.setActionType(GroupCustomerEJBEvent.OPENCHILDCUST_GETDEVICE);
				setCSIDTO(request,event);
				setProductInfo(request,event);
			    break;			    
			case CommonKeys.GET_FEE_LIST :
				event.setActionType(GroupCustomerEJBEvent.OPENCHILDCUST_GETFEE);
				setDeviceInfo(request,event);
				//setPayFeeInfo(request,event);
				setCSIDTO(request,event);
				setProductPropertyInfo(request,event);
				setProductInfo(request,event);
			    break;
			//提交
			case CommonKeys.OPEN_CHILD_CUSTOMER :
				try{
				event.setActionType(GroupCustomerEJBEvent.OPENCHILDCUST_CONFIRM);
				setDeviceInfo(request,event);
				setPayFeeInfo(request,event);
				setCSIDTO(request,event);
				setProductPropertyInfo(request,event);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					throw new WebActionException("错误："+e.getMessage());
				}
			    break;
		    default :
		    	break;
		}
		return event;
	}
	
	private void setDeviceInfo(HttpServletRequest request, GroupCustomerEJBEvent ejbEvent) throws WebActionException
	{
//		硬件设备信息
		HashMap hardFixingMp =new HashMap();
		String  deviceClass =(request.getParameter("DeviceClassList")==null) ? "" : request.getParameter("DeviceClassList");
		String[] deviceClassList =deviceClass.split(";");
		String  terminalDevice =(request.getParameter("TerminalDeviceList")==null) ? "" :request.getParameter("TerminalDeviceList");
		String[] terminalDeviceList =terminalDevice.split(";");
		
		if (deviceClassList.length !=terminalDeviceList.length){
			 throw new WebActionException("硬件设备和硬件值无法匹配！");
		} else {
			if(!("".equals(deviceClass))){
				for (int i=0; i< deviceClassList.length;i++) {
					hardFixingMp.put(terminalDeviceList[i],deviceClassList[i]);
				}
			}
		}
		ejbEvent.setDeviceTable(hardFixingMp);
		ejbEvent.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		
	    //虚拟电话号码
		ejbEvent.setPhoneNo(request.getParameter("phoneNo"));
		ejbEvent.setItemID(WebUtil.StringToInt(request.getParameter("itemID")));

	}
	
	/**
	 * @param request
	 * @param ejbEvent
	 * @throws WebActionException
	 */
	private void setPayFeeInfo(HttpServletRequest request, GroupCustomerEJBEvent ejbEvent) throws WebActionException
	{
		int customerID = WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID =WebUtil.StringToInt(request.getParameter("txtAccountID"));

		ejbEvent.setFeeList (getSessionFeeList(request)) ;
		getPayAndPrePayList(request,customerID,accountID,ejbEvent.getPaymentList(),ejbEvent.getCsiPrePaymentDeductionList());
	}

	/**
	 * @param request
	 * @param event
	 */
	private void setCSIDTO(HttpServletRequest request,GroupCustomerEJBEvent event){
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();  
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO); 
		if (WebUtil.StringHaveContent(request.getParameter("txtName")))
			csiDTO.setContactPerson(request.getParameter("txtName"));
		if (WebUtil.StringHaveContent(request.getParameter("txtTelephone")))
			;
		if (WebUtil.StringHaveContent(request.getParameter("txtAgentName")))
			csiDTO.setAgentName(request.getParameter("txtAgentName"));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
			csiDTO.setContactPhone(request.getParameter("txtContactPhone"));
		if (WebUtil.StringHaveContent(request.getParameter("txtAgentCardType")))
			csiDTO.setAgentCardType(request.getParameter("txtAgentCardType"));
		if (WebUtil.StringHaveContent(request.getParameter("txtAgentCardID")))
			csiDTO.setAgentCardID(request.getParameter("txtAgentCardID"));
		if (WebUtil.StringHaveContent(request.getParameter("txtChildCustID")))
			csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtChildCustID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
			csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
            csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
		event.setCsiDto(csiDTO);
		
	}
	//产品信息 
	private void setProductInfo(HttpServletRequest request, GroupCustomerEJBEvent ejbEvent)
	{
		//根据合同号得到产品包列表
		Collection listProductPackageID=Postern.getPackageIDByContractID(request.getParameter("txtContractNo"));
		// System.out.println("___txtContractNo="+request.getParameter("txtContractNo"));
	     //System.out.println("___listProductPackageID="+listProductPackageID);
	   ejbEvent.setCsiPackageArray(listProductPackageID);
	   //优惠先置空
	   ejbEvent.setCsiCampaignArray(new ArrayList());
	}
	//产品属性信息	
	/**
	 * @param request
	 * @param ejbEvent
	 */
	private void setProductPropertyInfo(HttpServletRequest request, GroupCustomerEJBEvent ejbEvent)
	{
		Map productPropertyMap = new HashMap();
		Map tempMap = new HashMap();
		
		String[] productProperty =request.getParameterValues("txtProductProperty");
		
		if (productProperty != null) {
			for (int j = 0; j < productProperty.length; j++) {
				if (!productProperty[j].equals("")) {
					String[] productPropertys = productProperty[j].split(";");
					for (int i = 0; i < productPropertys.length; i++) {
						String[] strIDs = productPropertys[i].split("_");
						String productID = strIDs[0];
						String propertyCode = strIDs[1];

						ProductPropertyDTO ppDTO = Postern
								.getProductPropertyDTObyPrimaryKey(productID,
										propertyCode);
						//2008-03-25 hjp 注释此if语句。产品属性的"取值方式"为固定时，也要维护客户产品属性表(t_cpconfigedproperty)表记录
						//if (!CommonKeys.PRODUCT_PROPERTY_MODE_FIXED.equals(ppDTO.getPropertyMode())) {

							String productPropertyHiddenName = "txtProductProperty"
									+ "_" + productPropertys[i];
							String productPropertyValue = (request
									.getParameter(productPropertyHiddenName) == null) ? ""
									: request
											.getParameter(productPropertyHiddenName);

							if (productPropertyMap.containsKey(productID))
								tempMap = (Map) productPropertyMap
										.get(productID);
							else
								tempMap = new HashMap();
							tempMap.put(propertyCode, productPropertyValue);
							productPropertyMap.put(productID, tempMap);

						//}
					}
				}
			}
		}		
		ejbEvent.setProductPropertyMap(productPropertyMap);
	}
	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		super.doEnd(request, cmdResponse);
		String txtActionType =(request.getParameter("txtActionType") ==null) ? "" :request.getParameter("txtActionType");
		if ((!txtActionType.equals(""))
				&& (Integer.parseInt(txtActionType) ==CommonKeys.GET_DEVICE_LIST)){
		   Object obj = request.getAttribute("ResponseFromEJBEvent");
		   if (obj != null) {
			  CommandResponseImp cmdImp =(CommandResponseImp)obj;
			  if(CommonKeys.COMMAND_ID_IMMEDIATEFEE.equals(cmdImp.getFlowHandler()))
			  {
				  request.setAttribute(WebKeys.REQUEST_ATTRIBUTE, obj);
			  }
		  }
	    }
	}
}
