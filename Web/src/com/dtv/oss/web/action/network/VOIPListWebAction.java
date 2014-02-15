package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VOIPConditionDTO;
import com.dtv.oss.dto.VOIPGatewayDTO;
import com.dtv.oss.dto.VOIPProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVOIPInfoEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;
public class VOIPListWebAction extends QueryWebAction{
	String txtQueryType;

	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
		txtQueryType=request.getParameter("txtQueryType");
		if(txtQueryType == null){
			return null;
		}
		if(txtQueryType.equals(CommonKeys.VOIP_QUERY_PRODUCT)){
			VOIPProductDTO dto=new VOIPProductDTO();
			dto.setProductID(WebUtil.StringToInt(request.getParameter("txtSmsProductId")));
			dto.setSssrvCode(request.getParameter("txtserviceCode"));
			return new QueryVOIPInfoEJBEvent(dto,pageFrom,pageTo,QueryVOIPInfoEJBEvent.QUERY_VOIP_PRODUCT_LIST);
		}else if(txtQueryType.equals(CommonKeys.VOIP_QUERY_CONDITION)){
			VOIPConditionDTO dto=new VOIPConditionDTO();
			return new QueryVOIPInfoEJBEvent(dto,pageFrom,pageTo,QueryVOIPInfoEJBEvent.QUERY_VOIP_CONDITION_LIST);
		}else if(txtQueryType.equals(CommonKeys.VOIP_QUERY_GATEWAY)){
			VOIPGatewayDTO dto=new VOIPGatewayDTO();
			return new QueryVOIPInfoEJBEvent(dto,pageFrom,pageTo,QueryVOIPInfoEJBEvent.QUERY_VOIP_GATEWAY_LIST);
		}
		return null;
	}

}
