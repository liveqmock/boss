package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.GroupBargainDetailDTO;

public interface GroupBargainDetail extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setDetailNo(String detailNo);

	public String getDetailNo();

	public void setGroupBargainID(int groupBargainID);

	public int getGroupBargainID();

	public void setUserID(int userID);

	public int getUserID();

	public void setUsedDate(Timestamp usedDate);

	public Timestamp getUsedDate();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(GroupBargainDetailDTO dto);
}