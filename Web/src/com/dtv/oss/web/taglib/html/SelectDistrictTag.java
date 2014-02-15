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
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SelectDistrictTag extends SelectTag {
	
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
	 	
        mapKeyValue=Postern.getChargeDistrictSettingByOpOrgID(dtoOper.getOrgID());

	 	WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");
        if (mapKeyValue==null)
            mapKeyValue = new HashMap();

        setSet("AllDistrictList_forStore_100");
        if (mapKeyValue!=null) pageContext.setAttribute("AllDistrictList_forStore_100", mapKeyValue);

        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

        return super.doStartTag();
	 }

}
