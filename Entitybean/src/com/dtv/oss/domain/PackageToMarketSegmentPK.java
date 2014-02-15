package com.dtv.oss.domain;

import java.io.Serializable;

public class PackageToMarketSegmentPK implements Serializable {

	public int packageId;

	public int marketSegmentId;

	public PackageToMarketSegmentPK() {
	}

	public PackageToMarketSegmentPK(int packageId, int marketSegmentId) {
		this.packageId = packageId;
		this.marketSegmentId = marketSegmentId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				PackageToMarketSegmentPK that = (PackageToMarketSegmentPK) obj;
				return this.packageId == that.packageId
						&& this.marketSegmentId == that.marketSegmentId;
			}
		}
		return false;
	}

	public int hashCode() {
		return packageId | marketSegmentId;
	}
}