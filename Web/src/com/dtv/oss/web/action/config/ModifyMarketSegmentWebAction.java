package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.MarketSegmentDTO;
import com.dtv.oss.dto.MarketSegmentToDistrictDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigActivityEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2006-4-14
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyMarketSegmentWebAction extends GeneralWebAction {

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

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	MarketSegmentDTO dto = new MarketSegmentDTO();
        
        ConfigActivityEJBEvent event= new ConfigActivityEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_MARKET_SEGMENT_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.CONFIG_MARKET_SEGMENT_EDIT);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.CONFIG_MARKET_SEGMENT_DELETE);
	    }
	    if (WebUtil.StringHaveContent(request.getParameter("txtID")))
	      dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtVarID")))
	    	
		      dto.setId(WebUtil.StringToInt(request.getParameter("txtVarID"))); 
	      
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	   dto.setStatus(request.getParameter("txtStatus"));
    	 
    	if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            dto.setName(request.getParameter("txtName"));
    	if (WebUtil.StringHaveContent(request.getParameter("txtDescription")))
            dto.setDescription(request.getParameter("txtDescription"));
    	if (WebUtil.StringHaveContent(request
				.getParameter("txtDistrictList"))) {
			Collection segCol = new ArrayList();
			String[] marketSegList = request.getParameter(
					"txtDistrictList").split(",");
			for (int i = 0; i < marketSegList.length; i++) {
				if (marketSegList[i] == null)
					continue;
				MarketSegmentToDistrictDTO camToSegDto = new MarketSegmentToDistrictDTO();

				camToSegDto.setMarketSegmentId(WebUtil.StringToInt(request
						.getParameter("txtID")));
				camToSegDto.setDistrictId(WebUtil
						.StringToInt(marketSegList[i]));

				segCol.add(camToSegDto);
			}
			 
			event.setMarketSegmentToDistrictDtoCol(segCol);
		}
    	 
        event.setMarketSegmentDTO(dto);
        
        return event;
    }
}
