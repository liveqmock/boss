/*
 * Created on 2007-9-26
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.web.action.customer;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CAWalletChargeRecordDTO;
import com.dtv.oss.dto.CAWalletDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CAWalletEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.CurrentLogonOperator;
import java.math.BigDecimal;

/**
 * @author 260904l
 *
 * 创建小钱包(充值\确认创建)
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class IPPVCreateWebAction extends GeneralWebAction{

	/* (non-Javadoc)
	 * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		CAWalletEJBEvent saEvent = new CAWalletEJBEvent();
		CAWalletDTO dto = new CAWalletDTO();
		CAWalletChargeRecordDTO cawcrDto = new CAWalletChargeRecordDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtWalletId"))){
			dto.setWalletId(WebUtil.StringToInt(request.getParameter("txtWalletId")));
			cawcrDto.setWalletId(WebUtil.StringToInt(request.getParameter("txtWalletId")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID"))){
			dto.setServiceAccountId(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
			cawcrDto.setServiceAccountId(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));		
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtScSerialNo"))){
			dto.setScSerialNo(request.getParameter("txtScSerialNo"));
			cawcrDto.setScSerialNo(request.getParameter("txtScSerialNo"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtCAWalletCode"))){
			dto.setCaWalletCode(request.getParameter("txtCAWalletCode"));
			cawcrDto.setCaWalletCode(request.getParameter("txtCAWalletCode"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))){
			dto.setCustomerId(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
			cawcrDto.setCustomerId(WebUtil.StringToInt(request.getParameter("txtCustomerID")));		
		}
		
		if(WebUtil.StringHaveContent(request.getParameter("txtMopID"))){
			String mop = request.getParameter("txtMopID");
			String mopid = mop.split("-")[0];
			cawcrDto.setMopId(WebUtil.StringToInt(mopid));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtValue"))){

			cawcrDto.setValue(new BigDecimal(request.getParameter("txtValue")));

		}
		if(WebUtil.StringHaveContent(request.getParameter("txtPoint"))){			
			cawcrDto.setPoint(WebUtil.StringToInt(request.getParameter("txtPoint")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtDoPost"))){
			saEvent.setDoPost(request.getParameter("txtDoPost"));
		}
		cawcrDto.setOpId(CurrentLogonOperator.getOperator(request).getOperatorID());
		cawcrDto.setOrgId(CurrentLogonOperator.getCurrentOperatorOrgID(request));
		saEvent.setCawDto(dto);
		saEvent.setCawcrDto(cawcrDto);
		if(WebUtil.StringHaveContent(request.getParameter("txtActionType"))){
			if("walletCreate".equals(request.getParameter("txtActionType"))){
								//if(WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID"))
				//				&& Postern.walletCanBeCreate(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")),request.getParameter("txtCAWalletCode")))
						saEvent.setActionType(EJBEvent.IPPV_CREATE);
				//else
				//	throw new WebActionException("该小钱包已存在!");
			}
			else if("walletCharge".equals(request.getParameter("txtActionType")))
			saEvent.setActionType(EJBEvent.IPPV_CHARGE);
		}else
			throw new WebActionException("无法定位对小钱包的操作！");
		System.out.println("&&&&&&&&&&&&&&"+request.getParameter("txtCAWalletCode"));
		return saEvent;
	}

}
