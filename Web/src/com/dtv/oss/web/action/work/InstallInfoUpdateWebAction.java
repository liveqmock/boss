package com.dtv.oss.web.action.work;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

 
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebOperationUtil;
import com.dtv.oss.web.util.WebUtil;

public class InstallInfoUpdateWebAction extends PayFeeWebAction{
   boolean doPost = false;
   protected boolean needCheckToken() {
	   return doPost;
   }

   public void doStart(HttpServletRequest request) {
	  super.doStart(request);
	  if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
		 if (request.getParameter("txtDoPost").equalsIgnoreCase("TRUE"))
			doPost = true;
		 }
	}
	
    protected EJBEvent encapsulateData(HttpServletRequest request) 
       throws WebActionException{
  	     JobCardEJBEvent jcEjbEvent = new JobCardEJBEvent();
  	     
  	     //批量录入安装反馈信息 begin	
         if("batch".equals(request.getParameter("txtActionType")))
         {
        	 CommonQueryConditionDTO  commonConditionDto = new CommonQueryConditionDTO();
        	 commonConditionDto.setSpareStr1(request.getParameter("txtJobCardIDList"));
        	 commonConditionDto.setSpareStr2(request.getParameter("txtJobCardDtLastmodList"));
        	 commonConditionDto.setSpareStr3(request.getParameter("txtWorkDate"));
        	 commonConditionDto.setSpareStr4(request.getParameter("txtProcessMan"));
        	 commonConditionDto.setSpareStr5(request.getParameter("txtMemo"));
        	 commonConditionDto.setSpareStr6(request.getParameter("txtWorkTime"));
        	 jcEjbEvent.setCommonConditionDto(commonConditionDto);
        	 jcEjbEvent.setActionType(EJBEvent.BATCH_REGISTER_INSTALLATION_INFO);
        	 return  jcEjbEvent; 
         }
         //批量录入安装反馈信息 end
  	     
         JobCardProcessDTO  theDTO = new  JobCardProcessDTO();
  	     JobCardDTO  jcDto = new  JobCardDTO();
  	     
  	     int txtJobCardID =WebUtil.StringToInt(request.getParameter("txtJobCardID"));
         theDTO.setJcId(txtJobCardID);
         jcDto.setJobCardId(WebUtil.StringToInt(request.getParameter("txtJobCardID")));
         jcDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
         jcDto.setWorkDate(WebUtil.StringToTimestamp(request.getParameter("txtWorkDate")));
         jcDto.setWorkTime(request.getParameter("txtWorkTime"));
         if (WebUtil.StringHaveContent(request.getParameter("txtFaildComment"))){
        	 theDTO.setMemo(request.getParameter("txtFaildComment"));
         }
         if (WebUtil.StringHaveContent(request.getParameter("txtMemo"))){
       	    theDTO.setMemo(request.getParameter("txtMemo"));
         }
         theDTO.setProcessMan(request.getParameter("txtProcessMan"));
         theDTO.setOutOfDateReason(request.getParameter("txtOutOfDateReason"));
         theDTO.setWorkResultReason(request.getParameter("txtWorkResultReason"));
         jcDto.setWorkResultReason(request.getParameter("txtWorkResultReason"));
         
         String txtcsiPayOption =request.getParameter("txtcsiPayOption");
         //上门安装成功后收取
         int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
 		 int accountID =WebUtil.StringToInt(request.getParameter("txtAccountID"));
 		 boolean isSuccess = WebUtil.StringToBoolean(request.getParameter("txtIsSuccess"));
         if (doPost && isSuccess && CommonKeys.CSI_PAYMENT_OPTION_SP.equals(txtcsiPayOption)){
        	 jcEjbEvent.setFeeList(getSessionFeeList(request)) ;
        	 getPayAndPrePayList(request,customerID,accountID,
     				             jcEjbEvent.getPaymentList(),
     				             jcEjbEvent.getCsiPrePaymentDeductionList());
     		
         }
         
         jcEjbEvent.setAccountID(accountID);
         jcEjbEvent.setSuccess(isSuccess);
         jcEjbEvent.setActionType(EJBEvent.REGISTER_INSTALLATION_INFO);
         jcEjbEvent.setJobcardProcessDto(theDTO);
         jcEjbEvent.setJobCardDto(jcDto);
         jcEjbEvent.setDoPost(doPost);
        
         //物料使用情况
         Collection materialCol =DynamicServey.getMaterialUsage(request,"txtDynamicServey",txtJobCardID);
         jcEjbEvent.setMaterialUsage(materialCol);
         
         
         return  jcEjbEvent;        

    }
    protected  void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse) {
		//对新增的产品做预计费处理
		if(doPost){
			try{
				Object responseObj=cmdResponse.getPayload();
				if(responseObj!=null)
					WebOperationUtil.preCalculateFeeInfo(((Integer)responseObj).intValue(), WebOperationUtil.getOperator(request.getSession()).getOperatorID());
			}catch (Exception ex){
				throw new RuntimeException("预计费错误："+ex.getMessage());
			}
		}
    	
    }

}