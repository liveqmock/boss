package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DevicePutIntoDepotEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;

import com.dtv.oss.web.exception.WebActionException;

import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.smartUpload.Request;
import com.dtv.oss.web.action.CheckTokenWebAction;

public class DevicePutIntoDepotWebAction extends CheckTokenWebAction {
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
				
		if (!WebUtil.StringHaveContent(request.getParameter("txtDeviceModel")))
			throw new WebActionException("没有给出设备型号");
		if (!WebUtil.StringHaveContent(request.getParameter("txtDeviceClass")))
			throw new WebActionException("没有给出设备类别");
		if (!WebUtil.StringHaveContent(request
				.getParameter("txtDeviceProvider")))
			throw new WebActionException("没有给出设备制造商");
		if (!WebUtil.StringHaveContent(request
				.getParameter("txtGuaranteeLength")))
			throw new WebActionException("没有给出保修期");
		if (!WebUtil.StringHaveContent(request.getParameter("txtDepotID")))
			throw new WebActionException("没有选择仓库");
		if (!WebUtil.StringHaveContent(request.getParameter("txtBatchNo")))
			throw new WebActionException("没有给出入库单编号");
		if (!WebUtil.StringHaveContent(request
				.getParameter("txtTerminalDevices")))
			throw new WebActionException("没有给出设备序列号");
		if (!WebUtil.StringHaveContent(request.getParameter("txtInAndOut")))
			throw new WebActionException("没有录入否立即出库");
		if ("Y".equals(request.getParameter("txtInAndOut")) && !WebUtil.StringHaveContent(request.getParameter("txtOutOrgID")))
			throw new WebActionException("立即出库必须录入组织信息");
		
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		theDTO.setSpareStr1(request.getParameter("txtDeviceModel"));
		theDTO.setSpareStr2(request.getParameter("txtDeviceProvider"));
		theDTO.setSpareStr3(request.getParameter("txtGuaranteeLength"));
		theDTO.setSpareStr4(request.getParameter("txtDepotID"));
		theDTO.setSpareStr5(request.getParameter("txtBatchNo"));
		theDTO.setSpareStr6(request.getParameter("txtComments"));
		theDTO.setSpareStr7(request.getParameter("txtTerminalDevices"));
		theDTO.setSpareStr8(request.getParameter("checkModelField"));
		theDTO.setSpareStr9(request.getParameter("checkModelDesc"));
		theDTO.setSpareStr10(request.getParameter("txtDeviceClass"));
		theDTO.setSpareStr11(request.getParameter("txtStatusReason"));
		
		
		//add by jason 2007-3-11
		theDTO.setSpareStr12(request.getParameter("txtInAndOut"));
		theDTO.setSpareStr13(request.getParameter("txtOutOrgID"));
		//end
		
		//add by chaoqiu 2007-5-15
		theDTO.setSpareStr14(request.getParameter("txtPurposeStrList"));
		
		//end
		
		return new DevicePutIntoDepotEJBEvent(theDTO,LogisticsEJBEvent.DEVICE_PUT_INTO_DEPOT);

	}
}
