package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ProductPropertyWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		ConfigProductEJBEvent event=null;
		if("CREATE".equalsIgnoreCase(request.getParameter("txtActionType")))
			event=new ConfigProductEJBEvent(EJBEvent.PRODUCT_PROPERTY_CREATE);
		else if("MODIFY".equalsIgnoreCase(request.getParameter("txtActionType")))
			event=new ConfigProductEJBEvent(EJBEvent.PRODUCT_PROPERTY_MODFIY);
		else if("DELETE".equalsIgnoreCase(request.getParameter("txtActionType")))
			event=new ConfigProductEJBEvent(EJBEvent.PRODUCT_PROPERTY_DELETE);
		else{
			//throw new WebActionException("产品属性管理操作类型未知！");
			LogUtility.log(getClass(),LogLevel.WARN,"ProductPropertyWebAction，没有发现动作类型!");
			return null;
		}
		
		ProductPropertyDTO dto=new ProductPropertyDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtDtCreate")))
			dto.setDtCreate(WebUtil.StringToTimestamp(request.getParameter("txtDtCreate")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
			dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
		
		dto.setDescription(request.getParameter("txtDescription"));
		dto.setProductId(WebUtil.StringToInt(request.getParameter("txtProductID")));
		dto.setPropertyMode(request.getParameter("txtPropertyMode"));
		dto.setPropertyName(request.getParameter("txtPropertyName"));
		dto.setPropertyCode(request.getParameter("txtPropertyCode"));
		dto.setPropertyValue(request.getParameter("txtPropertyValue"));
		dto.setResourceName(request.getParameter("txtResourceName"));
		dto.setPropertyType(request.getParameter("txtPropertyType"));
		
		//如果是删除属性，则用dto的PropertyName属性来保存要操作的列表
		if("DELETE".equalsIgnoreCase(request.getParameter("txtActionType"))){
			if(!WebUtil.StringHaveContent(request.getParameter("txtPropertyNameList")))
				throw new WebActionException("参数错误，没有选择要删除的属性");
			event.setProductPropertyNames(request.getParameter("txtPropertyNameList"));
		}
		
		event.setProductPropertyDTO(dto);
		
		return event;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
