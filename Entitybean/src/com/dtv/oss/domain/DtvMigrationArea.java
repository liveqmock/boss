package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.DtvMigrationAreaDTO;

public interface DtvMigrationArea extends EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setRegionalAreaId(int regionalAreaId);

	public int getRegionalAreaId();

	public void setMigrationTeamName(String migrationTeamName);

	public String getMigrationTeamName();

	public void setMigrationTeamId(String migrationTeamId);

	public String getMigrationTeamId();

	public void setBatchStartDate(Timestamp batchStartDate);

	public Timestamp getBatchStartDate();

	public void setBatchEndDate(Timestamp batchEndDate);

	public Timestamp getBatchEndDate();

	public int getPlanMigrationRoomNum();

	public void setPlanMigrationRoomNum(int planMigrationRoomNum);

	public void setStatus(String status);

	public String getStatus();

	public void setStatusDate(Timestamp statusDate);

	public Timestamp getStatusDate();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getId();

	public void setCreateDate(Timestamp createDate);

	public Timestamp getCreateDate();

	public void setCreateOpId(Integer createOpId);

	public Integer getCreateOpId();

	public void setAreaCode(String areaCode);

	public String getAreaCode();

	public void setAreaName(String areaName);

	public String getAreaName();

	public int ejbUpdate(DtvMigrationAreaDTO dto);
}
