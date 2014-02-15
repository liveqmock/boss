package com.dtv.oss.domain;

import java.io.Serializable;

public class OssAuthorizationPK implements Serializable {

	public int deviceID;

	public int productID;

	public OssAuthorizationPK() {
	}

	public OssAuthorizationPK(int deviceID, int productID) {
		this.deviceID = deviceID;
		this.productID = productID;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				OssAuthorizationPK that = (OssAuthorizationPK) obj;
				return this.deviceID == that.deviceID
						&& this.productID == that.productID;
			}
		}
		return false;
	}

	public int hashCode() {
		return deviceID | productID;
	}
}