package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.QueryCustDepositEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.CommonKeys;

public class QueryCustDepositWebAction extends QueryWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		//DTO封装参数说明
		//------------------用于客户押金查询-------
		//SpareStr1:账单号
		//SpareStr2:受理单号
		//SpareStr4:客户证号
		//SpareStr5:帐目类型
		//SpareTime1:创建起始时间
		//SpareTime2:创建截止时间
		//---------------结束用于客户押金查询-------

		//------------用于客户押金结果集的查询-----
		//SpareStr3:费用记录流水号
		//---------结束用于客户押金结果集的查询-----

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************用于客户押金查询****************************************
		//SpareStr1:账单号
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID")) && (!"0".equals(request.getParameter("txtAcctID")))){
			//进行格式检查
			if(WebUtil.StringToInt(request.getParameter("txtAcctID"))==0)
				throw new WebActionException("账单号格式错误，必须为整数！");
			dto.setSpareStr1(request.getParameter("txtAcctID"));
		}
		//SpareStr2:受理单号
		if(WebUtil.StringHaveContent(request.getParameter("txtCSIID")) && (!"0".equals(request.getParameter("txtCSIID")))){
			//进行格式检查
			if(WebUtil.StringToInt(request.getParameter("txtCSIID"))==0)
				throw new WebActionException("受理单号格式错误，必须为整数！");
			dto.setSpareStr2(request.getParameter("txtCSIID"));
		}
		//SpareStr4:客户证号
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")) && (!"0".equals(request.getParameter("txtCustomerID")))){
			//进行格式检查
			if(WebUtil.StringToInt(request.getParameter("txtCustomerID"))==0)
				throw new WebActionException("客户证号格式错误，必须为整数！");
			dto.setSpareStr4(request.getParameter("txtCustomerID"));
		}
		//SpareStr5:帐目类型
		dto.setSpareStr5(Postern.getAcctItemType());
		//SpareStr6:状态(有效)
		dto.setSpareStr6(CommonKeys.SET_F_FTSTATUS_V);
		//SpareTime1:创建起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:创建截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		//****************************用于客户押金结果集的查询*****************************************
		//SpareStr3:费用记录流水号
		if(WebUtil.StringHaveContent(request.getParameter("txtAiNo")) && (!"0".equals(request.getParameter("txtAiNo")))){
			//进行格式检查
			if(WebUtil.StringToInt(request.getParameter("txtAiNo"))==0)
				throw new WebActionException("费用记录流水号格式错误，必须为整数！");
			dto.setSpareStr3(request.getParameter("txtAiNo"));
		}
	    return new QueryCustDepositEJBEvent(dto,pageFrom,pageTo,0);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
