package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ContractDTO;

public interface ContractHome extends EJBLocalHome {
    public Contract create(String contractNo) throws CreateException;

    public Contract create(ContractDTO dto) throws CreateException;

    public Contract findByPrimaryKey(String contractNo) throws FinderException;
    
    public Collection findByAll() throws FinderException;
}
