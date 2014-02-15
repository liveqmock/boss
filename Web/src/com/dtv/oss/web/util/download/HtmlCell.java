package com.dtv.oss.web.util.download;

public class HtmlCell {
	private String caption;
	private String captionbgcolor;
	private String value;
	private String valuebgcolor;
	private String align;
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getCaptionbgcolor() {
		return captionbgcolor;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public void setCaptionbgcolor(String captionbgcolor) {
		this.captionbgcolor = captionbgcolor;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValuebgcolor() {
		return valuebgcolor;
	}
	public void setValuebgcolor(String valuebgcolor) {
		this.valuebgcolor = valuebgcolor;
	}
	public String toString(){
		StringBuffer to=new StringBuffer();
		to.append("caption:").append(caption).append(",");
		to.append("captionbgcolor:").append(captionbgcolor).append(",");
		to.append("value:").append(value).append(",");
		to.append("valuebgcolor:").append(valuebgcolor).append(",");
		to.append("align:").append(align).append(";");
		return to.toString();
	}
}
