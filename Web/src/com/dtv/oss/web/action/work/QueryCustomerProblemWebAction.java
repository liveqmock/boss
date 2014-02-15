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
public class QueryCustomerProblemWebAction extends QueryWebAction {


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
    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM);
    }
    //����ʱ��(��ʼ),����ʱ��(����)
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
        dto.setBeginTime(WebUtil.StringToTimestampWithDayBegin(
        		request.getParameter("txtCreateStartDate"))) ;
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
        dto.setEndTime(WebUtil.StringToTimestampWithDayEnd(
        		request.getParameter("txtCreateEndDate")));
    //�������
    if (WebUtil.StringHaveContent(request.getParameter("txtProblemCategory")))
    	dto.setSpareStr13(request.getParameter("txtProblemCategory"));
    //�շ����
   if (WebUtil.StringHaveContent(request.getParameter("txtFeeClass")))
      dto.setSpareStr14(request.getParameter("txtFeeClass"));  
   //status
   if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
       dto.setSpareStr1(request.getParameter("txtStatus")); 
   if (WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
   		dto.setSpareStr15(request.getParameter("txtQueryType"));
    return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM);
  }

}
