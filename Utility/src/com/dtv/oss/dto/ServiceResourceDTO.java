package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ServiceResourceDTO implements ReflectionSupport,
		java.io.Serializable {
	private String status;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private String resourceName;

	private String resourceDesc;

	private String resourceType;

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

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
		// put("resourceName");
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
		put("resourceDesc");
	}

	/**
	 * @return Returns the resourceType.
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType
	 *            The resourceType to set.
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
		put("resourceType");
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				ServiceResourceDTO that = (ServiceResourceDTO) obj;
				return (((this.getStatus() == null) && (that.getStatus() == null)) || (this
						.getStatus() != null && this.getStatus().equals(
						that.getStatus())))
						&& (((this.getDtCreate() == null) && (that
								.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())))
						&& (((this.getResourceName() == null) && (that
								.getResourceName() == null)) || (this
								.getResourceName() != null && this
								.getResourceName().equals(
										that.getResourceName())))
						&& (((this.getResourceDesc() == null) && (that
								.getResourceDesc() == null)) || (this
								.getResourceDesc() != null && this
								.getResourceDesc().equals(
										that.getResourceDesc())))
						&&

						(((this.getResourceType() == null) && (that
								.getResourceType() == null)) || (this
								.getResourceType() != null && this
								.getResourceType().equals(
										that.getResourceType())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append("status=").append(status);
		buf.append(",").append("resourceName=").append(resourceName);
		buf.append(",").append("resourceDesc=").append(resourceDesc);
		buf.append(",").append("status=").append(status);
		buf.append(",").append("resourceType=").append(resourceType);
		buf.append(",").append("dtCreate=").append(dtCreate);
		buf.append(",").append("dtLastmod=").append(dtLastmod);
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
