package com.dtv.oss.web.taglib.html;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.WebPrint;

public class SelectSubcompanyTag extends SelectTag {

          protected String styleClassName = "textgray";

    protected String includeParent = "false";

          public String getStyleClassName() {

               return styleClassName;
          }

          public void setStyleClassName(String pStyleClassName) {
              styleClassName = pStyleClassName;
          }

    public String getIncludeParent() {
        return includeParent;
    }

    public void setIncludeParent(String includeParent) {
        this.includeParent = includeParent;
    }

        public int doStartTag() throws JspException {
          
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "is Parent Company");
            Map mapSubcompanyKeyValue = new HashMap();

            Map mapSubcompany = Postern.getAllFiliales();
              if ("true".equals(includeParent)) {
                  mapSubcompany.putAll(Postern.getParentCompanys()) ;
              }
            Collection lst = mapSubcompany.values();
            Iterator itSubcompany = lst.iterator();
            while (itSubcompany.hasNext())
            {
                OrganizationDTO dtoSubcompany = (OrganizationDTO)itSubcompany.next();
                System.out.println(dtoSubcompany.getOrgID()+";"+dtoSubcompany.getOrgName());
                mapSubcompanyKeyValue.put(String.valueOf(dtoSubcompany.getOrgID()), dtoSubcompany.getOrgName());
            }

            setSet("AllSubcompanyList_forStore_100");
            if (mapSubcompanyKeyValue!=null) pageContext.setAttribute("AllSubcompanyList_forStore_100", mapSubcompanyKeyValue);

            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

            return super.doStartTag();
          
          
          }
        

    /**
    * Release any acquired resources.
    */
    public void release() {

        super.release();
        if ((styleClassName==null)||(!styleClassName.equals("textgray")))
          styleClassName = "textgray";
    }

}