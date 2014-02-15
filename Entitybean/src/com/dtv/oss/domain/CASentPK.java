package com.dtv.oss.domain;

import java.io.Serializable;

public class CASentPK implements Serializable {

	public int queueId;

	public int eventId;

	public int transId;

	public CASentPK() {
	}

	public CASentPK(int queueId, int eventId, int transId) {
		this.queueId = queueId;
		this.eventId = eventId;
		this.transId = transId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CASentPK that = (CASentPK) obj;
				return this.queueId == that.queueId
						&& this.eventId == that.eventId
						&& this.transId == that.transId;
			}
		}
		return false;
	}

	public int hashCode() {
		return queueId | eventId | transId;
	}
}