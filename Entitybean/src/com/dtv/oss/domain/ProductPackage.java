package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ProductPackageDTO;

public interface ProductPackage extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setDateFrom(Timestamp dateFrom);

	public Timestamp getDateFrom();

	public void setDateTo(Timestamp dateTo);

	public Timestamp getDateTo();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getPackageID();

	public void setPackageName(String packageName);

	public String getPackageName();
	
	public  void setPackageClass(String packageClass);
	
	public  void setPackagePriority(int packagePriority);
	
	public   String getPackageClass();
	
	public  int getPackagePriority();
	
	public  void setMinSelProductNum(int minSelProductNum);
	
	public  void setMaxSelProductNum(int maxSelProductNum);
	
	public  void setHasOptionProductFlag(String hasOptionProductFlag);
	
	public  int getMinSelProductNum();
	
	public  int getMaxSelProductNum();
	
	public  String getHasOptionProductFlag();

	public void setGrade(int grade);

	public  int getGrade();

	public int ejbUpdate(ProductPackageDTO dto);
	
	public  void setCaptureFlag(String captureFlag);
	
	public  void setCsiTypeList(String csiTypeList);
	
	public  void setCustTypeList(String custTypeList);
	
	public  String getCaptureFlag();
	
	public  String getCsiTypeList();
	
	public  String getCustTypeList();
}