package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VodstbDeviceImportDetailDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryVodstbDeviceImportDetailWebAction extends DownloadWebAction {

	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		VodstbDeviceImportDetailDTO dto=new VodstbDeviceImportDetailDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtBatchID")))
		 dto.setBatchID(WebUtil.StringToInt(request.getParameter("txtBatchID")));
		setQueryParameter(new Integer(dto.getBatchID()));
		return new LogisticsQueryEJBEvent(dto, pageFrom, pageTo, LogisticsQueryEJBEvent.QUERY_VOD_STB_DEVICE_IMPORT_DETAIL);
	}
}