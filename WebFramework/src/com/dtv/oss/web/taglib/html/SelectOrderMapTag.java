package com.dtv.oss.web.taglib.html;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.*;
import com.dtv.oss.web.util.*;

import com.dtv.oss.web.view.TableResource;

public class SelectOrderMapTag extends SelectTag{

        protected String tableName = null;

        public String getTableName() {

            return tableName;
        }

        public void setTableName(String pTableName) {
            tableName = pTableName;
        }

        public int doStartTag() throws JspException {
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");

            if (tableName!=null)
            {
                String sPgName="";
                if (super.set!=null) sPgName=super.set;
                else
                {
                    sPgName=tableName+"_forOrder_100";
                    setSet(sPgName);
                }

                Map map=null;

                HashMap mapTable = (HashMap)pageContext.getServletContext().getAttribute(WebKeys.TABLE_DEFINITION);
                if (mapTable!=null)
                {
                    TableResource tr=(TableResource)mapTable.get(tableName);
                    if (tr!=null)
                    {
                        map = tr.getFields();
                    }
                }

                if (map!=null) pageContext.setAttribute(sPgName, map);
            }

            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

            return super.doStartTag();

        }

    /**
    * Release any acquired resources.
    */
    public void release() {

        super.release();
        tableName = null;

    }

}