package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class ProductToServiceDTO implements java.io.Serializable {

	private int productId;

	private int serviceId;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	public ProductToServiceDTO() {
	}

	public void setProductId(int productId) {
		this.productId = productId;

	}

	public int getProductId() {
		return productId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;

	}

	public int getServiceId() {
		return serviceId;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				ProductToServiceDTO that = (ProductToServiceDTO) obj;
				return this.getProductId() == that.getProductId()
						&& this.getServiceId() == that.getServiceId()
						&&

						(((this.getDtCreate() == null) && (that.getDtCreate() == null)) || (this
								.getDtCreate() != null && this.getDtCreate()
								.equals(that.getDtCreate())))
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
		buf.append(productId);
		buf.append(",").append(serviceId);

		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);

		return buf.toString();
	}

	 
 
}

