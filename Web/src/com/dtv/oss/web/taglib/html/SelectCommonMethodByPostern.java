package com.dtv.oss.web.taglib.html;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.CommonUtils;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

public class SelectCommonMethodByPostern extends SelectTag {
	 // 这里parentMatch指的是上一级下拉框选中的内容
	  protected String parentMatch ;
	  // 决定Postern中的Sql取值(必填)
	  protected String posternMethod  ;
	  
	  public int doStartTag() throws JspException {
          WebPrint.PrintTagDebugInfo(this.getClass().getName(), "SelectCommonMethodByPostern.doStartTag enter ...");
         
          Map mapKeyValue = new HashMap();

          if (WebUtil.StringHaveContent(parentMatch)){
          	  System.out.println("!!!!!!!!!"+parentMatch);
          	  Object obj = new Postern();
          	  Method[] methods = obj.getClass().getMethods();
              boolean matchFlag =false;
			  Object value =null ;
			  try{	  
          	     for (int i=0; i<methods.length && matchFlag==false; i++){
          	       if (posternMethod.equalsIgnoreCase(methods[i].getName())){
          	    	   String[] parameter ={parentMatch};
          	    	   value =methods[i].invoke(obj, parameter); 
          	    	   matchFlag =true;
					   if (value instanceof Map) {
					      Map map =(Map)value;
                          if (map!=null){
                             Iterator iterator = map.entrySet().iterator();
                             while(iterator.hasNext()){
                                Map.Entry item = (Map.Entry)iterator.next();
                                mapKeyValue.put(item.getKey(), item.getValue());
                             }
                          }
          	          }
          	       }
                }
			  }catch(Exception e){
				  e.printStackTrace();
				  throw new JspException (e.toString());
			  }
          }
          setSet("CommonMethodByPostern_forStore_100");
          if (mapKeyValue!=null) pageContext.setAttribute("CommonMethodByPostern_forStore_100", mapKeyValue);

          WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");
          
          return super.doStartTag();
	  }
	  
	  public String getParentMatch() {
	       return parentMatch;
	  }

	  public void setParentMatch(String parentMatch) {
	      if (parentMatch!=null){
	          this.parentMatch = CommonUtils.GetBeanPropertyReturnString(pageContext, parentMatch);
	          if(this.parentMatch==null){
	               this.parentMatch = pageContext.getRequest().getParameter(parentMatch);
	          }

	          if(this.parentMatch==null){
	              this.parentMatch = parentMatch;
	          }
	      }
	  }

	public String getPosternMethod() {
		return posternMethod;
	}

	public void setPosternMethod(String posternMethod) {
		this.posternMethod = posternMethod;
	}

	 
}
