package com.dtv.oss.web.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.tree.DynamicRootNode;
import com.dtv.oss.tree.DynamicTreeNode;
import com.dtv.oss.util.Postern;
import com.dtv.oss.util.TreePostern;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CurrentCustomer;

public class ClearCurCustomerWebAction extends WebAction {

    public EJBEvent perform(HttpServletRequest request) throws WebActionException {
	    CurrentCustomer.ClearCurrentCustomer(request);
	    
	    if(request.getParameter("txtSystemModule")!=null && !"".equals(request.getParameter("txtSystemModule"))){
	    	request.getSession().setAttribute("SET_G_SYSTEMPLATFORM",request.getParameter("txtSystemModule"));
	    }
	    if("N".equalsIgnoreCase(request.getParameter("txtSystemModule"))){
	    	  Collection col=Postern.getNetInterfaceType("SET_S_INTERFACEUSED");
	    	  if(col==null || col.isEmpty()==true){
	    	  	 throw new WebActionException("没有配置可用的公用数据");
	    	  }
	    	  Iterator ite1 =col.iterator();
	    	  ArrayList maplist= new ArrayList();
	    	  
	    	   while(ite1.hasNext()){
	    			Map.Entry entry=(Map.Entry) ite1.next();
	    			System.out.println("*****entry**********"+entry.getKey());
	    			maplist.add(entry);
	    	   }
	    	  
	    	  Collection treeNodecol = TreePostern.initRootNode(1,maplist); 
	    	  Collection rootNodecol = new ArrayList();
	    	  Iterator ite=treeNodecol.iterator();
	  		
	  		  while (ite.hasNext()){
	  		     Map RootMap = new LinkedHashMap();
	  		     DynamicRootNode dynamicRootNode =new DynamicRootNode();
	  		     dynamicRootNode.setCurrentClickDeep(1);
	  			 DynamicTreeNode  interfaceNode= (DynamicTreeNode) ite.next();
	  			 RootMap.put(interfaceNode.getCurrentNode().getKey(),interfaceNode);
	  			 dynamicRootNode.setNodeMap(RootMap);
	  			 rootNodecol.add(dynamicRootNode);
	  			 
	  		  }
			 
	  		  request.getSession().setAttribute("dynamicRootNodecol" , rootNodecol);
			
			 
	    	 }
	    
	  
	   	
        return null;
    }
    public void doEnd(HttpServletRequest request, CommandResponse cmdResponse)  
    {
    	
    
    } 
}