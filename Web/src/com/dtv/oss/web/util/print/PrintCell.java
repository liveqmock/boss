package com.dtv.oss.web.util.print;

public class PrintCell {
	private String name;
	private String value;
	private String left;
	private String top;
	private String width;
	private String migrationRow;
	private String align;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMigrationRow() {
		return migrationRow;
	}
	public void setMigrationRow(String migrationRow) {
		this.migrationRow = migrationRow;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String toString(){
		StringBuffer to=new StringBuffer();
		to.append("name:").append(name).append(",");
		to.append("value:").append(value).append(",");
		to.append("left:").append(left).append(";");
		to.append("top:").append(top).append(";");
		to.append("width:").append(width).append(";");
		to.append("align:").append(align).append(";");
		to.append("migrationRow:").append(migrationRow).append(";");
		to.append("type:").append(type).append(";");
		return to.toString();
	}
	
}
