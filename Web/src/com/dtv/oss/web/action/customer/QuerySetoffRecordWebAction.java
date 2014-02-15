package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * 销帐记录查询
 * author     ：Jason.Zhou 
 * date       : 2006-2-15
 * description:
 * @author 250713z
 *
 */
public class QuerySetoffRecordWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO封装参数说明
		//------------------用于销帐查询-------
		//SpareStr1:序号
		//SpareStr2:客户证号
		//SpareStr3:帐户号
		//SpareStr4:组织
		//SpareStr5:区域
		//SpareStr6:实收关联类型
		//SpareStr7:费用类型
		//SpareStr8:支付记录ID
		//SpareStr9:帐目类型
		//SpareStr10:费用记录ID
		
		//SpareTime1:销帐时间1
		//SpareTime2:销帐时间2
		//SpareTime3:支付时间1
		//SpareTime4:支付时间2
		//SpareTime5:费用创建时间1
		//SpareTime6:费用创建时间2

		//---------------用于销帐查询-------

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************用于所有销帐查询****************************************
		//SpareStr2:客户证号
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID")))
			dto.setSpareStr2(request.getParameter("txtCustID"));
		//SpareStr3:帐户号
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID")))
			dto.setSpareStr3(request.getParameter("txtAcctID"));
		//SpareStr4:组织
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr4(request.getParameter("txtOrgID"));
		//SpareStr5:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr5(request.getParameter("txtDistrictID"));
		//SpareStr6:实收关联类型
		if(WebUtil.StringHaveContent(request.getParameter("txtPlusReferType")))
			dto.setSpareStr6(request.getParameter("txtPlusReferType"));
		
		//SpareStr7:费用类型
    	if(WebUtil.StringHaveContent(request.getParameter("txtMinusReferType")))
		    dto.setSpareStr7(request.getParameter("txtMinusReferType"));
		
		//SpareStr8:支付记录ID
		if(WebUtil.StringHaveContent(request.getParameter("txtPlusReferID")))
			dto.setSpareStr8(request.getParameter("txtPlusReferID"));
		//SpareStr9:帐目类型
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctItemTypeID")))
			dto.setSpareStr9(request.getParameter("txtAcctItemTypeID"));
		//SpareStr10:费用记录ID
		if(WebUtil.StringHaveContent(request.getParameter("txtMinusReferID")))
			dto.setSpareStr10(request.getParameter("txtMinusReferID"));

		//SpareTime1:销帐时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:销帐时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		//SpareTime3:支付时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtPaymentTime1")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPaymentTime1")));
		//SpareTime4:支付时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtPaymentTime2")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPaymentTime2")));
		//SpareTime5:费用创建时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtFeeTime1")))
			dto.setSpareTime5(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtFeeTime1")));
		//SpareTime6:费用创建时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtFeeTime2")))
			dto.setSpareTime6(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtFeeTime2")));
		

		//****************************用于调帐查询*****************************************
		//SpareStr1:记录ID
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")) && (!"0".equals(request.getParameter("txtSeqNo")))){
			dto.setSpareStr1(request.getParameter("txtSeqNo"));
		}

		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_SETOFF_RECORD);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
