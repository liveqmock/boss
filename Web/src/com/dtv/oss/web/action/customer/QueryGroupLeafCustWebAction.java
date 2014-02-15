package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryGroupLeafCustWebAction extends QueryWebAction {

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        
        //�ͻ�֤��
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID") ) );
        	//theDTO.setNo(request.getParameter("txtCustomerID"));
        //�ͻ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            theDTO.setSpareStr1(request.getParameter("txtName"));
        //��װ��ַ
        if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
            theDTO.setSpareStr3(request.getParameter("txtDetailAddress"));
        //��Դ����
        if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
            theDTO.setSpareStr13(request.getParameter("txtOpenSourceType"));
        //֤����
        if (WebUtil.StringHaveContent(request.getParameter("txtCardID")))
            theDTO.setSpareStr5(request.getParameter("txtCardID"));
        //�籣����
        if (WebUtil.StringHaveContent(request.getParameter("txtSocialSecCardID")))
            theDTO.setSpareStr6(request.getParameter("txtSocialSecCardID"));
        //�̶��绰
        if (WebUtil.StringHaveContent(request.getParameter("txtTelephone")))
            theDTO.setSpareStr7(request.getParameter("txtTelephone"));
        //�ƶ��绰
        if (WebUtil.StringHaveContent(request.getParameter("txtTelephoneMobile")))
            theDTO.setSpareStr8(request.getParameter("txtTelephoneMobile"));
        //���ѷ�ʽ
        if (WebUtil.StringHaveContent(request.getParameter("txtMopID")))
            theDTO.setSpareStr9(request.getParameter("txtMopID"));
        //�����˺�
        if (WebUtil.StringHaveContent(request.getParameter("txtBankAccount")))
            theDTO.setSpareStr10(request.getParameter("txtBankAccount"));
        
        //add by jason 2006-7-3 
        //Mac��ַ
        if (WebUtil.StringHaveContent(request.getParameter("txtMacAddress")))
            theDTO.setSpareStr11(request.getParameter("txtMacAddress"));
        //�ڲ�Mac��ַ 
        if (WebUtil.StringHaveContent(request.getParameter("txtInterMacAddress")))
            theDTO.setSpareStr12(request.getParameter("txtInterMacAddress"));
        //end 
        
        // �����
        if (WebUtil.StringHaveContent(request.getParameter("txtServiceCode")))
        	theDTO.setSpareStr14(request.getParameter("txtServiceCode"));
        //����
        if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
        	theDTO.setSpareStr15(request.getParameter("txtDistrictID"));
        //��֯
        if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
        	theDTO.setSpareStr16(request.getParameter("txtOrgID"));
        
         //�ͻ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
            theDTO.setType(request.getParameter("txtCustomerType"));
        //�ͻ�״̬
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
            theDTO.setStatus(request.getParameter("txtStatus"));
        //��������
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
            theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
            theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));

        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerStyle")))
        	theDTO.setSpareStr2(request.getParameter("txtCustomerStyle"));
        if (WebUtil.StringHaveContent(request.getParameter("txtGroupCustID")))
        	theDTO.setSpareStr4(request.getParameter("txtGroupCustID"));
        if (WebUtil.StringHaveContent(request.getParameter("txtContractNo")))
        	theDTO.setSpareStr17(request.getParameter("txtContractNo"));
                
        theDTO.setOrderField(orderByFieldName);
        theDTO.setIsAsc(!orderDescFlag);
        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTOMER);
        //return null;

    }
    
}