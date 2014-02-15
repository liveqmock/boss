/*
 * Created on 2005-10-20
 * @author david.Yang
 */
package com.dtv.oss.web.taglib.html;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.WebPrint;

public class PrintCssTrTag extends BodyTagSupport {
    private String strCurNo ;
    private String style1 ="";
    private String style2 ="";
    private String overStyle ="";
       
    public String getStyle1(){
    	return style1;
    }
    
    public void setStyle1(String pStyle1){
    	style1 =pStyle1;
    }
    
    public String getStyle2(){
    	return style2;
    }
    
    public void setStyle2(String pStyle2){
    	style2 =pStyle2;
    }
    
    public String getOverStyle(){
    	return overStyle ;
    }
    
    public void setOverStyle(String pStyle3){
    	overStyle =pStyle3;
    }
    
    public int doStartTag() throws JspException {
		WebPrint.PrintTagDebugInfo(this.getClass().getName(),"invoke doStartTag...");
		strCurNo =(pageContext.getAttribute("itemNo_csstr100") ==null) ? "" :(String)pageContext.getAttribute("itemNo_csstr100");
		if (strCurNo.equals("")){
			strCurNo ="1";
			pageContext.setAttribute(("itemNo_csstr100"),strCurNo); 
		}
		
		int intCurNo = Integer.parseInt(strCurNo);
		pageContext.setAttribute(("itemNo_csstr100"),String.valueOf(intCurNo+1));
		String  result="";
		
		if (!style1.equals("") && !style2.equals("") && !overStyle.equals("")){
	       if (intCurNo % 2 ==0){
	       	 result ="<tr class=\""+ style2 +"\" onmouseover=\"this.className='" + overStyle+ "'\" onmouseout=\"this.className='"+style2+"' \" >";
		   } else {
		     result ="<tr class=\""+ style1 +"\" onmouseover=\"this.className='" + overStyle+ "'\" onmouseout=\"this.className='"+style1+"' \" >";
		   }
	       ResponseUtils.write(pageContext,result);
		   return (EVAL_BODY_BUFFERED);
        }
		
		if (!style1.equals("") && !style2.equals("")){
		   if (intCurNo % 2 ==0){
			   result ="<tr class=\""+ style2 +"\" >";
		   } else {
		       result ="<tr class=\""+ style1 +"\" >";
		   }
		   ResponseUtils.write(pageContext,result);
		   return (EVAL_BODY_BUFFERED);
		}
		
		if ((!style1.equals("") || !style2.equals("")) && !overStyle.equals("")){
			if (!style1.equals(""))  result ="<tr class=\""+ style1 +"\" onmouseover=\"this.className='" + overStyle+ "'\" onmouseout=\"this.className='"+style1+"' \" >";
		    if (!style2.equals(""))  result ="<tr class=\""+ style2 +"\" onmouseover=\"this.className='" + overStyle+ "'\" onmouseout=\"this.className='"+style2+"' \" >";
		    ResponseUtils.write(pageContext,result);
		    return (EVAL_BODY_BUFFERED);
		 }
		
		 if (!style1.equals("") || !style2.equals("")){
			if (!style1.equals("")) result ="<tr class=\""+ style1 +"\" >";
			if (!style2.equals("")) result ="<tr class=\""+ style2 +"\" >";
			ResponseUtils.write(pageContext,result);
			return (EVAL_BODY_BUFFERED);
		 }
		
		 if (style1.equals("") && style2.equals("")) {
			result ="<tr>";
		 }
		
		 ResponseUtils.write(pageContext,result);	
		 return (EVAL_BODY_BUFFERED);
    }
    
    public int doEndTag() throws JspException {
        WebPrint.PrintTagDebugInfo(this.getClass().getName(),"invoke doAfterBody ...");
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
        ResponseUtils.write(pageContext,"</tr>");
		return (SKIP_BODY);
	}
    /**
     * Release any acquired Resources
     */
    public void release(){
    	strCurNo =null;
        style1 =null;
        style2 =null;
        overStyle =null;
    }
}
