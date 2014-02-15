package com.dtv.oss.dto;

import java.sql.Timestamp;

public class FoundCustomerHeadDTO  implements  java.io.Serializable {
   private int           batchNo;
   private String        comments;
   private int           createopid;
   private int           createorgid;
   private Timestamp     createTime;
   private int           dealopid;
   private Timestamp     dealTime;
   private String        status;
   
   public int getBatchNo() {
	   return batchNo;
   } 
   public void setBatchNo(int batchNo) {
	   this.batchNo = batchNo;
   }
   
   public String getComments() {
	   return comments;
   }
   public void setComments(String comments) {
	   this.comments = comments;
   }
   
   public int getCreateopid() {
	   return createopid;
   }
   public void setCreateopid(int createopid) {
	   this.createopid = createopid;
   }
   
   public int getCreateorgid() {
	   return createorgid;
   }
   public void setCreateorgid(int createorgid) {
	   this.createorgid = createorgid;
   }
   
   public Timestamp getCreateTime() {
	   return createTime;
   }
   public void setCreateTime(Timestamp createTime) {
	   this.createTime = createTime;
   }
   
   public int getDealopid() {
	  return dealopid;
   }
   public void setDealopid(int dealopid) {
	  this.dealopid = dealopid;
   }
   
   public Timestamp getDealTime() {
	  return dealTime;
   }
   public void setDealTime(Timestamp dealTime) {
	  this.dealTime = dealTime;
   }
   
   public String getStatus() {
	  return status;
   }
   public void setStatus(String status) {
	  this.status = status;
   }
   
}
