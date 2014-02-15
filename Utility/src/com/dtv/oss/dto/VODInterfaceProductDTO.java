package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class VODInterfaceProductDTO implements ReflectionSupport, Serializable {
	private java.util.Map map = new java.util.HashMap();

	// SMSPRODUCTID 1 1 N NUMBER (10) 0
	private int smsProductID;

	// VODPRODUCTIDLIST 2 Y VARCHAR2 (500) NULL
	private String vodProductIDList;

	// VODSERVICEIDLIST 3 Y VARCHAR2 (500) NULL
	private String vodServiceIDList;

	// BILLINGCODELIST 4 Y VARCHAR2 (500) NULL
	private String billingCodeList;

	// NEWSAFLAG 5 Y VARCHAR2 (5) NULL
	private String newsaflag;

	// STATUS 6 Y VARCHAR2 (5) NULL
	private String status;

	// DT_CREATE 7 Y TIMESTAMP(6) NULL
	private Timestamp dtCreate;

	// DT_LASTMOD 8 Y TIMESTAMP(6) NULL
	private Timestamp dtLastMod;

	// ACCTITEMTYPEID 9 Y VARCHAR2 (20) null
	private String acctItemTypeID;

	// SMSPRODUCTID, VODPRODUCTIDLIST, VODSERVICEIDLIST, BILLINGCODELIST,
	// NEWSAFLAG, STATUS, DT_CREATE, DT_LASTMOD, ACCTITEMTYPEID
	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

	public String getAcctItemTypeID() {
		return acctItemTypeID;
	}
				   
	public void setAcctItemTypeID(String acctItemTypeID) {
		this.acctItemTypeID = acctItemTypeID;
		put("acctItemTypeID");
	}

	public String getBillingCodeList() {
		return billingCodeList;
	}

	public void setBillingCodeList(String billingCodeList) {
		this.billingCodeList = billingCodeList;
		put("billingCodeList");
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;

	}

	public Timestamp getDtLastMod() {
		return dtLastMod;
	}

	public void setDtLastMod(Timestamp dtLastMod) {
		this.dtLastMod = dtLastMod;

	}

	public String getNewsaflag() {
		return newsaflag;
	}

	public void setNewsaflag(String newSAFlag) {
		this.newsaflag = newSAFlag;
		put("newsaflag");
	}

	public int getSmsProductID() {
		return smsProductID;
	}

	public void setSmsProductID(int smsProductID) {
		this.smsProductID = smsProductID;
		// put("SMSPRODUCTID");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getVodProductIDList() {
		return vodProductIDList;
	}

	public void setVodProductIDList(String productIDList) {
		vodProductIDList = productIDList;
		put("vodProductIDList");
	}

	public String getVodServiceIDList() {
		return vodServiceIDList;
	}

	public void setVodServiceIDList(String serviceIDList) {
		vodServiceIDList = serviceIDList;
		put("vodServiceIDList");
	}

	public String toString() {
		StringBuffer desc = new StringBuffer();

		desc.append("smsProductID=").append(smsProductID);

		// VODPRODUCTIDLIST 2 Y VARCHAR2 (500) NULL
		desc.append(",vodProductIDList=").append(vodProductIDList);

		// VODSERVICEIDLIST 3 Y VARCHAR2 (500) NULL
		desc.append(",vodServiceIDList=").append(vodServiceIDList);

		// BILLINGCODELIST 4 Y VARCHAR2 (500) NULL
		desc.append(",billingCodeList=").append(billingCodeList);

		// NEWSAFLAG 5 Y VARCHAR2 (5) NULL
		desc.append(",newSAFlag=").append(newsaflag);

		// STATUS 6 Y VARCHAR2 (5) NULL
		desc.append(",status=").append(status);

		// DT_CREATE 7 Y TIMESTAMP(6) NULL
		desc.append(",dtCreate=").append(dtCreate);

		// DT_LASTMOD 8 Y TIMESTAMP(6) NULL
		desc.append(",dtLastMod=").append(dtLastMod);

		// ACCTITEMTYPEID 9 Y VARCHAR2 (20) null
		desc.append(",acctItemTypeID=").append(acctItemTypeID);

		return desc.toString();
	}
}
