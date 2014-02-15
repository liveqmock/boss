package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ListEJBEvent;
import com.dtv.oss.service.ejbevent.config.OrganizationEJBEvent;
import com.dtv.oss.tree.DynamicRootNode;
import com.dtv.oss.tree.DynamicTreeNode;
import com.dtv.oss.tree.GenerateTree;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.util.TreePostern;
import com.dtv.oss.web.action.MultipleWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * 
 * @author 260327h
 *
 */
public class OrganizationWebAction extends MultipleWebAction {
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
		OrganizationDTO dto = null;

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

		return new OrganizationEJBEvent(actionType, currentDataList,dto);
	}

	private OrganizationDTO fillDto(HttpServletRequest request) {
		OrganizationDTO dto = new OrganizationDTO();
		 
			dto.setOrgID(WebUtil.StringToInt(request.getParameter("txtOrgID")));
		 
			dto.setOrgName(request.getParameter("txtOrgName"));
	 
			dto.setOrgType(request.getParameter("txtOrgType"));
		 
			dto.setOrgSubType(request.getParameter("txtOrgSubType"));
	 
			dto.setOrgCode(request.getParameter("txtOrgCode"));
		 
			dto.setParentOrgID(WebUtil.StringToInt(request
					.getParameter("txtParentOrgID")));
		 
			dto.setOrgDesc(request.getParameter("txtOrgDesc"));
		 
			dto.setRank(request.getParameter("txtRank"));
		 
			dto.setRegisterNo(request.getParameter("txtRegisterNo"));
	 
			dto.setHasCustomerFlag(request.getParameter("txtHasCustomerFlag"));
	 
			dto.setStatus(request.getParameter("txtStatus"));
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
//		Postern.initHaveCustomerDistrictSetting();
		//节点发生改变时就重新一下session存储的树结构,
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"当前根节点的点击深度:"+dynamicRootNode.getCurrentClickDeep());
		DynamicTreeNode districtNode = TreePostern.initRootNode(0,1,"org");
		dynamicRootNode.setRootNode(districtNode.getCurrentNode().getKey(),districtNode);

		Iterator itRes = lstRes.iterator();

		
//		删除后返回的是belongID
		String key=null;
		while (itRes.hasNext()) {
			Integer curId = Integer.valueOf(itRes.next().toString());
			key="org_" + curId;
			LogUtility.log(this.getClass(),LogLevel.DEBUG,"节点修改后返回Key:"+key);
		}
		GenerateTree.openTree(key,dynamicRootNode);

		
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
