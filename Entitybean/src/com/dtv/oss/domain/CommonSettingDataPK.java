package com.dtv.oss.domain;

import java.io.Serializable;

public class CommonSettingDataPK implements Serializable {

	public String name;

	public String key;

	public CommonSettingDataPK() {
	}

	public CommonSettingDataPK(String name, String key) {
		this.name = name;
		this.key = key;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CommonSettingDataPK that = (CommonSettingDataPK) obj;
				return (((this.name == null) && (that.name == null)) || (this.name != null && this.name
						.equals(that.name)))
						&& (((this.key == null) && (that.key == null)) || (this.key != null && this.key
								.equals(that.key)));
			}
		}
		return false;
	}

	public int hashCode() {
		return (name + key).hashCode();
	}
}