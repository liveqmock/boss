package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.web.util.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerProductDTO;

public class DeviceSwapWebAction extends PayFeeWebAction{
	boolean confirmPost = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}
	

	public void doStart(HttpServletRequest request) {
		super.doStart(request);

		confirmPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}

	}
	
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
     
    	CustomerProductEJBEvent event=new CustomerProductEJBEvent();
    	String txtActionType = request.getParameter("txtActionType");
        
    	if (!WebUtil.StringHaveContent(request.getParameter("txtPsID")))
              throw new WebActionException("没有相关的产品信息标识");
    	int iDeviceID =0;
    	if (confirmPost){
    	   if (!WebUtil.StringHaveContent(request.getParameter("txtNewDeviceSerialNo")))
              throw new WebActionException("没有相关的新设备序列号标识");
           System.out.println("txtNewDeviceSerialNo======"+request.getParameter("txtNewDeviceSerialNo"));
           iDeviceID = Postern.getTerminalDeviceIDBySerialNo(request.getParameter("txtNewDeviceSerialNo"));
           
           if (iDeviceID <= 0) throw new WebActionException("设备序列号无效");
           
   		   int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
   		   int accountID =WebUtil.StringToInt(request.getParameter("txtAccountID")); 
           event.setCsiFeeList(getSessionFeeList(request)); 
           getPayList(request,customerID,accountID,
        		   event.getCsiPaymentList(),
				   CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE,
				   event.getCsiPrePaymentDeductionList());
    	}
              
        CustServiceInteractionDTO csiDTO=new CustServiceInteractionDTO();
        csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID"))); 
        if(Integer.parseInt(txtActionType)== CommonKeys.DEVICE_SWAP){
        csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_DS);
        event.setActionType(CustomerProductEJBEvent.DEVICESWAP);
        }
        else if(Integer.parseInt(txtActionType)== CommonKeys.DEVICE_UPGRADE){
        	csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_DU);
        	event.setActionType(CustomerProductEJBEvent.DEVICEUPGRADE);           
        }
        csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
        csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
        csiDTO.setWorkDate(WebUtil.StringToTimestamp(request.getParameter("txtWorkDate")));
        //李燕春 需要同时设置 受理单 的 statusreason 字段
        csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
        
        csiDTO.setComments(request.getParameter("txtComments"));
        if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
            csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
        
        event.setCsiDto(csiDTO);
        event.setOldSerialNo(request.getParameter("txtSerialNo"));  //老设备序列号
        event.setNewSeralNo(request.getParameter("txtNewDeviceSerialNo"));
        ArrayList colPostid =new ArrayList();
         
        colPostid.add(new Integer(WebUtil.StringToInt(request.getParameter("txtPsID"))));
        event.setColPsid(colPostid);
        event.setSaID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
   
        //2007-5-18修改为目标产品，而不是txtProductID
        event.setProductID(WebUtil.StringToInt(request.getParameter("txtObjectProduct")));
        event.setDeviceID(iDeviceID);
        event.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        event.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
	
        //add by zhouxushun , 2005-12-20
        CustomerProductDTO cpDTO=new CustomerProductDTO();
        cpDTO.setPsID(WebUtil.StringToInt(request.getParameter("txtPsID")));
        //2007-5-18修改为目标产品，而不是txtProductID
        cpDTO.setProductID(WebUtil.StringToInt(request.getParameter("txtObjectProduct")));
        cpDTO.setDeviceID(iDeviceID);
        cpDTO.setServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
        event.setCustomerProductDTO(cpDTO);
        event.setDoPost(confirmPost);
        
        if (!confirmPost) {
			String deviceFee = request.getParameter("txtDeviceFee");
			if (WebUtil.StringHaveContent(deviceFee) && 0 != WebUtil.StringTodouble(deviceFee)) {
				AccountItemDTO acctItemDto = new AccountItemDTO();
				acctItemDto.setValue(WebUtil.StringTodouble(deviceFee));
				acctItemDto.setAcctItemTypeID("D000000");
				acctItemDto.setForcedDepositFlag(CommonKeys.FORCEDDEPOSITFLAG_N);
				acctItemDto.setAcctID(event.getAccountID());
				acctItemDto.setCustID(event.getCustomerID());
				acctItemDto.setServiceAccountID(event.getSaID());
				ArrayList feeList=new ArrayList();
				feeList.add(acctItemDto);
				event.setCsiFeeList(feeList);
			}
		}
        
        return event;

    }

}