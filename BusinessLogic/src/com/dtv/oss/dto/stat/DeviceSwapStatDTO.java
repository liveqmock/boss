package com.dtv.oss.dto.stat;

import java.io.Serializable;
import java.sql.Timestamp;

public class DeviceSwapStatDTO  implements Serializable {
	private int id;
	private String customername;
	private String createreason;
	private String olddevicemodel;
	private String olddeviceid;
	private String devicemodel;
	private String deviceid;
	private double value;
	private String operatorname;
	private Timestamp workdate;
	private Timestamp createtime;

	public DeviceSwapStatDTO() {
	}
	
	public String toString() {
		return "id:"+id+",customername:"+customername+
			   ",createreason:"+createreason+
			   ",olddeviceclass:"+olddevicemodel+",olddeviceid:"+olddeviceid+
			   ",deviceclass:"+devicemodel+",deviceid:"+deviceid+
			   ",value:"+value+",operatorid:"+operatorname+
			   ",workdate:"+workdate+",createtime:"+createtime;
	}

	public String getCreatereason() {
		return createreason;
	}

	public void setCreatereason(String createreason) {
		this.createreason = createreason;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}



	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOlddeviceid() {
		return olddeviceid;
	}

	public void setOlddeviceid(String olddeviceid) {
		this.olddeviceid = olddeviceid;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Timestamp getWorkdate() {
		return workdate;
	}

	public void setWorkdate(Timestamp workdate) {
		this.workdate = workdate;
	}
	
	public String getOperatorname() {
		return operatorname;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	public String getDevicemodel() {
		return devicemodel;
	}

	public void setDevicemodel(String devicemodel) {
		this.devicemodel = devicemodel;
	}

	public String getOlddevicemodel() {
		return olddevicemodel;
	}

	public void setOlddevicemodel(String olddevicemodel) {
		this.olddevicemodel = olddevicemodel;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

}
