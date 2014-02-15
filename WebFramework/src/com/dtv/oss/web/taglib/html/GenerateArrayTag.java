package com.dtv.oss.web.taglib.html;

import java.io.IOException;

import java.util.*;
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


public class GenerateArrayTag extends BodyTagSupport{

        protected Collection collection = null;
        protected String[] lstKey = null;
        protected String[] lstValue = null;
        protected String[] lstSubcollection = null;
        protected int levelAmount = 0;

        protected String subCollection = null;
        public String getSubCollection(){
                return subCollection;
        }
        public void setSubCollection(String pSubCollection){
                subCollection = pSubCollection;
        }

        protected String property = null;

        public String getProperty(){
          return property;
        }

        public void setProperty(String pProperty){
            property = pProperty;
            Object obj = pageContext.findAttribute(pProperty);
            if(obj!=null)
                  if(obj instanceof Collection)
                          collection = (Collection)obj;
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

        protected String arrayName = "Level";

        public String getArrayName(){
                return arrayName;
        }
        public void setArrayName(String pArrayName){
                arrayName = pArrayName;
        }

        protected void initCurrentLevelCollection(String lstNo) throws JspException
        {
            if (levelAmount <= 0)
            {
                WebPrint.PrintErrorInfo("GenerateArrayInitTag", "levelAmount is not a valid value");
                return;
            }

            String[] index = null;
            int baseLevel=0;
            String optKey          = "";
            String optValue        = "";
            String curIdx =  null;

            if (WebUtil.StringHaveContent(lstNo)) index = lstNo.split("/");

            if (index!=null) baseLevel=index.length;

            for (int i=baseLevel; i<levelAmount; i++)
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
                        if (k<baseLevel) curIdx = index[k];
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
                    if (k<baseLevel) curIdx = index[k];
                      else curIdx ="0";

                    optKey = optKey + "[" + curIdx +  "]" ;
                    optValue = optValue + "[" + curIdx + "]";

                }

                optKey = optKey + "[0]=[\"\"]\n";
                optValue = optValue + "[0]=[\"-----------------------\"]\n";

                ResponseUtils.write(pageContext, optKey);
                ResponseUtils.write(pageContext, optValue);

            }

        }

        protected void LoopCollection(Collection col, int curLevelIdx, String lstNo) throws JspException
        {
            if (col==null) return;

            Iterator iterator = col.iterator();
            int iCursor = 0;

            initCurrentLevelCollection(lstNo);

            String prefixKey = arrayName + String.valueOf(curLevelIdx) + "Key";
            String prefixValue = arrayName + String.valueOf(curLevelIdx) + "Value";

            if (WebUtil.StringHaveContent(lstNo))
            {
                String[] indexNo = lstNo.split("/");
                for(int i=0;i<indexNo.length;i++)
                {
                    prefixKey = prefixKey + "[" + indexNo[i] + "]";
                    prefixValue = prefixValue+ "[" + indexNo[i] + "]";
                }
            }

            while (iterator.hasNext())
            {
                Object ob = iterator.next();
                iCursor++;

                Object objKey = null;
                Object objValue = null;
                Collection colSub = null;
                try
                {
                     objKey = WebUtil.excuteMethod(ob,
                                          WebUtil.Name2GetMethod(lstKey[curLevelIdx-1]));
                     objValue = WebUtil.excuteMethod(ob,
                                            WebUtil.Name2GetMethod(lstValue[curLevelIdx-1]));

                     if ((lstSubcollection!=null)&&(curLevelIdx<=lstSubcollection.length))
                       colSub = (Collection) WebUtil.excuteMethod(ob,
                                          WebUtil.Name2GetMethod(lstSubcollection[curLevelIdx-1]));
                }
                catch (Exception e) {
                     WebPrint.PrintErrorInfo("GenerateArrayTag", e.getMessage());
                }

                String tmpKey = prefixKey + "[" + String.valueOf(iCursor) + "]";
                String tmpValue = prefixValue + "[" + String.valueOf(iCursor) + "]";

                tmpKey = tmpKey + "=[\"";
                tmpValue = tmpValue + "=[\"";

                if (objKey!=null) tmpKey = tmpKey + objKey.toString();
                if (objValue!=null) tmpValue = tmpValue + objValue.toString();

                tmpKey = tmpKey + "\"];\n";
                tmpValue = tmpValue + "\"];\n";

                ResponseUtils.write(pageContext, tmpKey);
                ResponseUtils.write(pageContext, tmpValue);

                if (colSub!=null)
                {
                    String lstCurLevelNo = lstNo;
                    if  (WebUtil.StringHaveContent(lstNo)) lstCurLevelNo +="/";
                    lstCurLevelNo += String.valueOf(iCursor) ;

                    int newLevelIdx = curLevelIdx + 1;

                    LoopCollection(colSub, newLevelIdx, lstCurLevelNo);
                }
            }

        }

        public int doStartTag() throws JspException {
            //assume name keyProperty valueProperty indexList has a real value.
            if (collection==null)
            {
                WebPrint.PrintErrorInfo("GenerateArrayTag", "collection is null");
                return (SKIP_BODY);
            }


            if ((keyProperty == null)||(valueProperty == null))
            {
                WebPrint.PrintErrorInfo("GenerateArrayTag", "keyProperty or valueProperty is null");
                return (SKIP_BODY);
            }

            lstKey = keyProperty.split("/");
            lstValue = valueProperty.split("/");

            if ((lstKey.length<=0)||(lstValue.length<=0)||(lstKey.length!=lstValue.length))
            {
                 WebPrint.PrintErrorInfo("GenerateArrayTag", "the amounts of keyProperty and valueProperty are not equivalent.");
                 return (SKIP_BODY);
            }

            levelAmount = lstKey.length;
            if (subCollection!=null) lstSubcollection = subCollection.split("/");

            if (levelAmount>1)
            {
                if ((lstSubcollection == null)||(lstSubcollection.length !=(levelAmount -1)))
                {
                     WebPrint.PrintErrorInfo("GenerateArrayTag", "the amount of Subcollection is invalid.");
                     return (SKIP_BODY);
                 }

            }

            LoopCollection(collection, 1, "");

            return (EVAL_BODY_BUFFERED);
        }



    public void release() {

        super.release();
        subCollection = null;
        property = null;
        keyProperty = null;
        valueProperty = null;
        arrayName = "Level";

        collection = null;
        lstKey = null;
        lstValue = null;
        lstSubcollection = null;
        levelAmount = 0;
    }

}