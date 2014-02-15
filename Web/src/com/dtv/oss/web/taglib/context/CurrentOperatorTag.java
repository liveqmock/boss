package com.dtv.oss.web.taglib.context;


import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.JspException;
import com.dtv.oss.web.taglib.util.ResponseUtils;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.*;
import com.dtv.oss.web.util.LogonWebCurrentOperator;

public class CurrentOperatorTag extends BodyTagSupport{
        private String property = null;

        public String getProperty(){
                return property;
        }
        public void setProperty(String pProperty){
                property = pProperty;
        }

        private String typeName = null;

        public String getTypeName(){
                return typeName;
        }
        public void setTypeName(String pTypeName){
                typeName = pTypeName;
        }


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

            if (WebUtil.StringHaveContent(typeName))
            {
                //如果是所属公司或者部门直接获取
                if ((typeName.compareToIgnoreCase("orgname") == 0))
                {
                    String strName="";
                    try{
                    	strName=Postern.getOrgNameByID(dtoOper.getOrgID());
                    }catch(Exception ex){
                    	ex.printStackTrace();
                	}
                    ResponseUtils.write(pageContext, strName);
                    return (SKIP_BODY);
                }
            }


            Object value = WebUtil.excuteMethod(dtoOper, WebUtil.Name2GetMethod(property));
            if (value==null)
            {
                WebPrint.PrintErrorInfo(this.getClass().getName(), "can not get property --"+property);
                return SKIP_BODY;
            }

            if (WebUtil.StringHaveContent(typeName))
            {
                if (value instanceof String) {
                        String strVal = (String) value;
                        value = Postern.getHashValueByNameKey(typeName, strVal);
                }
                else
                       WebPrint.PrintErrorInfo(this.getClass().getName(), "the value of typeName --"+typeName+" is invalid");

            }

            /*
            if (WebUtil.StringHaveContent(style))
            {
                            //bit0=1 denotes chars of return which will be erased
                            if ((WebUtil.StringToInt(style) & 1)!=0)
                              sVal = CommonUtils.RemoveAwayReturn(sVal);

            }*/

            ResponseUtils.write(pageContext, value.toString());

            return (SKIP_BODY);
        }


        public void release() {

            super.release();
            typeName = null;
            property = null;

        }

}