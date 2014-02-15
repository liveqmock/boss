/*
 * Created on 2005-9-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.web.taglib.html;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.CurrentOperator;
import com.dtv.oss.web.util.LogonWebCurrentOperator;
import com.dtv.oss.web.util.WebPrint;


/**
 * 仓库所有者的树,copy组织者树的代码,只改了map,
 *
 */
public class SelectOwnerTag extends SelectTag {
	
	 public int doStartTag() throws JspException {
	 	
	 	LogonWebCurrentOperator wrapOper = (LogonWebCurrentOperator)CurrentOperator.GetCurrentOperator(pageContext.getSession());
        if (wrapOper==null)
        {
            WebPrint.PrintErrorInfo(this.getClass().getName(), "current operator wrap is null");
            return SKIP_BODY;
        }

        OperatorDTO dtoOper = (OperatorDTO)wrapOper.getCurrentOperator();
        if (dtoOper==null)
        {
            WebPrint.PrintErrorInfo(this.getClass().getName(), "current operator dto is null");
            return SKIP_BODY;
        }
        
        Map mapKeyValue = new HashMap();
	 	
        mapKeyValue=Postern.getOwnerTreeByOrgID(dtoOper.getOrgID());

	 	WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");
        if (mapKeyValue==null)
            mapKeyValue = new HashMap();

        setSet("OWNERTREEFOR_ORG_IN_CB");
        if (mapKeyValue!=null) pageContext.setAttribute("OWNERTREEFOR_ORG_IN_CB", mapKeyValue);

        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

        return super.doStartTag();
	 }

}
