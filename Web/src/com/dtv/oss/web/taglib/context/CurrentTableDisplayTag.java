package com.dtv.oss.web.taglib.context;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.WebPrint;

public class CurrentTableDisplayTag extends BodyTagSupport{
	private String sourceTableId =null;
	private String objectTableId =null;
	private String sourceTd =null;
	private String objectTd =null;
	protected String  noSpanClass="false";
	
	

	public String getNoSpanClass() {
		return noSpanClass;
	}

	public void setNoSpanClass(String pVal) {
		 if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
			 noSpanClass="true";
	     else
	         noSpanClass="false";
	}

	public void setSourceTableId(String pSourceTableId){
		sourceTableId =pSourceTableId;
	}
	
	public String getSourceTableId(){
		return sourceTableId;
	}
	
	public void setObjectTableId(String pObjectTableId){
		objectTableId =pObjectTableId;
	}
	
	public String getObjectTableId(){
		return objectTableId;
	}
	
	public void setSourceTd(String pSourceTd){
		sourceTd =pSourceTd;
	}
	
	public String getSourceTd(){
		return sourceTd;
	}
	
	public void setObjectTd(String pObjectTd){
		objectTd =pObjectTd;
	}
	
	public String getObjectTd(){
		return objectTd;
	}
	
	public int doStartTag() throws JspException {
	   WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");
	   String displayValue ="";
	   String tdSpanClass ="";
	   if (sourceTableId !=null && !sourceTableId.equals(objectTableId)){
		   displayValue ="display:none";
	   }
	   
	   if (sourceTableId !=null && objectTableId !=null)
	       ResponseUtils.write(pageContext, displayValue);
	   
	   if (noSpanClass.equals("true")){
		   tdSpanClass ="";
	   } else{
		   tdSpanClass ="submenu2";
	   }
	   
	   if (sourceTd !=null && sourceTd.equals(objectTd)){
		   tdSpanClass ="spanClass";
	   }
	   
	   if (sourceTd !=null && objectTd !=null ){
		   ResponseUtils.write(pageContext, "<span class=\""+tdSpanClass+"\"> ");
	   }
	   
	   return (EVAL_BODY_BUFFERED);
	}
	
	public int doEndTag() throws JspException {
	   if (sourceTd !=null && objectTd !=null){
		   bodyContent =getBodyContent();
	        if (bodyContent != null)
			{
	        	 try {
		            bodyContent.writeOut(bodyContent.getEnclosingWriter());		        
		         } catch(IOException e){
		        		WebPrint.PrintErrorInfo(this.getClass().getName(), "JspWriter error:"+e.getMessage());
			     }         	
		         bodyContent.clearBody();
			}
		   ResponseUtils.write(pageContext,"</span>"); 
	   }
	   return (SKIP_BODY);
	}
	
	public void release() { 
		 sourceTableId =null;
		 objectTableId =null;
		 sourceTd =null;
		 objectTd =null;
	 }

}
