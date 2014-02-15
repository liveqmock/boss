package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.OpGroupToPrivilegeDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigSystemEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2004 </p>
 * <p> Company: Digivision</p>
 * User: thurm zhang
 * Date: 2004-12-21
 * Time: 13:42:44
 * To change this template use File | Settings | File Templates.
 */
public class ModifyOpGroup2PrivilegeWebAction extends GeneralWebAction {
	boolean doPost = false;
	 
	protected boolean needCheckToken()
	{
		return doPost;
	}
	 public void doStart(HttpServletRequest request)
		{
			super.doStart(request);
			if (WebUtil.StringHaveContent(request.getParameter("confirm")))
			{
				if (request.getParameter("confirm").equalsIgnoreCase("true")) 
				{
					doPost = true;
					 
				 
				}
			}
			 
		 }
     protected EJBEvent encapsulateData (HttpServletRequest request) throws WebActionException {
     	
     	  ConfigSystemEJBEvent ejbEvent = new ConfigSystemEJBEvent();
     	  
     	 if("DeleteOperatorGroupPrivilege".equals(request.getParameter("Action")))
     	 	ejbEvent.setActionType(EJBEvent.DELETE_GROUP_TO_PRIVILEGE);
     	 if("AddOperatorGroupPrivilege".equals(request.getParameter("Action")))
     	 	ejbEvent.setActionType(EJBEvent.ADD_GROUP_TO_PRIVILEGE);
        String opGroupID = request.getParameter("opGroupID");
        String privID = request.getParameter("privID");
        List opg2plist = new ArrayList();
        if (opGroupID != null && privID != null) {
            String[] privID_array = privID.split(";");
            if (privID_array.length ==0) {
                throw new WebActionException ("当前页面没有数据！");
            }
            for (int i = 0; i < privID_array.length; i++) {
                OpGroupToPrivilegeDTO dto = new OpGroupToPrivilegeDTO();
                dto.setPrivId(WebUtil.StringToInt(privID_array[i]));
                dto.setOpGroupId(WebUtil.StringToInt(opGroupID));
                opg2plist.add(dto);
            }
            privID_array = null;
        }
        privID = null;
        opGroupID = null;
      
       
        ejbEvent.setCol(opg2plist);
        return ejbEvent;
    }
}
