package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ServiceAccountDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * 说明：查找终端设备
 * @author 250713z
 *
 */
public class QueryFiberNodeWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CommonQueryConditionDTO dto=new CommonQueryConditionDTO();
		
		//给查询条件赋值，从上至下依次为：编码、光收机ID、地址

		if(WebUtil.StringHaveContent(request.getParameter("txtFiberNodeCode"))){
			dto.setSpareStr1(request.getParameter("txtFiberNodeCode"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtFiberReceiverIdBegin"))){
			dto.setBeginStr(request.getParameter("txtFiberReceiverIdBegin"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtFiberReceiverIdEnd"))){
			dto.setEndStr(request.getParameter("txtFiberReceiverIdEnd"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtDetailAddress"))){
			dto.setSpareStr2(request.getParameter("txtDetailAddress"));
		}
		
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_FiberNode);
	}

}
