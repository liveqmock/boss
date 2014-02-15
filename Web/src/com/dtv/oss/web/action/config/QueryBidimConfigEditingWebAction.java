package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BidimConfigSettingDTO;
import com.dtv.oss.dto.BidimConfigSettingWithValueDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryBidimConfigEditingWebAction extends QueryWebAction {
	public static final String EDITING_TYPE = "editing_type";

	public static final String EDITING_TYPE_VIEW_UPDATE = "view_detail_update_config";

	public static final String EDITING_TYPE_ADD = "new_config";
    /**
	public EJBEvent perform(HttpServletRequest request)
			throws WebActionException {
		// CurrentCustomer.ClearCurrentCustomer(request);
		String actionType = request.getParameter(EDITING_TYPE);
		System.out
				.println("*****************************************************************");
		if (actionType != null
				&& actionType.trim().equals(EDITING_TYPE_VIEW_UPDATE)) {
			System.out.println(this.getClass() + "view_detail_update_config");
			return super.perform(request);
		}
		System.out.println(this.getClass() + "other type");
		return null;

	}
**/
	 protected EJBEvent encapsulateData(HttpServletRequest request)
       throws Exception {
		BidimConfigSettingDTO dto = new BidimConfigSettingDTO();
		 if(request.getSession().getAttribute("ID")!=null){
		 	String txtId=(String) request.getSession().getAttribute("ID");
		 	dto.setId(WebUtil.StringToInt(txtId));
			 
	    }
		if (WebUtil.StringHaveContent(request.getParameter("txtID"))) {
			dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
		}
		
		
		return new ConfigQueryEJBEvent(dto, 0, 1,
				ConfigQueryEJBEvent.QUERY_BIDIM_CONFIG_WITH_VALUES);

	}

	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
	 
		System.out.println(this.getClass());
		System.out.println("cmdRespose:" + cmdResponse);
		if (cmdResponse == null) {
			List list = new ArrayList();
			BidimConfigSettingWithValueDTO dto = new BidimConfigSettingWithValueDTO();
			dto.setBidimConfigSettingDTO(new BidimConfigSettingDTO());
			list.add(dto);
			cmdResponse = new QueryCommandResponseImpl(1, list, 0, 1);
		}
		 if(request.getSession().getAttribute("ID")!=null){
		  request.getSession().removeAttribute("ID");
		 }
		super.doEnd(request, cmdResponse);
		String editingType = request.getParameter(EDITING_TYPE);
		if (editingType != null) {
			editingType = editingType.trim();
			request.setAttribute(EDITING_TYPE, editingType);
			String pageTitle = "";
			if (editingType.equals(EDITING_TYPE_VIEW_UPDATE)) {
				pageTitle = "配置详细信息";
			} else if (editingType.equals(EDITING_TYPE_ADD)) {
				pageTitle = "新增配置信息";
			}
			request.setAttribute("page_title", pageTitle);
		}

		String queryType = (String) request.getParameter("query_type");
		String strUrl = null;
		if (queryType != null && queryType.trim().equals("result")) {
			String configType = (String) request.getParameter("txtConfigType");
			String configSubType = (String) request
					.getParameter("txtConfigSubType");
			String valueType = (String) request.getParameter("txtValueType");
			String status = (String) request.getParameter("txtStatus");
			String from = (String) request.getParameter("txtFrom");
			String to = (String) request.getParameter("txtTo");
			String page = (String) request.getParameter("txtPage");
			if (configType == null) {
				configType = "";
			}
			if (configSubType == null) {
				configSubType = "";
			}
			if (valueType == null) {
				valueType = "";
			}
			if (status == null) {
				status = "";
			}
			if (from == null) {
				from = "0";
			}
			if (to == null) {
				to = String.valueOf(Integer.valueOf(from).intValue() + 10);
			}
			if (page == null) {
				page = null;
			}

			strUrl = "bidim_config_query.do?query_type=result&txtConfigType="
					+ configType + "&txtConfigSubType=" + configSubType;
			strUrl += "&txtValueType=" + valueType + "&txtStatus=" + status;
			strUrl += "&txtFrom=" + from + "&txtTo=" + to + "&txtPage=" + page;
		} else {
			strUrl = "bidim_config_query.do?query_type=entrance";
		}
		request.setAttribute("back_url", strUrl);
		System.out.println("***********************");
		System.out.println("strUrl:" + strUrl);
		System.out.println("***********************");
	}
}
