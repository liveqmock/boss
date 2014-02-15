package com.dtv.oss.web.taglib.logic;

/**
 * 页面元素可见性配置标签,
 */
import java.util.LinkedHashMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;
import com.dtv.oss.web.util.*;

public class DynamicFormElementTag extends BodyTagSupport {
	protected static final String BUFFERNAME="DynamicFormElements";
	protected static final int defaultElementColumn=2;

	private String elementName;
	private String functionName;
	protected String column;

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	private void putElement(String body){
		LinkedHashMap elements = (LinkedHashMap) pageContext.getAttribute(BUFFERNAME);
		if(elements==null){
			elements=new LinkedHashMap();
			pageContext.setAttribute(BUFFERNAME, elements);
		}
		String []element=new String[3];
		element[0]=body;
		element[1]=functionName;
		element[2]=(column!=null&&!column.equals("")&&Integer.parseInt(column)%defaultElementColumn==0)?column:defaultElementColumn+"";
		elements.put(elementName.toUpperCase(), element);
	}	
	public int doStartTag() throws JspException {
//		WebPrint.PrintDebugInfo(this.getClass().getName(),"\n>>>>>"+elementName);

		return (EVAL_BODY_BUFFERED);
	}

	public int doAfterBody() throws JspException {
		WebPrint.PrintDebugInfo(this.getClass().getName(),
				"\nelementName:"+elementName+"   doAfterBody enter ...");

		if (bodyContent != null&&bodyContent.getString().trim().length()!=0) {
			putElement(bodyContent.getString());
			WebPrint.PrintDebugInfo(this.getClass().getName(),"\nelementName:"+elementName+"  StringLength:"+bodyContent.getString().trim().length());
//			System.out.println(bodyContent.getString());
			bodyContent.clearBody();
		}

		WebPrint.PrintDebugInfo(this.getClass().getName(),
				"doAfterBody return.");

		return (SKIP_BODY);

	}	
	
	public void release() {

		super.release();

	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

}