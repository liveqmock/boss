/*
 * Created on 2004-12-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.web.taglib.html;

import java.sql.Timestamp;
import javax.servlet.jsp.JspException;

import com.dtv.oss.web.taglib.html.BaseHandlerTag;
import com.dtv.oss.web.taglib.util.CommonUtils;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author 220226
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * modify by jason 2007-9-5
 * 用于支持精确到分和秒
 */
public class DateTimeTag extends BaseHandlerTag{
	protected String name = null;
	protected String myClass = null;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }


    protected String size = null;


    public String getSize() {
        return (this.size);
    }

    public void setSize(String size) {
        this.size = size;
    }

    
   
    
    protected Timestamp match = null;
    /*
     * 在match有效的条件下，是否时间是参数，一般情况是
     */
    protected boolean hourMatched = true;
    protected boolean minuteMatched = true;
    protected boolean secondMatched = true;
    
    public String getMatch() {
    	if (match==null)
    		return null;
    	
        return (this.match.toString());
    }

    public void setMatch(String strmatch) {
    	String curmatch=null;
    	
        if(strmatch!=null)
        {
        	curmatch = CommonUtils.GetBeanPropertyReturnString(pageContext,strmatch);
            if(curmatch==null)
            {
            	curmatch = pageContext.getRequest().getParameter(strmatch);
            	//System.out.println("***********");
            	if(!WebUtil.StringHaveContent(curmatch))
            	{
            		//System.out.println("******1*****");
            		curmatch = pageContext.getRequest().getParameter(strmatch+"DatePart");
            		//System.out.println(curmatch);
            		if (WebUtil.StringHaveContent(curmatch)) 
            		{
            			//System.out.println("******2*****");
            			if (WebUtil.StringHaveContent(pageContext.getRequest().getParameter(strmatch+"HourPart")))
            			{
                        	curmatch = curmatch + " " 
						         + pageContext.getRequest().getParameter(strmatch+"HourPart");
            			}
            			else{
            				curmatch = curmatch + " "  + "00";
            				hourMatched=false;
            			}
            			
            			if (WebUtil.StringHaveContent(pageContext.getRequest().getParameter(strmatch+"MinutePart")))
            			{
                        	curmatch = curmatch + ":"  + pageContext.getRequest().getParameter(strmatch+"MinutePart");
            			}
            			else{
            				curmatch = curmatch + ":"  + "00";
            				minuteMatched=false;
            			}            
            			
            			if (WebUtil.StringHaveContent(pageContext.getRequest().getParameter(strmatch+"SecondPart")))
            			{
                        	curmatch = curmatch + ":"  + pageContext.getRequest().getParameter(strmatch+"SecondPart");
            			}
            			else{
            				curmatch = curmatch + ":"  + "00";
            				secondMatched=false;
            			}            			
            		}
            		
            		//System.out.println("******3*****");
            		//System.out.println(curmatch);
            	}
            }

            if(curmatch==null)
            {
            	curmatch = strmatch;
            }
            
            match = WebUtil.StringToTimestamp(curmatch);

       }
        
       curmatch=null;
    }
    
    protected String picURL = null;

    public String getPicURL() {
        return (this.picURL);
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }
    
    protected boolean includeHour = false;
    protected boolean includeMinute=false;
    protected boolean includeSecond=false;
    
    public String getIncludeHour() {
        return String.valueOf(includeHour);
    }
    public void setIncludeHour(String pVal) {
      if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
      	includeHour=true;
      else
      	includeHour=false;
    }
    public String getIncludeMinute() {
        return String.valueOf(includeMinute);
    }
    public void setIncludeMinute(String pVal) {
      if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
    	  includeMinute=true;
      else
    	  includeMinute=false;
    }
    public String getIncludeSecond() {
        return String.valueOf(includeSecond);
    }
    public void setIncludeSecond(String pVal) {
      if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
    	  includeSecond=true;
      else
    	  includeSecond=false;
    }
    
    /*
     * attribute for hour
     */
    protected boolean empty = true;

    public String getEmpty() {
        return String.valueOf(this.empty);
    }

    public void setEmpty(String pVal) {
    	if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
    		empty=true;
          else
          	empty=false;
    }
    /**
     * <p>
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");

        // Create an appropriate "form" element based on our parameters
        StringBuffer results = new StringBuffer("");
        
        /*if (includeHour)
        {
        	results.append("<input type=\"hidden\" name=\"");
        	results.append(name);
        	results.append("\"");
        	
        	if (match!=null)
        	{
        		results.append(" value=\"");
        		results.append(match.toString());
        		results.append("\"");
        	}
        
        	results.append(">");
        }*/
        
        results.append("<input type=\"text\" name=\"");
        results.append(name);
        if (includeHour) results.append("DatePart");
        results.append("\"");
        
        results.append(" maxlength=\"10\" ");
        
        if (myClass != null) {
            results.append(" class=\"");
            results.append(myClass);
            results.append("\"");
        }
        
        if (size != null) {
            results.append(" size=\"");
            results.append(size);
            results.append("\"");
        }
        
        if (match != null)
        {
        	results.append(" value=\"");
    		results.append(WebUtil.TimestampToString(match, "yyyy-MM-dd"));
    		results.append("\"");
        }

       
        	results.append(" onfocus=\"viewModule(this)\"");
        	String showName = name;
        	if (includeHour)
        		showName = name+"DatePart";
        	results.append(" onKeydown=\"inputDate(document.frmPost."+showName+")\"");
        	results.append(" onblur=\"lostFocus(this)\"");
        
        
        results.append(">");

        results.append("<IMG src=\"");
        results.append(picURL);
        results.append("\" border=\"0\"");
        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(">");
        
        if (includeHour)
        {
        	int iSelHour = -1;
        	if ((match!=null)&&(hourMatched))
        		iSelHour = WebUtil.getHourOfTimestamp(match);
        	
        	results.append("&nbsp;");
        	results.append("<select");
	        results.append(" name=\"");
	        results.append(name);
	        results.append("HourPart");
	        results.append("\"");
	        results.append(">");
	        
	        if (empty)
	        {
	        	results.append("<option value=\"\" >");
	        	results.append("--");
	        	results.append("</option>\n");
	        }
	        
	        for (int i=0; i<24; i++)
	        {
	        	results.append("<option value=\"").append(i).append("\"");
	        	if (i==iSelHour)
	        		results.append(" selected ");
	        	results.append(">");
	        	results.append(i);
	        	results.append("</option>\n");
	        }
	        
	        results.append("</select>时");
        }
        
        if (includeMinute)
        {
        	int iSelHour = -1;
        	if ((match!=null)&&(minuteMatched))
        		iSelHour = WebUtil.getMinuteOfTimestamp(match);
        	
        	results.append("&nbsp;");
        	results.append("<select");
	        results.append(" name=\"");
	        results.append(name);
	        results.append("MinutePart");
	        results.append("\"");
	        results.append(">");
	        
	        if (empty)
	        {
	        	results.append("<option value=\"\" >");
	        	results.append("--");
	        	results.append("</option>\n");
	        }
	        
	        for (int i=0; i<60; i++)
	        {
	        	results.append("<option value=\"").append(i).append("\"");
	        	if (i==iSelHour)
	        		results.append(" selected ");
	        	results.append(">");
	        	results.append(i);
	        	results.append("</option>\n");
	        }
	        
	        results.append("</select>分");
        }
        
        if (includeSecond)
        {
        	int iSelHour = -1;
        	if ((match!=null)&&(secondMatched))
        		iSelHour = WebUtil.getSecondOfTimestamp(match);
        	
        	results.append("&nbsp;");
        	results.append("<select");
	        results.append(" name=\"");
	        results.append(name);
	        results.append("SecondPart");
	        results.append("\"");
	        results.append(">");
	        
	        if (empty)
	        {
	        	results.append("<option value=\"\" >");
	        	results.append("--");
	        	results.append("</option>\n");
	        }
	        
	        for (int i=0; i<60; i++)
	        {
	        	results.append("<option value=\"").append(i).append("\"");
	        	if (i==iSelHour)
	        		results.append(" selected ");
	        	results.append(">");
	        	results.append(i);
	        	results.append("</option>\n");
	        }
	        
	        results.append("</select>秒");
        }
        
        //System.out.println("the resulte which writen by jason is " + results.toString());
        
        // Print this field to our output writer
        ResponseUtils.write(pageContext, results.toString());     

        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

        return (SKIP_BODY);

    }
    
    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();
        name = null;
        size = null;
        match = null;
        hourMatched = true;
        picURL = null;
        includeHour = false;
        empty = true;
        includeMinute=false;
        includeSecond=false;
    }

	/**
	 * @return Returns the myClass.
	 */
	public String getMyClass() {
		return myClass;
	}
	/**
	 * @param myClass The myClass to set.
	 */
	public void setMyClass(String myClass) {
		this.myClass = myClass;
	}
}
