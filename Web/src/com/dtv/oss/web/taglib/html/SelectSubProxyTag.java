package com.dtv.oss.web.taglib.html;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.CommonUtils;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2005 </p>
 * <p> Company: Digivision</p>
 * User: thurm zhang
 * Date: 2005-6-10
 * Time: 15:37:22
 * To change this template use File | Settings | File Templates.
 */
public class SelectSubProxyTag extends SelectTag {
    //这里parentMatch指的是上一级下拉框选中的内容
    protected String parentMatch = null;
    
    public String getParentMatch() {

        return parentMatch;
   }

    public void setParentMatch(String parentMatch) {
        if (parentMatch != null) {
            this.parentMatch = CommonUtils.GetBeanPropertyReturnString(pageContext, parentMatch);
            if (this.parentMatch == null) {
                this.parentMatch = pageContext.getRequest().getParameter(parentMatch);
            }
            if (this.parentMatch == null) {
                this.parentMatch = parentMatch;
            }
        }
    }
  
    public int doStartTag() throws JspException {
        
            System.out.println("the parent company is " + parentMatch);
            WebPrint.PrintTagDebugInfo(parentMatch, "is Parent Company");

            Map mapKeyValue = new HashMap();

            if ((parentMatch != null) && WebUtil.IsInt(parentMatch)) {
            	
               Map map = Postern.getProxyOrg(WebUtil.StringToInt(parentMatch));
                if (map!=null)
                {
                  Iterator iterator = map.entrySet().iterator();
                  while(iterator.hasNext())
                  {
                    Map.Entry item = (Map.Entry)iterator.next();
                    
                    mapKeyValue.put(item.getKey(), item.getValue());
                  }
                }

                
            }

            setSet("AllSSAndDptList_forStore_100");
            if (mapKeyValue != null) pageContext.setAttribute("AllSSAndDptList_forStore_100", mapKeyValue);

            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

            return super.doStartTag();
        
          

        }

      

    public void release() {
        super.release();
        parentMatch = null;
    }
}
