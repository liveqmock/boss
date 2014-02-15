package com.dtv.oss.domain;

import java.io.Serializable;

public class CACommandPK implements Serializable {
	public int commandID;

	public CACommandPK() {
	}

	public CACommandPK(int commandID) {
		this.commandID = commandID;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CACommandPK that = (CACommandPK) obj;
				return this.commandID == that.commandID;
			}
		}
		return false;
	}

	public int hashCode() {
		return commandID;
	}
}