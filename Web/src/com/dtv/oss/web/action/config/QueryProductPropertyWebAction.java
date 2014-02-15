package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;

import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * 
 * @author 260327h
 *
 */
public class QueryProductPropertyWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		ProductPropertyDTO dto =new ProductPropertyDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtProductID")))
			dto.setProductId(WebUtil.StringToInt(request.getParameter("txtProductID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtPropertyName")))
			dto.setPropertyName(request.getParameter("txtPropertyName"));
		
		//���Դ���
		if(WebUtil.StringHaveContent(request.getParameter("txtPropertyCode")))
			dto.setPropertyCode(request.getParameter("txtPropertyCode"));
		//ȡֵ��ʽ
		if(WebUtil.StringHaveContent(request.getParameter("txtPropertyMode")))
			dto.setPropertyMode(request.getParameter("txtPropertyMode"));
		//ȡֵ��Դ����
		if(WebUtil.StringHaveContent(request.getParameter("txtPropertyValue")))
			dto.setPropertyValue(request.getParameter("txtPropertyValue"));
		//ȡֵ��Դ����
		if(WebUtil.StringHaveContent(request.getParameter("txtResourceName")))
			dto.setResourceName(request.getParameter("txtResourceName"));
		//ȡֵ����
		if(WebUtil.StringHaveContent(request.getParameter("txtPropertyType")))
			dto.setPropertyType(request.getParameter("txtPropertyType"));

		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.CONFIG_PRODUCT_PROPERTY_QUERY);	
	}
    
}
