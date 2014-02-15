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
import com.dtv.oss.web.util.*;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
 
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;

public class ContactUserForRepairWebAction extends GeneralWebAction{
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
      
  	   JobCardEJBEvent jobCardEJBEvent = new JobCardEJBEvent();
  	   //批量预约 begin
		if("batch".equals(request.getParameter("txtActionType")))
		{
			
			CommonQueryConditionDTO commonConditionDto = new CommonQueryConditionDTO();
			commonConditionDto.setSpareStr1(request.getParameter("txtJobCardIDList"));
			commonConditionDto.setSpareStr2(request.getParameter("txtJobCardDtLastmodList"));
			commonConditionDto.setSpareStr3(request.getParameter("txtPreferedDate"));
			commonConditionDto.setSpareStr4(request.getParameter("txtPreferedTime"));
			commonConditionDto.setSpareStr5(request.getParameter("txtMemo"));
			commonConditionDto.setSpareStr6(request.getParameter("txtWorkResult"));
			jobCardEJBEvent.setActionType(EJBEvent.BATCH_CONTACT_USER_FOR_REPAIR) ;
			jobCardEJBEvent.setCommonConditionDto(commonConditionDto);
			return jobCardEJBEvent;
		}
		//批量预约 end
  	      
	   JobCardDTO jcDto = new JobCardDTO();
		
	   JobCardProcessDTO jcpDto = new JobCardProcessDTO();
     
          jcDto.setJobCardId(WebUtil.StringToInt(request.getParameter("txtJobCardID")));
          
          jcDto.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
          
          jcDto.setWorkResult(request.getParameter("txtWorkResult"));
          
          jcpDto.setWorkResult(request.getParameter("txtWorkResult"));
          
          jcpDto.setMemo(request.getParameter("txtMemo"));
          
          jcDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtJobCardDtLastmod")));
          
          jcDto.setPreferedTime(request.getParameter("txtPreferedTime"));

          jcDto.setContactPerson(request.getParameter("txtContactPerson"));
         
          jcDto.setContactPhone(request.getParameter("txtContactPhone"));

          jobCardEJBEvent.setActionType(EJBEvent.CONTACT_USER_FOR_REPAIR);
          
          jobCardEJBEvent.setJobCardDto(jcDto);
           
          jobCardEJBEvent.setJobcardProcessDto(jcpDto);
          
        return  jobCardEJBEvent;

    }

}
