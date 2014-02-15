package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.wrap.VOIPEventWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVOIPInfoEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * VOIP接口信息查询
 * @author 260904l
 *
 */

public class QueryVOIPInfoWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		VOIPEventWrap wrapDto=new VOIPEventWrap(); 
		String startNO=request.getParameter("txtStartNo");
		if(WebUtil.StringHaveContent(startNO)){
			wrapDto.setStartNO(WebUtil.StringToInt(startNO));
		}
		String endNO=request.getParameter("txtEndNo");
		if(WebUtil.StringHaveContent(endNO)){
			wrapDto.setEndNO(WebUtil.StringToInt(endNO));
		}
		String srartTime=request.getParameter("txtStartTime");
		if(WebUtil.StringHaveContent(srartTime)){
			wrapDto.setStartTime(WebUtil.StringToTimestampDefaultDayBegin(srartTime,null));
		}
		String endTime=request.getParameter("txtEndTime");
		if(WebUtil.StringHaveContent(endTime)){
			wrapDto.setEndTime(WebUtil.StringToTimestampDefaultDayEnd(endTime,null));
		}
		String eventClass=request.getParameter("txtEventClass");
		if(WebUtil.StringHaveContent(eventClass)){
			wrapDto.getSysEventDTO().setEventClass(WebUtil.StringToInt(eventClass));
		}
		String custmerID=request.getParameter("txtCustmerID");
		if(WebUtil.StringHaveContent(custmerID)){
			wrapDto.getSysEventDTO().setCustomerID(WebUtil.StringToInt(custmerID));
		}
		String serviceAccountID=request.getParameter("txtServiceAccountID");
		if(WebUtil.StringHaveContent(serviceAccountID)){
			wrapDto.getSysEventDTO().setServiceAccountID(WebUtil.StringToInt(serviceAccountID));
		}
		String csiID=request.getParameter("txtCsiID");
		if(WebUtil.StringHaveContent(csiID)){
			wrapDto.getSysEventDTO().setCsiID(WebUtil.StringToInt(csiID));
		}
		String serviceCode=request.getParameter("txtServiceCode");
		if(WebUtil.StringHaveContent(serviceCode)){
			wrapDto.setServiceCode(serviceCode);
		}
		return new QueryVOIPInfoEJBEvent(wrapDto,pageFrom,pageTo,QueryVOIPInfoEJBEvent.QUERY_VOIP_EVENTLOG);
	}
}
