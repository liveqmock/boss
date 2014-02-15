package com.dtv.oss.domain;

import java.io.Serializable;

public class CsiFeeSettingPK implements Serializable {

	public String csiType;

	public int eventClass;

	public CsiFeeSettingPK() {
	}

	public CsiFeeSettingPK(String csiType, int eventClass) {
		this.csiType = csiType;
		this.eventClass = eventClass;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CsiFeeSettingPK that = (CsiFeeSettingPK) obj;
				return (((this.csiType == null) && (that.csiType == null)) || (this.csiType != null && this.csiType
						.equals(that.csiType)))
						&& this.eventClass == that.eventClass;
			}
		}
		return false;
	}

	public int hashCode() {
		return (csiType + eventClass).hashCode();
	}
}