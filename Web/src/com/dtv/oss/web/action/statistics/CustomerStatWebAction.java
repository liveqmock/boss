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
 * �ͻ�ͳ��
 * author     ��Jason.Zhou 
 * date       : 2006-3-14
 * description:
 * @author 250713z
 *
 */
public class CustomerStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO��װ����˵��
		//------------------���ڿͻ���ͳ��-------
		//SpareStr1:ͳ�Ʒ�ʽ
		//SpareStr2:�ͻ�״̬
		//SpareStr3:������֯
		//SpareStr4:����
		//SpareStr5:��Դ����
		//SpareStr6:��Դ����ID
		//SpareTime1:����ʱ��1
		//SpareTime2:��������ʱ��2
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//��֯������ͬͬʱ��ѡ��
//		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")) && 
//				WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
//			throw new WebActionException("ͳ�Ʒ�ʽ����������֯��������ͬʱ��ѡ��");
//		//SpareStr1:ͳ�Ʒ�ʽ
//		if(WebUtil.StringHaveContent(request.getParameter("selStatType")))
//			dto.setSpareStr1(request.getParameter("selStatType"));
//		else
//			throw new WebActionException("ͳ�Ʒ�ʽ��������:ͳ�Ʒ�ʽ�ֶβ���Ϊ�գ�");
//		
//		//SpareStr2:�ͻ�״̬
//		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
//			dto.setSpareStr2(request.getParameter("txtStatus"));
//		//SpareStr3:������֯
//		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
//			dto.setSpareStr3(request.getParameter("txtOrgID"));	
		//SpareStr4:����
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr4(request.getParameter("txtDistrictID"));
		//SpareStr5:��Դ����
		if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
			dto.setSpareStr5(request.getParameter("txtOpenSourceType"));
		//SpareStr6:��Դ����ID
		if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceID")))
			dto.setSpareStr6(request.getParameter("txtOpenSourceID"));
	
		//SpareTime1:����ʱ��1
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:����ʱ��2
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));		
		
//		//  �ͻ��г���Ϣ
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
