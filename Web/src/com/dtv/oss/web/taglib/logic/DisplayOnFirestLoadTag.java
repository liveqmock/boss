/**
 * 用来处理第一次load的时候控制内容的显示
 */
package com.dtv.oss.web.taglib.logic;

import javax.servlet.jsp.JspException;

/**
 * @author 240910y
 *
 */
public class DisplayOnFirestLoadTag extends BaseCompareTag {
	/**
	 * 
	 */
	private String name;
	private static final String attributePerfix ="DISPLAY_ON_FIREST_LOAD_";;
	protected boolean condition() throws JspException {
		if(getName()==null || "".equals(getName()))
			throw new JspException("Name must be set.");
		String haveLoad=(String)pageContext.getSession().getAttribute(attributePerfix+getName());
		if("Y".equals(haveLoad))
			return false;
		else{
			pageContext.getSession().setAttribute(attributePerfix+getName(),"Y");
		}
		return true;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
