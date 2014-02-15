package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CPCampaignMapDTO;

public interface CPCampaignMapHome extends javax.ejb.EJBLocalHome {
	public CPCampaignMap create(Integer id) throws CreateException;

	public CPCampaignMap create(CPCampaignMapDTO dto) throws CreateException;
 
    public Collection findByCustProductID(int custProductID) throws FinderException;
        
	public CPCampaignMap findByPrimaryKey(Integer id) throws FinderException;
	
	public Collection findByCustomerCampaign(Integer ccID) throws FinderException;
	
	public Collection findByCustomerCampaignAndStatus(Integer ccID,java.lang.String status) throws FinderException;

}