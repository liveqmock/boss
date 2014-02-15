package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DeviceMatchToProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigSystemEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ModifyProduct2DeviceWebAction extends GeneralWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ConfigSystemEJBEvent ejbEvent=new ConfigSystemEJBEvent();
		
		if("DeletDeviceModel".equals(request.getParameter("Action")))
			ejbEvent.setActionType(EJBEvent.DELETE_DEVICE_TO_PRODUCT);
		else if("AddDeviceModel".equals(request.getParameter("Action")))
			ejbEvent.setActionType(EJBEvent.ADD_DEVICE_TO_PRODUCT);
		String productID=request.getParameter("txtProductID");
		String deviceID=request.getParameter("deviceID");
		List d2pList=new ArrayList();
		if (productID != null && deviceID != null) {
            String[] deviceModel_array = deviceID.split(";");
            if(deviceModel_array.length == 0)
            	throw new WebActionException("当前页面没有数据！");
            for(int i=0;i<deviceModel_array.length;i++){
            	DeviceMatchToProductDTO dto=new DeviceMatchToProductDTO();
            	dto.setDeviceModel(deviceModel_array[i]);
            	dto.setProductId(WebUtil.StringToInt(productID));
            	d2pList.add(dto);
            }
            deviceModel_array=null;
		}
		productID=null;
		deviceID=null;
		
		ejbEvent.setCol(d2pList);
		return ejbEvent;
	}

}
