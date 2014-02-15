package com.dtv.oss.web.action.customer;
/**
 * 
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.web.action.BatchBuyWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;


public class CheckChooseProductWebAction extends BatchBuyWebAction {


	public void doStart(HttpServletRequest request) {
		super.doStart(request);
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
    
		try{
		ServiceAccountEJBEvent ejbEvent =new ServiceAccountEJBEvent();
		ejbEvent.setBuyProductList(getBuyProductInfo(request));
		CustServiceInteractionDTO csiDto=new CustServiceInteractionDTO();
		String csiType=request.getParameter("txtCsiType");
		if(WebUtil.StringHaveContent(csiType)){
			csiDto.setType(csiType);
		}else{
			throw new WebActionException("缺少受理类型");
		}
		ejbEvent.setCsiDto(csiDto);
		CustomerDTO custDto = new CustomerDTO();
		String custType=request.getParameter("txtCustType");
		if(WebUtil.StringHaveContent(custType)){
			custDto.setCustomerType(custType);
		}else{
			throw new WebActionException("缺少客户类型");
		}
		String openSourceType=request.getParameter("txtOpenSourceType");
		if(WebUtil.StringHaveContent(openSourceType)){
			custDto.setOpenSourceType(openSourceType);
		}else{
			throw new WebActionException("缺少客户来源");
		}
		ejbEvent.setCustomerDTO(custDto);
		AddressDTO addrDto=new AddressDTO();
		String dist=request.getParameter("txtDistrictID");
		if(WebUtil.StringHaveContent(dist)){
			addrDto.setDistrictID(WebUtil.StringToInt(dist));
		}else{
			throw new WebActionException("缺少区域信息");
		}
		ejbEvent.setAddressDTO(addrDto);
		ejbEvent.setActionType(EJBEvent.CHECK_BUY_PRODUCT);
		return ejbEvent;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

}