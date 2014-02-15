package com.dtv.oss.web.action.groupcustomer;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.dto.ContractToPackageDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.ContractEJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>
 * Title: BOSS P5
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Digivision
 * </p>
 * User: chen jiang Date: 2006-3-20 Time: 15:22:51 To change this template use
 * File | Settings | File Templates.
 */
public class ModifyContractWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		ContractDTO contractDTO = new ContractDTO();

		ContractEJBEvent event = new ContractEJBEvent();
		if ("create".equals(request.getParameter("Action")))
			event.setActionType(GroupCustomerEJBEvent.CONTRACT_CREATE);
		if ("update".equals(request.getParameter("Action")))
			event.setActionType(GroupCustomerEJBEvent.CONTRACT_MODIFY);
		if (WebUtil.StringHaveContent(request.getParameter("dtLastmod")))
			contractDTO.setDtLastmod(WebUtil.StringToTimestamp(request
					.getParameter("dtLastmod")));
		contractDTO.setName(request.getParameter("txtName"));
		contractDTO.setNormaldate(WebUtil.StringToTimestamp(request
				.getParameter("txtNormalDate")));
		contractDTO.setStatus(request.getParameter("txtStatus"));
		contractDTO.setDescription(request.getParameter("txtDescription"));
		contractDTO.setSourceOrg(WebUtil.StringToInt(request
				.getParameter("txtOrgID")));

		contractDTO.setDatefrom(WebUtil.StringToTimestamp(request
				.getParameter("txtDateFrom")));
		contractDTO.setDateto(WebUtil.StringToTimestamp(request
				.getParameter("txtDateTo")));
		contractDTO.setOneOffFeeTotal(WebUtil.StringTodouble(request
				.getParameter("txtOneOffFeeTotal")));
		contractDTO.setRentFeeTotal(WebUtil.StringTodouble(request
				.getParameter("txtRentFeeTotal")));

		contractDTO.setPrepayAmount(WebUtil.StringTodouble(request
				.getParameter("txtPrepayAmount")));

		contractDTO.setPrepayMopID(WebUtil.StringToInt(request
				.getParameter("txtPrepayMopID")));

		contractDTO.setContractNo(request.getParameter("txtContractNo"));
		contractDTO.setSheetNo(request.getParameter("txtSheetNo"));
		contractDTO.setUserCount(WebUtil.StringToInt(request
				.getParameter("txtUserCount")));

		if (WebUtil.StringHaveContent(request
				.getParameter("ProductPackageList"))) {
			Collection packageCol = new ArrayList();
			String[] packageList = request.getParameter("ProductPackageList")
					.split(";");
			for (int i = 0; i < packageList.length; i++) {
				ContractToPackageDTO ctpDto = new ContractToPackageDTO();

				ctpDto.setContractNo(request.getParameter("txtContractNo"));
				ctpDto.setProductPackageID(WebUtil.StringToInt(packageList[i]));
				packageCol.add(ctpDto);
			}
			event.setContractToPackageIDCol(packageCol);
		}

		event.setContractDTO(contractDTO);
		System.out.println("the action type is " + event.getActionType());
		return event;
	}
}
