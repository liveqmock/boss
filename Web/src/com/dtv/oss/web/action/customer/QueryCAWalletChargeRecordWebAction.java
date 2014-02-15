package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryCAWalletChargeRecordWebAction extends QueryWebAction{

protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		//DTO��װ����˵��
		//SpareStr1:СǮ����� walletId
		//SpareStr2:֧����ʽ
		//SpareStr3:�豸���к�
		//SpareTime1:��ֵ������ʼʱ��
		//SpareTime2:��ֵ���ڽ�ֹʱ��
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//*****************************************************************************
		//SpareStr1СǮ����� walletId
		if(WebUtil.StringHaveContent(request.getParameter("txtWalletId")))
			dto.setSpareStr1(request.getParameter("txtWalletId"));
		//SpareStr2:֧����ʽ
		if(WebUtil.StringHaveContent(request.getParameter("txtMopId"))){
			String mop=request.getParameter("txtMopId");
	        String mopid=mop.split("-")[0];
	        if(WebUtil.StringToInt(mopid)>0){
	        	dto.setSpareStr2(mopid);
	        }
		}
		//SpareStr3:�豸���к�
		if(WebUtil.StringHaveContent(request.getParameter("txtScSerialNo")))
			dto.setSpareStr3(request.getParameter("txtScSerialNo"));
		//SpareTime1:��ֵ������ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:��ֵ���ڽ�ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		
		//SpareStr1ҵ���ʻ�ID
		if(WebUtil.StringHaveContent(request.getParameter("txtServiceAccountId")))
			dto.setSpareStr4(request.getParameter("txtServiceAccountId"));
		/*********************************************************************
		//SpareStr5:�ͻ�֤��
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")) && (!"0".equals(request.getParameter("txtCustomerID")))){
			//���и�ʽ���
			if(WebUtil.StringToInt(request.getParameter("txtCustomerID"))==0)
				throw new WebActionException("�ͻ�֤�ţ�����Ϊ������");
			dto.setSpareStr5(request.getParameter("txtCustomerID"));
		}
		**/
		
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_CA_WALLET_CHARGE);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
