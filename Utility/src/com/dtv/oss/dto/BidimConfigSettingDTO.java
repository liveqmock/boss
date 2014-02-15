package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BidimConfigSettingDTO implements ReflectionSupport,
		java.io.Serializable {
	private int id;

	private String name;

	private String description;

	private String configType;

	private String configSubType;

	private int serviceId;

	private String valueType;

	private String allowNull;

	private String status;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		put("name");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
		put("configType");
	}

	public String getConfigSubType() {
		return configSubType;
	}

	public void setConfigSubType(String configSubType) {
		this.configSubType = configSubType;
		put("configSubType");
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
		put("serviceId");
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
		put("valueType");
	}

	public String getAllowNull() {
		return allowNull;
	}

	public void setAllowNull(String allowNull) {
		this.allowNull = allowNull;
		put("allowNull");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				BidimConfigSettingDTO that = (BidimConfigSettingDTO) obj;
				return this.getId() == that.getId()
						&& (((this.getName() == null) && (that.getName() == null)) || (this
								.getName() != null && this.getName().equals(
								that.getName())))
						&& (((this.getDescription() == null) && (that
								.getDescription() == null)) || (this
								.getDescription() != null && this
								.getDescription().equals(that.getDescription())))
						&& (((this.getConfigType() == null) && (that
								.getConfigType() == null)) || (this
								.getConfigType() != null && this
								.getConfigType().equals(that.getConfigType())))
						&& (((this.getConfigSubType() == null) && (that
								.getConfigSubType() == null)) || (this
								.getConfigSubType() != null && this
								.getConfigSubType().equals(
										that.getConfigSubType())))
						&&

						this.getServiceId() == that.getServiceId()
						&& (((this.getValueType() == null) && (that
								.getValueType() == null)) || (this
								.getValueType() != null && this.getValueType()
								.equals(that.getValueType())))
						&& (((this.getAllowNull() == null) && (that
								.getAllowNull() == null)) || (this
								.getAllowNull() != null && this.getAllowNull()
								.equals(that.getAllowNull())))
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
						&& (((this.getDtCreate() == null) && (that
								.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append("name=").append(name);
		buf.append(",id=").append(id);
		buf.append(",description=").append(description);
		buf.append(",configType=").append(configType);
		buf.append(",configSubType=").append(configSubType);
		buf.append(",serviceId=").append(serviceId);
		buf.append(",dtCreate=").append(dtCreate);
		buf.append(",dtLastmod=").append(dtLastmod);
		buf.append(",valueType=").append(valueType);
		buf.append(",allowNull=").append(allowNull);
		buf.append(",status=").append(status);

		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

}
