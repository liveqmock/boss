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

import com.dtv.oss.web.util.*;
import com.dtv.oss.web.util.WebOperationUtil;
import com.dtv.oss.web.taglib.util.CommonUtils;
import com.dtv.oss.web.taglib.html.SelectTag;

import com.dtv.oss.web.util.CurrentLogonOperator;;

public class SelectOpenSourceIDTag extends SelectTag {

  //这里parentMatch指的是上一级下拉框选中的内容
  protected String parentMatch = null;

  public String getParentMatch() {

       return parentMatch;
  }

  public void setParentMatch(String parentMatch) {

      if(parentMatch!=null)
      {
          this.parentMatch = CommonUtils.GetBeanPropertyReturnString(pageContext, parentMatch);
          if(this.parentMatch==null)
          {
               this.parentMatch = pageContext.getRequest().getParameter(parentMatch);
          }

          if(this.parentMatch==null)
          {
              this.parentMatch = parentMatch;
          }

      }
  }

     public int doStartTag() throws JspException {
       Map mapKeyValue = null;
       if (WebUtil.StringHaveContent(parentMatch))
       {
         if (parentMatch.equals("P"))
         {
           //是代理商要形成二级实体
           int iOrgID = CurrentLogonOperator.getCurrentOperatorOrgID(pageContext.getSession());
           mapKeyValue = WebOperationUtil.getProxyByOrgID(iOrgID);
         }
       }
 
       setSet("AllOpenSourceIDList_forStore_100");
       if (mapKeyValue!=null) pageContext.setAttribute("AllOpenSourceIDList_forStore_100", mapKeyValue);

       WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

       return super.doStartTag();

     }

}