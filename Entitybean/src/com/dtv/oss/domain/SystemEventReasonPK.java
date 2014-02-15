package com.dtv.oss.domain;

import java.io.Serializable;

public class SystemEventReasonPK implements Serializable {

	public int eventClass;

	public String reasonCode;

	public SystemEventReasonPK() {
	}

	public SystemEventReasonPK(int eventClass, String reasonCode) {
		this.eventClass = eventClass;
		this.reasonCode = reasonCode;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				SystemEventReasonPK that = (SystemEventReasonPK) obj;
				return this.eventClass == that.eventClass
						&& (((this.reasonCode == null) && (that.reasonCode == null)) || (this.reasonCode != null && this.reasonCode
								.equals(that.reasonCode)));
			}
		}
		return false;
	}

	public int hashCode() {
		return (eventClass + "" + reasonCode).hashCode();
	}
}