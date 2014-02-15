package com.dtv.oss.web.taglib.logic;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;
import java.util.*;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.taglib.util.ResponseUtils;
/**
 * The tag is used to traverse the collection.
 */
public class LoopTag extends BodyTagSupport {


	protected Iterator iterator = null;

	protected Collection collection = null;

	public Collection getCollection(){
		return collection;
	}
	public void setCollection(Collection pCollection){
		collection = pCollection;
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

	protected String item = null;

	public String getItem(){
		return item ;
	}
        public void setItem(String pItem ){
        	item  = pItem ;
        }

        protected boolean isOne = false;

        public String getIsOne(){
                return String.valueOf(isOne);
        }

        public void setIsOne(String pIsOne){
                if (WebUtil.StringHaveContent(pIsOne))
                {
                    if (pIsOne.toLowerCase().equals("true")) isOne=true;
                      else isOne=false;
                }
        }

        /**
         * It is an attribute of pageContext which is used to store the current place in  collection
         * index is based on 0
         */
        protected String itemNo = null;

        public String getItemNo(){
                return itemNo ;
        }
        public void setItemNo(String pItem ){
                itemNo  = pItem ;
        }

        private void initItemNo()
        {
            //itemNo==null means this loop does not need an item no
            if (itemNo==null) return;

            pageContext.setAttribute(itemNo, new String("1"));
        }

        private void increaseItemNo()
        {
            //itemNo==null means this loop does not need an item no
            if (itemNo==null) return;

            String strCurNo = (String)pageContext.getAttribute(itemNo);

            if (!WebUtil.StringHaveContent(strCurNo))
            {
                initItemNo();
                return;
            }

            int iCurNo = WebUtil.StringToInt(strCurNo);
            strCurNo = String.valueOf(iCurNo + 1);

            pageContext.setAttribute(itemNo, strCurNo);
        }

        public void ResponseWriteRecordsetError() throws JspException{
                String strInfo="";
                if (getCollection()==null) strInfo="无法获得结果集！";
                else
                {
                  if (getCollection().size() > 1)
                    strInfo = "返回的结果不止一条，请重新搜索。";
                  else
                    strInfo = "没有满足条件的结果，请重新搜索。";
                }
                String result="<br><table width=90%><tr><td align=center><font color=#FF0000>"+strInfo+"</font></td></tr><tr><td>&nbsp;</td></tr><tr><td align=center><input type=button name=buttonback value=\"返回\" onclick=\"javascript:history.back(-1)\" ></td></tr></table>";

                ResponseUtils.write(pageContext,result);

        }

        public int doStartTag() throws JspException {
		WebPrint.PrintTagDebugInfo(this.getClass().getName(),"invoke doStartTag...");

		Collection coll = getCollection();
		if ((coll==null)||(item==null))
		{
			WebPrint.PrintTagDebugInfo(this.getClass().getName(),"collection or item is empty");
			return (SKIP_BODY);
		}

                WebPrint.PrintTagDebugInfo(this.getClass().getName(),"collection size="+String.valueOf(coll.size()));

                if (isOne && (coll.size()!=1))
                {
                    ResponseWriteRecordsetError();
                    return (SKIP_BODY);
                }

		iterator = coll.iterator();

		if(iterator.hasNext())
		{
			Object ob = iterator.next();
			pageContext.setAttribute(item,ob);
                        initItemNo();
			return (EVAL_BODY_BUFFERED);
		}
		return (SKIP_BODY);

        }


	public int doAfterBody() throws JspException {
                WebPrint.PrintTagDebugInfo(this.getClass().getName(),"invoke doAfterBody ...");

		if (bodyContent != null)
		{
                      /*
                             try
                             {
                                 JspWriter out = pageContext.getOut();
                                 if (out instanceof BodyContent)
                                   out = ((BodyContent) out).getEnclosingWriter();
                                 out.print(bodyContent.getString());
                             }
                             catch(Exception e)
                             {
                                 WebPrint.PrintErrorInfo(this.getClass().getName(), "JspWriter error:"+e.getMessage());
                             }

                      */
		        try
		        {
	            		bodyContent.writeOut(bodyContent.getEnclosingWriter());		        }
		        catch(IOException e)
	        	{
	        		WebPrint.PrintErrorInfo(this.getClass().getName(), "JspWriter error:"+e.getMessage());
		        }
			bodyContent.clearBody();
		}

		if(iterator.hasNext())
		{
			Object ob = iterator.next();
                        pageContext.setAttribute(item,ob);
                        increaseItemNo();
			return (EVAL_BODY_BUFFERED);
		}
                else return (SKIP_BODY);

	}

    public void release() {

        super.release();
        iterator = null;
        collection = null;
        property = null;
        item = null;
        isOne = false;
        itemNo = null;
    }

}
