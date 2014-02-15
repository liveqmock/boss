package com.dtv.oss.domain;

import java.io.Serializable;

public class VOIPProductPK implements Serializable{
	
	public int productID;
	public String sssrvCode;
	
	public VOIPProductPK(){
	}
	
	public VOIPProductPK(int SMSID,String sssrvCode){
		this.productID=SMSID;
		this.sssrvCode=sssrvCode;
	}
	
	public boolean equals(Object obj) {
	    if (obj != null) {
	        if (this.getClass().equals(obj.getClass())) {
	          VOIPProductPK that = (VOIPProductPK) obj;
	          return (this.productID == that.productID &&
	              this.sssrvCode.equals(that.sssrvCode));
	        }
	      }
	      return false;
	    }
	public int hashCode() {
	    return (productID + "" + sssrvCode).hashCode();
	  }
}
