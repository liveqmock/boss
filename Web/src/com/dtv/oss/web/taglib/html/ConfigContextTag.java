package com.dtv.oss.web.taglib.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.WebPrint;

/**
 * 根据配置输出相应的内容
 * author     ：Jason.Zhou 
 * date       : 2006-3-9
 * description:
 * @author 250713z
 *
 */
public class ConfigContextTag extends BodyTagSupport {

	protected String prefix;
	protected String suffix;
	protected String showName;
	
	private String configName=null;
	
	
	public int doStartTag() throws JspException {
		super.doStartTag();
		
		setConfigNameFromDB();
		if(configName==null || "null".equals(configName))
			configName="";
		
		if("".equals(configName)){
			prefix="";
			suffix="";
		}
		
		if(prefix==null)
			prefix="";
		if(suffix==null)
			suffix="";
		
		WebPrint.PrintTagDebugInfo(this.getClass().getName(),"返回结果为：" + prefix + configName + suffix);
		ResponseUtils.write(pageContext,prefix + configName + suffix);	
		
		return EVAL_BODY_BUFFERED;
	}
	
	public int doEndTag() throws JspException {
        WebPrint.PrintTagDebugInfo(this.getClass().getName(),"invoke doAfterBody ...");
        bodyContent =getBodyContent();
        if (bodyContent != null)
		{
        	 try {
	            bodyContent.writeOut(bodyContent.getEnclosingWriter());		        
	         } catch(Exception e){
	        		WebPrint.PrintErrorInfo(this.getClass().getName(), "JspWriter error:"+e.getMessage());
		     }         	
	         bodyContent.clearBody();
		}
		return (SKIP_BODY);
	}
	
	public String getPrefix() {
		return prefix;
	}


	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public String getShowName() {
		return showName;
	}


	public void setShowName(String showName) {
		this.showName = showName;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void release() {
		super.release();
		showName=null;
		prefix=null;
		suffix=null;
		configName=null;
	}

	private void setConfigNameFromDB()
	{
		configName=String.valueOf(Postern.excuteQueryBySQL("select " + showName + " from t_bossconfiguration"));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
