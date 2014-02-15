/*
 * �������� 2005-10-12
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dtv.oss.web.action.catv;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.catv.CatvJobCardEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * @author chen jiang
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class QueryCatvJobCardWebAction extends QueryWebAction {


  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request)
      throws Exception {
    CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
    //�������
    if (WebUtil.StringHaveContent(request.getParameter("txtJobCardID")))
        dto.setNo(request.getParameter("txtJobCardID"));
    //�ͻ���
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
    //����������
    if (WebUtil.StringHaveContent(request.getParameter("txtSubType")))
        dto.setSpareStr1(request.getParameter("txtSubType"));
    //  ����״̬����
    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
      dto.setStatus(request.getParameter("txtStatus"));
    //�ͻ�����
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
        dto.setSpareStr2(request.getParameter("txtCustomerType"));
    //  ��������
    if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
        dto.setSpareStr3(request.getParameter("txtCounty"));
    //����ʱ��
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDateDatePart")))
        dto.setBeginTime(WebUtil.StringToTimestampDefaultDayBegin(
        		request.getParameter("txtCreateStartDateDatePart"),
				request.getParameter("txtCreateStartDateHourPart")));
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDateDatePart")))
        dto.setEndTime(WebUtil.StringToTimestampDefaultDayEnd(
        		request.getParameter("txtCreateEndDateDatePart"),
				request.getParameter("txtCreateEndDateHourPart")));
    /**
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
        dto.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
        dto.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));
    **/
	if (WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
		dto.setSpareStr19(request.getParameter("txtQueryType"));
    //  ԤԼʱ�侫ȷ����
    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedStartDate")))
    	dto.setSpareBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPreferedStartDate")));
    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedEndDate")))
    	dto.setSpareEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPreferedEndDate")));
    //  ���ʱ��
    if (WebUtil.StringHaveContent(request.getParameter("txtFinishedStartDate")))
    	dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtFinishedStartDate")));
    if (WebUtil.StringHaveContent(request.getParameter("txtFinishedEndDate")))
    	dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtFinishedEndDate")));
    //����ʧ��ԭ��
    if (WebUtil.StringHaveContent(request.getParameter("txtWorkResultReason")))
        dto.setSpareStr4(request.getParameter("txtWorkResultReason"));
    //��������
    if (WebUtil.StringHaveContent(request.getParameter("txtJobType")))
        dto.setSpareStr5(request.getParameter("txtJobType"));
    //����������Ա
    if (WebUtil.StringHaveContent(request.getParameter("txtProcessMan")))
    	dto.setSpareStr22(request.getParameter("txtProcessMan"));
    //--add by david.Yang
    //��ϵ��
    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerName")))
    	dto.setSpareStr23(request.getParameter("txtCustomerName"));
    //��ϵ�绰
    if (WebUtil.StringHaveContent(request.getParameter("textTelehone")))
    	dto.setSpareStr24(request.getParameter("textTelehone"));
    //����ʽ
    if (WebUtil.StringHaveContent(request.getParameter("txtorderStyle"))){
    	dto.setOrderField(request.getParameter("txtorderStyle"));
    }


    return new CsrQueryEJBEvent(dto, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_JOBCARD_CATV);
  }

}
