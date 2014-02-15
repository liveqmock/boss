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
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
      // TODO �Զ����ɷ������
      CustomerProblemDTO dto = new CustomerProblemDTO();
      CustomerDTO custDto = new CustomerDTO();
    
      RepairEJBEvent  repairEvent = new RepairEJBEvent();
      Collection col = new ArrayList();
      //��������,����,��ID,�ֵ�վID,��ϵ��,��ַID,��������
      if (WebUtil.StringHaveContent(request.getParameter("txtContactPersonBak")))
    	  custDto.setCustomerType(request.getParameter("txtCustType"));
      // ����ҵ���˻�
      if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
          dto.setCustServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
      //�����˻�id
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
      
      //�ѹ�������͹��������ϲ�һ��
      if (WebUtil.StringHaveContent(request.getParameter("txtProblemPhenomena"))){
    	  if (WebUtil.StringHaveContent(request.getParameter("txtProblemDesc"))){
    		  dto.setProblemDesc("��������("+Postern.getCommonSettingDataValueByNameAndKey("SET_C_CPPROBLEMPHENOMENA", request.getParameter("txtProblemPhenomena"))+");"+dto.getProblemDesc());
      	  }else{
    		  dto.setProblemDesc("��������("+Postern.getCommonSettingDataValueByNameAndKey("SET_C_CPPROBLEMPHENOMENA", request.getParameter("txtProblemPhenomena"))+")");
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
