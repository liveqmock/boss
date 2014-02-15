package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * 查找可购买的软件产品包，以及产品包中的具体产品
 * @author 250713z
 *
 */
public class QueryCanPurchaseProductPackageWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		ProductPackageDTO dto=new ProductPackageDTO();
		
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus"))){
			dto.setStatus(request.getParameter("txtStatus"));
		}
		
		if (WebUtil.StringHaveContent(request.getParameter("txtPackageID"))){
			dto.setPackageID(WebUtil.StringToInt(request.getParameter("txtPackageID")));
			return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_CURRENT_PACKAGE);
		}
		else{
			return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_CAN_PURCHASE_PRODUCTPACKAGE);
		}
	}
}
