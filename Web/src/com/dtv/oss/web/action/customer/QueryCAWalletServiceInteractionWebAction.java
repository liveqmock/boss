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
			
			//DTO封装参数说明
			//SpareStr1:业务帐户ID
			//SpareStr2:设备序列号
			//SpareStr3:小钱包序号 walletId
			//SpareStr4:小钱包名称

			
			CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
			
			//*************************************托收头记录查询****************************************
			//SpareStr1业务帐户ID
			if(WebUtil.StringHaveContent(request.getParameter("txtServiceAccountId")))
				dto.setSpareStr1(request.getParameter("txtServiceAccountId"));
			//SpareStr2:设备序列号
			if(WebUtil.StringHaveContent(request.getParameter("txtScSerialNo")))
				dto.setSpareStr2(request.getParameter("txtScSerialNo"));
			//SpareStr3:小钱包序号 walletId
			if(WebUtil.StringHaveContent(request.getParameter("txtWalletId")))
				dto.setSpareStr3(request.getParameter("txtWalletId"));
			//SpareStr4:小钱包代码
			if(WebUtil.StringHaveContent(request.getParameter("txtCaWalletCode")))
				dto.setSpareStr4(request.getParameter("txtCaWalletCode"));
			

			//SpareStr5:客户证号
			if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")) && (!"0".equals(request.getParameter("txtCustomerID")))){
				//进行格式检查
				if(WebUtil.StringToInt(request.getParameter("txtCustomerID"))==0)
					throw new WebActionException("客户证号，必须为整数！");
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
