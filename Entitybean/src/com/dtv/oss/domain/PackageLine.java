package com.dtv.oss.domain;

import java.sql.Timestamp;

public interface PackageLine extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getPackageId();

	public int getProductId();

	public void setProductNum(int productNum);

	public int getProductNum();
	
	public  void setOptionFlag(String optionFlag);
	
	public  String getOptionFlag();
}