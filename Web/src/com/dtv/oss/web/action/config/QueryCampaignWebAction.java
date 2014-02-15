/*
 * 创建日期 2006-1-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
 


/**
 * @author chen jiang
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class QueryCampaignWebAction  extends QueryWebAction {

  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO 自动生成方法存根
    
  	CampaignDTO	dto = new CampaignDTO();
	if(WebUtil.StringHaveContent(request.getParameter("txtCampaignID"))) 
  	     dto.setCampaignID(WebUtil.StringToInt(request.getParameter("txtCampaignID")));	
  	if(WebUtil.StringHaveContent(request.getParameter("txtCampaignType"))) 
  		 dto.setCampaignType(request.getParameter("txtCampaignType"));	
	 
  	if(WebUtil.StringHaveContent(request.getParameter("txtCampaignName"))) 
 		 dto.setCampaignName(request.getParameter("txtCampaignName"));	
  	if (WebUtil.StringHaveContent(request.getParameter("txtDateFrom")))
		dto.setDateFrom(WebUtil.StringToTimestampWithDayBegin(request
				.getParameter("txtDateFrom")));

  	if(WebUtil.StringHaveContent(request.getParameter("txtDateTo"))) 
		 dto.setDateTo(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtDateTo")));	 
	if(WebUtil.StringHaveContent(request.getParameter("txtStatus"))) 
      dto.setStatus(request.getParameter("txtStatus"));
    return new ConfigQueryEJBEvent(dto, pageFrom, pageTo, ConfigQueryEJBEvent.CAMPAIGN_QUERY);
    
     
  }

}
