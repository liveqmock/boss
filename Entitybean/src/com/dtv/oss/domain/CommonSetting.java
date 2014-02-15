package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CommonSettingDTO;

public interface CommonSetting extends javax.ejb.EJBLocalObject {
	public String getName();

	public void setDescription(String description);

	public String getDescription();

	public void setModule(String module);

	public String getModule();

	public void setAttable(String attable);

	public String getAttable();

	public void setField(String field);

	public String getField();

	public void setFixed(String fixed);

	public String getFixed();

	public void setType(String type);

	public String getType();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(CommonSettingDTO dto);
}