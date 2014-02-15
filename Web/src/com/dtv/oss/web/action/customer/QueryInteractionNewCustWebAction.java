/*
 * author:david.Yang
 */
package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.web.action.QueryWebAction;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.web.util.CommonKeys;

public class QueryInteractionNewCustWebAction extends QueryWebAction {

	protected void commonSetDTO(HttpServletRequest request, CommonQueryConditionDTO theDTO)
	{
	    //�������
	    if (WebUtil.StringHaveContent(request.getParameter("txtID")))
            theDTO.setNo(request.getParameter("txtID"));
		
	     //�طñ�־
	    if (WebUtil.StringHaveContent(request.getParameter("txtCallBackFlag")))
            theDTO.setSpareStr6(request.getParameter("txtCallBackFlag"));
	    
	    //�û�֤��
	    if (WebUtil.StringHaveContent(request.getParameter("txtUserID")))
            theDTO.setSpareStr1(request.getParameter("txtUserID"));    
	    
	    //������
	    if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
            theDTO.setStreet(WebUtil.StringToInt(request.getParameter("txtCounty")));  
	    
	    //����ʱ��
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
    		theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
    		theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));
	    
        //ԤԼ��������
	    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedStartDate")))
	        theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPreferedStartDate")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtPreferedEndDate")))
	        theDTO.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPreferedEndDate")));
        
         //�ط�����
         if (WebUtil.StringHaveContent(request.getParameter("txtCallBackStartDate")))
             theDTO.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCallBackStartDate")));
         if (WebUtil.StringHaveContent(request.getParameter("txtCallBackEndDate")))
             theDTO.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCallBackEndDate")));   
         
         //��������
         if (WebUtil.StringHaveContent(request.getParameter("txtType")))
             theDTO.setType(request.getParameter("txtType"));
         //��������2 (ԤԼ���� and �ŵ�����) add by chaoqiu ���ڿ����ط������� ԤԼ���� and �ŵ��������֣���ԭ��������������
         if (WebUtil.StringHaveContent(request.getParameter("txtType2")))
             theDTO.setSpareStr7(request.getParameter("txtType2"));

         //����״̬
         if (WebUtil.StringHaveContent(request.getParameter("txtStatus"))){
           theDTO.setStatus(request.getParameter("txtStatus"));
         } else{
           String txtstatus =CommonKeys.CUSTSERVICEINTERACTION_STATUS_SUCCESS +";"+CommonKeys.CUSTSERVICEINTERACTION_STATUS_FAIL;
           theDTO.setStatus(txtstatus);
         }
	}
	
	protected void specialSetDTO(HttpServletRequest request, CommonQueryConditionDTO theDTO)
	{
		
	}
	
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
    	
    	commonSetDTO(request, theDTO);
    	
    	specialSetDTO(request, theDTO);
    	
    	return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTSERVICEINTERACTION);
    }

}
