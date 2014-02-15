package com.dtv.oss.web.action.logistics;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;
import java.util.*;

//import oss class
import com.dtv.oss.web.util.*;
import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;

import com.dtv.oss.web.action.CheckTokenWebAction;
import com.dtv.oss.web.exception.WebActionException;

public class ManualChangeDeviceStatusWebAction extends CheckTokenWebAction {
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "����webaction");

		DeviceTransitionDTO theDTO;
		DeviceStockEJBEvent event = null;
		ArrayList lstDeviceID;

		theDTO = new DeviceTransitionDTO();
		event = new DeviceStockEJBEvent();
		theDTO.setBatchNo(request.getParameter("txtOperationNo"));
		theDTO.setAction("M"); //��ʾ�ֹ��޸��豸״̬
		if (WebUtil.StringHaveContent(request.getParameter("txtDescription")))
			theDTO.setDescription(request.getParameter("txtDescription"));
		//�޹صĶ���,ҳ���ϲ�û����ص������߼�,
//		if (WebUtil.StringHaveContent(request.getParameter("txtFromType")))
//			theDTO.setFromType(request.getParameter("txtFromType"));
//		if (WebUtil.StringHaveContent(request.getParameter("txtFromID")))
//			theDTO.setFromID(WebUtil.StringToInt(request
//					.getParameter("txtFromID")));
//		if (WebUtil.StringHaveContent(request.getParameter("txtFromType")))
//			theDTO.setToType(request.getParameter("txtFromType"));
//		if (WebUtil.StringHaveContent(request.getParameter("txtFromID")))
//			theDTO.setToID(WebUtil.StringToInt(request
//					.getParameter("txtFromID")));
		//theDTO.setFromDeviceStatus(request.getParameter("txtFromDeviceStatus"));
		//theDTO.setToDeviceStatus(request.getParameter("txtToDeviceStatus"));
		
		
		String[] aDeviceID = request.getParameterValues("DeviceList");
		
		lstDeviceID = new ArrayList();
		if ((aDeviceID != null) && (aDeviceID.length > 0)) {
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "�豸ID:"
					+ aDeviceID.length);
			for (int i = 0; i < aDeviceID.length; i++)
				lstDeviceID.add(WebUtil.StringToInteger(aDeviceID[i]));
		} else {
			throw new WebActionException("û��ת���豸�б�.");
		}

		theDTO.setDeviceNumber(lstDeviceID.size());
		event.setActionType(LogisticsEJBEvent.DEVICE_MANULCHANGE_STATUS);
		event.setDevTransDTO(theDTO);
		event.setDeviceArray(lstDeviceID);
		event.setToStatus(request.getParameter("txtToDeviceStatus"));
		event.setDoPost(true);

		return event;
	}

}