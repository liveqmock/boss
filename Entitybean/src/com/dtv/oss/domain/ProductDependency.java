package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ProductDependencyDTO;

public interface ProductDependency extends javax.ejb.EJBLocalObject {
	 
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSeqNo();

	public void setProductId(int productId);

	public int getProductId();

    public  void setType(String type);
    
	public  String getType();
	
	public  void setReferProductIDList(String referProductIDList);
	
	public  void setReferAllFlag(String referAllFlag);
	
	public  void setReferProductNum(int referProductNum);
	  
	public  String getReferProductIDList();
	
	public  String getReferAllFlag();
	
	public  int getReferProductNum();
	  
	 

	public int ejbUpdate(ProductDependencyDTO dto);

}