package com.dtv.oss.web.action.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.tree.DynamicRootNode;
import com.dtv.oss.tree.DynamicTreeNode;
import com.dtv.oss.util.TreePostern;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;

/**
 * <p> Title: boss_p5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: Stone Liang
 * Date: 2006-5-9
 * To change this template use File | Settings | File Templates.
 */
public class TreeInitWebAction extends GeneralWebAction {

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {

    	return null;
    }
    public void doEnd(HttpServletRequest request, CommandResponse cmdResponse)
    {
    	String from = request.getParameter("from");

		DynamicRootNode dynamicRootNode =new DynamicRootNode();
		
		dynamicRootNode.setCurrentClickDeep(1);
		dynamicRootNode.setNodeMap(generateMyCurrentTreeDate(from));
		
		request.getSession().setAttribute("dynamicRootNode" , dynamicRootNode);
    }
    private Map generateMyCurrentTreeDate(String from) {
		Map RootMap = new LinkedHashMap();

		int key = 0;
		int level = 1;

		DynamicTreeNode districtNode = TreePostern.initRootNode(key, level,
				from);

		RootMap.put(districtNode.getCurrentNode().getKey(), districtNode);
		return RootMap;
	}
}
