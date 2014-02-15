package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * author     ：zhouxushun 
 * date       : 2005-10-11
 * description:
 * @author 250713z
 *
 */
public class QueryCampaignWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		CampaignDTO dto=new CampaignDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtCampaignID"))){
			dto.setCampaignID(WebUtil.StringToInt(request.getParameter("txtCampaignID")));
		}	
		if (WebUtil.StringHaveContent(request.getParameter("txtCampaignType"))){
			dto.setCampaignType(request.getParameter("txtCampaignType"));
		}
		//这个条件专用于客户树上手工授予促销时过滤掉开户/团购促销,及没有定义NEWProduct的促销.DTO上没有可用字段,先占用着NAME
		if (WebUtil.StringHaveContent(request.getParameter("apply"))){
			dto.setCampaignName(request.getParameter("apply"));
		}
		//这个条件用于过滤客户类型对应的产品
		if (WebUtil.StringHaveContent(request.getParameter("custtype"))){
			dto.setCustTypeList(request.getParameter("custtype"));
		}
		//这个条件用于过滤客户来源渠道对应产品
		if (WebUtil.StringHaveContent(request.getParameter("opensourcetype"))){
			dto.setOpenSourceTypeList(request.getParameter("opensourcetype"));
		}
		//开户类型
		if (WebUtil.StringHaveContent(request.getParameter("txtObligationFlag"))){
			dto.setObligationFlag(request.getParameter("txtObligationFlag"));
		}

		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_CAMPAIGN);
	}
}
