package com.dtv.oss.domain;

import java.io.Serializable;

public class CAProcessEventPK implements Serializable {

	public int eventId;

	public int hostId;

	public CAProcessEventPK() {
	}

	public CAProcessEventPK(int eventId, int hostId) {
		this.eventId = eventId;
		this.hostId = hostId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CAProcessEventPK that = (CAProcessEventPK) obj;
				return this.eventId == that.eventId
						&& this.hostId == that.hostId;
			}
		}
		return false;
	}

	public int hashCode() {
		return eventId | hostId;
	}
}