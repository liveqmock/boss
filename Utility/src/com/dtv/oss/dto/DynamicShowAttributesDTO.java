package com.dtv.oss.dto;

public class DynamicShowAttributesDTO implements  java.io.Serializable
{
    private String dtoName;
    private String labelName;
    private int    labelCols;
    private String textName;
    private String textValue;
    private String valueSourceType;
    private String valueTerm;
    private int    labelOrder;
	public String getDtoName() {
		return dtoName;
	}
	public void setDtoName(String dtoName) {
		this.dtoName = dtoName;
	}
	public int getLabelCols() {
		return labelCols;
	}
	public void setLabelCols(int labelCols) {
		this.labelCols = labelCols;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public int getLabelOrder() {
		return labelOrder;
	}
	public void setLabelOrder(int labelOrder) {
		this.labelOrder = labelOrder;
	}
	public String getTextName() {
		return textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public String getTextValue() {
		return textValue;
	}
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	public String getValueSourceType() {
		return valueSourceType;
	}
	public void setValueSourceType(String valueSourceType) {
		this.valueSourceType = valueSourceType;
	}
	public String getValueTerm() {
		return valueTerm;
	}
	public void setValueTerm(String valueTerm) {
		this.valueTerm = valueTerm;
	}
}

