package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.PackageLineDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;

public class ConfigPackageLineWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		
		ConfigProductEJBEvent event=new ConfigProductEJBEvent(); 
		
		 if("DeleteProduct".equals(request.getParameter("Action")))
		 	event.setActionType(EJBEvent.DELETE_PRODUCT);
     	 if("AddProduct".equals(request.getParameter("Action")))
     	 	event.setActionType(EJBEvent.ADD_PRODUCT);
     	 String packageID = request.getParameter("packageID");
         String productID = request.getParameter("productID"); 
         String selectProductID = request.getParameter("selectProductID"); 
       
         String existProductID = request.getParameter("existedProductID"); 
         String allProductIDs = productID+existProductID;
       
         List op2glist = new ArrayList();
         if (packageID != null && productID != null) {
             String[] operatorID_array = productID.split(";");
             if (operatorID_array.length == 0) {
                 throw new WebActionException ("当前页面没有数据！");
             }
             for (int i = 0; i < operatorID_array.length; i++) {
                 PackageLineDTO dto = new PackageLineDTO();
                 dto.setProductId(Integer.parseInt(operatorID_array[i]));
                 dto.setPackageId(Integer.parseInt(packageID));
                 if(selectProductID!=null && selectProductID.indexOf(operatorID_array[i])!=-1)
                 	 dto.setOptionFlag("Y");
                 else 
                 	dto.setOptionFlag("N");
                 
                 op2glist.add(dto);
             }
             operatorID_array = null;
         }
       
         
         event.setProductIDstr(allProductIDs); 
         packageID = null;
         productID = null;
       
        
         event.setPackageLineCol(op2glist);
         return event;
		 
	}

	 

}
