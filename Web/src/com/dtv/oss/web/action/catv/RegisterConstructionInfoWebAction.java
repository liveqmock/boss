/*
 * Created on 2007-7-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.web.action.catv;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.catv.CatvJobCardEJBEvent;
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CurrentLogonOperator;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.CommonKeys;
import java.util.*;

/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RegisterConstructionInfoWebAction extends PayFeeWebAction{

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
	private void setFeeInfo(HttpServletRequest request, CatvJobCardEJBEvent ejbEvent) throws WebActionException
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
  	  
  	    CatvJobCardEJBEvent jcEvent = new CatvJobCardEJBEvent();
  	    JobCardDTO jobCardDto = new JobCardDTO();
  	    Collection col = new ArrayList();
	    JobCardProcessDTO jcpDto = new JobCardProcessDTO();
	    boolean isSuccess = WebUtil.StringToBoolean(request.getParameter("isSuccess"));
	    int txtJobCardID =WebUtil.StringToInt(request.getParameter("txtJobCardID"));
	    jobCardDto.setJobCardId(txtJobCardID);
	    jobCardDto.setCustId(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
	    jobCardDto.setReferSheetId(WebUtil.StringToInt(request.getParameter("txtReferSheetId")));
	    jobCardDto.setJobType(CommonKeys.JOBCARD_TYPE_CATVNETWORK);
	    jobCardDto.setSubType(request.getParameter("txtSubType"));
	    jobCardDto.setAddPortNumber(WebUtil.StringToInt(request.getParameter("txtPortNo")));
	    
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
        
        jobCardDto.setCreateOpID(CurrentLogonOperator.getOperator(request).getOperatorID());
        jobCardDto.setCreateOrgID(CurrentLogonOperator.getCurrentOperatorOrgID(request));
                                                
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
