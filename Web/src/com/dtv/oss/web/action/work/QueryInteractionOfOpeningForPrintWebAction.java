package com.dtv.oss.web.action.work;

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
import com.dtv.oss.dto.LongCommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;

//public class QueryInteractionOfOpeningForPrintWebAction extends QueryInteractionNewCustWebAction 
public class QueryInteractionOfOpeningForPrintWebAction extends QueryWebAction {	
	
	protected void specialSetDTO(HttpServletRequest request, LongCommonQueryConditionDTO theDTO)
	{
		//只查询OS	门店开户
        //OB	预约开户
        //theDTO.setType("OB;BK;OS");
        theDTO.setExtraTime1(null);
        theDTO.setExtraTime2(null);
        //theDTO.setSpareStr4(null);
        String id[]=request.getParameterValues("txtID");
        String contonctId=null;
        if(id!=null){
        	for(int i=0;i<id.length;i++){
        		if(i==0){
        			contonctId=id[i];
        		}else{
        			contonctId=contonctId+";"+id[i];
        		}
        	}
        } 

        theDTO.setExtraStr1(contonctId);
        theDTO.setStatus(null);
	}
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	LongCommonQueryConditionDTO theDTO = new LongCommonQueryConditionDTO();
    	
    	//commonSetDTO(request, theDTO);
    	
    	specialSetDTO(request, theDTO);
    	
    	return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTSERVICEINTERACTION_ONLY);
       //return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CSI2CUSTOMER2ACCOUNT);
    }

}