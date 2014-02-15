/*
 * Created on 2004-11-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.web.taglib.html;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import com.dtv.oss.tree.DynamicRootNode;
import com.dtv.oss.tree.GenerateTree;
import com.dtv.oss.web.taglib.util.ResponseUtils;


/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TreeWriteTag extends BodyTagSupport {

	private String property = null;

	public String getProperty(){
		return property;
	}
    public void setProperty(String pProperty){
    	property = pProperty;
    }

    private String treeName = null;

    public String getTreeName(){
            return this.treeName;
    }
    public void setTreeName(String treeName){
    	this.treeName = treeName;
    }
    private StringBuffer  treeMessage=new StringBuffer ();
    
    public int doStartTag() throws JspException {
    	DynamicRootNode dynamicRootNode=null;
    	Collection rootCol=null;
    	StringBuffer treeMessage=new StringBuffer();
    	String moduleName =(String)pageContext.getSession().getAttribute("SET_G_SYSTEMPLATFORM");
    	 
    	if(property!=null && !"".equals(property)){
    		dynamicRootNode = (DynamicRootNode)pageContext.getSession().getAttribute(property);
    	}
    	if(!"N".equalsIgnoreCase(moduleName)){
    	   
    		dynamicRootNode = (DynamicRootNode)pageContext.getSession().getAttribute("dynamicRootNode");
    		treeMessage=GenerateTree.generateDynamicTree(dynamicRootNode);
            ResponseUtils.write(pageContext, treeMessage.toString());
    	    
    	}else {
    	 
    		rootCol=(Collection) pageContext.getSession().getAttribute("dynamicRootNodecol");
    		 Iterator ite=rootCol.iterator();
    		 while (ite.hasNext()){
    		 	dynamicRootNode = (DynamicRootNode) ite.next();
    		 	treeMessage=GenerateTree.generateDynamicTree(dynamicRootNode);
    		 	ResponseUtils.write(pageContext, treeMessage.toString());
    		 	  
    		 	}
    	  		
    	 
    	}
    	
        return (SKIP_BODY);
    }
}
