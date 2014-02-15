package com.dtv.oss.domain;

import java.io.Serializable;

public class ProductToServicePK implements Serializable {

	public int productId;

	public int serviceId;

	public ProductToServicePK() {
	}

	public ProductToServicePK(int productId, int serviceId) {
		this.productId = productId;
		this.serviceId = serviceId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				ProductToServicePK that = (ProductToServicePK) obj;
				return this.productId == that.productId
						&& this.serviceId == that.serviceId;
			}
		}
		return false;
	}

	public int hashCode() {
		return productId | serviceId;
	}
}