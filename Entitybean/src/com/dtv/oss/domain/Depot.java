package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.DepotDTO;

public interface Depot extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setStatus(String status);

	public String getStatus();

	public Integer getDepotID();

	public void setDepotName(String depotName);

	public String getDepotName();

	public void setDetailAddress(String detailAddress);

	public String getDetailAddress();

	public void setOwnerID(int ownerID);

	public int getOwnerID();

	public int ejbUpdate(DepotDTO dto);
}