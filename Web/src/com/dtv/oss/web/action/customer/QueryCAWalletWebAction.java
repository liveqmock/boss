package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryCAWalletWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
			
			CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

			//SpareStr1业务帐户ID
			if(WebUtil.StringHaveContent(request.getParameter("txtServiceAccountId")))
				dto.setSpareStr1(request.getParameter("txtServiceAccountId"));
			System.out.println("*********************"+request.getParameter("txtServiceAccountId"));
			/*********************************************************************
			//SpareStr5:客户证号
			if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")) && (!"0".equals(request.getParameter("txtCustomerID")))){
				//进行格式检查
				if(WebUtil.StringToInt(request.getParameter("txtCustomerID"))==0)
					throw new WebActionException("客户证号，必须为整数！");
				dto.setSpareStr5(request.getParameter("txtCustomerID"));
			}
			**/
			
			return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_CA_WALLET);
			

		}
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			// TODO Auto-generated method stub

		}

		
		
	}
