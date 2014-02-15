package com.dtv.oss.web.taglib.html;


import java.util.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.*;

import com.dtv.oss.web.util.WebPrint;

public class HiddenParametersTag extends BodyTagSupport {


	private String pass = null;
	private String unpass = null;

	public String getPass(){
		return this.pass ;
	}
        public void setPass(String pass ){
        	this.pass  = pass ;
        }

	public String getUnpass(){
		return this.unpass ;
	}
        public void setUnpass(String unpass ){
        	this.unpass  = unpass ;
        }

        private void AppendParamToStringBuffer(StringBuffer sOut, String sKey, String[] sVal)
        {
            for (int i = 0; i < sVal.length; i++)
            {
              sOut.append("<input type=\"hidden\" name=\"");
              sOut.append(sKey);
              sOut.append("\"");
              sOut.append(" value=\"");
              sOut.append(sVal[i]);
              sOut.append("\">\r\n");
            }

        }

        public int doStartTag() throws JspException {

		StringBuffer sOut = new StringBuffer();
                Map map = null;

		try
		{
                    map = pageContext.getRequest().getParameterMap();
                }
                catch(Exception e)
                {
                    WebPrint.PrintErrorInfo("HiddenParametersTag","get parameter map error:"+e.getMessage());
                }

		if (map==null) return (EVAL_PAGE);

                if(pass!=null)
                {
                    WebPrint.PrintTagDebugInfo("HiddenParametersTag", "pass had set:"+pass);

                    String[] aPass = pass.split("/");//CommonUtils.GetSplitArray(pass);

                    for(int i=0;i<aPass.length;i++)
                    {
                        String[] lstValue = null;
                        try
		        {
                            lstValue = (String[]) map.get(aPass[i]);
                        }
                        catch(Exception e)
                        {
                            WebPrint.PrintErrorInfo("HiddenParametersTag","get paramter --"+aPass[i]+"-- fail, error:"+e.getMessage());
                        }
                        if ((lstValue==null)||(lstValue.length<=0))
                          WebPrint.PrintErrorInfo("HiddenParametersTag","paramter --"+aPass[i]+"-- is null or its size is 0. ");
                        else
                          AppendParamToStringBuffer(sOut, aPass[i], lstValue);

                    }


                }
                else
                {
                    Iterator iterator = map.entrySet().iterator();
                    while(iterator.hasNext())
                    {
                        Map.Entry item = (Map.Entry)iterator.next();

                        try
		        {
                          AppendParamToStringBuffer(sOut, item.getKey().toString(), (String[]) item.getValue());
                        }
                        catch(Exception e)
                        {
                            WebPrint.PrintErrorInfo("HiddenParametersTag","get paramter --"+item.getKey().toString()+"-- fail, error:"+e.getMessage());
                        }

                    }


                }

                if(unpass!=null)
                {
                    WebPrint.PrintTagDebugInfo("HiddenParametersTag", "unpass had set:"+unpass);

                    String[] aUnpass = unpass.split("/");//CommonUtils.GetSplitArray(unpass);

                    for(int i=0;i<aUnpass.length;i++) map.remove(aUnpass[i]);
                }

                try
                {
                    JspWriter out = pageContext.getOut();
                    out.println(sOut);
                }
                catch(Exception e)
                {
                    WebPrint.PrintErrorInfo("HiddenParametersTag", "JspWriter error:"+e.getMessage());
                }

		return (EVAL_PAGE);
        }


}
