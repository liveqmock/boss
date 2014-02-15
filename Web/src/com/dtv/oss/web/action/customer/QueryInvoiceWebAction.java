package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;


public class QueryInvoiceWebAction extends QueryWebAction{
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
      CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
      if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceNo")))
              theDTO.setNo(request.getParameter("txtInvoiceNo"));
      if (WebUtil.StringHaveContent(request.getParameter("txtBarCode")))
              theDTO.setSpareStr1(request.getParameter("txtBarCode").trim());
      if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
              theDTO.setSpareStr11(request.getParameter("txtAccountID"));
      if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
              theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceSourceType")))
              theDTO.setSpareStr12(request.getParameter("txtInvoiceSourceType"));
      if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceCycleName")))
    	      theDTO.setSpareStr13(request.getParameter("txtInvoiceCycleName"));    
      //客户所在区
      if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))  
    	      theDTO.setDistrict(WebUtil.StringToInt(request.getParameter("txtDistrictID")));
    	  
      if (WebUtil.StringHaveContent(request.getParameter("txtReferenceNo")))
              theDTO.setSpareStr14(request.getParameter("txtReferenceNo"));
      if("Y".equals(request.getParameter("txtInvoiceBatchPayAndPrey"))){
    	  //用于对特定帐户进行帐单批量支付预存的情况
    	  theDTO.setStatus("W");
      }else{
	      if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
	              theDTO.setStatus(request.getParameter("txtStatus"));
      }
      if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeFrom")))
 	          theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTimeFrom")));
      if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeTo")))
             theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTimeTo")));
      if (WebUtil.StringHaveContent(request.getParameter("txtAddress")))
      		theDTO.setSpareStr15(request.getParameter("txtAddress"));
      return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTINVOICE);

    }

}
