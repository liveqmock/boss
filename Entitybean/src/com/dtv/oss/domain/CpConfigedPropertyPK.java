package com.dtv.oss.domain;

import java.io.Serializable;

public class CpConfigedPropertyPK implements Serializable {

	public Integer psID;

	public String propertyCode;

	public CpConfigedPropertyPK() {
	}

	public CpConfigedPropertyPK(Integer psID, String propertyCode) {
		this.psID = psID;
		this.propertyCode = propertyCode;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CpConfigedPropertyPK that = (CpConfigedPropertyPK) obj;
				return (((this.propertyCode == null) && (that.propertyCode == null)) || (this.propertyCode != null && this.propertyCode
						.equals(that.propertyCode)))
						&& (((this.psID == null) && (that.psID == null)) || (this.psID != null && this.psID
						.equals(that.psID))) ;
			}
		}
		return false;
	}

	public int hashCode() {
		return (psID + "" + propertyCode).hashCode();
	}
 
}