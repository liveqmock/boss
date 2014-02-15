/**
 * 
 */
package com.dtv.oss.web.taglib.logic;

import javax.servlet.jsp.JspException;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.logic.BaseCompareTag;
/**
 * @author 240910y
 *
 */
public class ShowContentBySetting extends BaseCompareTag {
	private String settingName;
	/**
	 * 待比较的值，可以自己设置，默认值为Y
	 */
	private String compareValue="Y";
    protected boolean condition() throws JspException {
    	String value=Postern.getSystemSettingValue(settingName);
    	if(value!=null && !"".equals(value)&& compareValue.equalsIgnoreCase(value))
    		return true;
    	
    	return false;
    }
	/**
	 * @return the settingName
	 */
	public String getSettingName() {
		return settingName;
	}
	/**
	 * @param settingName the settingName to set
	 */
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}
	/**
	 * @return the compareValue
	 */
	public String getCompareValue() {
		return compareValue;
	}
	/**
	 * @param compareValue the compareValue to set
	 */
	public void setCompareValue(String compareValue) {
		this.compareValue = compareValue;
	}
}
