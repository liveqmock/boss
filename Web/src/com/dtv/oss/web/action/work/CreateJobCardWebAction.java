/*
 * �������� 2005-11-10
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
      // TODO �Զ����ɷ������
      JobCardEJBEvent  jobCardEJBEvent = new JobCardEJBEvent();
      JobCardDTO jobCardDTO=new JobCardDTO();
      AddressDTO addressDTO=new AddressDTO();
      Collection col = new ArrayList();
      //��������
      if (WebUtil.StringHaveContent(request.getParameter("txtJobType")))
    	  jobCardDTO.setJobType(request.getParameter("txtJobType"));
      // ����������
      if (WebUtil.StringHaveContent(request.getParameter("txtSubtype")))
    	  jobCardDTO.setSubType(request.getParameter("txtSubtype"));
      // �ն˱��
      if (WebUtil.StringHaveContent(request.getParameter("txtCatvID")))
    	  jobCardDTO.setCatvID(request.getParameter("txtCatvID"));
      //�����˿���
      if (WebUtil.StringHaveContent(request.getParameter("txtAddPortNumber")))
    	  jobCardDTO.setAddPortNumber(WebUtil.StringToInt(request.getParameter("txtAddPortNumber")));
      //��ת����
      if (WebUtil.StringHaveContent(request.getParameter("txtAutoNextOrgID")))
    	  jobCardDTO.setProcessOrgId(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
      //��ַ����
      if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
    	  addressDTO.setDistrictID(WebUtil.StringToInt(request.getParameter("txtDistrictID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
    	  addressDTO.setDetailAddress(request.getParameter("txtDetailAddress"));
      if (WebUtil.StringHaveContent(request.getParameter("txtAddressID")))
    	  addressDTO.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
      
      //ԭ��ַ����
      AddressDTO oldAddressDTO=null;
      if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID"))||WebUtil.StringHaveContent(request.getParameter("txtDetailAddress"))){
    	  oldAddressDTO=new AddressDTO();
    	  oldAddressDTO.setDistrictID(WebUtil.StringToInt(request.getParameter("txtOldDistrictID")));
    	  oldAddressDTO.setDetailAddress(request.getParameter("txtOldDetailAddress"));
    	  jobCardEJBEvent.setOldAddressDTO(oldAddressDTO);
      }
      // ��ע��Ϣ
      if (WebUtil.StringHaveContent(request.getParameter("txtComments")))
    	  jobCardDTO.setDescription(request.getParameter("txtComments"));
      
      //��ϵ����Ϣ
      if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	  jobCardDTO.setCustId(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtName")))
    	  jobCardDTO.setContactPerson(request.getParameter("txtName"));
      //�����ʻ�id
      if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
    	  jobCardDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
      //���ù̶��绰���ƶ��绰
      if (WebUtil.StringHaveContent(request.getParameter("txtTelephone"))&&
    	  WebUtil.StringHaveContent(request.getParameter("txtTelephoneMobile"))){
    	  jobCardDTO.setContactPhone(request.getParameter("txtTelephone")+"/"+request.getParameter("txtTelephoneMobile"));
      }else if (WebUtil.StringHaveContent(request.getParameter("txtTelephone"))){
    	  jobCardDTO.setContactPhone(request.getParameter("txtTelephone"));
      }else if (WebUtil.StringHaveContent(request.getParameter("txtTelephoneMobile"))){
    	  jobCardDTO.setContactPhone(request.getParameter("txtTelephoneMobile"));
      }
      //����ԤԼ����ʱ�������ʱ���
      if (WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))
    	  jobCardDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
      if (WebUtil.StringHaveContent(request.getParameter("txtPreferedTime")))
    	  jobCardDTO.setPreferedTime(request.getParameter("txtPreferedTime"));
      //���ù����������� ����
      jobCardDTO.setReferType(CommonConstDefinition.JOBCARD_REFERTYPE_S);
      
      //�����ֹ��������ͻ������Ʒ���
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
