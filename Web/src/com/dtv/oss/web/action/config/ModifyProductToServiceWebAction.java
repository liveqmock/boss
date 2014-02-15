package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductToServiceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: Chen jiang
 * Date: 2006-05-31
 * Time: 13:42:44
 * To change this template use File | Settings | File Templates.
 */
public class ModifyProductToServiceWebAction extends GeneralWebAction {
	 
     protected EJBEvent encapsulateData (HttpServletRequest request) throws WebActionException {
     	
     	ConfigProductEJBEvent ejbEvent = new ConfigProductEJBEvent();
     	  
     	 if("DeleteService".equals(request.getParameter("Action")))
     	 	ejbEvent.setActionType(EJBEvent.DEL_SERVICE);
     	 if("AddService".equals(request.getParameter("Action")))
     	 	ejbEvent.setActionType(EJBEvent.ADD_SERVICE);
     	 String serviceID = request.getParameter("serviceID");
         String productID = request.getParameter("productID");
         String productClass = request.getParameter("txtProdClass");
         String existedServiceID = request.getParameter("existedServiceID");
         int num = WebUtil.StringToInt(existedServiceID);
        
         List op2glist = new ArrayList();
       
          if (serviceID != null) {
            String[] serviceID_array = serviceID.split(";");
            
            if (serviceID_array.length == 0) {
                throw new WebActionException ("当前页面没有数据！");
            }
            if("S".equalsIgnoreCase(productClass)){
            	if( num ==0 && serviceID_array.length>1)
            		throw new WebActionException ("软件产品只能对应一个业务！");
            	 if(num ==1 && serviceID_array.length>0){
                	throw new WebActionException ("软件产品只能对应一个业务！");
                }
            }
           
            for (int i = 0; i < serviceID_array.length; i++) {
            	ProductToServiceDTO dto = new ProductToServiceDTO();
                dto.setServiceId(Integer.parseInt(serviceID_array[i]));
                dto.setProductId(Integer.parseInt(productID));
                op2glist.add(dto);
            }
            serviceID_array = null;
        }
        serviceID = null;
        productID = null;
      
       
        ejbEvent.setPackageLineCol(op2glist);
        return ejbEvent;
    }
}
