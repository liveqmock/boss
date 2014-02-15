package com.dtv.oss.domain;

import java.io.Serializable;

public class OpToGroupPK implements Serializable {

	public int operatorId;

	public int opGroupId;

	public OpToGroupPK() {
	}

	public OpToGroupPK(int operatorId, int opGroupId) {
		this.operatorId = operatorId;
		this.opGroupId = opGroupId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				OpToGroupPK that = (OpToGroupPK) obj;
				return this.operatorId == that.operatorId
						&& this.opGroupId == that.opGroupId;
			}
		}
		return false;
	}

	public int hashCode() {
		return operatorId | opGroupId;
	}
}