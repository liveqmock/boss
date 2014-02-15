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
import com.dtv.oss.web.taglib.html.SelectTag;

public class SelectDepotByOperTag extends SelectTag {

        public int doStartTag() throws JspException {

          Map mapKeyValue = null;

          //2004-12-3去掉仓库显示的限制
          mapKeyValue = Postern.getAllDepot();
          
          //如果是总公司操作员没有仓库限制
          //如果分公司或者街道站只能操作本分公司的仓库
          /*if (ScndCurrentOperator.isParentCompany(pageContext.getSession()))
          {
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "is Parent Company");
            mapKeyValue = Postern.getAllDepot();
          }
          else
          {
            int iOrgID = ScndCurrentOperator.getCurrentOperatorOrgID(pageContext.getSession());
            mapKeyValue = Postern.getDepotByOwnerID(iOrgID);
          }
          */

            setSet("AllDepotList_forStore_100");
            if (mapKeyValue!=null) pageContext.setAttribute("AllDepotList_forStore_100", mapKeyValue);

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