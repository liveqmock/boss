package com.dtv.oss.web.action.monistat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.MonistatQueryEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.util.WebQueryUtility;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryCustomerInfoWebAction extends QueryWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request)
	throws WebActionException {
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		int districtID =WebUtil.StringToInt(request.getParameter("txtDistrictID"));
		Map reportMp = new LinkedHashMap();
		Map distTreeMap =Postern.getDistrictSettingForCreateTree();
		int hrdistinctId =(Postern.getSystemSettingValue("SET_HRDISTICNTID_FOR_STATIC")==null) ? -2 :Integer.parseInt(Postern.getSystemSettingValue("SET_HRDISTICNTID_FOR_STATIC"));
		int yqdistinctId =(Postern.getSystemSettingValue("SET_YQDISTINCTID_FOR_STATIC")==null) ? -3 :Integer.parseInt(Postern.getSystemSettingValue("SET_YQDISTINCTID_FOR_STATIC"));

		ArrayList yqCol =(ArrayList)distTreeMap.get(new Integer(yqdistinctId));
		ArrayList hrCol =(ArrayList)distTreeMap.get(new Integer(hrdistinctId));
		   
		if ((yqCol !=null &&  yqCol.contains(new Integer(districtID))) || districtID ==yqdistinctId){
		     reportMp= WebQueryUtility.getReportCustomerInfo(yqdistinctId);   
		} else if ((hrCol !=null && hrCol.contains(new Integer(districtID))) || districtID ==hrdistinctId){
		     reportMp= WebQueryUtility.getReportCustomerInfo(hrdistinctId); 
	    }
		theDTO.setReportMap(reportMp);
		theDTO.setDistrict(districtID);
		return new MonistatQueryEJBEvent(theDTO, pageFrom, pageTo,
	    		   MonistatQueryEJBEvent.QUERY_CUSTOMER_INFO_STAT);
	}
}
