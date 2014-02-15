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
public class GeHuaUploadCustBatchHeaderDTO implements Serializable {

	/**
	 * 
	 */
	public GeHuaUploadCustBatchHeaderDTO() {
		// TODO Auto-generated constructor stub
	}
	
	private int batchNo;
	private int createOpID;
	private int createOrgID;
	private Timestamp createTime;
	private int batchID;
	private String Comments;

	private String jobType;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(batchNo);
		buf.append(",").append(createOpID);
		buf.append(",").append(createOrgID);
		buf.append(",").append(createTime);
		buf.append(",").append(batchID);
		buf.append(",").append(Comments);
		buf.append(",").append(jobType);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	/**
	 * @return the batchID
	 */
	public int getBatchID() {
		return batchID;
	}

	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}

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
	 * @return the createOpID
	 */
	public int getCreateOpID() {
		return createOpID;
	}

	/**
	 * @param createOpID the createOpID to set
	 */
	public void setCreateOpID(int createOpID) {
		this.createOpID = createOpID;
	}

	/**
	 * @return the createOrgID
	 */
	public int getCreateOrgID() {
		return createOrgID;
	}

	/**
	 * @param createOrgID the createOrgID to set
	 */
	public void setCreateOrgID(int createOrgID) {
		this.createOrgID = createOrgID;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	 * @return the jobType
	 */
	public String getJobType() {
		return jobType;
	}

	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
}
