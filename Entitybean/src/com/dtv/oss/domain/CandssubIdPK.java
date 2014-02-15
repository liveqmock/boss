package com.dtv.oss.domain;

import java.io.Serializable;

public class CandssubIdPK implements Serializable {

	public String cardsn;

	public int hostId;

	public CandssubIdPK() {
	}

	public CandssubIdPK(String cardsn, int hostId) {
		this.cardsn = cardsn;
		this.hostId = hostId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CandssubIdPK that = (CandssubIdPK) obj;
				return (((this.cardsn == null) && (that.cardsn == null)) || (this.cardsn != null && this.cardsn
						.equals(that.cardsn)))
						&&  this.hostId == that.hostId;
			}
		}
		return false;
	}

	public int hashCode() {
		 int result = 17;
	        result = 37 * result + (int) this.hostId;
	        result = 37 * result + this.cardsn.hashCode();
	        return result;
	}
}