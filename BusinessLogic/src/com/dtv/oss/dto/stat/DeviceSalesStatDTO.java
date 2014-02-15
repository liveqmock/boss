package com.dtv.oss.dto.stat;

import java.io.Serializable;

public class DeviceSalesStatDTO  implements Serializable {
	private String orgname;
	private String devicemodel;
	private double value;
	private long amount;
	
	public String toString() {
		return "orgname:"+orgname+",devicemodel:"+devicemodel+
			   ",value:"+value+",amount:"+amount;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getDevicemodel() {
		return devicemodel;
	}

	public void setDevicemodel(String devicemodel) {
		this.devicemodel = devicemodel;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
}
