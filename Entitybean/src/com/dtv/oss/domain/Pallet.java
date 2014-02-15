package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.PalletDTO;

public interface Pallet extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setStatus(String status);

	public String getStatus();

	public Integer getPalletID();

	public void setPalletName(String palletName);

	public String getPalletName();

	public void setPalletDesc(String palletDesc);

	public String getPalletDesc();

	public void setMaxNumberAllowed(int maxNumberAllowed);

	public int getMaxNumberAllowed();

	public void setDepotID(int depotID);

	public int getDepotID();

	public int ejbUpdate(PalletDTO dto);
}