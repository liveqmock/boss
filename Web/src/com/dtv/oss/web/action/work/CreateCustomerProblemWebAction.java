/*
 * 创建日期 2005-11-10
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dtv.oss.web.action.work;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProblemDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.RepairEJBEvent;
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
 

/**
 * @author chenjiang
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CreateCustomerProblemWebAction extends PayFeeWebAction {
	boolean confirmPost = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		 super.doStart(request);
         if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("TRUE"))
				confirmPost = true;
		}

	}
	 
	private void setFeeInfo(HttpServletRequest request, RepairEJBEvent ejbEvent) throws WebActionException
	{
	    Collection feeList =getSessionFeeList(request); 
		ejbEvent.setFeeList(feeList) ;
	}
  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
      // TODO 自动生成方法存根
      CustomerProblemDTO dto = new CustomerProblemDTO();
      CustomerDTO custDto = new CustomerDTO();
    
      RepairEJBEvent  repairEvent = new RepairEJBEvent();
      Collection col = new ArrayList();
      //问题类型,级别,区ID,街道站ID,联系人,地址ID,问题描述
      if (WebUtil.StringHaveContent(request.getParameter("txtContactPersonBak")))
    	  custDto.setCustomerType(request.getParameter("txtCustType"));
      // 设置业务账户
      if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
          dto.setCustServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
      //设置账户id
      if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
    	  dto.setCustAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
      if (WebUtil.StringHaveContent(request.getParameter("txtContactPersonBak")))
          dto.setContactPerson(request.getParameter("txtContactPersonBak"));
      if (request.getParameter("txtContactPersonBak")==null)
          dto.setContactPerson(request.getParameter("txtContactPerson"));
      if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
          dto.setContactphone(request.getParameter("txtContactPhone"));
      if (WebUtil.StringHaveContent(request.getParameter("txtProblemCategory")))
          dto.setProblemCategory(request.getParameter("txtProblemCategory"));
      if (WebUtil.StringHaveContent(request.getParameter("txtProblemDesc")))
          dto.setProblemDesc(request.getParameter("txtProblemDesc"));
      
      //把故障现象和故障描述合并一起
      if (WebUtil.StringHaveContent(request.getParameter("txtProblemPhenomena"))){
    	  if (WebUtil.StringHaveContent(request.getParameter("txtProblemDesc"))){
    		  dto.setProblemDesc("故障现象("+Postern.getCommonSettingDataValueByNameAndKey("SET_C_CPPROBLEMPHENOMENA", request.getParameter("txtProblemPhenomena"))+");"+dto.getProblemDesc());
      	  }else{
    		  dto.setProblemDesc("故障现象("+Postern.getCommonSettingDataValueByNameAndKey("SET_C_CPPROBLEMPHENOMENA", request.getParameter("txtProblemPhenomena"))+")");
    	  }
      }else{
    	  dto.setProblemDesc(dto.getProblemDesc());
      }
      if (WebUtil.StringHaveContent(request.getParameter("txtAddressId")))
          dto.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressId")));
      if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
          dto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
      dto.setProblemCategory(request.getParameter("txtProblemCategory"));
      dto.setProblemLevel(request.getParameter("txtProblemLevel"));
      dto.setFeeClass(request.getParameter("txtFeeClass"));
      if(WebUtil.StringHaveContent(request.getParameter("txtID")))
    	  dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
      if(WebUtil.StringHaveContent(request.getParameter("txtMemo")))
    	  repairEvent.setMemo(request.getParameter("txtMemo"));
   //   String  diaIdName=request.getParameter("txtDiaName");
      col =DynamicServey.getDiagnosisInfo(request,"txtDiaName");
      if (WebUtil.StringHaveContent(request.getParameter("transferType"))){
    	  if(("auto").equalsIgnoreCase(request.getParameter("transferType"))){
    		  repairEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
    	  }else if(("manual").equalsIgnoreCase(request.getParameter("transferType"))){
    		  repairEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtNextOrgID")));
    	  }
      }
      repairEvent.setDoPost(confirmPost);
      repairEvent.setActionType(EJBEvent.PROCESS_CUSTOMER_PROBLEM);
      repairEvent.setCustomerProblemDto(dto);
      repairEvent.setCustDto(custDto);
      repairEvent.setDiagnosisInfoDtos(col);
      //System.out.println("*************zhen duan xin xi lie biao:"+col);
      if("diagnosis".equals(request.getParameter("txtActionType"))){
    	  repairEvent.setDiagnosisResult(request.getParameter("txtDiagnosisResult"));
    	 repairEvent.setActionType(EJBEvent.DIAGNOSIS_REPAIR);
      }
      if(confirmPost)
  		setFeeInfo(request,repairEvent);
    return   repairEvent;
  }
    protected void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse) {
       super.afterSuccessfulResponse(request,cmdResponse);
   // CurrentCustomer.UpdateCurrentCustomer(request);
}

}
