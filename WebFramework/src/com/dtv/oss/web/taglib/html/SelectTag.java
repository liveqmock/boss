package com.dtv.oss.web.taglib.html;


import javax.servlet.jsp.JspException;
import java.util.*;

import com.dtv.oss.web.util.CommonSettingValue;
import com.dtv.oss.web.util.*;
import com.dtv.oss.web.taglib.util.*;


/**
 * Custom tag that represents an HTML select element, associated with a
 * bean property specified by our attributes.  This tag must be nested
 * inside a form tag.
 *
 */

public class SelectTag extends BaseHandlerTag {


    // ----------------------------------------------------- Instance Variables


    /**
     * Should multiple selections be allowed.  Any non-null value will
     * trigger rendering this.
     */
    protected String multiple = null;

    public String getMultiple() {
        return (this.multiple);
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    protected String value = null;

    /**
     * Return the comparison value.
     */
    public String getValue() {
        return (this.value);
    }

    /**
     * Set the comparison value.
     *
     * @param value The new comparison value
     */
    public void setValue(String value) {
        this.value = value;
    }


    protected String name = null;

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


    protected String set = null;

    public String getSet() {
        return (this.set);
    }

    public void setSet(String set) {
        this.set = set;
    }

    protected String width = null;

    public String getWidth() {
        return (this.width);
    }

    public void setWidth(String width) {
        this.width = width;
    }
    
    protected String selDisabled=null;
    
	public String getSelDisabled() {
		return selDisabled;
	}

	public void setSelDisabled(String selDisabled) {
		if(selDisabled!=null && "TRUE".equalsIgnoreCase(selDisabled)){
			this.selDisabled="true";
		}
		else{
			this.selDisabled="false";
		}
	}

	public String getSpaceString() {
        //if str=null or str="", the amount of spaces equal width
        int wide = 0;
        String str="";
        try
        {
            wide = Integer.parseInt(width,10);
        }
        catch(Exception e)
        {
            wide = 0;
        }

        /*if (wide>6)
        {
            str = "请选择";
            wide=wide-6;
        }
        */

        for (int i=0;i<(wide/2);i++) str="-"+str+"-";
        if ((wide % 2)!=0) str=str+"-";
        return str;

    }

    protected String match = null;

    public String getMatch() {
        return (this.match);
    }

    public void setMatch(String match) {
        if(match!=null)
        {
            this.match = CommonUtils.GetBeanPropertyReturnString(pageContext,match);
            if(this.match==null)
            {
                this.match = pageContext.getRequest().getParameter(match);
            }

            if(this.match==null)
            {
                this.match = match;
            }

       }
    }

    protected String empty = null;

    public String getEmpty() {
        return (this.empty);
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    protected boolean displayIncludeValue = false;

    public String getDisplayIncludeValue() {
        return String.valueOf(displayIncludeValue);
    }

    public void setDisplayIncludeValue(String pVal) {
      if ((pVal!=null)&&(pVal.compareToIgnoreCase("true")==0))
        displayIncludeValue=true;
      else
        displayIncludeValue=false;

    }

    //if match is not set, the value which equals defaultValue will be selected.
    protected String defaultValue = null;

    public String getDefaultValue() {
        return (this.defaultValue);
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    //if match is not set, the value which is arranged in the first row will be selected.
    protected int defaultIndex = 0;

    public String getDefaultIndex() {
        return String.valueOf(defaultIndex);
    }

    public void setDefaultIndex(String defaultIndex) {
        this.defaultIndex = WebUtil.StringToInt(defaultIndex);
    }

    // --------------------------------------------------------- Public Methods

    private int getSelectedIndex(Object optSet)
    {
        int iCurIndex = 1;
        Iterator iterator = null;

        if (WebUtil.StringHaveContent(match))
        {
            //preprocess that which value will be selected
            //set iSelectedIndex
            iCurIndex = 1;

            if(optSet instanceof Collection)
            {
              iterator = ( (Collection) optSet).iterator();
              while(iterator.hasNext())
              {
                  Object item = iterator.next();
                  if(item.equals(match)) return iCurIndex;
                  iCurIndex ++;
              }

            }
            else if(optSet instanceof Map)
            {
              iterator = ( (Map) optSet).entrySet().iterator();
              while(iterator.hasNext())
              {
                  Map.Entry item = (Map.Entry)iterator.next();
                  if(item.getKey().equals(match)) return iCurIndex;
                  iCurIndex ++;
              }

            }
        }

        //if match is not valid, default value or default will be available.
        if (WebUtil.StringHaveContent(defaultValue))
        {
            //preprocess that which value will be selected
            //set iSelectedIndex
            iCurIndex = 1;

            if(optSet instanceof Collection)
            {
              iterator = ( (Collection) optSet).iterator();
              while(iterator.hasNext())
              {
                  Object item = iterator.next();
                  if(item.equals(defaultValue)) return iCurIndex;
                  iCurIndex ++;
              }

            }
            else if(optSet instanceof Map)
            {
              iterator = ( (Map) optSet).entrySet().iterator();
              while(iterator.hasNext())
              {
                  Map.Entry item = (Map.Entry)iterator.next();
                  //2006-5-11	侯修改	原为if(item.getKey().equals(defaultValue))
	                  if(item.getKey().toString().equals(defaultValue)){
                	  //System.out.println("value :===defaultValue  "+defaultValue+":===item.getKey() "+item.getKey());
                	  return iCurIndex;
                  }
                  iCurIndex ++;
              }

            }
        }

        return defaultIndex;
    }

    /**
     * <p>
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");

        // Create an appropriate "form" element based on our parameters
        StringBuffer results = new StringBuffer("<select");
        results.append(" name=\"");
        results.append(name);
        results.append("\"");
        if (accesskey != null) {
            results.append(" accesskey=\"");
            results.append(accesskey);
            results.append("\"");
        }
        if (multiple != null) {
            results.append(" multiple=\"multiple\"");
        }
        if (size != null) {
            results.append(" size=\"");
            results.append(size);
            results.append("\"");
        }
        if (tabindex != null) {
            results.append(" tabindex=\"");
            results.append(tabindex);
            results.append("\"");
        }
        
        //add by zhouxushun , 2005-12-19
        if("true".equalsIgnoreCase(this.selDisabled)){
        	results.append(" disabled ");
        }
        
        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(">");

        // Print this field to our output writer
        ResponseUtils.write(pageContext, results.toString());


        // Continue processing this page
        if(set==null)
        	return (EVAL_BODY_BUFFERED);
        Object optSet = CommonUtils.GetBeanProperty(pageContext,set);
        StringBuffer opt = new StringBuffer();

        //if empty is not set and width is set, empty will be set as true automaticlly.
        if ((width!=null)&&(empty==null)) empty="true";
        if(empty!=null)
        	if(empty.equals("true")||empty.equals("empty"))
        	{
        		opt.append("<option value=\"\" >");
                        opt.append(getSpaceString());
			opt.append("</option>\n");
		}

        int iSelectedIndex = getSelectedIndex(optSet);
        int iCurIndex = 1;
        if(optSet instanceof Collection)
        {
        	Iterator iterator = ((Collection)optSet).iterator();
        	while(iterator.hasNext())
        	{
        		Object item = iterator.next();
        		opt.append("<option value=\"");
        		opt.append(item.toString());
		        opt.append("\"");
		        if ((iSelectedIndex!=0)&&(iCurIndex==iSelectedIndex))
		            opt.append(" selected=\"selected\"");

		        opt.append(">");
		        opt.append(item.toString());
		        opt.append("</option>\r\n");
                        iCurIndex ++;
        	}
        }
        else if(optSet instanceof Map)
        {
        	Iterator iterator = ((Map)optSet).entrySet().iterator();
        	while(iterator.hasNext())
        	{
        		Map.Entry item = (Map.Entry)iterator.next();
        		opt.append("<option value=\"");
		        opt.append(item.getKey().toString());
		        opt.append("\"");
		        if((iSelectedIndex!=0)&&(iCurIndex==iSelectedIndex))
		            opt.append(" selected=\"selected\"");

		        opt.append(">");
                if (displayIncludeValue)
                {
                   opt.append(item.getKey().toString());
                   opt.append(":");
                }
               //侯2006-7-5改了两行,修正当Map中有空值时抛出空指针异常
               if (item.getValue() instanceof CommonSettingValue){
                 	CommonSettingValue settingValue =(CommonSettingValue)item.getValue();
                    opt.append(settingValue.getValue());
               } else{
            	    opt.append(item.getValue().toString());
               }
               
		       opt.append("</option>\r\n");
                        iCurIndex ++;
		    }
        }

        ResponseUtils.write(pageContext, opt.toString());

        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

        return (SKIP_BODY);

    }



    /**
     * Save any body content of this tag, which will generally be the
     * option(s) representing the values displayed to the user.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {
        return (SKIP_BODY);
    }


    /**
     * Render the end of this form.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doEndTag enter ...");

        ResponseUtils.write(pageContext, "</select>");

        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doEndTag return.");
        // Continue processing this page
        return (EVAL_PAGE);

    }


    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();
        multiple = null;

        value = null;
        name = null;
        size = null;
        set = null;
        width = null;
        match = null;
        empty = null;
        displayIncludeValue = false;
        defaultValue = null;
        defaultIndex = 0;
    }


}
