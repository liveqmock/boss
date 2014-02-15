package com.dtv.oss.web.action.network;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VODInterfaceHostDTO;
import com.dtv.oss.dto.VODInterfaceProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.ModifyVODInterfaceEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author wanghao 2006-6-1 15:29:25
 */
public class ModifyVODInterfaceWebAction extends GeneralWebAction {
	private static String dateFormatPattern = "yyyy-MM-dd";

	private static final String MODIFY_TYPE = "modify_type";

	private static final String MODIFY_VOD_HOST_UPDATE = "vod_host_update";

	private static final String MODIFY_VOD_HOST_NEW = "vod_host_new";

	private static final String MODIFY_VOD_PRODUCT_UPDATE = "vod_product_update";

	private static final String MODIFY_VOD_PRODUCT_NEW = "vod_product_new";

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		String actionType = (String) request.getParameter(MODIFY_TYPE);
		if (actionType == null) {
			return null;
		}

		// 
		ModifyVODInterfaceEJBEvent event = null;
		Object dto = null;
		int type = 0;
		String backUrl = null;
		if (MODIFY_VOD_HOST_NEW.equals(actionType)) {
			VODInterfaceHostDTO fsDto = new VODInterfaceHostDTO();
			setDtoValueByRequest(fsDto, request);
			dto = fsDto;
			type = ModifyVODInterfaceEJBEvent.HOST_NEW;
			backUrl = "vod_interface_host_query.do";
		} else if (MODIFY_VOD_HOST_UPDATE.equals(actionType)) {
			VODInterfaceHostDTO aitDto = new VODInterfaceHostDTO();
			setDtoValueByRequest(aitDto, request);
			dto = aitDto;
			type = ModifyVODInterfaceEJBEvent.HOST_UPDATE;
			backUrl = "vod_interface_host_query.do";
		} else if (MODIFY_VOD_PRODUCT_NEW.equals(actionType)) {
			VODInterfaceProductDTO aitDto = new VODInterfaceProductDTO();
			setDtoValueByRequest(aitDto, request);
			dto = aitDto;
			type = ModifyVODInterfaceEJBEvent.PRODUCT_NEW;
			backUrl = "vod_interface_product_query.do";
		} else if (MODIFY_VOD_PRODUCT_UPDATE.equals(actionType)) {
			VODInterfaceProductDTO aitDto = new VODInterfaceProductDTO();
			setDtoValueByRequest(aitDto, request);
			dto = aitDto;
			type = ModifyVODInterfaceEJBEvent.PRODUCT_UPDATE;
			backUrl = "vod_interface_product_query.do";
		}

		if (backUrl == null) {
			backUrl = "vod_interface_index.do";
		}
		request.setAttribute("back_url", backUrl);
		event = new ModifyVODInterfaceEJBEvent(dto, type);
		return event;
	}

	private void setDtoValueByRequest(VODInterfaceHostDTO dto,
			HttpServletRequest request) {
		String hostName = request.getParameter("txtHostName");
		String hostID = request.getParameter("txtHostID");
		String vodType = request.getParameter("txtVodType");
		String status = request.getParameter("txtStatus");
		String ip = request.getParameter("txtIp");
		String port = request.getParameter("txtPort");
		String backIp = request.getParameter("txtIpBack");
		String backPort = request.getParameter("txtPortBack");
		String protocolType = request.getParameter("txtProtocolType");
		String loopSize = request.getParameter("txtLoopSize");
		String loopInterval = request.getParameter("txtLoopInterval");
		String tryTime = request.getParameter("txtTryTime");
		String validStartTime = request.getParameter("txtValidStartTime");
		String validEndTime = request.getParameter("txtValidEndTime");
		String lastRunTime = request.getParameter("txtLastRunTime");
		String lastStopTime = request.getParameter("txtLastStopTime");
		String lastModifyTime = request.getParameter("txtLastMod");

		if (hostID == null || (hostID = hostID.trim()).length() == 0) {
			hostID = "0";
		}
		dto.setHostID(Integer.valueOf(hostID).intValue());
		dto.setHostName(hostName);
		dto.setVodType(vodType);
		dto.setStatus(status);
		dto.setIp(ip);
		dto.setPort(port);
		dto.setIpBack(backIp);
		dto.setPortBack(backPort);
		dto.setProtocolType(protocolType);
		dto.setLoopSize(Integer.valueOf(loopSize).intValue());

		if (loopInterval == null
				|| (loopInterval = loopInterval.trim()).length() == 0) {
			loopInterval = "0";
		}
		dto.setLoopInterval(Integer.valueOf(loopInterval).intValue());

		if (tryTime == null || (tryTime = tryTime.trim()).length() == 0) {
			tryTime = "0";
		}
		dto.setTryTime(Integer.valueOf(tryTime).intValue());

		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
		long time;

		if (validStartTime != null
				&& (validStartTime = validStartTime.trim()).length() > 0) {
			try {
				time = dateFormat.parse(validStartTime).getTime();
				dto.setValidStartTime(new Timestamp(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (validEndTime != null
				&& (validEndTime = validEndTime.trim()).length() > 0) {
			try {
				time = dateFormat.parse(validEndTime).getTime();
				dto.setValidEndTime(new Timestamp(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (lastRunTime != null
				&& (lastRunTime = lastRunTime.trim()).length() > 0) {
			try {
				time = dateFormat.parse(lastRunTime).getTime();
				dto.setLastRunTime(new Timestamp(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (lastStopTime != null
				&& (lastStopTime = lastStopTime.trim()).length() > 0) {
			try {
				time = dateFormat.parse(lastStopTime).getTime();
				dto.setLastStopTime(new Timestamp(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		 if(WebUtil.StringHaveContent(lastModifyTime))
	        dto.setDtLastMod(WebUtil.StringToTimestamp(lastModifyTime));
		 

	}

	private void setDtoValueByRequest(VODInterfaceProductDTO dto,
			HttpServletRequest request) {

		String smsProductID = request.getParameter("txtSmsProductID");
		String billingCodeList = request.getParameter("txtBillingCodeList");
		String newSaFlag = request.getParameter("txtNewSAFlag");
		String acctItemTypeID = request.getParameter("txtAcctItemTypeID");
		String status = request.getParameter("txtStatus");
		String lastModifyTime = request.getParameter("txtLastMod");

		if (smsProductID == null
				|| (smsProductID = smsProductID.trim()).length() == 0) {
			smsProductID = "0";
		}
		dto.setSmsProductID(Integer.valueOf(smsProductID).intValue());

		dto.setBillingCodeList(billingCodeList);
		dto.setNewsaflag(newSaFlag);
		dto.setAcctItemTypeID(acctItemTypeID);
		dto.setStatus(status);
	 if(WebUtil.StringHaveContent(lastModifyTime))
        dto.setDtLastMod(WebUtil.StringToTimestamp(lastModifyTime));
		 
	}

}
