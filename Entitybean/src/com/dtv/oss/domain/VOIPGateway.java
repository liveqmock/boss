package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.VOIPGatewayDTO;

public interface VOIPGateway extends javax.ejb.EJBLocalObject {
	
	public String getDeviceModel();
	
	public String getDevsType();
	
	public void setDescription(String description);
	
	public String getDescription();
	
	public void setDtCreate(Timestamp dtCreate);
	
	public Timestamp getDtCreate();
	
	public void setDtLastmod(Timestamp dtLastmod);
	
	public Timestamp getDtLastmod();
	
	public int ejbUpdate(VOIPGatewayDTO dto);
}
