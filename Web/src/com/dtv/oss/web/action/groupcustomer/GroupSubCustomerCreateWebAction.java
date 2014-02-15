package com.dtv.oss.web.action.groupcustomer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

public class GroupSubCustomerCreateWebAction extends GeneralWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		//GroupCustomerWrap wrap=new GroupCustomerWrap();
		NewCustomerInfoDTO cDto=new NewCustomerInfoDTO();
		AddressDTO aDto=new AddressDTO();
		CustServiceInteractionDTO csiDto=new CustServiceInteractionDTO();
		//TerminalDeviceDTO tDto=new TerminalDeviceDTO();
		ContractDTO contractDto=new ContractDTO();
		GroupCustomerEJBEvent event=new GroupCustomerEJBEvent();
		event.setCustomerID(WebUtil.StringToInt(request.getParameter("txtGroupCustID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtAccount"))) {
			event.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
		} else {
			throw new WebActionException("无效付费帐户");
		}
		setCustomerInfo(request,cDto);
        setAddressInfo(request,aDto);
        event.setNewCustomerInfoDTO(cDto);
        event.setAddressDTO(aDto);
        setCSIInfo(request,csiDto);
        event.setCsiDto(csiDto);
        setDeviceInfo(request,event);
        this.setProductPropertyInfo(request,event);
        event.setContractNO(request.getParameter("txtContractNo"));
        event.setActionType(event.CHILDCUST_CREATE);
		return event;
	}
	
	private void setContractInfo(HttpServletRequest request, ContractDTO contractDto) {
		contractDto.setContractNo(request.getParameter("txtContractNo"));
		contractDto.setName(request.getParameter("txtContractName"));
		contractDto.setDescription(request.getParameter("txtContractDescription"));
	}

	private void setCustomerInfo(HttpServletRequest request,NewCustomerInfoDTO dto){
		dto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		dto.setGender(request.getParameter("txtGender"));
		dto.setName(request.getParameter("txtName"));
		dto.setBirthday(WebUtil.StringToTimestamp(request.getParameter("txtBirthday")));
		dto.setCardType(request.getParameter("txtCardType"));
		dto.setCardID(request.getParameter("txtCardID"));
		dto.setSocialSecCardID(request.getParameter("txtSocialSecCardID"));
		dto.setEmail(request.getParameter("txtEmail"));
		dto.setNationality(request.getParameter("txtNationality"));
		dto.setFaxNumber(request.getParameter("txtFaxNumber"));
		dto.setTelephone(request.getParameter("txtTelephone"));
		dto.setMobileNumber(request.getParameter("txtMobileNumber"));
		dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtCustomerDtLastmod")));
		dto.setCustStyle(CommonConstDefinition.CUSTOMERSTYLE_GROUP);
		dto.setGroupCustID(WebUtil.StringToInt(request.getParameter("txtGroupCustID")));
		dto.setType(CommonConstDefinition.CUSTOMERTYPE_GROUP);
		dto.setCatvID(request.getParameter("txtCatvID"));
		dto.setOpenSourceType(request.getParameter("txtOpenSourceType"));
	}
	
	private void setAddressInfo(HttpServletRequest request,AddressDTO aDto){
		//		客户地址信息
		aDto.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
		aDto.setPostcode(request.getParameter("txtPostcode"));
		aDto.setDetailAddress(request.getParameter("txtDetailAddress"));
		aDto.setDistrictID(WebUtil.StringToInt(request.getParameter("txtCounty")));
		aDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtAddressDtLastmod")));
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
		
	    //虚拟电话号码
		ejbEvent.setPhoneNo(request.getParameter("phoneNo"));
		ejbEvent.setItemID(WebUtil.StringToInt(request.getParameter("itemID")));

	}
	
	private void setCSIInfo(HttpServletRequest request, CustServiceInteractionDTO csiDto)
	{
		//受理单信息
		csiDto.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU);
				
		if (WebUtil.StringHaveContent(request.getParameter("txtID")))
			csiDto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
        if (WebUtil.StringHaveContent(request.getParameter("txtPreferedTime")))
        	csiDto.setPreferedTime(request.getParameter("txtPreferedTime") );
        if (WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))
        	csiDto.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	csiDto.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
        	csiDto.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount") ));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
			csiDto.setContactPhone(request.getParameter("txtContactPhone"));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPerson")))
			csiDto.setContactPerson(request.getParameter("txtContactPerson"));
        if (WebUtil.StringHaveContent(request.getParameter("txtCsiDtLastMod")))
        	csiDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtCsiDtLastMod")));
        csiDto.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS);
	}
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
}
