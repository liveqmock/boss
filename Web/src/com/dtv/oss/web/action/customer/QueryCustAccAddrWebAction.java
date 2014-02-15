package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerEJBEvent;
import com.dtv.oss.dto.wrap.customer.Customer2AddressWrap;
import javax.servlet.http.HttpServletRequest;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2004 </p>
 * <p> Company: Digivision</p>
 * User: thurm zhang
 * Date: 2004-12-24
 * Time: 12:44:46
 * To change this template use File | Settings | File Templates.
 */
public class QueryCustAccAddrWebAction extends QueryWebAction {
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	CustomerEJBEvent csrEJBEvent = new CustomerEJBEvent(CustomerEJBEvent.CHECK_FOR_MOVE);
    	
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
         	theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCatvID")))
         	theDTO.setNo(request.getParameter("txtCatvID"));
        if (WebUtil.StringHaveContent(request.getParameter("txtNewDetailAddress")))
         	theDTO.setSpareStr1(request.getParameter("txtNewDetailAddress"));
        if (WebUtil.StringHaveContent(request.getParameter("txtNewPostcode")))
         	theDTO.setSpareStr2(request.getParameter("txtNewPostcode"));
       // if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
       //     theDTO.setType(request.getParameter("txtCustomerType"));
        //return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUST_ACC_ADDR);
        return null;
    }
    
//	注意：该方法的作用是暂时的，强制返回，为了页面的流转
    public void doEnd(HttpServletRequest request, CommandResponse cmdResponse)
    {
    	WebPrint.PrintDebugInfo(this.getClass().getName(), "doEnd method enter ...");

        //check whether some exceptions are happened
        if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)!=null)
        {
            afterExceptionHappen(request);
            WebPrint.PrintDebugInfo(this.getClass().getName(), "doEnd Exception out");
            return;
        }
        
    	ArrayList list = new ArrayList();
		Customer2AddressWrap wrap = new Customer2AddressWrap();
		Customer2AddressWrap wrap2 = new Customer2AddressWrap();
		CustomerDTO custDTO = new CustomerDTO();
		AddressDTO  addrDTO = new AddressDTO();
		CustomerDTO custDTO2 = new CustomerDTO();
		AddressDTO  addrDTO2 = new AddressDTO();
		
		custDTO.setName("MyName");
		addrDTO.setDetailAddress( "111111");
		
		wrap.setAddrDto( addrDTO);
		wrap.setCustDto( custDTO);
		
		list.add(wrap);
		
		custDTO2.setName("MyName2");
		addrDTO2.setDetailAddress("222222");
		
		wrap2.setAddrDto( addrDTO);
		wrap2.setCustDto( custDTO);
		
		list.add(wrap2);
		
		if(cmdResponse != null)
			cmdResponse.setPayload( list);
		else
			cmdResponse = new CommandResponse(list);
	
        request.setAttribute("ResponseQueryResult", cmdResponse);
        afterSuccessfulResponse(request, cmdResponse);
        
        WebPrint.PrintDebugInfo(this.getClass().getName(), "doEnd out");

      }
	
}
