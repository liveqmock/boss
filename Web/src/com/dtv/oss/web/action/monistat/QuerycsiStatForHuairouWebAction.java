package com.dtv.oss.web.action.monistat;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.MonistatQueryEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.MonistatDataProcedure;

public class QuerycsiStatForHuairouWebAction extends QueryWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request)
	throws WebActionException {
		
		String txtStatBeginTime =request.getParameter("txtStatBeginTime");
		String txtStatEndTime =request.getParameter("txtStatEndTime");
		String txtDistrictID =request.getParameter("txtDistrictID");
		String batchStr ="";
		String actionSubmit =request.getParameter("txtAct");
		Collection precintId =Postern.getPrecinctIdColsByIds(txtDistrictID);
		String[] districtIds =txtDistrictID.split(",");
		String paramdistId ="";
		for (int i=0;i<districtIds.length; i++){
			if (!precintId.contains(districtIds[i])){
				if (paramdistId.equals("")){
					paramdistId =districtIds[i];
				} else{
					paramdistId =paramdistId+","+ districtIds[i];
				}
			}
		}

		if (actionSubmit.equals("query")){
			System.out.println("paramdistId----->"+paramdistId);
			batchStr = MonistatDataProcedure.csiInfoStatForHuairou(txtStatBeginTime,txtStatEndTime,paramdistId);
		}else{
			batchStr =request.getParameter("txtBatchStr");
		}
		request.setAttribute("txtBatchStr", batchStr);

		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
				
		theDTO.setSpareStr1(batchStr);
		
		theDTO.setSpareStr2(actionSubmit);
	   
	    return new MonistatQueryEJBEvent(theDTO, pageFrom, pageTo,
	    		   MonistatQueryEJBEvent.QUERY_CSI_STAT_FOR_HUAIROU);
	}

}
