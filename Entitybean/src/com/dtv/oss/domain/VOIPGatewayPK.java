package com.dtv.oss.domain;

import java.io.Serializable;

public class VOIPGatewayPK implements Serializable{
	public String deviceModel;
	public String devsType;
	
	public VOIPGatewayPK(){
	}
	
	public VOIPGatewayPK(String deviceModel,String devsType){
		this.deviceModel=deviceModel;
		this.devsType=devsType;
	}
	
	public boolean equals(Object obj){
		if(obj!=null){
			if(obj.getClass().equals(this.getClass())){
				VOIPGatewayPK that=(VOIPGatewayPK)obj;
				return (this.deviceModel.equals(that.deviceModel)&&this.devsType.equals(that.devsType));
			}
		}
		return false;
	}
	
	public int hashCode(){
		return (deviceModel+""+devsType).hashCode();
	}
}
