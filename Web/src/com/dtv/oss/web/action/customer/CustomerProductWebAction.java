package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

/**
 * 暂停/恢复/取消客户产品，提交
 * 
 * @author Stone Liang 2005/11/30
 */

public class CustomerProductWebAction extends PayFeeWebAction {
	boolean confirmPost = false;
    private String said=null;
	String txtActionType = "";

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if (request.getParameter("txtDoPost").equalsIgnoreCase("TRUE"))
				confirmPost = true;
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		txtActionType = request.getParameter("txtActionType");
		CustomerProductEJBEvent ejbEvent = new CustomerProductEJBEvent();
		int eJbActionType = 0;
		ejbEvent.setDoPost(confirmPost);
		setCustomerInfo(request, ejbEvent);
		setCSIInfo(request, ejbEvent);
		setSAInfo(request, ejbEvent);
		setProductInfo(request, ejbEvent);
		setAccountInfo(request, ejbEvent);

		if (confirmPost) {
			setPayFeeInfo(request, ejbEvent);
		}
		switch (Integer.parseInt(txtActionType)) {
		case CommonKeys.CUSTOMER_PRODUCT_PAUSE:
			eJbActionType = CustomerProductEJBEvent.SUSPEND;
			break;
		case CommonKeys.CUSTOMER_PRODUCT_RESUME:
			eJbActionType = CustomerProductEJBEvent.RESUME;
			break;
		case CommonKeys.CUSTOMER_PRODUCT_CANCEL:
			eJbActionType = CustomerProductEJBEvent.CANCEL;
			String sendBack=request.getParameter("txtIsSendBack");
			if(WebUtil.StringHaveContent(sendBack)&&CommonKeys.FORCEDDEPOSITFLAG_Y.equals(sendBack)){
				ejbEvent.setSendBackDevice(true);
			}
			//不是正式提交的时候,封装前台输入的设备费用信息.
			if (!confirmPost) {
				String deviceFee = request.getParameter("txtDeviceFee");
				if (WebUtil.StringHaveContent(deviceFee) && 0 != WebUtil.StringTodouble(deviceFee)) {
					AccountItemDTO acctItemDto = new AccountItemDTO();
					acctItemDto.setValue(WebUtil.StringTodouble(deviceFee));
					acctItemDto.setAcctItemTypeID("D000000");
					acctItemDto.setForcedDepositFlag(CommonKeys.FORCEDDEPOSITFLAG_N);
					acctItemDto.setAcctID(ejbEvent.getAccountID());
					acctItemDto.setCustID(ejbEvent.getCustomerID());
					acctItemDto.setServiceAccountID(ejbEvent.getSaID());
					ArrayList feeList=new ArrayList();
					feeList.add(acctItemDto);
					ejbEvent.setCsiFeeList(feeList);
				}
			}
			break;
		case CommonKeys.CUSTOMER_PRODUCT_MOVE:
			eJbActionType = CustomerProductEJBEvent.MOVE;
			String sendBack2=request.getParameter("txtIsSendBack");
			if(WebUtil.StringHaveContent(sendBack2)&&CommonKeys.FORCEDDEPOSITFLAG_Y.equals(sendBack2)){
				ejbEvent.setSendBackDevice(true);
			}
			//不是正式提交的时候,封装前台输入的设备费用信息.
			if (!confirmPost) {
				String deviceFee = request.getParameter("txtDeviceFee");
				if (WebUtil.StringHaveContent(deviceFee) && 0 != WebUtil.StringTodouble(deviceFee)) {
					AccountItemDTO acctItemDto = new AccountItemDTO();
					acctItemDto.setValue(WebUtil.StringTodouble(deviceFee));
					acctItemDto.setAcctItemTypeID("D000000");
					acctItemDto.setForcedDepositFlag(CommonKeys.FORCEDDEPOSITFLAG_N);
					acctItemDto.setAcctID(ejbEvent.getAccountID());
					acctItemDto.setCustID(ejbEvent.getCustomerID());
					acctItemDto.setServiceAccountID(ejbEvent.getSaID());
					ArrayList feeList=new ArrayList();
					feeList.add(acctItemDto);
					ejbEvent.setCsiFeeList(feeList);
				}
			}
			break;
		case CommonKeys.CUSTOMER_PRODUCT_UPGRADE:
			eJbActionType = CustomerProductEJBEvent.UPGRADE;
			break;
		case CommonKeys.CUSTOMER_PRODUCT_DOWNGRADE:
			eJbActionType = CustomerProductEJBEvent.DOWNGRADE;
			break;
		case CommonKeys.CUSTOMER_DEVICE_PROVIDE_MODIFY:
			setProductInfoWithDeviceProvideModify(request, ejbEvent);
			eJbActionType = CustomerProductEJBEvent.DEVICEPROVIDEMODIFY;
			break;
		case CommonKeys.CUSTOMER_PRODUCT_ACCOUNT_MODIFY:
			setProductInfoWithAcountModify(request, ejbEvent);
			eJbActionType = CustomerProductEJBEvent.PRODUCTACCOUNTMODIFY;
			break;

		default:
			System.out.println("txtActionType1: "
					+ Integer.parseInt(txtActionType));
			break;
		}
		ejbEvent.setActionType(eJbActionType);
		return ejbEvent;
	}

	private void setSAInfo(HttpServletRequest request,
			CustomerProductEJBEvent ejbEvent) throws WebActionException {
		String saID = request.getParameter("txtServiceAccountID");
		String targetSaID = request.getParameter("txtTargetServiceAccountID");
		if (WebUtil.StringHaveContent(saID)) {
			ejbEvent.setSaID(WebUtil.StringToInt(saID));
		} else {
			throw new WebActionException("业务帐户信息缺失！");
		}
		if (WebUtil.StringHaveContent(targetSaID)) {
			ejbEvent.setTargetSaID(WebUtil.StringToInt(targetSaID));
			said=targetSaID;
		} 
	}

	private void setCustomerInfo(HttpServletRequest request,
			CustomerProductEJBEvent ejbEvent) throws WebActionException {
		String customer = request.getParameter("txtCustomerID");
		if (WebUtil.StringHaveContent(customer)) {
			ejbEvent.setCustomerID(WebUtil.StringToInt(customer));
		} else {
			throw new WebActionException("请先做客户查询，定位客户！");
		}
	}

	private void setAccountInfo(HttpServletRequest request,
			CustomerProductEJBEvent ejbEvent) throws WebActionException {
		String account = request.getParameter("txtAccount");
		if (WebUtil.StringHaveContent(account)) {
			ejbEvent.setAccountID(WebUtil.StringToInt(account));
//		} else {
//			throw new WebActionException("请指定一个有效帐户！");
		}
	}

	private void setProductInfoWithDeviceProvideModify(
			HttpServletRequest request, CustomerProductEJBEvent ejbEvent)
			throws WebActionException {
		if (CommonKeys.CUSTOMER_DEVICE_PROVIDE_MODIFY == WebUtil
				.StringToInt(txtActionType)) {
			CustomerProductDTO cp = new CustomerProductDTO();
			if (WebUtil.StringHaveContent(request.getParameter("txtPsID"))) {
				cp.setPsID(WebUtil.StringToInt(request.getParameter("txtPsID")));
			} else {
				throw new WebActionException("数据异常！");
			}
			if (WebUtil.StringHaveContent(request
					.getParameter("txtDeviceProvide"))) {
				cp.setDeviceProvideFlag(request
						.getParameter("txtDeviceProvide"));
			} else {
				throw new WebActionException("数据异常！");
			}
			ejbEvent.setCustomerProductDTO(cp);
		}
	}

	private void setProductInfoWithAcountModify(HttpServletRequest request,
			CustomerProductEJBEvent ejbEvent) throws WebActionException {
		CustomerProductDTO cpDto = new CustomerProductDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtPsID"))) {
			cpDto.setPsID(WebUtil.StringToInt(request.getParameter("txtPsID")));
		} else {
			throw new WebActionException("setProductInfoWithAcountModify 数据异常！");
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtAccountID"))) {
			cpDto.setAccountID(WebUtil.StringToInt(request
					.getParameter("txtAccountID")));
		} else {
			throw new WebActionException("setProductInfoWithAcountModify 数据异常！");
		}
		ejbEvent.setCustomerProductDTO(cpDto);
	}

	private void setProductInfo(HttpServletRequest request,
			CustomerProductEJBEvent ejbEvent) throws WebActionException{
		// 客户暂停/恢复的产品ID信息
		ArrayList lstProductID = new ArrayList();

		if (CommonKeys.CUSTOMER_PRODUCT_UPGRADE == WebUtil
				.StringToInt(txtActionType)
				|| CommonKeys.CUSTOMER_PRODUCT_DOWNGRADE == WebUtil
						.StringToInt(txtActionType)) {
			String psid = request.getParameter("txtPsID");
			lstProductID.add(WebUtil.StringToInteger(psid));
			ejbEvent.setProductID(WebUtil.StringToInt(request.getParameter("txtObjectProduct")));
	      //  ejbEvent.setOldProductName(request.getParameter("txtProductName"));
		} else {
			if (WebUtil.StringHaveContent(request.getParameter("txtCPIDs"))) {
				String[] aProductID = request.getParameter("txtCPIDs").split(
						";");
				if ((aProductID != null) && (aProductID.length > 0)) {
					for (int i = 0; i < aProductID.length; i++){
						if(said!=null){
							if(Postern.isCPSANN(said, aProductID[i])){
								throw new WebActionException("目标账户不是正常状态，或者迁移产品不是正常状态，不能进行产品迁移！");
							}
						    if(Postern.ishasSameProduct(said, aProductID[i])){
							    throw new WebActionException("目标账户已经含有相同的产品，不能进行产品迁移！");
						    }
						}
						lstProductID.add(WebUtil.StringToInteger(aProductID[i]));
					}
				}
			}
		}
		ejbEvent.setColPsid(lstProductID);

	}

	private void setPayFeeInfo(HttpServletRequest request,
			CustomerProductEJBEvent ejbEvent) throws WebActionException {
		Collection feeList = getSessionFeeList(request);
		int customerID = WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID  = WebUtil.StringToInt(request.getParameter("txtAccount"));
		
		getPayList(request, customerID,accountID,
				   ejbEvent.getCsiPaymentList(),
				   CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE,
				   ejbEvent.getCsiPrePaymentDeductionList());

		ejbEvent.setCsiFeeList(feeList);

	}

	private void setCSIInfo(HttpServletRequest request,
			CustomerProductEJBEvent ejbEvent) {
		// 受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();

		if (Integer.parseInt(txtActionType) == CommonKeys.CUSTOMER_PRODUCT_PAUSE) {
			if (WebUtil.StringHaveContent(request.getParameter("txtEventReason")))
				csiDTO.setStatusReason(request.getParameter("txtEventReason"));
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PS);
		} else if (Integer.parseInt(txtActionType) == CommonKeys.CUSTOMER_PRODUCT_RESUME) {
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_RM);
		} else if (Integer.parseInt(txtActionType) == CommonKeys.CUSTOMER_PRODUCT_CANCEL) {
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CP);
		} else if (Integer.parseInt(txtActionType) == CommonKeys.CUSTOMER_PRODUCT_UPGRADE) {
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PU);
		} else if (Integer.parseInt(txtActionType) == CommonKeys.CUSTOMER_PRODUCT_DOWNGRADE) {
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PD);
		}else if (Integer.parseInt(txtActionType) == CommonKeys.CUSTOMER_PRODUCT_MOVE) {
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PM);
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
			csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
		        
		if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
	        csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
		ejbEvent.setCsiDto(csiDTO);
	}
}
