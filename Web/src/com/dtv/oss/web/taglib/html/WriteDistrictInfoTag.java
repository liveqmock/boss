package com.dtv.oss.web.taglib.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.RequestUtils;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

public class WriteDistrictInfoTag extends BodyTagSupport {

	protected String name = null;

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		name = pName;
	}

	protected String property = null;

	public String getProperty() {
		return property;
	}

	public void setProperty(String pProperty) {
		property = pProperty;
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
	public int doStartTag() throws JspException {
		Object value = null;
		//name不为空的时候直接从name对应的对象中取得对应property的值
		if (name != null &&!"".equals(name)&& property!=null &&!"".equals(property)) {
			Object objSource = RequestUtils.lookup(pageContext, name, scope);
			if (objSource != null) {
				try {
					value = WebUtil.excuteMethod(objSource, WebUtil
							.Name2GetMethod(property));
				} catch (Exception e) {
					WebPrint.PrintErrorInfo("WriteTag", e.getMessage());
				}
			}
		//name为空的时候，直接从请求中取得属性值	
		}else if ((name == null||"".equals(name)) && (property!=null&&!"".equals(property))) {
			value=pageContext.getRequest().getParameter(property);
			if (value ==null || "".equals(value)){
				int index =property.indexOf("[");
				int last =property.indexOf("]");
				if (index !=-1){
				   	String strProperty =property.substring(0,index);
				   	String strIndex =property.substring(index+1,last);
				   	String[] propertyList =pageContext.getRequest().getParameterValues(strProperty);
				    value= propertyList[Integer.parseInt(strIndex)];
				}
			}
		}
		if (value == null || "".equals(value)) {
			return (SKIP_BODY);
		}else{
			int districtID=Integer.parseInt(String.valueOf(value));
			//output to html page
			ResponseUtils.write(pageContext, Postern.getDistrictDesc(districtID));
		}
		return (EVAL_BODY_BUFFERED);
	}

	public void release() {

		super.release();
		name = null;
		property = null;
		scope = null;
	}

}
