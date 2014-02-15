package com.dtv.oss.dto.custom;
import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

/**
 * QueryConditionDTO 用来填充查询条件的dto
 * 其中
 * no：一般存放记录的编号，序列号等
 * status：记录的状态
 * type：记录的类型
 * beginTime：起始时间
 * endTime：结束时间
 */
public class QueryConditionDTO implements java.io.Serializable
{
	private String no;
	private String status;
	private String type;
	private String operator;
	private Timestamp beginTime;
	private Timestamp endTime;
	private String orderField;
	private boolean isAsc;
	private int customerID;
	private int filiale;
	private int district;
	private int street;


	public QueryConditionDTO()
	{
	}

	public QueryConditionDTO(String no, String status, String type, Timestamp beginTime, Timestamp endTime, String orderField, boolean isAsc, int filiale, int district, int street)
	{
		this.no = no;
		this.status = status;
		this.type = type;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.orderField = orderField;
		this.isAsc = isAsc;
		this.filiale = filiale;
		this.district = district;
		this.street = street;
	}

	public void setNo(String no)
	{
		this.no = no;
	}

	public String getNo()
	{
		return no;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public void setBeginTime(Timestamp beginTime)
	{
		this.beginTime = beginTime;
	}

	public Timestamp getBeginTime()
	{
		return beginTime;
	}

	public void setEndTime(Timestamp endTime)
	{
		this.endTime = endTime;
	}

	public Timestamp getEndTime()
	{
		return endTime;
	}

	public void setOrderField(String orderField)
	{
		this.orderField = orderField;
	}

	public String getOrderField()
	{
		return orderField;
	}

	public void setIsAsc(boolean isAsc)
	{
		this.isAsc = isAsc;
	}

	public boolean getIsAsc()
	{
		return isAsc;
	}

	public void setFiliale(int filiale)
	{
		this.filiale = filiale;
	}

	public int getFiliale()
	{
		return filiale;
	}

	public void setDistrict(int district)
	{
		this.district = district;
	}

	public int getDistrict()
	{
		return district;
	}

	public void setStreet(int street)
	{
		this.street = street;
	}

	public int getStreet()
	{
		return street;
	}
	
	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				QueryConditionDTO that = (QueryConditionDTO) obj;
				return
					(((this.getNo() == null) && (that.getNo() == null)) ||
						(this.getNo() != null && this.getNo().equals(that.getNo()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					(((this.getType() == null) && (that.getType() == null)) ||
						(this.getType() != null && this.getType().equals(that.getType()))) &&
					(((this.getBeginTime() == null) && (that.getBeginTime() == null)) ||
						(this.getBeginTime() != null && this.getBeginTime().equals(that.getBeginTime()))) &&
					(((this.getEndTime() == null) && (that.getEndTime() == null)) ||
						(this.getEndTime() != null && this.getEndTime().equals(that.getEndTime()))) &&
					(((this.getOrderField() == null) && (that.getOrderField() == null)) ||
						(this.getOrderField() != null && this.getOrderField().equals(that.getOrderField()))) &&
					this.getIsAsc() == that.getIsAsc()  &&
					this.getFiliale() == that.getFiliale()  &&
					this.getDistrict() == that.getDistrict()  &&
					this.getStreet() == that.getStreet() ;
			}
		}
		return false;
	}

	public int hashCode()
	{
		return toString().hashCode();
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(no);
		buf.append(",").append(status);
		buf.append(",").append(type);
		buf.append(",").append(operator);
		buf.append(",").append(beginTime);
		buf.append(",").append(endTime);
		buf.append(",").append(orderField);
		buf.append(",").append(isAsc);
		buf.append(",").append(customerID);
		buf.append(",").append(filiale);
		buf.append(",").append(district);
		buf.append(",").append(street);
		return buf.toString();
	}

	public String toXml()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append("<no>").append(no).append("</no>");
		buf.append("<status>").append(status).append("</status>");
		buf.append("<type>").append(type).append("</type>");
		buf.append("<operator>").append(operator).append("</operator>");
		buf.append("<begintime>").append(beginTime).append("</begintime>");
		buf.append("<endtime>").append(endTime).append("</endtime>");
		buf.append("<orderfield>").append(orderField).append("</orderfield>");
		buf.append("<isasc>").append(isAsc).append("</isasc>");
		buf.append("<customerID>").append(customerID).append("</customerID>");
		buf.append("<filiale>").append(filiale).append("</filiale>");
		buf.append("<district>").append(district).append("</district>");
		buf.append("<street>").append(street).append("</street>");
		return buf.toString();
	}

	/**
	 * @return Returns the operator.
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator The operator to set.
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * @return Returns the customerID.
	 */
	public int getCustomerID() {
		return customerID;
	}
	/**
	 * @param customerID The customerID to set.
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
}