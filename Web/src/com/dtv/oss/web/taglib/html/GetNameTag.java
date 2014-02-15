package com.dtv.oss.web.taglib.html;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import com.dtv.oss.web.util.*;
import com.dtv.oss.web.taglib.util.*;

public class GetNameTag extends TagSupport{
   protected String value = null;

   public void setValue(String pValue)
   {
       value = pValue;
   }

   public String getValue()
   {
       return value;
   }

        /**
        * match store the key. You can get the value by the key.
        * and get the key before invoking doStartTag.
        */
       protected String match = null;

       public void setMatch(String match)
       {
         if(match!=null)
         {
            this.match = CommonUtils.GetBeanPropertyReturnString(pageContext,match);
            if(this.match==null)
            {
              this.match = pageContext.getRequest().getParameter(match);
            }

            if(this.match==null)
            {
              this.match = match;
            }

          }

       }

       public String getMatch()
       {
           return match;
       }

       public int doStartTag() throws JspTagException {

           try
           {
               if (value != null)
                 ResponseUtils.write(pageContext, value);

           }
           catch(Exception e)
           {
               WebPrint.PrintErrorInfo(this.getClass().getName(),  "doStartTag exception "+e.getMessage());
           }

           return SKIP_BODY;
       }

       public void release() {

           super.release();
           value = null;
           match = null;

       }

}