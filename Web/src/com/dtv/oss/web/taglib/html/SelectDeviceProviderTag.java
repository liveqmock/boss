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

public class SelectDeviceProviderTag extends SelectTag {

     protected String deviceModel = null;

     public String getDeviceModel() {

          return deviceModel;
     }

     public void setDeviceModel(String pDeviceModel) {

         if(pDeviceModel!=null)
         {
             this.deviceModel = CommonUtils.GetBeanPropertyReturnString(pageContext, pDeviceModel);
             if(this.deviceModel==null)
             {
                  this.deviceModel = pageContext.getRequest().getParameter(pDeviceModel);
             }

             if(this.deviceModel==null)
             {
                 this.deviceModel = pDeviceModel;
             }

         }

     }

        public int doStartTag() throws JspException {
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");

            Map mapKeyValue = new HashMap();

            if (WebUtil.StringHaveContent(deviceModel))
            {
                Map map = Postern.getDeviceProviderByModel(deviceModel);
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
        deviceModel = null;
    }

}