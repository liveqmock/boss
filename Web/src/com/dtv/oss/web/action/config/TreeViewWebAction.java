package com.dtv.oss.web.action.config;

import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.tree.DynamicRootNode;
import com.dtv.oss.tree.GenerateTree;
import com.dtv.oss.service.ejbevent.EJBEvent;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title: boss_p5
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Digivision
 * </p>
 * User: Stone Liang Date: 2006-4-28 Time: 16:33:19 To change this template use
 * File | Settings | File Templates.
 */
public class TreeViewWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		DynamicRootNode dynamicRootNode = (DynamicRootNode) request.getSession().getAttribute("dynamicRootNode");
		if(dynamicRootNode!=null){
			dynamicRootNode=GenerateTree.changeDynamicTreeNode((String) request.getParameter("key"),dynamicRootNode);
		}
		request.getSession().setAttribute("dynamicRootNode",
				dynamicRootNode);
		return null;
	}
}
