package com.dtv.oss.domain;

import java.io.Serializable;

public class CPParameterPK implements Serializable {
	public Integer psID;

	public String paramName;

	public CPParameterPK() {
	}

	public CPParameterPK(Integer psID, String paramName) {
		this.psID = psID;
		this.paramName = paramName;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CPParameterPK that = (CPParameterPK) obj;
				return (((this.psID == null) && (that.psID == null)) || (this.psID != null && this.psID
						.equals(that.psID)))
						&& (((this.paramName == null) && (that.paramName == null)) || (this.paramName != null && this.paramName
								.equals(that.paramName)));
			}
		}
		return false;
	}

	public int hashCode() {
		return (psID + "" + paramName).hashCode();
	}
}