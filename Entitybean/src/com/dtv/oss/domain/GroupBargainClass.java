package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.GroupBargainClassDTO;

public interface GroupBargainClass extends javax.ejb.EJBLocalObject {
	public void setName(String name);

	public String getName();

	public void setKeepBilling(String keepBilling);

	public String getKeepBilling();

	public void setDescription(String description);

	public String getDescription();

	public void setNewProductPackage1(int newProductPackage1);

	public int getNewProductPackage1();

	public void setNewProductPackage2(int newProductPackage2);

	public int getNewProductPackage2();

	public void setNewProductPackage3(int newProductPackage3);

	public int getNewProductPackage3();

	public void setNewProductPackage4(int newProductPackage4);

	public int getNewProductPackage4();

	public void setNewProductPackage5(int newProductPackage5);

	public int getNewProductPackage5();

	public void setTimeLengthUnitType(String timeLengthUnitType);

	public String getTimeLengthUnitType();

	public void setTimeLengthUnitNumber(int timeLengthUnitNumber);

	public int getTimeLengthUnitNumber();

	public void setAllowPause(String allowPause);

	public String getAllowPause();

	public void setAllTransition(String allTransition);

	public String getAllTransition();

	public void setAllowTransfer(String allowTransfer);

	public String getAllowTransfer();

	public void setAllowCancel(String allowCancel);

	public String getAllowCancel();

	public void setStatus(String status);

	public String getStatus();

	public void setCreateOpID(int createOpID);

	public int getCreateOpID();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setUpdateOpID(int updateOpID);

	public int getUpdateOpID();

	public void setUpdateTime(Timestamp updateTime);

	public Timestamp getUpdateTime();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getId();

	public int ejbUpdate(GroupBargainClassDTO dto);

}