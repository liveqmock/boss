package com.dtv.oss.web.action.config;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BidimConfigSettingDTO;
import com.dtv.oss.dto.BidimConfigSettingValueDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigBidimEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ModifyBidimConfigWebAction extends GeneralWebAction {

	public static final String MODIFY_TYPE = "modify_type";

	public static final String MODIFY_TYPE_NEW = "new";

	public static final String MODIFY_TYPE_UPDATE = "update";

	public static final String MODIFY_TYPE_DELETE = "delete";

	public static final String PARAMETER_NEW_APPENDED_VALUES_STRING = "new_appended_values";

	public static final String PARAMETER_TO_BE_DELETED_VALUES_STRING = "to_be_deleted_values";

	private static final String OBJECT_STRING_SEPERATOR = "@*@";

	private static final String OBJECT_ITEM_STRING_SEPERATOR = "@@";
	
	public static final String EDITING_TYPE = "editing_type";

	boolean doPost = false;

	protected boolean needCheckToken() {
		return doPost;

	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm"))) {
			if (request.getParameter("confirm").equalsIgnoreCase("true")) {
				doPost = true;

			}
		}

	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		BidimConfigSettingDTO dto = getBidimConfigSettingDTOFromRequest(request);
		ConfigBidimEJBEvent event = new ConfigBidimEJBEvent();
		event.setBidimDto(dto);

		String modifyType = request.getParameter(MODIFY_TYPE);
		int eventType = -1;
		if (MODIFY_TYPE_NEW.equals(modifyType)) {
			Timestamp time = TimestampUtility.getCurrentTimestamp();
			dto.setDtCreate(time);
			//dto.setDtLastmod(time);

			eventType = EJBEvent.CONFIG_BIDIM_CREATE;
			List list = getNewAppendedValues(request);
			System.out.println("*****NewAppendedValues****&&&&&&&&&&======="+list.toString());
			event.setNewAppendedBidimValuesList(list);
			// request.setAttribute("back_url",
			// "bidim_config_editing.do?editing_type=new_config");
		} else if (MODIFY_TYPE_UPDATE.equals(modifyType)) {
			//Timestamp time = TimestampUtility.getCurrentTimestamp();
			//dto.setDtLastmod(time);
			//dto.setDtCreate(time);
			eventType = EJBEvent.CONFIG_BIDIM_UPDATE;
			List list = getToBeDeletedValues(request);
			event.setToBeDeletedBidimValuesList(list);
			list = getNewAppendedValues(request);
			event.setNewAppendedBidimValuesList(list);
			// request.setAttribute("back_url",
			// "bidim_config_editing.do?editing_type=view_detail_update_config&txtID="
			// + dto.getId());
		} else if (MODIFY_TYPE_DELETE.equals(modifyType)) {
			eventType = EJBEvent.CONFIG_BIDIM_DELETE;
		}
		 if(request.getParameter("txtID")!=null && !"".equals(request.getParameter("txtID"))){
	    	request.getSession().setAttribute("ID",request.getParameter("txtID"));
	    }
		 if(request.getParameter(EDITING_TYPE)!=null && !"".equals(request.getParameter(EDITING_TYPE))){
	    	request.getSession().setAttribute("EDITINGTYPE",request.getParameter(EDITING_TYPE));
	    }
		 
		event.setActionType(eventType);
		System.out.println("********************************************");
		System.out.println(request.getParameter("txtServiceId"));
		System.out.println("event.getActionType():" + event.getActionType());
		System.out.println("event.getBidimDto()" + event.getBidimDto());
		System.out.println("event.getNewAppendedBidimValuesList()"
				+ event.getNewAppendedBidimValuesList());
		System.out.println("event.getToBeDeletedBidimValuesList()"
				+ event.getToBeDeletedBidimValuesList());
		System.out.println("********************************************");
		String backUrl = (String) request.getParameter("back_url");
		if (backUrl == null || backUrl.trim().length() == 0) {
			backUrl = "bidim_config_query.do?query_type=entrance";
		}
		request.setAttribute("back_url", backUrl);

		return event;
	}

	private BidimConfigSettingDTO getBidimConfigSettingDTOFromRequest(
			HttpServletRequest request) {
		BidimConfigSettingDTO dto = new BidimConfigSettingDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtID"))) {
			dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtName"))) {
			dto.setName(request.getParameter("txtName"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtConfigType"))) {
			dto.setConfigType(request.getParameter("txtConfigType"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtConfigSubType"))) {
			dto.setConfigSubType(request.getParameter("txtConfigSubType"));
		}
		//if (WebUtil.StringHaveContent(request.getParameter("txtServiceId"))) {
		//业务类型可以为空
			dto.setServiceId(WebUtil.StringToInt(request
					.getParameter("txtServiceId")));
		//}
		if (WebUtil.StringHaveContent(request.getParameter("txtValueType"))) {
			dto.setValueType(request.getParameter("txtValueType"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus"))) {
			dto.setStatus(request.getParameter("txtStatus"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtAllowNull"))) {
			dto.setAllowNull(request.getParameter("txtAllowNull"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtDescription"))) {
			dto.setDescription(request.getParameter("txtDescription"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("dt_lastmod_time"))) {
			long time = WebUtil.StringToLong(request
					.getParameter("dt_lastmod_time"));
			Timestamp dtLastmod = new Timestamp(time);
			dto.setDtLastmod(dtLastmod);
		}
		if (WebUtil.StringHaveContent(request.getParameter("dt_create_time"))) {
			long time = WebUtil.StringToLong(request
					.getParameter("dt_create_time"));
			Timestamp dtCreate = new Timestamp(time);
			dto.setDtCreate(dtCreate);			
		}
		return dto;
	}

	private List getToBeDeletedValues(HttpServletRequest request) {
		String pString = request
				.getParameter(PARAMETER_TO_BE_DELETED_VALUES_STRING);
		System.out.println("********************************************");
		System.out
				.println("request.getParameter(PARAMETER_TO_BE_DELETED_VALUES_STRING):"
						+ request
								.getParameter(PARAMETER_TO_BE_DELETED_VALUES_STRING));
		System.out.println("********************************************");
		return parsingValues(pString);
	}

	private List getNewAppendedValues(HttpServletRequest request) {
		String pString = request
				.getParameter(PARAMETER_NEW_APPENDED_VALUES_STRING);
		return parsingValues(pString);
	}

	public List parsingValues(String pString) {
		List valuesList = new ArrayList();
		if (pString == null || (pString = pString.trim()).length() == 0) {
			return valuesList;
		}

		int index = -1;
		String oneObjectString;
		BidimConfigSettingValueDTO valueDTO = null;
		while (true) {
			index = pString.indexOf(OBJECT_STRING_SEPERATOR);
			if (index > 0) {
				oneObjectString = pString.substring(0, index);
				valueDTO = parsingValueDTO(oneObjectString);
				valuesList.add(valueDTO);
			} else if (index == -1) {
				valueDTO = parsingValueDTO(pString);
				valuesList.add(valueDTO);
				break;
			}
			if (index < pString.length() - OBJECT_STRING_SEPERATOR.length()) {
				pString = pString.substring(index
						+ OBJECT_STRING_SEPERATOR.length());
			} else {
				break;
			}

		}
		return valuesList;

	}

	/**
	 * id,code,description,status/,code,description,status
	 * 
	 * @param valueString
	 * @return
	 */
	private BidimConfigSettingValueDTO parsingValueDTO(String valueString) {
		BidimConfigSettingValueDTO dto = new BidimConfigSettingValueDTO();
		Timestamp time = TimestampUtility.getCurrentTimestamp();
		dto.setDtCreate(time);
		dto.setDtLastmod(time);
		if (valueString == null
				|| (valueString = valueString.trim()).length() == 0) {
			return dto;
		}

		int index = -1;
		String oneItem;

		// id
		index = valueString.indexOf(OBJECT_ITEM_STRING_SEPERATOR);
		if (index > 0) {
			oneItem = valueString.substring(0, index);
			if (oneItem.trim().length() > 0) {
				dto.setId(Integer.valueOf(oneItem).intValue());
			}
		}
		if (index == -1) {
			dto.setId(Integer.valueOf(valueString).intValue());
			return dto;
		}
		if (index >= valueString.length()
				- OBJECT_ITEM_STRING_SEPERATOR.length()) {
			return dto;
		}

		// code
		valueString = valueString.substring(index
				+ OBJECT_ITEM_STRING_SEPERATOR.length());
		index = valueString.indexOf(OBJECT_ITEM_STRING_SEPERATOR);
		if (index > 0) {
			oneItem = valueString.substring(0, index);
			if (oneItem.trim().length() > 0) {
				dto.setCode(oneItem.trim());
			}
		}
		if (index == -1) {
			dto.setCode(valueString);
			return dto;
		}
		if (index >= valueString.length()
				- OBJECT_ITEM_STRING_SEPERATOR.length()) {
			return dto;
		}

		// description
		valueString = valueString.substring(index
				+ OBJECT_ITEM_STRING_SEPERATOR.length());

		index = valueString.indexOf(OBJECT_ITEM_STRING_SEPERATOR);
		if (index > 0) {
			oneItem = valueString.substring(0, index);
			if (oneItem.trim().length() > 0) {
				dto.setDescription(oneItem.trim());
			}
		}
		if (index == -1) {
			dto.setDescription(valueString);
			return dto;
		}
		if (index >= valueString.length()
				- OBJECT_ITEM_STRING_SEPERATOR.length()) {
			return dto;
		}
		// status
		valueString = valueString.substring(index
				+ OBJECT_ITEM_STRING_SEPERATOR.length());
		index = valueString.indexOf(OBJECT_ITEM_STRING_SEPERATOR);
		if (index > 0) {
			oneItem = valueString.substring(0, index);
			if (oneItem.trim().length() > 0) {
				dto.setStatus(oneItem.trim());
			}
		}
		if (index == -1) {
			dto.setStatus(valueString);
			return dto;
		}
		if (index >= valueString.length()
				- OBJECT_ITEM_STRING_SEPERATOR.length()) {
			return dto;
		}
//		 排列顺序	
		valueString = valueString.substring(index
				+ OBJECT_ITEM_STRING_SEPERATOR.length());
		index = valueString.indexOf(OBJECT_ITEM_STRING_SEPERATOR);
		if (index > 0) {
			oneItem = valueString.substring(0, index);
			if (oneItem.trim().length() > 0) {
				dto.setPriority(WebUtil.StringToInt(oneItem.trim()));
			}
		}
		if (index == -1) {
			dto.setPriority(WebUtil.StringToInt(valueString));
			return dto;
		}
		if (index >= valueString.length()
				- OBJECT_ITEM_STRING_SEPERATOR.length()) {
			return dto;
		}

//		 默认值
		valueString = valueString.substring(index
				+ OBJECT_ITEM_STRING_SEPERATOR.length());
		index = valueString.indexOf(OBJECT_ITEM_STRING_SEPERATOR);
		if (index > 0) {
			oneItem = valueString.substring(0, index);
			if (oneItem.trim().length() > 0) {
				dto.setDefaultOrNot(oneItem.trim());
			}
		}
		if (index == -1) {
			dto.setDefaultOrNot(valueString);
			return dto;
		}
		if (index >= valueString.length()
				- OBJECT_ITEM_STRING_SEPERATOR.length()) {
			return dto;
		}

		return dto;
	}

	// public static void main(String[] args) {
	// ModifyBidimConfigWebAction action = new ModifyBidimConfigWebAction();
	// List list1 = action.parsingValues("@*@");
	// System.out.println(list1);
	// list1 = action.parsingValues("@*@3345");
	// System.out.println(list1);
	// list1 = action.parsingValues("@*@32342@@234");
	// System.out.println(list1);
	// list1 = action.parsingValues("@*@23342@@asdf@@asdf@@@*@");
	// System.out.println(list1);
	// list1 = action.parsingValues("@*@23342@@asdf@@asdf@@ssdfas@*@");
	// System.out.println(list1);
	// list1 = action.parsingValues("@*@23342@@asdf@@asdf@@asdff@*@1223@@");
	// System.out.println(list1);
	// list1 = action.parsingValues("23342@@asdf@@asdf@@sdfs");
	// System.out.println(list1);
	// list1 = action.parsingValues("23342@@asdf@@asdf@@sadf@*@");
	// System.out.println(list1);
	// list1 = action
	// .parsingValues("23342@@asdf@@asdf@@sadf@*@234@@asdkf@@sdfsd@@");
	// System.out.println(list1);
	// list1 = action
	// .parsingValues("23342@@asdf@@asdf@@sadf@*@234@@asdkf@@sdfsd@@xxxx@@yyyy");
	// System.out.println(list1);
	// list1 = action.parsingValues("@@@@@@@*@@@@@@@@@");
	// System.out.println(list1);
	// }
}
