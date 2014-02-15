package com.dtv.oss.web.taglib.pri;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.*;

import com.dtv.oss.web.util.*;
import com.dtv.oss.web.taglib.util.CommonUtils;
import com.dtv.oss.web.taglib.html.SelectTag;
import com.dtv.oss.web.util.PrivilegeUtil;

public class SelectWithPrivilegeTag extends SelectTag{
        protected String dataTypeName = null;

        public String getDataTypeName() {
            return dataTypeName;
        }

        public void setDataTypeName(String pDataTypeName) {
            dataTypeName = pDataTypeName;
        }


        public int doStartTag() throws JspException {

            //if dataTypeName is not defined, SelectWithPrivilegeTag equals SelectTag
            if (WebUtil.StringHaveContent(dataTypeName))
            {
              Object optSet = CommonUtils.GetBeanProperty(pageContext,set);

              if(optSet instanceof Collection)
              {
                ArrayList newlst = new ArrayList();
                Collection lst = (Collection)optSet;
                Iterator iterator = lst.iterator();
                while(iterator.hasNext())
                {
                        Object item = iterator.next();
                        if (PrivilegeUtil.isAuthorized(pageContext.getSession(), dataTypeName + ":" + item.toString()))
                        newlst.add(item);

                }
                //re set the list
                pageContext.setAttribute(set, newlst, pageContext.getAttributesScope(set));
              }
              else if(optSet instanceof Map)
              {
                Map newmap = new HashMap();
                Map map = (Map)optSet;
                Iterator iterator = map.entrySet().iterator();
                while(iterator.hasNext())
                {
                  Map.Entry item = (Map.Entry)iterator.next();
                  if (PrivilegeUtil.isAuthorized(pageContext.getSession(), dataTypeName + ":" + item.getKey().toString()))
                    newmap.put(item.getKey(),item.getValue());
                }

                //re set the map
                pageContext.setAttribute(set, newmap, pageContext.getAttributesScope(set));
              }
            }

            return super.doStartTag();

        }

    /**
    * Release any acquired resources.
    */
    public void release() {

        super.release();
        dataTypeName = null;
    }

}