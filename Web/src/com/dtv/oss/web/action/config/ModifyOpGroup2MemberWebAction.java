package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.OpToGroupDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigSystemEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: Chen jiang
 * Date: 2006-05-31
 * Time: 13:42:44
 * To change this template use File | Settings | File Templates.
 */
public class ModifyOpGroup2MemberWebAction extends GeneralWebAction {
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
     	  
     	 if("DeleteGroupMember".equals(request.getParameter("Action")))
     	 	ejbEvent.setActionType(EJBEvent.DELETE_GROUP_TO_MEMBER);
     	 if("AddGroupMember".equals(request.getParameter("Action")))
     	 	ejbEvent.setActionType(EJBEvent.ADD_GROUP_TO_MEMBER);
     	 String operatorID = request.getParameter("operatorID");
         String opGroupID = request.getParameter("opGroupID");
        
       
       
        List op2glist = new ArrayList();
        if (opGroupID != null && operatorID != null) {
            String[] operatorID_array = operatorID.split(";");
            if (operatorID_array.length == 0) {
                throw new WebActionException ("当前页面没有数据！");
            }
            for (int i = 0; i < operatorID_array.length; i++) {
                OpToGroupDTO dto = new OpToGroupDTO();
                dto.setOperatorId(Integer.parseInt(operatorID_array[i]));
                dto.setOpGroupId(Integer.parseInt(opGroupID));
                op2glist.add(dto);
            }
            operatorID_array = null;
        }
        operatorID = null;
        opGroupID = null;
      
       
        ejbEvent.setCol(op2glist);
        return ejbEvent;
    }
}
