package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.MethodOfPaymentDTO;

public interface MethodOfPayment extends javax.ejb.EJBLocalObject {
	public void setName(String name);

	public String getName();

	public void setDescription(String description);

	public String getDescription();

	public void setStatus(String status);

	public String getStatus();

	public void setPayType(String payType);

	public String getPayType();

	public void setCashFlag(String cashFlag);

	public String getCashFlag();

	public void setAccountflag(java.lang.String accountflag);

	public void setPaymentflag(java.lang.String paymentflag);

	public String getAccountflag();

	public String getPaymentflag();

	public void setPartnerID(int partnerID);

	public int getPartnerID();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getMopID();
	
	public  java.lang.String getCsiTypeList();
	
	public  void setCsiTypeList(java.lang.String csiTypeList);

	public int ejbUpdate(MethodOfPaymentDTO dto);
}