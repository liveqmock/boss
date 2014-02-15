/*
 * �������� 2006-1-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class QueryCampaignWebAction  extends QueryWebAction {

  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO �Զ����ɷ������
    
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
