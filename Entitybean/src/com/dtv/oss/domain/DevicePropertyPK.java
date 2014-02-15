package com.dtv.oss.domain;

import java.io.Serializable;

public class DevicePropertyPK implements Serializable {

	public int deviceId;

	public String propertyName;

	public DevicePropertyPK() {
	}

	public DevicePropertyPK(int deviceId, String propertyName) {
		this.deviceId = deviceId;
		this.propertyName = propertyName;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				DevicePropertyPK that = (DevicePropertyPK) obj;
				return this.deviceId == that.deviceId
				&& (((this.propertyName == null) && (that.propertyName == null)) || (this.propertyName != null && this.propertyName
						.equals(that.propertyName)));
			}
		}
		return false;
	}

	public int hashCode() {
		 
		return (deviceId + "" + propertyName).hashCode();
	}
}