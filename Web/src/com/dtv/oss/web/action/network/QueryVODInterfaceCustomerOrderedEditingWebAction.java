package com.dtv.oss.web.action.network;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VODInterfaceCustomerOrderedDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVODInterfaceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author wanghao 2006-5-30 15:48:24
 */
public class QueryVODInterfaceCustomerOrderedEditingWebAction extends
		QueryWebAction {

	private static final String EDITING_TYPE = "editing_type";

	private static final String EDITING_TYPE_NEW = "new";

	private static final String EDITING_TYPE_UPDATE = "update";

	private static final String EDITING_TYPE_VIEW = "view";

	private String editingType;

	public EJBEvent perform(HttpServletRequest request)
			throws WebActionException {
		editingType = (String) request.getParameter(EDITING_TYPE);
		if (editingType == null
				|| (editingType = editingType.trim()).length() == 0) {
			editingType = EDITING_TYPE_VIEW;
		}
		System.out.println(getClass() + ",editingtype:" + editingType);
		request.setAttribute(EDITING_TYPE, editingType);
		setQueryFileterAttribute(request);
		if (EDITING_TYPE_VIEW.equals(editingType)
				|| EDITING_TYPE_UPDATE.equals(editingType = editingType.trim())) {
			return super.perform(request);
		}
		return null;
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		VODInterfaceCustomerOrderedDTO dto = new VODInterfaceCustomerOrderedDTO();
		setDtoByRequest(dto, request);
		QueryVODInterfaceEJBEvent event = new QueryVODInterfaceEJBEvent(dto, 1,
				1, QueryVODInterfaceEJBEvent.QUERY_CUSTOMER_ORDERED_DETAIL);
		return event;

	}

	private void setDtoByRequest(VODInterfaceCustomerOrderedDTO dto,
			HttpServletRequest request) {
		// SEQNO
		String seqNO = request.getParameter("txtSeqNO");
		dto.setSeqNO(WebUtil.StringToInt(seqNO));
		 
	}

	private void setQueryFileterAttribute(HttpServletRequest request) {
		VODInterfaceCustomerOrderedDTO dto = new VODInterfaceCustomerOrderedDTO();
		// VOD主机名称 点播类型 创建时间起始 创建时间截止 设备Mac地址 影片ID 帐目类型 客户ID
		// HOSTID,VODTYPE,CREATETIMEbegin,CREATETIMEend,DEVICEMACADDR,MOVIEID,ACCTITEMTYPEID,CUSTID
		String hostID = request.getParameter("txtHostID");
		String vodType = request.getParameter("txtVodType");
		String createTimeBegin = request.getParameter("txtCreateTimeBegin");
		String createTimeEnd = request.getParameter("txtCreateTimeEnd");
		String deviceMacAddr = request.getParameter("txtDeviceMacAddr");
		String movieID = request.getParameter("txtMovieID");
		String acctItemTypeID = request.getParameter("txtAcctItemTypeID");
		String custID = request.getParameter("txtCustID");
		if (hostID == null || (hostID = hostID.trim()).length() == 0) {
			hostID = "0";
		}
		if (vodType == null || (vodType = vodType.trim()).length() == 0) {
			vodType = "";
		}
	  
		if (deviceMacAddr == null
				|| (deviceMacAddr = deviceMacAddr.trim()).length() == 0) {
			deviceMacAddr = "";
		}
		if (movieID == null || (movieID = movieID.trim()).length() == 0) {
			movieID = "";
		}
		if (acctItemTypeID == null
				|| (acctItemTypeID = acctItemTypeID.trim()).length() == 0) {
			acctItemTypeID = "";
		}
		if (custID == null || (custID = custID.trim()).length() == 0) {
			custID = "0";
		}
		dto.setHostID(WebUtil.StringToInt(hostID));
		dto.setVodType(vodType);
		dto.setCreateTimeBegin(WebUtil.StringToTimestampWithDayBegin(createTimeBegin));
		dto.setCreateTimeEnd(WebUtil.StringToTimestampWithDayEnd(createTimeEnd));
	//	dto.setCreateTimeBegin(createBegin);
	//	dto.setCreateTimeEnd(createEnd);
		dto.setDeviceMacAddr(deviceMacAddr);
		dto.setMovieID(movieID);
		dto.setAcctItemTypeID(acctItemTypeID);
		dto.setCustID(WebUtil.StringToInt(custID));

		request.setAttribute("QueryFilter", dto);

		String from = request.getParameter("txtFrom");
		String to = request.getParameter("txtTo");
		String page = request.getParameter("txtPage");
		if (from == null || (from = from.trim()).length() == 0) {
			from = "1";
		}
		if (to == null || (to = to.trim()).length() == 0) {
			to = "10";
		}
		request.setAttribute("txtFrom", from);
		request.setAttribute("txtTo", to);
		request.setAttribute("txtPage", page);
	}

	public void doEnd(HttpServletRequest request, CommandResponse cr) {

		if (cr == null) {
			List list = new ArrayList();
			VODInterfaceCustomerOrderedDTO dto = new VODInterfaceCustomerOrderedDTO();
			list.add(dto);
			cr = new QueryCommandResponseImpl(1, list, 0, 1);
		}
		super.doEnd(request, cr);
	}

}
