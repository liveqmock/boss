package com.dtv.oss.web.taglib.html;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.jsp.JspException;
import java.util.*;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.*;
import com.dtv.oss.web.taglib.util.CommonUtils;
import com.dtv.oss.web.taglib.html.SelectTag;

public class SelectDeviceModelTag extends SelectTag {

     protected String deviceClass = null;

     public String getDeviceClass() {

          return deviceClass;
     }

     public void setDeviceClass(String pDeviceClass) {

         if(pDeviceClass!=null)
         {
             this.deviceClass = CommonUtils.GetBeanPropertyReturnString(pageContext, pDeviceClass);
             if(this.deviceClass==null)
             {
                  this.deviceClass = pageContext.getRequest().getParameter(pDeviceClass);
             }

             if(this.deviceClass==null)
             {
                 this.deviceClass = pDeviceClass;
             }

         }

     }

        public int doStartTag() throws JspException {
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");

            Map mapKeyValue = new HashMap();

            if (WebUtil.StringHaveContent(deviceClass))
            {
            	System.out.println("!!!!!!!!!"+deviceClass);
                Map map = Postern.getDeviceModelByClass(deviceClass);
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

            setSet("AllDeviceModelList_forStore_100");
            if (mapKeyValue!=null) pageContext.setAttribute("AllDeviceModelList_forStore_100", mapKeyValue);

            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

            return super.doStartTag();

        }

    /**
    * Release any acquired resources.
    */
    public void release() {
        super.release();
        deviceClass = null;
    }

}