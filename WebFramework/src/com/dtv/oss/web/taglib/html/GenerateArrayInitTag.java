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

public class GenerateArrayInitTag extends BodyTagSupport{
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

      protected String levelAmount = null;

      public String getLevelAmount(){
              return levelAmount;
      }
      public void setLevelAmount(String pLevelAmount){
              levelAmount = pLevelAmount;
      }


      public int doStartTag() throws JspException {
            //assume LevelAmount has a real value.

            int iLevelAmount = WebUtil.StringToInt(levelAmount);

            if (iLevelAmount <= 0)
            {
                WebPrint.PrintErrorInfo("GenerateArrayInitTag", "levelAmount is not a valid value");
                return (SKIP_BODY);
            }

            String[] index = null;
            int baseLevel=0;
            String optKey          = "";
            String optValue        = "";
            String curIdx =  null;

            if (indexList != null) index = indexList.split("/");

            if (index!=null) baseLevel=index.length;

            for (int i=baseLevel; i<iLevelAmount; i++)
            {
                optKey          = "";
                optValue        = "";
                if (baseLevel<=0)
                {
                    optKey = "var ";
                    optValue ="var ";
                }

                for (int j=baseLevel; j<=i; j++)
                {
                    optKey = optKey + arrayName + String.valueOf(i+1) +  "Key" ;
                    optValue = optValue + arrayName + String.valueOf(i+1) + "Value";

                    for(int k=0;k<j;k++)
                    {
                        if (k<baseLevel) curIdx = (String)pageContext.getAttribute(index[k]);
                          else curIdx ="0";

                        optKey = optKey + "[" + curIdx +  "]" ;
                        optValue = optValue + "[" + curIdx + "]";
                    }

                    optKey = optKey + "=new Array();\n";
                    optValue = optValue + "=new Array();\n";

                }

                optKey = optKey + arrayName + String.valueOf(i+1) +  "Key" ;
                optValue = optValue + arrayName + String.valueOf(i+1) + "Value";

                for(int k=0;k<i;k++)
                {
                    if (k<baseLevel) curIdx = (String)pageContext.getAttribute(index[k]);
                        else curIdx ="0";

                    optKey = optKey + "[" + curIdx +  "]" ;
                    optValue = optValue + "[" + curIdx + "]";

                }

                optKey = optKey + "[0]=[\"\"]\n";
                optValue = optValue + "[0]=[\"-----------------------\"]\n";

                ResponseUtils.write(pageContext, optKey);
                ResponseUtils.write(pageContext, optValue);

            }



            return (EVAL_BODY_BUFFERED);
        }

    public void release() {

        super.release();
        indexList = null;
        arrayName = "Level";
        levelAmount = null;
    }

}