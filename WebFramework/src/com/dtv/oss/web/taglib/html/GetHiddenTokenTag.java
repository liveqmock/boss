package com.dtv.oss.web.taglib.html;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;

import javax.servlet.jsp.PageContext;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.taglib.util.ResponseUtils;
/**
 *
 * <p>Title: BOSS</p>
 * <p>Description: it will invoke request.getAttribute(WebKeys.PAGE_TOKEN_KEY) and then output into webpage.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Digivision</p>
 * @author lhd
 * @version 1.0
 */

public class GetHiddenTokenTag extends TagSupport{

    public int doStartTag() throws JspTagException {

          try
          {

              String sVal = (String)pageContext.getSession().getAttribute(WebKeys.TRANSACTION_TOKEN_KEY);
              if (sVal != null)
                ResponseUtils.write(pageContext, "<input type=\"hidden\" name=\""+WebKeys.PAGE_TOKEN_KEY+"\" value=\""+sVal+"\">");
              else
                WebPrint.PrintTagDebugInfo("GetTokenTag", "without token in the attribute of session");

          }
          catch(Exception e)
          {
              WebPrint.PrintErrorInfo(this.getClass().getName(),  "doStartTag exception "+e.getMessage());
          }

          return SKIP_BODY;
      }

}