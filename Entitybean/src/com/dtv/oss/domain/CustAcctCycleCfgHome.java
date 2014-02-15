package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustAcctCycleCfgDTO;

public interface CustAcctCycleCfgHome extends javax.ejb.EJBLocalHome {
	public CustAcctCycleCfg create(Integer seqNo) throws CreateException;

	public CustAcctCycleCfg create(CustAcctCycleCfgDTO dto)
			throws CreateException;

	public CustAcctCycleCfg findByPrimaryKey(Integer seqNo)
			throws FinderException;
	
	public Collection findBillCycleTypeIDByCustomerTypeAndAccountType(String customerType,String accountType)  throws FinderException; 
}