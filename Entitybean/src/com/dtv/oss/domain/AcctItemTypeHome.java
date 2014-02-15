package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.AcctItemTypeDTO;

public interface AcctItemTypeHome extends javax.ejb.EJBLocalHome {
	public AcctItemType create(String acctItemTypeID) throws CreateException;

	public AcctItemType create(AcctItemTypeDTO dto) throws CreateException;

	public AcctItemType findByPrimaryKey(String acctItemTypeID)
			throws FinderException;
        public Collection findBySummaryTo(String summaryTo) throws FinderException;			

}