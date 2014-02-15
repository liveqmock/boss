package com.dtv.oss.domain;

import java.io.Serializable;

public class CustMarketInfoPK implements Serializable {

	public int customerId;

	public int infoSettingId;

	public CustMarketInfoPK() {
	}

	public CustMarketInfoPK(int customerId, int infoSettingId) {
		this.customerId = customerId;
		this.infoSettingId = infoSettingId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CustMarketInfoPK that = (CustMarketInfoPK) obj;
				return this.customerId == that.customerId
				&& this.infoSettingId == that.infoSettingId;
			}
		}
		return false;
	}

	public int hashCode() {
		 
		return customerId | infoSettingId;
	}
}