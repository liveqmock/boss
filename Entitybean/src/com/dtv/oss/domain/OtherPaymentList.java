package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.OtherPaymentListDTO;

public interface OtherPaymentList extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setMopID(int mopID);

	public int getMopID();

	public void setReferId(int referId);

	public int getReferId();

	public void setPaymentType(String paymentType);

	public String getPaymentType();

	public void setSubPaymentType(String subPaymentType);

	public String getSubPaymentType();

	public void setValue(double value);

	public double getValue();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(OtherPaymentListDTO dto);

	public void setComments(String comments);

	public String getComments();

	public void setCreateDepartmentID(int createDepartmentID);

	public void setCreateOrgID(int createOrgID);

	public void setCreateOperatorID(int createOperatorID);

	public void setCreateDate(Timestamp createDate);

	public int getCreateDepartmentID();

	public int getCreateOperatorID();

	public int getCreateOrgID();

	public Timestamp getCreateDate();
}