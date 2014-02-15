/*
 * Created on 2004-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.util.WebUtil;
/**
 * @author 220226
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryDistrictSettingWebAction extends QueryWebAction {
		
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	DistrictSettingDTO theDTO = new DistrictSettingDTO();
    	//修改查询时的ID,特有的
    	if (WebUtil.StringHaveContent(request.getParameter("txtDetailID"))){
    		theDTO.setId(WebUtil.StringToInt(request.getParameter("txtDetailID")));
    		return new ConfigQueryEJBEvent(theDTO, pageFrom, pageTo, ConfigQueryEJBEvent.QUERY_DISTRICT_SETTING);
    	}
    	if (WebUtil.StringHaveContent(request.getParameter("txtQryID")))
    		theDTO.setId(WebUtil.StringToInt(request.getParameter("txtQryID")));
    	
    	if (WebUtil.StringHaveContent(request.getParameter("txtQryName")))
    		theDTO.setName(request.getParameter("txtQryName"));
    	
    	//特殊的字段，0是有含义的
    	if (WebUtil.StringHaveContent(request.getParameter("txtQryBelongTo")))
    		theDTO.setBelongTo(WebUtil.StringToInt(request.getParameter("txtQryBelongTo")));
    	else
    		theDTO.setBelongTo(-1);
    	
    	if (WebUtil.StringHaveContent(request.getParameter("txtQryType")))
    		theDTO.setType(request.getParameter("txtQryType"));
    	
    	if (WebUtil.StringHaveContent(request.getParameter("txtQryDistrictCode")))
    		theDTO.setDistrictCode(request.getParameter("txtQryDistrictCode"));
    	
        return new ConfigQueryEJBEvent(theDTO, pageFrom, pageTo, ConfigQueryEJBEvent.QUERY_DISTRICT_SETTING);

    }

}
