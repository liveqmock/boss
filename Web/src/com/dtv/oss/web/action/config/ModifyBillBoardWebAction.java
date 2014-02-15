package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.BillboardEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2007-1-12
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyBillBoardWebAction extends GeneralWebAction {

	 
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	BillBoardDTO dto = new BillBoardDTO();
        BillboardEJBEvent event= new BillboardEJBEvent();
         
       
        if("CREATE".equals(request.getParameter("txtActionType"))){
			event.setActionType(EJBEvent.BILLBOARD_CREATE);
            dto.setPublishDate(new java.sql.Timestamp(System.currentTimeMillis()));
           }
	     else if("UPDATE".equals(request.getParameter("txtActionType"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
	    	 
			event.setActionType(EJBEvent.BILLBOARD_MODIFY);
	    }
	      else if("deleteDetail".equals(request.getParameter("txtActionType"))){
	    	 event.setActionType(EJBEvent.BILLBOARD_DELE);
	    }
	      if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
	        dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
 
	        dto.setDescription(request.getParameter("txtDesc"));
	 
    	    dto.setStatus(request.getParameter("txtStatus"));
	 
	    	dto.setGrade(request.getParameter("txtGrade"));
	    	
	    	dto.setPublishReason(request.getParameter("txtPublishReason"));
    
            dto.setPublishPerson(request.getParameter("txtPublishPerson"));
   
            dto.setName(request.getParameter("txtName"));
  
          
   
            dto.setDateFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom"))); 
     
            dto.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo"))); 
   
            
           event.setDto(dto);
            
        return event;
    }
}
