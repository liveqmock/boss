package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dtv.oss.web.action.MultipleWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.tree.DynamicRootNode;
import com.dtv.oss.tree.DynamicTreeNode;
import com.dtv.oss.tree.GenerateTree;
import com.dtv.oss.util.Postern;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.util.TreePostern;
import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ListEJBEvent;
import javax.servlet.http.HttpServletRequest;

/**
 * <p> Title: boss_p5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: Stone Liang
 * Date: 2006-5-9
 * To change this template use File | Settings | File Templates.
 */
public class DistrictSettingWebAction extends MultipleWebAction {
	protected int getSepecialAction(String pAction) {
		if (pAction == null)
			return -1;

		if (pAction.equals("update")) {
			return ListEJBEvent.DISTRICT_SETTING_UPDATE;
		} else if (pAction.equals("add")) {
			return ListEJBEvent.DISTRICT_SETTING_ADD;
		} else if (pAction.equals("delete")) {
			return ListEJBEvent.DISTRICT_SETTING_DELETE;
		}
		return -1;
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		List currentDataList = null;

		//删除的ID列表
		String[] id = null;

		currentDataList = new ArrayList();
		DistrictSettingDTO dto = null;

		switch (actionType) {
		case ListEJBEvent.DISTRICT_SETTING_UPDATE:
			dto = fillDto(request);
//			dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());
			break;

		case ListEJBEvent.DISTRICT_SETTING_ADD:
			dto = fillDto(request);
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			break;

		case ListEJBEvent.DISTRICT_SETTING_DELETE:
			currentDataList = new ArrayList();
			id = request.getParameterValues("txtIndex");
			for (int i = 0; i < id.length; i++) {
				currentDataList.add(WebUtil.StringToInteger(id[i]));

			}

			break;
		}

		return new ListEJBEvent(actionType, currentDataList,dto);
	}

	private DistrictSettingDTO fillDto(HttpServletRequest request) {
		DistrictSettingDTO dto = new DistrictSettingDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtOPID")))
			dto.setId(WebUtil.StringToInt(request.getParameter("txtOPID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictCode")))
			dto.setDistrictCode(request.getParameter("txtDistrictCode"));
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictName")))
			dto.setName(request.getParameter("txtDistrictName"));
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictType")))
			dto.setType(request.getParameter("txtDistrictType"));
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		if (WebUtil.StringHaveContent(request.getParameter("txtQryBelongTo")))
			dto.setBelongTo(WebUtil.StringToInt(request
					.getParameter("txtQryBelongTo")));
		if (WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
			dto.setDtLastmod(WebUtil.StringToTimestamp(request
					.getParameter("txtDtLastmod")));

		return dto;
	}

	protected void afterSuccessfulResponse(HttpServletRequest request,
			CommandResponse cmdResponse) {
		super.afterSuccessfulResponse(request, cmdResponse);

		Collection lstRes = (Collection) cmdResponse.getPayload();
		if (lstRes == null)
			return;

		DynamicRootNode dynamicRootNode = null;
		dynamicRootNode = (DynamicRootNode) request.getSession()
				.getAttribute("dynamicRootNode");

		//=========================================
		//选择列表的数据需要更新,
		Postern.initHaveCustomerDistrictSetting();
		//节点发生改变时就重新一下session存储的树结构,
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"当前根节点的点击深度:"+dynamicRootNode.getCurrentClickDeep());
		DynamicTreeNode districtNode = TreePostern.initRootNode(0,1,"dist");
		dynamicRootNode.setRootNode(districtNode.getCurrentNode().getKey(),districtNode);

		Iterator itRes = lstRes.iterator();

		
//		删除后返回的是belongID
		String key=null;
		while (itRes.hasNext()) {
			Integer curId = Integer.valueOf(itRes.next().toString());
			key="district_" + curId;
			LogUtility.log(this.getClass(),LogLevel.DEBUG,"节点修改后返回Key:"+key);
//			GenerateTree.refreshTreeNode("district_" + curId,
//					dynamicRootNode);
		}
		GenerateTree.openTree(key,dynamicRootNode);

//		//change the tree on the left
//		switch (actionType) {
//		case ListEJBEvent.DISTRICT_SETTING_UPDATE:
//			while (itRes.hasNext()) {
//				Integer curId = Integer.valueOf(itRes.next().toString());
//				String key="district_" + curId;
//				
//				GenerateTree.changeDynamicTreeNode(key,dynamicRootNode);
//			}
//			break;
//
//		case ListEJBEvent.DISTRICT_SETTING_ADD:
//			while (itRes.hasNext()) {
//				Integer curId = Integer.valueOf(itRes.next().toString());
//				String key="district_" + curId;
//				GenerateTree.addTreeNode(key,dynamicRootNode);
//			}
//			break;
//
//		case ListEJBEvent.DISTRICT_SETTING_DELETE:
//			while (itRes.hasNext()) {
//				Integer curId = Integer.valueOf(itRes.next().toString());
//				GenerateTree.deleteTreeNode("district_" + curId,
//						dynamicRootNode);
//			}
//			break;
//		}

		
	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.web.action.MultipleWebAction#isAllowedAction()
	 */
	protected boolean isAllowedAction() {
		// TODO Auto-generated method stub
		switch (actionType) {
		case ListEJBEvent.DISTRICT_SETTING_UPDATE:
		case ListEJBEvent.DISTRICT_SETTING_ADD:
		case ListEJBEvent.DISTRICT_SETTING_DELETE:
			return true;
		}
		return false;
	}
}
