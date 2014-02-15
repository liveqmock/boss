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

          //2004-12-3ȥ���ֿ���ʾ������
          mapKeyValue = Postern.getAllDepot();
          
          //������ܹ�˾����Աû�вֿ�����
          //����ֹ�˾���ֵ߽�վֻ�ܲ������ֹ�˾�Ĳֿ�
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