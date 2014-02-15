package com.dtv.oss.domain;

import java.io.Serializable;

public class MarketSegmentToDistrictPK implements Serializable {

	public int marketSegmentId;

	public int districtId;

	public MarketSegmentToDistrictPK() {
	}

	public MarketSegmentToDistrictPK(int marketSegmentId, int districtId) {
		this.marketSegmentId = marketSegmentId;
		this.districtId = districtId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				MarketSegmentToDistrictPK that = (MarketSegmentToDistrictPK) obj;
				return this.marketSegmentId == that.marketSegmentId
						&& this.districtId == that.districtId;
			}
		}
		return false;
	}

	public int hashCode() {
		return marketSegmentId | districtId;
	}
}