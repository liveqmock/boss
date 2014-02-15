package com.dtv.oss.web.taglib.html;

import java.util.Map;

import javax.servlet.jsp.JspTagException;
import com.dtv.oss.util.Postern;

import com.dtv.oss.web.util.WebPrint;
/**
 *
 * <p>Title: BOSS</p>
 * <p>Description: it will invoke ConfigurationData.getHashValueByNameKey and then output into webpage.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Digivision</p>
 * @author lhd
 * @version 1.0
 */
public class GetCommonNameTag extends GetNameTag {

  /**
   * typeName indicates the type of the code used in ConfigurationData.
   * For example, you want to get the name of the type of the customer by the key,
   * so its typeName is SET_C_CUSTOMERTYPE which also can be gained through ConfigurationData.CC_CUSTOMERTYPE.
   */
        protected String  typeName = null;

        public void setTypeName(String pTypeName)
        {
            typeName = pTypeName;
        }

        public String getTypeName()
        {
            return typeName;
        }




        public int doStartTag() throws JspTagException {

            try
            {
                value = Postern.getHashValueByNameKey(typeName, match);
                if(value==null || "".equals(value)){

                    Map map=(Map)pageContext.getAttribute(typeName);

                    if(map!=null && map.size()>0){

                        value=(String)map.get(match);

                        if(value==null)

                           value="";

                    }
                }

            }
            catch(Exception e)
            {
                WebPrint.PrintErrorInfo(this.getClass().getName(),  "doStartTag exception "+e.getMessage());
            }

            return super.doStartTag();
        }

        public void release() {

            super.release();
            typeName = null;

        }

}