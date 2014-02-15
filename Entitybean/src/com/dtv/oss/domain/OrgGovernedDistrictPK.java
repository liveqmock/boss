package com.dtv.oss.domain;

import java.io.Serializable;

public class OrgGovernedDistrictPK implements Serializable {

	public int orgId;

	public int districtId;

	public OrgGovernedDistrictPK() {
	}

	public OrgGovernedDistrictPK(int orgId, int districtId) {
		this.orgId = orgId;
		this.districtId = districtId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				OrgGovernedDistrictPK that = (OrgGovernedDistrictPK) obj;
				return this.orgId == that.orgId
						&& this.districtId == that.districtId;
			}
		}
		return false;
	}

	public int hashCode() {
		return orgId | districtId;
	}
}