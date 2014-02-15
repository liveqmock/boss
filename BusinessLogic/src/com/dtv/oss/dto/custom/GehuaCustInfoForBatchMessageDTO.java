/**
 * 
 */
package com.dtv.oss.dto.custom;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 240910y
 *
 */
public class GehuaCustInfoForBatchMessageDTO implements Serializable {
	/**
	 * @return the batchNo
	 */
	public int getBatchNo() {
		return batchNo;
	}
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * @return the cATVID
	 */
	public String getCATVID() {
		return CATVID;
	}
	/**
	 * @param catvid the cATVID to set
	 */
	public void setCATVID(String catvid) {
		CATVID = catvid;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return Comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		Comments = comments;
	}
	/**
	 * @return the cpList
	 */
	public String getCpList() {
		return cpList;
	}
	/**
	 * @param cpList the cpList to set
	 */
	public void setCpList(String cpList) {
		this.cpList = cpList;
	}
	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	/**
	 * @return the deviceSerialNoList
	 */
	public String getDeviceSerialNoList() {
		return deviceSerialNoList;
	}
	/**
	 * @param deviceSerialNoList the deviceSerialNoList to set
	 */
	public void setDeviceSerialNoList(String deviceSerialNoList) {
		this.deviceSerialNoList = deviceSerialNoList;
	}
	/**
	 * @return the dtCreate
	 */
	public Timestamp getDtCreate() {
		return dtCreate;
	}
	/**
	 * @param dtCreate the dtCreate to set
	 */
	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}
	/**
	 * @return the dtLastmod
	 */
	public Timestamp getDtLastmod() {
		return dtLastmod;
	}
	/**
	 * @param dtLastmod the dtLastmod to set
	 */
	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}
	/**
	 * @return the matchStatus
	 */
	public String getMatchStatus() {
		return matchStatus;
	}
	/**
	 * @param matchStatus the matchStatus to set
	 */
	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the seqNo
	 */
	public int getSeqNo() {
		return seqNo;
	}
	/**
	 * @param seqNo the seqNo to set
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	/**
	 * 
	 */
	public GehuaCustInfoForBatchMessageDTO() {
		// TODO Auto-generated constructor stub
	}
	private int seqNo;
	private int batchNo;
	private int customerID;
	private String CATVID;
	private String name;
	private String matchStatus;
	private String deviceSerialNoList;
	private String cpList;
	private String Comments;
	private String serviceAccountIdList;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(seqNo);
		buf.append(",").append(batchNo);
		buf.append(",").append(customerID);
		buf.append(",").append(CATVID);
		buf.append(",").append(name);
		buf.append(",").append(matchStatus);
		buf.append(",").append(deviceSerialNoList);
		buf.append(",").append(cpList);
		buf.append(",").append(serviceAccountIdList);
		buf.append(",").append(Comments);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}
	/**
	 * @return the serviceAccountIdList
	 */
	public String getServiceAccountIdList() {
		return serviceAccountIdList;
	}
	/**
	 * @param serviceAccountIdList the serviceAccountIdList to set
	 */
	public void setServiceAccountIdList(String serviceAccountIdList) {
		this.serviceAccountIdList = serviceAccountIdList;
	}
}
