package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryInvoiceForBatchPayWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		CommonQueryConditionDTO dto=new CommonQueryConditionDTO();
		String[] indexStr=request.getParameterValues("Index");
		String invoiceNo=null;
        if(indexStr!=null){
        	for(int i=0;i<indexStr.length;i++){
        		if(i==0){
        			invoiceNo=indexStr[i];
        		}else{
        			invoiceNo=invoiceNo+";"+indexStr[i];
        		}
        	}
        } 
        dto.setSpareStr3(invoiceNo);
        //  ²éÑ¯·½Ê½
		if (WebUtil.StringHaveContent(request.getParameter("txtqueryStyle"))){
		   dto.setOrderField(request.getParameter("txtqueryStyle"));
		}
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_BATCH_INVOICE);
	}

}
