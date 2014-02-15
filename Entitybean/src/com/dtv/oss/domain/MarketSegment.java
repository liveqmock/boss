package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.MarketSegmentDTO;

public interface MarketSegment extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setName(String name);

	public String getName();

	public void setDescription(String description);

	public String getDescription();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(MarketSegmentDTO dto);
}