package com.dtv.oss.service.ejbevent.batch;


public class BatchModifyCustEJBEvent extends BatchEJBEvent {
     private String strCustIds ;
     private int object_DistrictID;
     private String object_CustomerType;
     private String source_DetailAddress;
     private String object_DetailAddress;
     private String addrOption;
      
	 public String getAddrOption() {
		return addrOption;
	 }
	 public void setAddrOption(String addrOption) {
		this.addrOption = addrOption;
	 }
	 
	 public String getObject_CustomerType() {
		return object_CustomerType;
	 }
	 public void setObject_CustomerType(String object_CustomerType) {
		this.object_CustomerType = object_CustomerType;
	 }
	 
 	 public String getSource_DetailAddress() {
		return source_DetailAddress;
	 }
	 public void setSource_DetailAddress(String source_DetailAddress) {
		this.source_DetailAddress = source_DetailAddress;
	 }
	 
	 public String getObject_DetailAddress() {
		return object_DetailAddress;
	 }
	 public void setObject_DetailAddress(String object_DetailAddress) {
		this.object_DetailAddress = object_DetailAddress;
	 }
	 
	 public int getObject_DistrictID() {
		return object_DistrictID;
	 }
	 public void setObject_DistrictID(int object_DistrictID) {
		this.object_DistrictID = object_DistrictID;
	 }
	 
	 public String getStrCustIds() {
		return strCustIds;
	 }
	 public void setStrCustIds(String strCustIds) {
		this.strCustIds = strCustIds;
 	 }
     
}
