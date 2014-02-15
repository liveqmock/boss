package com.dtv.oss.domain;

import java.io.Serializable;

public class PackageLinePK implements Serializable {

	public int packageId;

	public int productId;

	public PackageLinePK() {
	}

	public PackageLinePK(int packageId, int productId) {
		this.packageId = packageId;
		this.productId = productId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				PackageLinePK that = (PackageLinePK) obj;
				return this.packageId == that.packageId
						&& this.productId == that.productId;
			}
		}
		return false;
	}

	public int hashCode() {
		return packageId | productId;
	}
}