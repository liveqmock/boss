package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryGeneralCustomerWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author Yangyong
 */
public class QueryInteractionOfBookingWebAction extends QueryGeneralCustomerWebAction {

	 protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        
        //ԤԼ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtID")))
            theDTO.setNo(request.getParameter("txtID"));
        
        //�ͻ�֤�� --ע�����ԤԼ�Ĳ�ѯtxtCustomerID��ҳ���ϵĴ�ֵ��0
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	theDTO.setSpareStr1(request.getParameter("txtCustomerID"));
        //����ʱ��
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
        	theDTO.setBeginTime(WebUtil.StringToTimestamp("txtCreateStartDate"));
        
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
        	theDTO.setEndTime(WebUtil.StringToTimestamp("txtCreateEndDate"));
        
        //��ϵ������
        if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            theDTO.setSpareStr11(request.getParameter("txtName"));
        
        //��ϵ�绰
        if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
        	theDTO.setSpareStr12(request.getParameter("txtContactPhone"));
        
        //��Դ����
        if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
            theDTO.setSpareStr13(request.getParameter("txtOpenSourceType"));
        
        //��Դ����ID
        if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceID")))
           theDTO.setSpareStr14(request.getParameter("txtOpenSourceID"));
        
        //������
        if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
           theDTO.setStreet(WebUtil.StringToInt(request.getParameter("txtCounty")));
        
        //�������
        if (WebUtil.StringHaveContent(request.getParameter("txtJobCardId")))
           theDTO.setSpareStr15(request.getParameter("txtJobCardId"));
        
        //��ϸ��ַ
        if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
           theDTO.setSpareStr16(request.getParameter("txtDetailAddress"));
        
        //����״̬
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus"))){
          theDTO.setStatus(request.getParameter("txtStatus"));
        }
        //��������
       	theDTO.setType(CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK);
        
        theDTO.setOrderField(orderByFieldName);
        theDTO.setIsAsc(!orderDescFlag);
        
        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTSERVICEINTERACTION);
	 }
	
}
