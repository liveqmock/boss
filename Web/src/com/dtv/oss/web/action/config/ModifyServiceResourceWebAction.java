package com.dtv.oss.web.action.config;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ServiceResourceEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ModifyServiceResourceWebAction extends GeneralWebAction {

	private static final String MODIFY_TYPE = "modify_type";

	private static final String SERVICE_RESOURCE_NEW = "resource_new";

	private static final String SERVICE_RESOURCE_UPDATE = "resource_update";

	private static final String SERVICE_RESOURCE_DELETE = "resource_delete";

	private static final String PHONE_NO_NEW = "phone_no_new";

	private static final String PHONE_NO_UPDATE = "phone_no_update";

	private static final String PHONE_NO_DELETE = "phone_no_delete";

	private static final String PHONE_NO_DELETE_MULTIPLY = "phone_no_delete_multiply";

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		String modifyType = request.getParameter(MODIFY_TYPE);
		if (modifyType == null) {
			return null;
		}
		int actionType = 0;
		Object dto = null;
		String backUrl = "config_service_resource_query.do";
		if (SERVICE_RESOURCE_NEW.equals(modifyType)) {
			actionType = ServiceResourceEJBEvent.RESOURCE_NEW;
			dto = getServiceResourceDTOForCreating(request);
			backUrl = "config_service_resource_query.do";
		} else if (SERVICE_RESOURCE_UPDATE.equals(modifyType)) {
			actionType = ServiceResourceEJBEvent.RESOURCE_UPDATE;
			dto = getServiceResourceDTOForUpdating(request);
			backUrl = "config_service_resource_query.do";
		} else if (SERVICE_RESOURCE_DELETE.equals(modifyType)) {
			actionType = ServiceResourceEJBEvent.RESOURCE_DELETE;
			dto = getServiceResourceDTOForDeleting(request);
			backUrl = "config_service_resource_query.do";
		} else if (PHONE_NO_NEW.equals(modifyType)) {
			String resourceName = request.getParameter("txtResourceName");
			actionType = ServiceResourceEJBEvent.RESOURCE_DETAIL_NEW;
			dto = getResourcePhoneNoDTOForCreating(request);
			backUrl = "config_service_resource_detail_entrance.do?txtResourceName="
					+ resourceName;
		} else if (PHONE_NO_UPDATE.equals(modifyType)) {
			String resourceName = request.getParameter("txtResourceName");
			actionType = ServiceResourceEJBEvent.RESOURCE_DETAIL_UPDATE;
			backUrl = "config_service_resource_detail_entrance.do?txtResourceName="
					+ resourceName;
			return null;
		} else if (PHONE_NO_DELETE.equals(modifyType)) {
			String resourceName = request.getParameter("txtResourceName");
			actionType = ServiceResourceEJBEvent.RESOURCE_DETAIL_DELETE;
			backUrl = "config_service_resource_detail_entrance.do?txtResourceName="
					+ resourceName;
			return null;
		} else if (PHONE_NO_DELETE_MULTIPLY.equals(modifyType)) {
			String resourceName = request.getParameter("txtResourceName");
			backUrl = "config_service_resource_detail_query.do?txtResourceName="
					+ resourceName;
			dto = getResourcePhoneNoDTOForMultiplyDeleting(request);
			actionType = ServiceResourceEJBEvent.RESOURCE_DETAIL_DELETE_MULTIPLY;
		}

		request.setAttribute("back_url", backUrl);

		ServiceResourceEJBEvent event = new ServiceResourceEJBEvent(dto,
				actionType);
		return event;
	}

	private ServiceResourceDTO getServiceResourceDTOForCreating(
			HttpServletRequest request) {
		ServiceResourceDTO dto = new ServiceResourceDTO();
		String resourceName = request.getParameter("txtResourceName");
		String resourceType = request.getParameter("txtResourceType");
		String desc = request.getParameter("txtResourceDesc");
		String status = request.getParameter("txtStatus");
		dto.setResourceName(resourceName);
		dto.setResourceType(resourceType);
		dto.setResourceDesc(desc);
		dto.setStatus(status);
		dto.setDtCreate(new Timestamp(System.currentTimeMillis()));
		return dto;
	}

	private ServiceResourceDTO getServiceResourceDTOForUpdating(
			HttpServletRequest request) {
		ServiceResourceDTO dto = new ServiceResourceDTO();
		String resourceName = request.getParameter("txtResourceName");
		String resourceType = request.getParameter("txtResourceType");
		String desc = request.getParameter("txtResourceDesc");
		String status = request.getParameter("txtStatus");
		String dtLastMod = request.getParameter("txtDtLastMod");
		dto.setResourceName(resourceName);
		dto.setResourceType(resourceType);
		dto.setResourceDesc(desc);
		dto.setStatus(status);
		// SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
		// format.for
		dto.setDtLastmod(new Timestamp(Long.valueOf(dtLastMod).longValue()));
		return dto;
	}

	private ServiceResourceDTO getServiceResourceDTOForDeleting(
			HttpServletRequest request) {
		ServiceResourceDTO dto = new ServiceResourceDTO();
		String resourceName = request.getParameter("txtResourceName");
		dto.setResourceName(resourceName);
		return dto;
	}

	private List getResourcePhoneNoDTOForCreating(HttpServletRequest request) {
		List list = new ArrayList();
		String resourceName = request.getParameter("txtResourceName");
		String countryCode = request.getParameter("txtCountryCode");
		String areaCode = request.getParameter("txtAreaCode");
		String desc = request.getParameter("txtComments");
		String batchAdd = request.getParameter("txtBatchAdded");
		String status = request.getParameter("txtStatus");
		String grade = request.getParameter("txtGrade");

		int districtID=0;
		if(WebUtil.StringHaveContent(request.getParameter("txtCounty"))){
			districtID = WebUtil.StringToInt(request.getParameter("txtCounty"));
		}
		if (status == null || (status = status.trim()).length() == 0) {
			status = "N";
		}
		if (batchAdd == null || (batchAdd = batchAdd.trim()).length() == 0) {
			batchAdd = "false";
		}
		String phoneNo = request.getParameter("txtPhoneNo");
		String number = request.getParameter("txtNumber");
		if ("true".equalsIgnoreCase(batchAdd)) {
			int n = Integer.valueOf(number).intValue();
			if (n > 10000) {
				n = 10000;
			}
			ResourcePhoneNoDTO dto = null;
			for (int i = 0; i < n; i++) {
				dto = new ResourcePhoneNoDTO();
				dto.setResourceName(resourceName);
				dto.setComments(desc);
				dto.setCountryCode(countryCode);
				dto.setAreaCode(areaCode);
				dto.setDistrictId(districtID);
				dto.setGrade(grade);
				dto.setPhoneNo(phoneNo);
				dto.setStatus(status);
				dto.setDtCreate(new Timestamp(System.currentTimeMillis()));
				list.add(dto);
				phoneNo = getNextPhoneNo(phoneNo);
			}

		} else {
			ResourcePhoneNoDTO dto = new ResourcePhoneNoDTO();
			dto.setResourceName(resourceName);
			dto.setComments(desc);
			dto.setCountryCode(countryCode);
			dto.setDistrictId(districtID);
			dto.setGrade(grade);
			dto.setAreaCode(areaCode);
			dto.setPhoneNo(phoneNo);
			dto.setStatus(status);
			dto.setDtCreate(new Timestamp(System.currentTimeMillis()));
			list.add(dto);
		}

		System.out.println(getClass().getName()
				+ ".getResourcePhoneNoDTOForCreating,list:" + list);
		return list;
	}

	private String getNextPhoneNo(String phoneNo) {
		char[] phoneChars = phoneNo.toCharArray();
		char aChar = 0;
		int index = phoneChars.length - 1;
		int increament = 1;
		while (index >= 0 && increament == 1) {
			aChar = phoneChars[index];
			if (aChar >= '0' && aChar < '9') {
				aChar += 1;
				increament = 0;
				phoneChars[index] = aChar;
				break;
			}
			if (aChar == '9') {
				aChar = '0';
			}
			phoneChars[index] = aChar;
			index--;
		}

		return new String(phoneChars);
	}

	// public static void main(String[] args){
	// String xxx = "5555";
	// System.out.println(getNextPhoneNo(xxx));
	// System.out.println(getNextPhoneNo("02x99"));
	// }

	private List getResourcePhoneNoDTOForMultiplyDeleting(
			HttpServletRequest request) {
		List list = new ArrayList();
		String itemIDsString = request.getParameter("txtPhoneNoItemIDs");
		if (itemIDsString != null
				&& (itemIDsString = itemIDsString.trim()).length() > 0) {
			int index1 = 0;
			int index2 = -1;
			while (true) {
				index2 = itemIDsString.indexOf(',', index1);
				if (index2 != -1) {
					list.add(Integer.valueOf(itemIDsString.substring(index1,
							index2)));
					index1 = ++index2;
				} else {
					if (index1 < itemIDsString.length()) {
						list.add(Integer.valueOf(itemIDsString
								.substring(index1)));
					}
					break;
				}
			}
		}
		return list;
	}
}
