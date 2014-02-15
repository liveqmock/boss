package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class VODInterfaceLogDTO implements ReflectionSupport, Serializable {
	private java.util.Map map = new java.util.HashMap();

	// SEQNO 1 1 N NUMBER (10) NULL
	private int seqNO;

	// TRANSACTIONID 2 Y NUMBER (10) NULL
	private int transactionID;

	// CREATETIME 3 Y DATE NULL
	private Timestamp createTime;

	// TYPE 4 Y VARCHAR2 (5) NULL
	private String type;

	// DESCRIPTION 5 Y VARCHAR2 (500) NULL
	private String description;

	// PROCESSRESULT 6 Y VARCHAR2 (5) NULL
	private String processResult;

	// PROCESSINFORMATION 7 Y VARCHAR2 (500) NULL
	private String processInformation;

	// CUSTOMERID 8 Y NUMBER (10) NULL
	private int customerID;

	// ACCOUNTID 9 Y NUMBER (10) NULL
	private int accountID;

	// SERVICEACCOUNTID 10 Y NUMBER (10) NULL
	private int serviceAccountID;

	// PSID 11 Y NUMBER (10) NULL
	private int psID;

	// DEVICENO 12 Y VARCHAR2 (50) NULL
	private String deviceNO;

	// SMSPRODUCTID 13 Y NUMBER (10) NULL
	private int smsProductID;

	// VODPRODUCTID 14 Y VARCHAR2 (50) NULL
	private String vodProductID;

	// VODSERVICEID 15 Y VARCHAR2 (50) NULL
	private String vodServiceID;

	// RECURINGFLAG 16 Y VARCHAR2 (5) NULL
	private String recuringFlag;

	// MOVIEID 17 Y NUMBER (10) NULL
	private String movieID;

	// MOVIENAME 18 Y VARCHAR2 (200) NULL
	private String movieName;

	// MOVIEGRADE 19 Y VARCHAR2 (5) NULL
	private String movieGrade;

	// DT_CREATE 20 Y TIMESTAMP(6) NULL
	private Timestamp dtCreate;

	// DT_LASTMOD 21 Y TIMESTAMP(6) NULL
	private Timestamp dtLastMod;
	
//	 PSID 11 Y NUMBER (10) NULL
	private int queueID;

	// DEVICENO 12 Y VARCHAR2 (50) NULL
	private int vodDataRecordID;

	
	
	public int getQueueID() {
		return queueID;
	}

	public void setQueueID(int queueID) {
		this.queueID = queueID;
	}

	public int getVodDataRecordID() {
		return vodDataRecordID;
	}

	public void setVodDataRecordID(int vodDataRecordID) {
		this.vodDataRecordID = vodDataRecordID;
	}

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
		put("accountID");
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		put("createTime");
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
		put("customerID");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public String getDeviceNO() {
		return deviceNO;
	}

	public void setDeviceNO(String deviceNO) {
		this.deviceNO = deviceNO;
		put("deviceNO");
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
		put("dtCreate");
	}

	public Timestamp getDtLastMod() {
		return dtLastMod;
	}

	public void setDtLastMod(Timestamp dtLastMod) {
		this.dtLastMod = dtLastMod;
		put("dtLastMod");
	}

	public String getMovieGrade() {
		return movieGrade;
	}

	public void setMovieGrade(String movieGrade) {
		this.movieGrade = movieGrade;
		put("movieGrade");
	}

	public String getMovieID() {
		return movieID;
	}

	public void setMovieID(String movieID) {
		this.movieID = movieID;
		put("movieID");
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
		put("movieName");
	}

	public String getProcessInformation() {
		return processInformation;
	}

	public void setProcessInformation(String processInformation) {
		this.processInformation = processInformation;
		put("processInformation");
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
		put("processResult");
	}

	public int getPsID() {
		return psID;
	}

	public void setPsID(int psID) {
		this.psID = psID;
		put("psID");
	}

	public String getRecuringFlag() {
		return recuringFlag;
	}

	public void setRecuringFlag(String recuringFlag) {
		this.recuringFlag = recuringFlag;
		put("recuringFlag");
	}

	public int getSeqNO() {
		return seqNO;
	}

	public void setSeqNO(int seqNO) {
		this.seqNO = seqNO;
	}

	public int getServiceAccountID() {
		return serviceAccountID;
	}

	public void setServiceAccountID(int serviceAccountID) {
		this.serviceAccountID = serviceAccountID;
		put("serviceAccountID");
	}

	public int getSmsProductID() {
		return smsProductID;
	}

	public void setSmsProductID(int smsProductID) {
		this.smsProductID = smsProductID;
		put("smsProductID");
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
		put("transactionID");
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		put("type");
	}

	public String getVodProductID() {
		return vodProductID;
	}

	public void setVodProductID(String vodProductID) {
		this.vodProductID = vodProductID;
		put("vodProductID");
	}

	public String getVodServiceID() {
		return vodServiceID;
	}

	public void setVodServiceID(String vodServiceID) {
		this.vodServiceID = vodServiceID;
		put("vodServiceID");
	}

	// for query constraints
	private Timestamp createTimeBegin;

	private Timestamp createTimeEnd;

	public Timestamp getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(Timestamp createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public Timestamp getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Timestamp createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

}
