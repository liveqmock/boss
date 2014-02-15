package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class VODInterfaceCustomerOrderedDTO implements ReflectionSupport,
		Serializable {
	private java.util.Map map = new java.util.HashMap();

	// SEQNO 1 1 N NUMBER (10) NULL
	private int seqNO;

	// HOSTID 2 Y NUMBER (10) NULL
	private int hostID;

	// CREATETIME 3 Y DATE NULL
	private Timestamp createTime;

	// VODRECORDID 4 Y NUMBER (10) NULL
	private int vodRecordID;

	// VODTYPE 5 Y VARCHAR2 (5) NULL
	private String vodType;

	// DEVICEMACADDR 6 Y VARCHAR2 (50) NULL
	private String deviceMacAddr;

	// MOVIEID 7 Y VARCHAR2 (20) NULL
	private String movieID;

	// MOVIENAME 8 Y VARCHAR2 (50) NULL
	private String movieName;

	// MOIVECATALOG 9 Y VARCHAR2 (5) NULL
	private String movieCataLog;

	// MOVIEGRADE 10 Y VARCHAR2 (5) NULL
	private String movieGrade;

	// VENDERID 11 Y NUMBER (10) NULL
	private int venderID;

	// VENDERNAME 12 Y VARCHR2 (50) NULL
	private String venderName;

	// PLAYSTATUS 13 Y VARCHAR2 (5) NULL
	private String playStatus;

	// PLAYSTARTTIME 14 Y DATE NULL
	private Timestamp playStartTime;

	// PLAYENDTIME 15 Y DATE NULL
	private Timestamp playEndTime;

	// RECORDSTATUS 16 Y VARCHAR2 (5) NULL
	private String recordStatus;

	// RATINGSTATUS 17 Y VARCHAR2 (5) NULL
	private String ratingStatus;

	// RATINGTIME 18 Y DATE NULL
	private Timestamp ratingTime;

	// ACCTITEMTYPEID 19 Y VARCHAR2 (20) NULL
	private String acctItemTypeID;

	// VALUE 20 Y NUMBER (12,2) NULL
	private double value;
	
//	 chargeAmount 20 Y NUMBER (12,2) NULL
	private double chargeAmount;

	// CUSTID 21 Y NUMBER (10) NULL
	private int custID;

	// ACCTID 22 Y NUMBER (10) NULL
	private int acctID;

	// USERID 23 Y NUMBER (10) NULL
	private int userID;

	// PSID 24 Y NUMBER (10) NULL
	private int psID;

	// ACCOUNTEDFLAG 25 Y VARCHAR2 (5) NULL
	private String accountedFlag;

	// ACCOUNTEDTIME 26 Y DATE NULL
	private Timestamp accountedTime;

	// DT_CREATE 27 Y TIMESTAMP(6) NULL
	private Timestamp dtCreate;

	// DT_LASTMOD 28 Y TIMESTAMP(6) NULL
	private Timestamp dtLastMod;

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

	public String getAccountedFlag() {
		return accountedFlag;
	}

	public void setAccountedFlag(String accountedFlag) {
		this.accountedFlag = accountedFlag;
		put("accountedFlag");
	}

	public Timestamp getAccountedTime() {
		return accountedTime;
	}

	public void setAccountedTime(Timestamp accountedTime) {
		this.accountedTime = accountedTime;
		put("accountedTime");
	}

	public int getAcctID() {
		return acctID;
	}

	public void setAcctID(int acctID) {
		this.acctID = acctID;
		put("acctID");
	}

/**
 * @return Returns the chargeAmount.
 */
public double getChargeAmount() {
	return chargeAmount;
}
/**
 * @param chargeAmount The chargeAmount to set.
 */
public void setChargeAmount(double chargeAmount) {
	this.chargeAmount = chargeAmount;
}
	public String getAcctItemTypeID() {
		return acctItemTypeID;
	}

	public void setAcctItemTypeID(String acctItemTypeID) {
		this.acctItemTypeID = acctItemTypeID;
		put("acctItemTypeID");
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		put("createTime");
	}

	public int getCustID() {
		return custID;
	}

	public void setCustID(int custID) {
		this.custID = custID;
		put("custID");
	}

	public String getDeviceMacAddr() {
		return deviceMacAddr;
	}

	public void setDeviceMacAddr(String deviceMacAddr) {
		this.deviceMacAddr = deviceMacAddr;
		put("deviceMacAddr");
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

	public int getHostID() {
		return hostID;
	}

	public void setHostID(int hostID) {
		this.hostID = hostID;
		put("hostID");
	}

	public String getMovieCataLog() {
		return movieCataLog;
	}

	public void setMovieCataLog(String moiveCataLog) {
		this.movieCataLog = moiveCataLog;
		put("movieCataLog");
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

	public void setMovieID(String movieId) {
		this.movieID = movieId;
		put("movieId");
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
		put("movieName");
	}

	public Timestamp getPlayEndTime() {
		return playEndTime;
	}

	public void setPlayEndTime(Timestamp palyEndTime) {
		this.playEndTime = palyEndTime;
		put("palyEndTime");
	}

	public Timestamp getPlayStartTime() {
		return playStartTime;
	}

	public void setPlayStartTime(Timestamp playStartTime) {
		this.playStartTime = playStartTime;
		put("playStartTime");
	}

	public String getPlayStatus() {
		return playStatus;
	}

	public void setPlayStatus(String playStatus) {
		this.playStatus = playStatus;
		put("playStatus");
	}

	public int getPsID() {
		return psID;
	}

	public void setPsID(int psID) {
		this.psID = psID;
		put("psID");
	}

	public String getRatingStatus() {
		return ratingStatus;
	}

	public void setRatingStatus(String ratingStatus) {
		this.ratingStatus = ratingStatus;
		put("ratingStatus");
	}

	public Timestamp getRatingTime() {
		return ratingTime;
	}

	public void setRatingTime(Timestamp ratingTime) {
		this.ratingTime = ratingTime;
		put("ratingTime");
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
		put("recordStatus");
	}

	public int getSeqNO() {
		return seqNO;
	}

	public void setSeqNO(int seqNO) {
		this.seqNO = seqNO;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
		put("userID");
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
		put("value");
	}

	public int getVenderID() {
		return venderID;
	}

	public void setVenderID(int venderID) {
		this.venderID = venderID;
		put("venderID");
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
		put("venderName");
	}

	public int getVodRecordID() {
		return vodRecordID;
	}

	public void setVodRecordID(int vodRecordID) {
		this.vodRecordID = vodRecordID;
		put("vodRecordID");
	}

	public String getVodType() {
		return vodType;
	}

	public void setVodType(String vodType) {
		this.vodType = vodType;
		put("vodType");
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
