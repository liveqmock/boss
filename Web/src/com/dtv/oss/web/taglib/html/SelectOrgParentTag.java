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
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.CurrentOperator;
import com.dtv.oss.web.util.LogonWebCurrentOperator;
import com.dtv.oss.web.util.WebPrint;


/**
 * 组织树选项
 * author     ：Jason.Zhou 
 * date       : 2006-2-9
 * description:
 * @author 250713z
 *
 */
public class SelectOrgParentTag extends SelectTag {
	
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
	 	
        mapKeyValue=Postern.getOrgParentTree(dtoOper.getOrgID());

	 	WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");
        if (mapKeyValue==null)
            mapKeyValue = new HashMap();

        setSet("AllORG_forStore_100");
        if (mapKeyValue!=null) pageContext.setAttribute("AllORG_forStore_100", mapKeyValue);

        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

        return super.doStartTag();
	 }

}
