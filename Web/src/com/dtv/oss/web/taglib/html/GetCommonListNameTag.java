package com.dtv.oss.web.taglib.html;

import javax.servlet.jsp.JspTagException;
import com.dtv.oss.util.Postern;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.taglib.util.CommonUtils;
/**
 *
 * <p>Title: BOSS</p>
 * <p>Description: it will invoke ConfigurationData.getHashValueByNameKey and then output into webpage.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Digivision</p>
 * @author lhd
 * @version 1.0
 */
public class GetCommonListNameTag extends GetNameTag {

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

        public void setMatch(String match)
        {
          if(match!=null)
          {
             this.match = CommonUtils.GetBeanPropertyReturnString(pageContext,match);
             if(this.match==null)
             {
               this.match = pageContext.getRequest().getParameter(match);
               	String[] currentParameter=pageContext.getRequest().getParameterValues(match);
                if(currentParameter!=null && currentParameter.length!=0){
                	for(int i=0;i<currentParameter.length;i++){
                		if(i==0){
                			this.match=currentParameter[i];
                		}else{
                			this.match=this.match+";"+currentParameter[i];
                		}
                	}
                }
             }
           }

        }
        public int doStartTag() throws JspTagException {

            try
            {
                value = Postern.getHashValueListByNameKey(typeName, match);
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