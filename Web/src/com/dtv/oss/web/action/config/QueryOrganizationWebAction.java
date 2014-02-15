/*
 * Created on 2004-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.util.TreePostern;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
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
public class QueryOrganizationWebAction extends QueryWebAction {
		
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	OrganizationDTO theDTO = new OrganizationDTO();
    	//修改查询时的ID,特有的
    	if (WebUtil.StringHaveContent(request.getParameter("txtDetailID"))){
    		theDTO.setOrgID(WebUtil.StringToInt(request.getParameter("txtDetailID")));
    		return new ConfigQueryEJBEvent(theDTO, pageFrom, pageTo, ConfigQueryEJBEvent.QUERY_ORGANIZATION);
    	}
    	
    	//特殊的字段，0是有含义的
    	String belongTo=request.getParameter("txtQryBelongTo");
    	try{
    		theDTO.setParentOrgID(Integer.parseInt(belongTo));
    	}catch(NumberFormatException e){
    		//如果传来的不是可以转换为数字的,应该是分类节点上的点击,分解取出父ID和本类的TYPE
    		String[] res=belongTo.split("_");
    		if(res.length==3){
    			theDTO.setParentOrgID(WebUtil.StringToInt(res[1]));
    			theDTO.setOrgType(res[2]);
    		}
    	}

    	LogUtility.log(this.getClass(), LogLevel.DEBUG, "封装好的DTO:"+ theDTO);
        return new ConfigQueryEJBEvent(theDTO, pageFrom, pageTo, ConfigQueryEJBEvent.QUERY_ORGANIZATION);

    }

}
