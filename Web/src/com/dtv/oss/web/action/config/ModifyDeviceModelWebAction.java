package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DeviceModelDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.DeviceModelEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * 设备型号操作
 * 
 * @author 260327h
 * 
 */
public class ModifyDeviceModelWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		DeviceModelDTO dto = new DeviceModelDTO();
		DeviceModelEJBEvent event = new DeviceModelEJBEvent();

		// 获取操作标志
		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "操作标志 dto" + aType);

		// 根据页面txtActionType来设置Event的操作类型,得不到任何操作标志时抛出异常.
		if ("CREATE".equalsIgnoreCase(aType)) {
			// 设置记录创建时间
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new DeviceModelEJBEvent(dto,
					DeviceModelEJBEvent.DEVICEMODEL_CREATE);
		} else if ("MODIFY".equalsIgnoreCase(aType)) {
			event = new DeviceModelEJBEvent(dto,
					DeviceModelEJBEvent.DEVICEMODEL_UPDATE);
			dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
		} else if ("DELETE".equalsIgnoreCase(aType)) {
			event = new DeviceModelEJBEvent(dto,
					DeviceModelEJBEvent.DEVICEMODEL_DELETE);
			event.setList(request.getParameterValues("ListID"));
			LogUtility.log(getClass(), LogLevel.DEBUG, "将删除的记录数:"+event.getList().length);
			return event;
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "非法操作");
			throw new WebActionException("非法操作");
		}
		
		// 加个验证,名称不空
		if (WebUtil.StringHaveContent(request
				.getParameter("txtDeviceModelName"))) {
			dto.setDeviceModel(request.getParameter("txtDeviceModelName"));
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "缺少设备型号名称");
			throw new WebActionException("缺少设备型号名称");
		}
 
			dto.setDeviceClass(request.getParameter("txtDeviceClass"));
		 
			dto.setStatus(request.getParameter("txtDeviceModelStatus"));
	 
			dto.setSerialLength(WebUtil.StringToInt(request
					.getParameter("txtSerialLength")));
		 
			dto.setProviderID(WebUtil.StringToInt(request
					.getParameter("txtProvider")));
		 
			dto.setDescription(request.getParameter("txtDeviceModelDesc"));
            
			if(WebUtil.StringHaveContent(request.getParameter("txtBusinessType")))
			dto.setBusinessType(request.getParameter("txtBusinessType"));
			
			dto.setManageDeviceFlag(request.getParameter("txtManagerDeviceFlag"));
  dto.setViewInCdtFlag(request.getParameter("txtViewInCdtFlag"));
			 if ("Y".equals(request.getParameter("txtViewInCdtFlag")) 
						&&	WebUtil.StringHaveContent(request.getParameter("txtKeySerialNoFrom")) 
						&&	WebUtil.StringHaveContent(request.getParameter("txtKeySerialNoTo")))	{
				 dto.setKeySerialNoFrom(WebUtil.StringToInt(request.getParameter("txtKeySerialNoFrom")));
				 dto.setKeySerialNoTo(WebUtil.StringToInt(request.getParameter("txtKeySerialNoTo")));
				
				  }
	 
		 if ("N".equals(request.getParameter("txtViewInCdtFlag")) 
						)	{
				 dto.setKeySerialNoFrom(0);
				 dto.setKeySerialNoTo(0);
				
				  }

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "修改设备型号 dto"
				+ dto.toString());

		return event;
	}

}
