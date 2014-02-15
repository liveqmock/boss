package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.OrgGovernedDistrictDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigSystemEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;

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
public class ModifyOrg2DistrictWebAction extends GeneralWebAction {
	 
     protected EJBEvent encapsulateData (HttpServletRequest request) throws WebActionException {
     	
     	  ConfigSystemEJBEvent ejbEvent = new ConfigSystemEJBEvent();
     	  
     	 if("DeleteOrgDistrict".equals(request.getParameter("Action")))
     	 	ejbEvent.setActionType(EJBEvent.DELETE_ORG_TO_DISTRICT);
     	 if("AddOrgDistrict".equals(request.getParameter("Action")))
     	 	ejbEvent.setActionType(EJBEvent.ADD_ORG_TO_DISTRICT);
     	 String districtID = request.getParameter("districtID");
         String orgID = request.getParameter("orgID");
        
       
       
        List op2glist = new ArrayList();
        if (districtID != null && orgID != null) {
            String[] districtID_array = districtID.split(";");
            if (districtID_array.length == 0) {
                throw new WebActionException ("当前页面没有数据！");
            }
            for (int i = 0; i < districtID_array.length; i++) {
            	OrgGovernedDistrictDTO dto = new OrgGovernedDistrictDTO();
                dto.setDistrictId(Integer.parseInt(districtID_array[i]));
                dto.setOrgId(Integer.parseInt(orgID));
                op2glist.add(dto);
            }
            districtID_array = null;
        }
        districtID = null;
        orgID = null;
        
       
        ejbEvent.setCol(op2glist);
        return ejbEvent;
    }
}
