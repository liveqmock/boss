package com.dtv.oss.web.taglib.html;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;

import javax.servlet.jsp.PageContext;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.TokenProcessor;

/**
 *
 * <p>Title: BOSS</p>
 * <p>Description: it will generate token and invoke request.setAttribute(WebKeys.PAGE_TOKEN_KEY) and then output into webpage.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Digivision</p>
 * @author lhd
 * @version 1.0
 */

public class GenerateTokenTag extends GetHiddenTokenTag{

    public int doStartTag() throws JspTagException {

          TokenProcessor token = TokenProcessor.getInstance();
          HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
          token.saveToken(req);


          return super.doStartTag();
      }

}