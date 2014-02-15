package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.TerminalDeviceDTO;

public interface TerminalDeviceHome extends javax.ejb.EJBLocalHome {
	public TerminalDevice create(Integer deviceID) throws CreateException;

	public TerminalDevice create(TerminalDeviceDTO dto) throws CreateException;

	 
	public TerminalDevice findByPrimaryKey(Integer deviceID) throws FinderException;
	
	public Collection findBySerialNoAndDeviceClass(java.lang.String serialNo,java.lang.String deviceClass) throws FinderException;	
	
	public Collection findBySerialNo(java.lang.String serialNo) throws FinderException;			

}