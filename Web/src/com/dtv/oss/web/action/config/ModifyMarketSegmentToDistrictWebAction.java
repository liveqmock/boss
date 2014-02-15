package com.dtv.oss.web.action.config;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.MarketSegmentToDistrictDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigActivityEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2006-4-14
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyMarketSegmentToDistrictWebAction extends GeneralWebAction {

	boolean doPost = false;
	 
	protected boolean needCheckToken()
	{
		return doPost;
	}
	 
	 
	
   public void doStart(HttpServletRequest request)
	{
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm")))
		{
			if (request.getParameter("confirm").equalsIgnoreCase("true")) 
			{
				doPost = true;
				 
			 
			}
		}
		 
	 }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
     
        ConfigActivityEJBEvent event= new ConfigActivityEJBEvent();
        int segmentId=0;
        ArrayList dtoList = new ArrayList();
        ArrayList disList = new ArrayList();
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_DISTRICT_CREATE);
        if (WebUtil.StringHaveContent(request.getParameter("txtSegmentID")))
  	        segmentId=WebUtil.StringToInt(request.getParameter("txtSegmentID"));    
//	    String[] districtList=request.getParameterValues("DistrictList"); 
//	    String[] districtList1=request.getParameterValues("DistrictList1"); 
	    String[] lstForSel=request.getParameterValues("lstForSel"); 
	    
//   	  if ((districtList!=null)&&(districtList.length>0)){
//   	  	
//   	 	  for (int i=0; i<districtList.length; i++){
//   	 	       disList.add(districtList[i]);
//   	     }
//   	  }
//   	  if ((districtList1!=null)&&(districtList1.length>0)){
// 	 	  for (int j=0; j<districtList1.length; j++){
// 	 	     disList.add(districtList1[j]);
// 	 	 
// 	     }
// 	  }
   	  
   	if ((lstForSel!=null)&&(lstForSel.length>0)){
	 	  for (int j=0; j<lstForSel.length; j++){
	 	     disList.add(lstForSel[j]);
	 	 
	     }
	  }
   	  for(int m=0; m<disList.size();m++){
  	      MarketSegmentToDistrictDTO dto = new MarketSegmentToDistrictDTO();
   	 	  dto.setMarketSegmentId(segmentId);
   	  
   	 	  dto.setDistrictId(WebUtil.StringToInt(disList.get(m).toString()));
   	 	  dtoList.add(dto);
   	  }
	    
           event.setMarketSegmentToDistrictDtoCol(dtoList);
           event.setSegmentId(segmentId);
        return event;
    }
}
