package com.dtv.oss.web.taglib.logic;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.taglib.util.*;

/**
 * The tag is extended by GridTag.
 * It puts recordset which is from pageContext.getRequest() into collection.
 */

public class BossLoopTag extends LoopTag {

     /**
     * if the name of getlist method is not getPayload, Please invoke setListMethodName before invoking setRequestObjectName
     */
      private String listMethodName = "Payload";

        public String getListMethodName(){
                return listMethodName;
        }
        public void setListMethodName(String pName){
                listMethodName = pName;
        }

      /**
       * if requestObjectName is null, GridTag2 is identical with GridTag.
       */
        private String requestObjectName = null;

        public String getRequestObjectName(){
                return requestObjectName;
        }

        public void setRequestObjectName(String pName){
            requestObjectName = pName;
        }


        public int doStartTag() throws JspException {

            if (requestObjectName!=null)
            {
                Object RepCmd = pageContext.getRequest().getAttribute(requestObjectName);

                if (RepCmd != null)
                {
                    WebPrint.PrintTagDebugInfo(this.getClass().getName(), "RepCmd not null");

                    try {
                        collection = (Collection) WebUtil.excuteMethod(RepCmd,
                           WebUtil.Name2GetMethod(listMethodName));
                    }
                    catch (Exception e) {
                        WebPrint.PrintErrorInfo(this.getClass().getName(),
                                          "get invoke method getlist exception: " +
                                          e.getMessage());
                    }
                }
            }

            return super.doStartTag();

        }


    public void release() {

        listMethodName = "Payload";
        requestObjectName = null;

        super.release();

    }

}
