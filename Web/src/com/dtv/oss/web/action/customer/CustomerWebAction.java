package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 * @author Wesley Shu
 * @version 1.0
 */

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.web.util.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.MultipleWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.csr.CustomerEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;

public class CustomerWebAction extends MultipleWebAction{
  protected int getSepecialAction(String pAction)
    {
        if (pAction==null) return -1;

        if (pAction.equals("update"))
          return EJBEvent.ALTERCUSTOMERINFO;

        return -1;
    }

    protected boolean isAllowedAction()
    {
        switch (actionType)
        {
          case EJBEvent.ALTERCUSTOMERINFO:
            return true;
        }

        return false;

    }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {

        if(!WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            throw new WebActionException("没有相关的客户信息标识");
    	//受理单信息
    	com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
    	csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
    	csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_IA);
    	//客户基本信息
        com.dtv.oss.dto.CustomerDTO customerDTO = new com.dtv.oss.dto.CustomerDTO();
        customerDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        customerDTO.setGender(request.getParameter("txtGender"));
        customerDTO.setName(request.getParameter("txtName"));
        customerDTO.setBirthday(WebUtil.StringToTimestamp(request.getParameter("txtBirthday")));
        customerDTO.setCardType(request.getParameter("txtCardType"));
        customerDTO.setCardID(request.getParameter("txtCardID"));
        customerDTO.setSocialSecCardID(request.getParameter("txtSocialSecCardID"));
        customerDTO.setEmail(request.getParameter("txtEmail"));
        customerDTO.setNationality(request.getParameter("txtNationality"));
        customerDTO.setFax(request.getParameter("txtFax"));
        customerDTO.setLoginID(request.getParameter("txtLoginID"));
        customerDTO.setLoginPwd(request.getParameter("txtLoginPwd"));
        customerDTO.setTelephone(request.getParameter("txtTelephone"));
        customerDTO.setTelephoneMobile(request.getParameter("txtTelephoneMobile"));
        customerDTO.setCurrentAccumulatePoint(WebUtil.StringToInt(request.getParameter("txtCurrentPoints")));
        customerDTO.setTotalAccumulatePoint(WebUtil.StringToInt(request.getParameter("txtTotalPoints")));
        customerDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtCustomerDtLastmod")));
        customerDTO.setAgentName(request.getParameter("txtAgentName"));
        customerDTO.setCustomerType(request.getParameter("txtCustomerType"));
        customerDTO.setContractNo(request.getParameter("txtContractNo"));
        customerDTO.setComments(request.getParameter("txtComments")); 
        customerDTO.setOpenSourceType(request.getParameter("txtOpenSourceType"));
        customerDTO.setCatvID(request.getParameter("txtCatvID"));
        customerDTO.setCustomerSerialNo(request.getParameter("txtCustomerSerialNo"));
        
        //客户地址信息
        com.dtv.oss.dto.AddressDTO custAddressDTO = null;
        if (WebUtil.StringHaveContent(request.getParameter("txtAddressID"))){	
        	custAddressDTO=	new com.dtv.oss.dto.AddressDTO();
	        custAddressDTO.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
	        if (WebUtil.StringHaveContent(request.getParameter("txtPostcode")))
	        custAddressDTO.setPostcode(request.getParameter("txtPostcode"));
	        if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
	        custAddressDTO.setDetailAddress(request.getParameter("txtDetailAddress"));
	        if (WebUtil.StringHaveContent(request.getParameter("txtDistrict")))
	        custAddressDTO.setDistrictID(WebUtil.StringToInt(request.getParameter("txtDistrict")));
	        if (WebUtil.StringHaveContent(request.getParameter("txtAddressDtLastmod")))
	        custAddressDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtAddressDtLastmod")));
        }
        //客户市场信息
		Collection custMarketInfoList =DynamicServey.getCustomermarketInfo(request,"txtDynamicServey",WebUtil.StringToInt(request.getParameter("txtCustomerID")));
/*
            boolean doPost = false;
            if (WebUtil.StringHaveContent(request.getParameter("confirm_post")))
            {
                if (request.getParameter("confirm_post").equalsIgnoreCase("true")) doPost = true;
            }
*/
        CustomerEJBEvent cusEv = new CustomerEJBEvent(EJBEvent.ALTERCUSTOMERINFO);
        cusEv.setCsiDto(csiDTO);
        cusEv.setCustomerDTO(customerDTO);
        cusEv.setCustAddrDto(custAddressDTO);
        cusEv.setCustMarketInfoList(custMarketInfoList);
        cusEv.setDoPost(true);
        return cusEv;
    }

}