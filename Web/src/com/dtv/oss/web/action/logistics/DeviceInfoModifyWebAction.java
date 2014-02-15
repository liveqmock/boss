package com.dtv.oss.web.action.logistics;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 * @author Wesley Shu
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class DeviceInfoModifyWebAction extends GeneralWebAction{
 
	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
		if(!WebUtil.StringHaveContent(request.getParameter("txtDeviceID")))
            throw new WebActionException("没有相关的设备信息标识");
   	 DeviceStockEJBEvent event= new DeviceStockEJBEvent(); 
   	TerminalDeviceDTO dtDto=new TerminalDeviceDTO();
   	DeviceTransitionDTO devTransDTO = new DeviceTransitionDTO();
   	dtDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDeviceDtLastmod")));
   	 //设备ID
   	 dtDto.setDeviceID(WebUtil.StringToInt(request.getParameter("txtDeviceID")));
   	 //序列号
   	 dtDto.setSerialNo(request.getParameter("txtSerialNo"));
   	 //类型
   	dtDto.setDeviceClass(request.getParameter("txtDeviceClass"));
   	//型号
   	dtDto.setDeviceModel(request.getParameter("txtDeviceModel"));
   	//批号
   	devTransDTO.setBatchNo(request.getParameter("txtBatchID"));
   	//仓库
   	dtDto.setDepotID(WebUtil.StringToInt(request.getParameter("txtDepotID")));
   	//属主类型
   	dtDto.setAddressType(request.getParameter("txtZoneStation"));
   	//属主
   	dtDto.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
   	//租售
   	dtDto.setLeaseBuy(request.getParameter("txtLeaseBuy"));
   	//租售日期
   	dtDto.setDateFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
   	//配对
   	dtDto.setMatchFlag(request.getParameter("txtMatchFlag"));
   	//配对设备号
   	dtDto.setMatchDeviceID(WebUtil.StringToInt(request.getParameter("txtMatchDeviceID")));
   	//置位标记
   	dtDto.setCaSetFlag(request.getParameter("txtCaSetFlag"));
   	//置位日期
   	dtDto.setCaSetDate(WebUtil.StringToTimestamp(request.getParameter("txtCaSetDate")));
   	//保修截止日期
   	dtDto.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
   	//保修期的长度
   	dtDto.setGuaranteeLength(WebUtil.StringToInt(request.getParameter("txtGuaranteeLength")));
   	//CM MAC地址
   	dtDto.setMACAddress(request.getParameter("txtMacAddress"));
   	//终端MAC地址
   	dtDto.setInterMACAddress(request.getParameter("txtInterMacAddress"));
   	//是否二手
   	dtDto.setUseFlag(request.getParameter("txtUseFlag"));
   	//状态
   	dtDto.setStatus(request.getParameter("txtStatus"));
   	//设备预授权
   	dtDto.setPreAuthorization(request.getParameter("txtPreAuthorization"));
   	//设备用途
   	String purposeStrList = request.getParameter("txtPurposeStrList");
   	//平移系统内部授权号
   	dtDto.setOkNumber(request.getParameter("txtOkNumber"));
   	//备注
   	dtDto.setComments(request.getParameter("txtComments"));
   	if(WebUtil.StringHaveContent(purposeStrList))
   	{
   		if(!purposeStrList.startsWith(","))
   		{
   			purposeStrList = ","+purposeStrList;
   		}
   		if(!purposeStrList.endsWith(","))
   		{
   			purposeStrList = purposeStrList+",";
   		}
   	}
   	dtDto.setPurposeStrList(purposeStrList);
   	
   	event.setTerDeviceDTO(dtDto);
   	event.setDevTransDTO(devTransDTO);
   	event.setActionType(LogisticsEJBEvent.DEVICE_MODIFY);
   	 
     	return event;
   }

}