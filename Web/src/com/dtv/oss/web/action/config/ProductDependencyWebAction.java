package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ProductDependencyWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)throws Exception {
		
		ProductDependencyDTO dto=new ProductDependencyDTO();
		
		ConfigProductEJBEvent event=null;
		if("CREATE".equalsIgnoreCase(request.getParameter("txtActionType")))
			event=new ConfigProductEJBEvent(EJBEvent.PRODUCT_DEPENDENCY_CREATE);
		else if("MODIFY".equalsIgnoreCase(request.getParameter("txtActionType")))
			event=new ConfigProductEJBEvent(EJBEvent.PRODUCT_DEPENDENCY_MODIFY);
		else if("DELETE".equalsIgnoreCase(request.getParameter("txtActionType")))
			event=new ConfigProductEJBEvent(EJBEvent.PRODUCT_DEPENDENCY_DELETE);
		else{
			//throw new WebActionException("产品依赖管理操作类型未知！");
			LogUtility.log(getClass(),LogLevel.WARN,"ProductDependencyWebAction，没有发现动作类型!");
			return null;
		}
		
		if(WebUtil.StringHaveContent(request.getParameter("txtDtCreate")))
			dto.setDtCreate(WebUtil.StringToTimestamp(request.getParameter("txtDtCreate")));
		if(WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
			dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
		dto.setReferProductNum(WebUtil.StringToInt(request.getParameter("txtReferProductNum")));
		dto.setProductId(WebUtil.StringToInt(request.getParameter("txtProductID")));
	    dto.setReferProductIDList(request.getParameter("txtReferProductList")) ;
		dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
		dto.setType(request.getParameter("txtType"));	
		dto.setReferAllFlag(request.getParameter("txtReferAllFlag"));	
		
		if("DELETE".equalsIgnoreCase(request.getParameter("txtActionType"))){
			if(!WebUtil.StringHaveContent(request.getParameter("txtSeqNoList")))
				throw new WebActionException("参数错误，没有选择要删除的属性");
			
			event.setProductDependencySeqNos(request.getParameter("txtSeqNoList"));
		}
		
		event.setProductDependencyDTO(dto);
		
		return event;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
