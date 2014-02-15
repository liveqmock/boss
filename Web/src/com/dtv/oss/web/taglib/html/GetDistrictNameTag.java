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
import com.dtv.oss.util.Postern;

import com.dtv.oss.web.util.*;

public class GetDistrictNameTag extends GetNameTag {



        public int doStartTag() throws JspTagException {


            if (WebUtil.IsInt(match))
            {
              try
              {
                value = Postern.getDistrictNameByID(WebUtil.StringToInt(match));


              }
              catch(Exception e)
              {
                WebPrint.PrintErrorInfo(this.getClass().getName(),  "doStartTag exception "+e.getMessage());
              }
            }
            return super.doStartTag();
        }



}