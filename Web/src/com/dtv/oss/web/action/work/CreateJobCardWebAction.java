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

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProblemDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;
 

/**
 * @author chenjiang
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CreateJobCardWebAction extends PayFeeWebAction {
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
	private void setFeeInfo(HttpServletRequest request, JobCardEJBEvent ejbEvent) throws WebActionException
	{
	    Collection feeList =getSessionFeeList(request); 
	    int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
	    int accountID =WebUtil.StringToInt(request.getParameter("txtAccount"));    
		ejbEvent.setFeeList(feeList) ;
		getPayList(request, customerID,accountID,
				ejbEvent.getPaymentList(),
				CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE,
			    ejbEvent.getCsiPrePaymentDeductionList());
	}
  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
      // TODO 自动生成方法存根
      JobCardEJBEvent  jobCardEJBEvent = new JobCardEJBEvent();
      JobCardDTO jobCardDTO=new JobCardDTO();
      AddressDTO addressDTO=new AddressDTO();
      Collection col = new ArrayList();
      //工单类型
      if (WebUtil.StringHaveContent(request.getParameter("txtJobType")))
    	  jobCardDTO.setJobType(request.getParameter("txtJobType"));
      // 工单子类型
      if (WebUtil.StringHaveContent(request.getParameter("txtSubtype")))
    	  jobCardDTO.setSubType(request.getParameter("txtSubtype"));
      // 终端编号
      if (WebUtil.StringHaveContent(request.getParameter("txtCatvID")))
    	  jobCardDTO.setCatvID(request.getParameter("txtCatvID"));
      //新增端口数
      if (WebUtil.StringHaveContent(request.getParameter("txtAddPortNumber")))
    	  jobCardDTO.setAddPortNumber(WebUtil.StringToInt(request.getParameter("txtAddPortNumber")));
      //流转部门
      if (WebUtil.StringHaveContent(request.getParameter("txtAutoNextOrgID")))
    	  jobCardDTO.setProcessOrgId(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
      //地址区域
      if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
    	  addressDTO.setDistrictID(WebUtil.StringToInt(request.getParameter("txtDistrictID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
    	  addressDTO.setDetailAddress(request.getParameter("txtDetailAddress"));
      if (WebUtil.StringHaveContent(request.getParameter("txtAddressID")))
    	  addressDTO.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
      
      //原地址区域
      AddressDTO oldAddressDTO=null;
      if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID"))||WebUtil.StringHaveContent(request.getParameter("txtDetailAddress"))){
    	  oldAddressDTO=new AddressDTO();
    	  oldAddressDTO.setDistrictID(WebUtil.StringToInt(request.getParameter("txtOldDistrictID")));
    	  oldAddressDTO.setDetailAddress(request.getParameter("txtOldDetailAddress"));
    	  jobCardEJBEvent.setOldAddressDTO(oldAddressDTO);
      }
      // 备注信息
      if (WebUtil.StringHaveContent(request.getParameter("txtComments")))
    	  jobCardDTO.setDescription(request.getParameter("txtComments"));
      
      //联系人信息
      if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	  jobCardDTO.setCustId(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtName")))
    	  jobCardDTO.setContactPerson(request.getParameter("txtName"));
      //设置帐户id
      if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
    	  jobCardDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
      //设置固定电话和移动电话
      if (WebUtil.StringHaveContent(request.getParameter("txtTelephone"))&&
    	  WebUtil.StringHaveContent(request.getParameter("txtTelephoneMobile"))){
    	  jobCardDTO.setContactPhone(request.getParameter("txtTelephone")+"/"+request.getParameter("txtTelephoneMobile"));
      }else if (WebUtil.StringHaveContent(request.getParameter("txtTelephone"))){
    	  jobCardDTO.setContactPhone(request.getParameter("txtTelephone"));
      }else if (WebUtil.StringHaveContent(request.getParameter("txtTelephoneMobile"))){
    	  jobCardDTO.setContactPhone(request.getParameter("txtTelephoneMobile"));
      }
      //设置预约上门时间和上门时间段
      if (WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))
    	  jobCardDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
      if (WebUtil.StringHaveContent(request.getParameter("txtPreferedTime")))
    	  jobCardDTO.setPreferedTime(request.getParameter("txtPreferedTime"));
      //设置关联单据类型 受理单
      jobCardDTO.setReferType(CommonConstDefinition.JOBCARD_REFERTYPE_S);
      
      //设置手工开工单客户化定制费用
      if (WebUtil.StringHaveContent(request.getParameter("txtCustomizeFee")))
    	  jobCardEJBEvent.setCustomizeFeeValue(request.getParameter("txtCustomizeFee"));
      
      jobCardEJBEvent.setJobCardDto(jobCardDTO);
      jobCardEJBEvent.setAddressDTO(addressDTO);
      jobCardEJBEvent.setDoPost(confirmPost);
      jobCardEJBEvent.setActionType(EJBEvent.MANUAL_CREATE_JOBCARD);
      if(confirmPost&& "calculatefee".equalsIgnoreCase(request.getParameter("txtScreenDirect")))
		setFeeInfo(request,jobCardEJBEvent);
    return   jobCardEJBEvent;
  }
    protected void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse) {
       super.afterSuccessfulResponse(request,cmdResponse);
    }

}
