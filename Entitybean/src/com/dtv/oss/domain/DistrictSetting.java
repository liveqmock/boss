package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.DistrictSettingDTO;

public interface DistrictSetting extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setDistrictCode(String districtCode);

	public String getDistrictCode();

	public void setName(String name);

	public String getName();

	public void setType(String type);

	public String getType();

	public void setBelongTo(int belongTo);

	public int getBelongTo();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setStatus(String status);

	public String getStatus();

	public int ejbUpdate(DistrictSettingDTO dto);
}