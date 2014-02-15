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
		
		//DTO封装参数说明
		//SpareStr1:小钱包序号 walletId
		//SpareStr2:支付方式
		//SpareStr3:设备序列号
		//SpareTime1:充值日期起始时间
		//SpareTime2:充值日期截止时间
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//*****************************************************************************
		//SpareStr1小钱包序号 walletId
		if(WebUtil.StringHaveContent(request.getParameter("txtWalletId")))
			dto.setSpareStr1(request.getParameter("txtWalletId"));
		//SpareStr2:支付方式
		if(WebUtil.StringHaveContent(request.getParameter("txtMopId"))){
			String mop=request.getParameter("txtMopId");
	        String mopid=mop.split("-")[0];
	        if(WebUtil.StringToInt(mopid)>0){
	        	dto.setSpareStr2(mopid);
	        }
		}
		//SpareStr3:设备序列号
		if(WebUtil.StringHaveContent(request.getParameter("txtScSerialNo")))
			dto.setSpareStr3(request.getParameter("txtScSerialNo"));
		//SpareTime1:充值日期起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:充值日期截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		
		//SpareStr1业务帐户ID
		if(WebUtil.StringHaveContent(request.getParameter("txtServiceAccountId")))
			dto.setSpareStr4(request.getParameter("txtServiceAccountId"));
		/*********************************************************************
		//SpareStr5:客户证号
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")) && (!"0".equals(request.getParameter("txtCustomerID")))){
			//进行格式检查
			if(WebUtil.StringToInt(request.getParameter("txtCustomerID"))==0)
				throw new WebActionException("客户证号，必须为整数！");
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
