package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ProductManageWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		ProductDTO dto=new ProductDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtDateFrom")))
			dto.setDateFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDateTo")))
			dto.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDtCreate")))
			dto.setDtCreate(WebUtil.StringToTimestamp(request.getParameter("txtDtCreate")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
			dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
		
		dto.setNewsaFlag(request.getParameter("txtNewSAFlag"));
		dto.setProductClass(request.getParameter("txtProductClass"));
		dto.setProductID(WebUtil.StringToInt(request.getParameter("txtProductID")));
		dto.setProductName(request.getParameter("txtProductName"));
		dto.setStatus(request.getParameter("txtStatus"));
		dto.setDescription(request.getParameter("txtDescription"));
		
		ConfigProductEJBEvent event=null;
		if("CREATE".equalsIgnoreCase(request.getParameter("txtActionType")))
			event=new ConfigProductEJBEvent(EJBEvent.PRODUCT_CREATE);
		else if("MODIFY".equalsIgnoreCase(request.getParameter("txtActionType")))
			event=new ConfigProductEJBEvent(EJBEvent.PRODUCT_MODIFY);
		else if("DELETE".equalsIgnoreCase(request.getParameter("txtActionType")))
			event=new ConfigProductEJBEvent(EJBEvent.PRODUCT_DELETE);
		else{
			//throw new WebActionException("产品管理操作类型未知！");
			LogUtility.log(getClass(),LogLevel.WARN,"ProductManageWebAction，没有发现动作类型!");
			return null;
		}

		event.setProductDTO(dto);
		
		return event;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
