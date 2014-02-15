/*
 * �������� 2005-10-16
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dtv.oss.web.action.work;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.WorkQueryEJBEvent;
import com.dtv.oss.web.util.WebUtil;
  
/**
 * @author chenjiang
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class QueryCustomerProblemAndProcessWebAction extends QueryWebAction {


  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request)
      throws Exception {
//״̬,����,ID,�����û�ID,Ŀǰ��ת����ID,
//Ŀǰ��ת������ʱ��(��ʼ),Ŀǰ��ת������ʱ��(����),���Ŵ���Ľ�ֹʱ��(��ʼ),���Ŵ���Ľ�ֹʱ��(����)
  	 CommonQueryConditionDTO	dto = new  CommonQueryConditionDTO();
   
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerProblemID")))
        dto.setNo(request.getParameter("txtCustomerProblemID"));
    if(WebUtil.StringHaveContent(request.getParameter("fflag")) && ("frombutton").equals(request.getParameter("fflag"))){
    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM2CUSTOMER_PROBLEM_PROCESS);
    }
    
    //����ʱ��(��ʼ),����ʱ��(����)
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDateDatePart")))
        dto.setBeginTime(WebUtil.StringToTimestampDefaultDayBegin(
        		request.getParameter("txtCreateStartDateDatePart"),
				request.getParameter("txtCreateStartDateHourPart")
				));
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDateDatePart")))
        dto.setEndTime(WebUtil.StringToTimestampDefaultDayEnd(
        		request.getParameter("txtCreateEndDateDatePart"),
				request.getParameter("txtCreateEndDateHourPart")
				));
    if(request.getParameter("txtCustID")!=null && !"".equals(request.getParameter("txtCustID"))){
    	// request.getSession().setAttribute("CUSTOMERID",request.getParameter("txtCustID"));
    	request.setAttribute("CUSTOMERID",request.getParameter("txtCustID"));
    }
   //��װ��ַ
    if (WebUtil.StringHaveContent(request.getParameter("txtAddress")))
        dto.setSpareStr4(request.getParameter("txtAddress"));  
//  �������
    if (WebUtil.StringHaveContent(request.getParameter("txtServiceCode")))
        dto.setSpareStr5(request.getParameter("txtServiceCode"));  
//  �豸����
    if (WebUtil.StringHaveContent(request.getParameter("txtDeviceClass")))
        dto.setSpareStr8(request.getParameter("txtDeviceClass"));  
//  �豸�ͺ�
    if (WebUtil.StringHaveContent(request.getParameter("txtDeviceModel")))
        dto.setSpareStr6(request.getParameter("txtDeviceModel"));  
    
    //������֯
    if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
        dto.setSpareStr7(request.getParameter("txtOrgID"));
   
   
    //�������
    if (WebUtil.StringHaveContent(request.getParameter("txtProblemCategory")))
    	dto.setType(request.getParameter("txtProblemCategory"));
    //�շ����
   if (WebUtil.StringHaveContent(request.getParameter("txtFeeClass")))
      dto.setSpareStr1(request.getParameter("txtFeeClass"));  
   //status
   if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
       dto.setStatus(request.getParameter("txtStatus"));  
   //�û�֤��
   if (WebUtil.StringHaveContent(request.getParameter("txtCustID")))
       dto.setSpareStr2(request.getParameter("txtCustID"));   
// ���ڷ���
   if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
       dto.setDistrict(WebUtil.StringToInt(request.getParameter("txtCounty")));
   //�طñ�־
   if (WebUtil.StringHaveContent(request.getParameter("txtCallBackFlag")))
    dto.setSpareStr3(request.getParameter("txtCallBackFlag")); 
   if (WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
	   dto.setSpareStr9(request.getParameter("txtQueryType"));
   if (WebUtil.StringHaveContent(request.getParameter("txtIsManualTransfer")))
	   dto.setSpareStr10(request.getParameter("txtIsManualTransfer"));
    return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM2CUSTOMER_PROBLEM_PROCESS);
  }

}
