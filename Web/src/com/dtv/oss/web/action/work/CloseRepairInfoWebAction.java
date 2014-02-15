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
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
import com.dtv.oss.web.action.CheckTokenWebAction;
import com.dtv.oss.web.util.WebUtil;
 
 
public class CloseRepairInfoWebAction extends CheckTokenWebAction{
  protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception
    {
   
  	  JobCardEJBEvent jcEvent = new JobCardEJBEvent();
      JobCardProcessDTO jcpDto= new JobCardProcessDTO();
      jcpDto.setJcId(WebUtil.StringToInt(request.getParameter("txtJobCardID")));
      jcpDto.setMemo(request.getParameter("txtMemo")); 
      jcEvent.setActionType(EJBEvent.CLOSE_REPAIR_INFO);
      jcEvent.setJobcardProcessDto(jcpDto);
      return  jcEvent;
    }

 

}
