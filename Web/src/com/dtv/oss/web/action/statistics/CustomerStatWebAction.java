package com.dtv.oss.web.action.statistics;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * 客户统计
 * author     ：Jason.Zhou 
 * date       : 2006-3-14
 * description:
 * @author 250713z
 *
 */
public class CustomerStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO封装参数说明
		//------------------用于客户数统计-------
		//SpareStr1:统计方式
		//SpareStr2:客户状态
		//SpareStr3:所属组织
		//SpareStr4:区域
		//SpareStr5:来源渠道
		//SpareStr6:来源渠道ID
		//SpareTime1:开户时间1
		//SpareTime2:开户创建时间2
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//组织和区域不同同时被选择
//		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
//				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
//			throw new WebActionException("统计方式参数错误：组织和区域不能同时被选择！");
//		//SpareStr1:统计方式
//		if(WebUtil.StringHaveContent(request.getParameter("selStatType")))
//			dto.setSpareStr1(request.getParameter("selStatType"));
//		else
//			throw new WebActionException("统计方式参数错误:统计方式字段不能为空！");
//		
//		//SpareStr2:客户状态
//		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
//			dto.setSpareStr2(request.getParameter("txtStatus"));
//		//SpareStr3:所属组织
//		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
//			dto.setSpareStr3(request.getParameter("txtOrgID"));	
		//SpareStr4:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
		//SpareStr5:来源渠道
		if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
			dto.setSpareStr5(request.getParameter("txtOpenSourceType"));
		//SpareStr6:来源渠道ID
		if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceID")))
			dto.setSpareStr6(request.getParameter("txtOpenSourceID"));
	
		//SpareTime1:创建时间1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:创建时间2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));		
		
//		//  客户市场信息
//        dto.setSpareStr19(getCustIdByMarketInfo(request));
	      
		return new StatQueryEJBEvent(dto,pageFrom,pageTo,StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMER);
	}
	
//	private String getCustIdByMarketInfo(HttpServletRequest request){
//		String marketId =(request.getParameter("txtDynamicServey")==null) ? "" : request.getParameter("txtDynamicServey");
//		String[] maketIds =marketId.split(";");
//		String customerIds ="";
//		boolean custMarketFlag =false;
//		for (int i=0 ;i<maketIds.length; i++){
//		   String marketInfoHiddenName ="txtDynamicServey"+"_"+maketIds[i];	  
//		   String[] marketInfoHiddenValues =request.getParameterValues(marketInfoHiddenName);
//		   if (marketInfoHiddenValues !=null){
//			  for (int j=0;j<marketInfoHiddenValues.length; j++){
//				 String marketInfoHiddenValue = marketInfoHiddenValues[j];
//				 if (marketInfoHiddenValue !=null && !marketInfoHiddenValue.equals("")){
//				    customerIds =Postern.getCustomerIdsBycustomerMarketInfo(WebUtil.StringToInt(maketIds[i]),WebUtil.StringToInt(marketInfoHiddenValues[j]),customerIds);
//				    custMarketFlag =true;
//				 }
//			  } 
//		   }
//		}
//		System.out.println("customerIds==========="+customerIds);
//
//		if (custMarketFlag && customerIds.equals("")) customerIds ="-1";
//		return customerIds;
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
