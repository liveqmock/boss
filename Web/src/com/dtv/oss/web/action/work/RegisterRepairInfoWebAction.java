package com.dtv.oss.web.action.work;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;

public class RegisterRepairInfoWebAction  extends PayFeeWebAction{
	
	boolean confirmPost = false;
	protected boolean needCheckToken() {
		return confirmPost;
	}
	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if(request.getParameter("txtDoPost").equalsIgnoreCase("TRUE"))
				confirmPost = true;
		}
	}
	private void setFeeInfo(HttpServletRequest request, JobCardEJBEvent ejbEvent) throws WebActionException
	{
	    Collection feeList =getSessionFeeList(request); 
	    int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
	    int accountID =WebUtil.StringToInt(request.getParameter("txtAccountID"));    
		ejbEvent.setFeeList(feeList) ;
		getPayList(request, customerID,accountID,
				ejbEvent.getPaymentList(),
				CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE,
			    ejbEvent.getCsiPrePaymentDeductionList());
	}
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
  	  
  	    JobCardEJBEvent jcEvent = new JobCardEJBEvent();
  	    JobCardDTO jobCardDto = new JobCardDTO();
  	    Collection col = new ArrayList();
	    JobCardProcessDTO jcpDto = new JobCardProcessDTO();
	    boolean isSuccess = WebUtil.StringToBoolean(request.getParameter("isSuccess"));
	    int txtJobCardID =WebUtil.StringToInt(request.getParameter("txtJobCardID"));
	    jobCardDto.setJobCardId(txtJobCardID);
	    jobCardDto.setCustId(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
	    jobCardDto.setReferSheetId(WebUtil.StringToInt(request.getParameter("txtReferSheetId")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtOutofDateReason"))) 
	      jcpDto.setOutOfDateReason(request.getParameter("txtOutofDateReason"));
	      
	    jobCardDto.setTroubleType(request.getParameter("txtTroubleType"));
	    jobCardDto.setTroubleSubType(request.getParameter("txtTroubleSubType"));
	    jobCardDto.setWorkDate(WebUtil.StringToTimestamp(request.getParameter("txtWorkDate")));
	    jobCardDto.setWorkTime(request.getParameter("txtWorkTime"));
	    jcpDto.setWorkDate(WebUtil.StringToTimestamp(request.getParameter("txtWorkDate")));
	    jcpDto.setWorkTime(request.getParameter("txtWorkTime"));
	    jobCardDto.setResolutionType(request.getParameter("txtResolutionType"));
        jcpDto.setProcessMan(request.getParameter("txtProcessMan"));
                                                
        //当维修失败时才填写诊断信息
        if (isSuccess==false)
            col =DynamicServey.getDiagnosisInfo(request,"txtDiaName");
        System.out.println("col================="+col);
        
        //物料使用情况                                    
        Collection materialCol =DynamicServey.getMaterialUsage(request,"txtMaterialName",txtJobCardID);
        
        System.out.println("materialCol================"+materialCol);
        
        if (WebUtil.StringHaveContent(request.getParameter("txtFaildComment")))
        {
        	jcpDto.setMemo(request.getParameter("txtFaildComment"));
        }
        if (WebUtil.StringHaveContent(request.getParameter("txtWorkResultReason")))
        {
        	jobCardDto.setWorkResultReason(request.getParameter("txtWorkResultReason"));
        jcpDto.setWorkResultReason(request.getParameter("txtWorkResultReason"));
        }
        if (WebUtil.StringHaveContent(request.getParameter("txtSucessComment")))
        {
        	
        	jcpDto.setMemo(request.getParameter("txtSucessComment"));
        } 
        if(confirmPost && isSuccess)
		{
			setFeeInfo(request,jcEvent);
			
		}
        
        jcEvent.setDoPost(confirmPost);
        jcpDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
        jobCardDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
    	jcEvent.setSuccess(isSuccess);
    	jcEvent.setActionType(EJBEvent.REGISTER_REPAIR_INFO);
    	jcEvent.setJobCardDto(jobCardDto);
    	jcEvent.setJobcardProcessDto(jcpDto);
    	jcEvent.setDiagnosisInfoDtos(col);
    	jcEvent.setMaterialUsage(materialCol);
        return jcEvent;

    }
   

}
