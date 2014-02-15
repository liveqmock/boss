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
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.taglib.pri.SelectWithPrivilegeTag;

public class SelectCommonDataWithPrivilegeTag extends SelectWithPrivilegeTag {
        protected String mapName = null;

        public String getMapName() {

            return mapName;
        }

        public void setMapName(String pMapName) {
            mapName = pMapName;
        }

        public int doStartTag() throws JspException {
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");

            if (mapName!=null)
            {
                String sPgName="";
                if (super.set!=null) sPgName=super.set;
                else
                {
                    sPgName=mapName+"_forStore_100";
                    setSet(sPgName);
                }

                Map map=Postern.getHashKeyValueByName(mapName);
                if (map!=null) pageContext.setAttribute(sPgName, map);

            }

            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

            return super.doStartTag();

        }

    /**
    * Release any acquired resources.
    */
    public void release() {

        super.release();
        mapName = null;

    }


}