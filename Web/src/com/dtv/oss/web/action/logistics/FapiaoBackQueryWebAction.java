package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.FaPiaoEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/*
 * 发票回库查询 根据条件查询发票序列号
 */
public class FapiaoBackQueryWebAction extends GeneralWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		//if (!WebUtil.StringHaveContent(request.getParameter("txtVolumnSN")))
		//	throw new WebActionException("没有给出发票册序列号!");
		if (!WebUtil.StringHaveContent(request.getParameter("txtType")))
			throw new WebActionException("参数错误:发票类型未知!");
//		if (!WebUtil.StringHaveContent(request.getParameter("txtFromAddressID")))
//			throw new WebActionException("参数错误:发票原地址未知!");

		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		// 发票册序列号
		//theDTO.setSpareStr1(request.getParameter("txtVolumnSN"));
		// 发票类型
		theDTO.setSpareStr2(request.getParameter("txtType"));
		// 起始发票字轨号码
		theDTO.setSpareStr3(request.getParameter("txtSerialBegin"));
		// 终止发票字轨号码
		theDTO.setSpareStr4(request.getParameter("txtSerialEnd"));
		//发票原地址类型
		theDTO.setSpareStr5(request.getParameter("txtFromAddressType"));
		//发票原地址
		theDTO.setSpareStr6(request.getParameter("txtFromAddressID"));
		FaPiaoEJBEvent theEvent = new FaPiaoEJBEvent();
		theEvent.setActionType(LogisticsEJBEvent.FAPIAO_BACK_QUERY);
		theEvent.setCommDTO(theDTO);
		return theEvent;
	}
}
