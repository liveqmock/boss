package com.dtv.oss.web.taglib.html;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Product: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.jsp.JspException;
import java.util.*;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.taglib.html.SelectTag;

public class SelectAllProductTag extends SelectTag {
		  protected String deviceClass="";
		  protected String mapName = null;

	        public String getMapName() {

	            return mapName;
	        }

	        public void setMapName(String pMapName) {
	            mapName = pMapName;
	        }
		  public String getDeviceClass() {

               return deviceClass;
          }

          public void setDeviceClass(String pdeviceClass) {
              deviceClass = pdeviceClass;
          }
         
        public int doStartTag() throws JspException {
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");
            
            String sPgName="";
            if (super.set!=null) sPgName=super.set;
            else
            {
                sPgName="AllProductList_forStore_100";
                setSet(sPgName);
            }
            Map mapProductKeyValue = Postern.getProductDataCache(deviceClass);
            if (mapProductKeyValue!=null) pageContext.setAttribute(sPgName, mapProductKeyValue);
            
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");
            return super.doStartTag();
        }

    /**
    * Release any acquired resources.
    */
    public void release() {
        super.release();
    }

}