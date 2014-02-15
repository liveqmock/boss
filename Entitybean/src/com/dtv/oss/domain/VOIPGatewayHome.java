package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.dtv.oss.dto.VOIPGatewayDTO;

public interface VOIPGatewayHome extends javax.ejb.EJBLocalHome{
	public VOIPGateway create(String deviceModel,String devsType)throws CreateException;
	
	public VOIPGateway create (VOIPGatewayDTO dto)throws CreateException;
	
	public VOIPGateway findByPrimaryKey(VOIPGatewayPK pk)throws FinderException;
	
}
