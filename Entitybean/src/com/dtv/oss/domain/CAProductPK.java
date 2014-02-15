package com.dtv.oss.domain;

import java.io.Serializable;

public class CAProductPK implements Serializable {

	public int productId;

	public int conditionId;

	public CAProductPK() {
	}

	public CAProductPK(int productId, int conditionId) {
		this.productId = productId;
		this.conditionId = conditionId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CAProductPK that = (CAProductPK) obj;
				return this.productId == that.productId
						&& this.conditionId == that.conditionId;
			}
		}
		return false;
	}

	public int hashCode() {
		return productId | conditionId;
	}
}