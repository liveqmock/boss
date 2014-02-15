package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ServiceAccountDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * ˵���������ն��豸
 * @author 250713z
 *
 */
public class QueryTerminateDeviceWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CommonQueryConditionDTO dto=new CommonQueryConditionDTO();
		
		//����ѯ������ֵ��������������Ϊ���豸�ͺš���ʼ���кš���ֹ���кš��ֿ�ID���豸����
		//SpareStr10:Mac��ַ  
		//SpareStr11:�ڲ�Mac��ַ
		if(WebUtil.StringHaveContent(request.getParameter("txtDeviceModel"))){
			dto.setSpareStr1(request.getParameter("txtDeviceModel"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtSerialNoBegin"))){
			dto.setBeginStr(request.getParameter("txtSerialNoBegin"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtSerialNoEnd"))){
			dto.setEndStr(request.getParameter("txtSerialNoEnd"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtDepotID"))){
			dto.setSpareStr2(request.getParameter("txtDepotID"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtDeviceClass"))){
			dto.setSpareStr3(request.getParameter("txtDeviceClass"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus"))){
			dto.setSpareStr4(request.getParameter("txtStatus"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtFlag"))){
			dto.setSpareStr5(request.getParameter("txtFlag"));
		}
		//����ԭ��
		if (WebUtil.StringHaveContent(request.getParameter("txtCsiType"))){
			dto.setSpareStr6(request.getParameter("txtCsiType"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason"))){
			dto.setSpareStr7(request.getParameter("txtCsiCreateReason"));
		}
		//add by david.Yang
		if (WebUtil.StringHaveContent(request.getParameter("txtMatchFlag"))){
			dto.setSpareStr8(request.getParameter("txtMatchFlag"));
		}
		
		//add by jason 2006-7-9
		if (WebUtil.StringHaveContent(request.getParameter("txtMacAddress")))
			dto.setSpareStr10(request.getParameter("txtMacAddress"));
		if (WebUtil.StringHaveContent(request.getParameter("txtInterMacAddress")))
			dto.setSpareStr11(request.getParameter("txtInterMacAddress"));
		
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))){
			dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID"))); 
		} 
		if(!WebUtil.StringHaveContent(request.getParameter("txtDeviceModel"))
				&& !WebUtil.StringHaveContent(request.getParameter("txtSerialNoBegin"))
				&& !WebUtil.StringHaveContent(request.getParameter("txtDeviceClass"))
				&& WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))){
			 
			 ServiceAccountDTO theDTO = new ServiceAccountDTO();
			 
		     theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID"))); 
		     theDTO.setDescription(request.getParameter("txtFlag"));
		     return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_SERVICE_ACCOUNT_BY_CUST);
		}
		
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_TERMINALDEVICE);
	}

}
