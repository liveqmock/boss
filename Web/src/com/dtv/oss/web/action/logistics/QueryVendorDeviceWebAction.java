package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * 查找供应商设备的Action
 * 主要用与供应商设备入库时，根据设备型号、设备类型和设备序列号查找供应商设备信息
 * add by jason.zhou 2007-3-13
 * @author 250713z
 *
 */
public class QueryVendorDeviceWebAction extends QueryWebAction {

	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {

		CommonQueryConditionDTO dto=new CommonQueryConditionDTO();
		 
         if(WebUtil.StringHaveContent(request.getParameter("txtDeviceClass")))
        	 dto.setSpareStr1(request.getParameter("txtDeviceClass"));
         if(WebUtil.StringHaveContent(request.getParameter("txtDeviceModel")))
        	 dto.setSpareStr2(request.getParameter("txtDeviceModel"));
         if(WebUtil.StringHaveContent(request.getParameter("txtSerialNo")))
        	 dto.setSpareStr3(request.getParameter("txtSerialNo"));
         
         return new LogisticsQueryEJBEvent(dto, pageFrom, pageTo, LogisticsQueryEJBEvent.QUERY_TYPE_VENDOR_DEVICE);
	}

}
