package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryRealIncomeWebAction extends QueryWebAction {

	CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		Map map = new LinkedHashMap();
        //SpareTime1:������ʼʱ��
		//SpareTime2:������ֹʱ��
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDatePart"))){
	        dto.setSpareTime1(WebUtil.StringToTimestampDefaultDayBegin(
	        		request.getParameter("txtCreateStartDatePart"),
					request.getParameter("txtCreateStartHourPart")
					));
	        map.put("�շ���ʼʱ��", 
	        		request.getParameter("txtCreateStartDatePart") +
					((request.getParameter("txtCreateStartHourPart") ==null || request.getParameter("txtCreateStartHourPart").equals("")) 
							             ? "" 
							             : "("+request.getParameter("txtCreateStartHourPart")+"ʱ)")
					);
	        
	    }
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtEndDatePart"))){
	        dto.setSpareTime2(WebUtil.StringToTimestampDefaultDayEnd(
	        		request.getParameter("txtEndDatePart"),
					request.getParameter("txtEndHourPart")
					));
	        map.put("�շѽ�ֹʱ��", 
	        		request.getParameter("txtEndDatePart") +
					((request.getParameter("txtEndHourPart") ==null || request.getParameter("txtEndHourPart").equals("")) 
							              ? "" 
							              : "("+request.getParameter("txtEndHourPart")+"ʱ)")
					);
	    }
		
		//SpareStr2:�ͻ�֤��
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID"))){
			map.put("�ͻ�֤��",request.getParameter("txtCustID"));
			dto.setSpareStr2(request.getParameter("txtCustID"));
		}
		//SpareStr3:�ʻ���
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID"))){
			map.put("�ʻ���",request.getParameter("txtAcctID"));
			dto.setSpareStr3(request.getParameter("txtAcctID"));
		}
        // SpareStr18:��������
		if(WebUtil.StringHaveContent(request.getParameter("txtFeeType"))){
			map.put("��������",Postern.getHashValueByNameKey("SET_F_BRFEETYPE",request.getParameter("txtFeeType")));
			dto.setSpareStr18(request.getParameter("txtFeeType"));
		}
		
        // SpareStr8:��Ŀ����
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctItemTypeID"))){
			map.put("��Ŀ����",Postern.getAllFeeType().get(request.getParameter("txtAcctItemTypeID")));
			dto.setSpareStr8(request.getParameter("txtAcctItemTypeID"));	
		}
			
        // SpareStr6:��֯���տ���֯��
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID"))){
			map.put("�շѻ���",Postern.getOrganizationDesc(WebUtil.StringToInt(request.getParameter("txtOrgID"))));
			dto.setSpareStr6(request.getParameter("txtOrgID"));
		}
        //  �տ���operatorid
	    if (WebUtil.StringHaveContent(request.getParameter("txtCollectorID"))){
	    	map.put("�տ���",request.getParameter("txtCollectorName"));
			dto.setSpareStr19(request.getParameter("txtCollectorID"));
	    }
			    
        //	  SpareStr4:�ͻ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerType"))){
			map.put("�ͻ�����", Postern.getHashValueByNameKey("SET_C_CUSTOMERTYPE",request.getParameter("txtCustomerType")));
			dto.setSpareStr4(request.getParameter("txtCustomerType"));
		}
		
        //	  SpareStr5:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID"))){
			map.put("�ͻ���������", request.getParameter("txtCountyDesc"));
			dto.setSpareStr5(request.getParameter("txtDistrictID"));
		}
		
		dto.setSpareStr7(request.getParameter("txtActionType"));
		
		//Ʊ�ݱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtTicketNo"))){
			dto.setSpareStr9(request.getParameter("txtTicketNo"));
		}
		
		request.setAttribute("RealIncomePrintInfo", map);
		
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_CASH_FLOW_COUNT);
	}

	
}
