package com.dtv.oss.web.taglib.html;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;

import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.taglib.util.RequestUtils;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

public class GenerateArrayItemTag extends BodyTagSupport{
        protected String name = null;

        public String getName(){
                return name;
        }
        public void setName(String pName){
                name = pName;
        }

        protected String keyProperty = null;

        public String getKeyProperty(){
                return keyProperty;
        }
        public void setKeyProperty(String pKeyProperty){
                keyProperty = pKeyProperty;
        }

        protected String valueProperty = null;

        public String getValueProperty(){
                return valueProperty;
        }
        public void setValueProperty(String pValueProperty){
                valueProperty = pValueProperty;
        }

        /**
         * The scope to search for the bean named by the name property, or
         * "any scope" if null.
         */
        protected String scope = null;

        public String getScope() {
            return (this.scope);
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        protected String indexList = null;

        public String getIndexList(){
                return indexList;
        }
        public void setIndexList(String pIndexList){
                indexList = pIndexList;
        }

        protected String arrayName = "Level";

        public String getArrayName(){
                return arrayName;
        }
        public void setArrayName(String pArrayName){
                arrayName = pArrayName;
        }

        public int doStartTag() throws JspException {
            //assume name keyProperty valueProperty indexList has a real value.
            if (indexList == null)
            {
                WebPrint.PrintErrorInfo("GenerateArrayTag", "indexList is null");
                return (SKIP_BODY);
            }


            Object objSource = RequestUtils.lookup(pageContext, name, scope);
            Object objKey = null;
            Object objValue = null;

            String[] index = indexList.split("/");

            if (objSource == null) {
                WebPrint.PrintErrorInfo(this.getClass().getName(),
                                    "Attribute " + name + " does not exist!");
                return (SKIP_BODY);
            }

            try
            {
                objKey = WebUtil.excuteMethod(objSource,
                                          WebUtil.Name2GetMethod(keyProperty));
                objValue = WebUtil.excuteMethod(objSource,
                                            WebUtil.Name2GetMethod(valueProperty));
            }
            catch (Exception e) {
                WebPrint.PrintErrorInfo("GenerateArrayTag", e.getMessage());
            }

            StringBuffer optKey          = new StringBuffer();
            StringBuffer optValue        = new StringBuffer();

            optKey.append(arrayName);
            optValue.append(arrayName);
            optKey.append(index.length);
            optValue.append(index.length);
            optKey.append("Key");
            optValue.append("Value");

            for(int i=0;i<index.length;i++)
            {
                 optKey.append("[");
                 optValue.append("[");
                 String curIdx =  (String)pageContext.getAttribute(index[i]);
                 optKey.append(curIdx);
                 optValue.append(curIdx);
                 optKey.append("]");
                 optValue.append("]");
            }

            optKey.append("=[\"");
            optValue.append("=[\"");
            optKey.append(objKey.toString());
            optValue.append(objValue.toString());
            optKey.append("\"];");
            optValue.append("\"];");

            ResponseUtils.write(pageContext, optKey.toString()+"\n");
            ResponseUtils.write(pageContext, optValue.toString()+"\n");

            return (EVAL_BODY_BUFFERED);
        }



    public void release() {

        super.release();
        name = null;
        keyProperty = null;
        valueProperty = null;
        scope = null;
        indexList = null;
        arrayName = "Level";
    }

}