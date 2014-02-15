package com.dtv.oss.domain;

import java.io.Serializable;

public class OpGroupToPrivilegePK implements Serializable {

	public int opGroupId;

	public int privId;

	public OpGroupToPrivilegePK() {
	}

	public OpGroupToPrivilegePK(int opGroupId, int privId) {
		this.opGroupId = opGroupId;
		this.privId = privId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				OpGroupToPrivilegePK that = (OpGroupToPrivilegePK) obj;
				return this.opGroupId == that.opGroupId
						&& this.privId == that.privId;
			}
		}
		return false;
	}

	public int hashCode() {
		return opGroupId | privId;
	}
}