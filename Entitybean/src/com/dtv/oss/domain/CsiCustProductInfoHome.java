package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CsiCustProductInfoDTO;

public interface CsiCustProductInfoHome extends javax.ejb.EJBLocalHome {
	public CsiCustProductInfo create(Integer id) throws CreateException;

	public CsiCustProductInfo create(CsiCustProductInfoDTO dto)
			throws CreateException;

	 public Collection findByCSIID(int csiID) throws FinderException; 
	 public Collection findByCSIIDAndBuyGroup(int csiID,int groupNo,int sheafNo) throws FinderException; 
	 public Collection findByCSIIDAndCustProductID(int csiID,int custProductID) throws FinderException; 
	public CsiCustProductInfo findByPrimaryKey(Integer id)
			throws FinderException;

}