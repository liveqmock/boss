package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.exception.WebActionException;

public class QueryCAWalletServiceInteractionWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
			
			//DTO��װ����˵��
			//SpareStr1:ҵ���ʻ�ID
			//SpareStr2:�豸���к�
			//SpareStr3:СǮ����� walletId
			//SpareStr4:СǮ������

			
			CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
			
			//*************************************����ͷ��¼��ѯ****************************************
			//SpareStr1ҵ���ʻ�ID
			if(WebUtil.StringHaveContent(request.getParameter("txtServiceAccountId")))
				dto.setSpareStr1(request.getParameter("txtServiceAccountId"));
			//SpareStr2:�豸���к�
			if(WebUtil.StringHaveContent(request.getParameter("txtScSerialNo")))
				dto.setSpareStr2(request.getParameter("txtScSerialNo"));
			//SpareStr3:СǮ����� walletId
			if(WebUtil.StringHaveContent(request.getParameter("txtWalletId")))
				dto.setSpareStr3(request.getParameter("txtWalletId"));
			//SpareStr4:СǮ������
			if(WebUtil.StringHaveContent(request.getParameter("txtCaWalletCode")))
				dto.setSpareStr4(request.getParameter("txtCaWalletCode"));
			

			//SpareStr5:�ͻ�֤��
			if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")) && (!"0".equals(request.getParameter("txtCustomerID")))){
				//���и�ʽ���
				if(WebUtil.StringToInt(request.getParameter("txtCustomerID"))==0)
					throw new WebActionException("�ͻ�֤�ţ�����Ϊ������");
				dto.setSpareStr5(request.getParameter("txtCustomerID"));
			}
			
			return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_CA_WALLET_Service_Interaction);
		}
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			// TODO Auto-generated method stub

		}

		
		
	}
