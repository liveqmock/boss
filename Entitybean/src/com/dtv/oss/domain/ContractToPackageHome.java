package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ContractToPackageDTO;

public interface ContractToPackageHome extends EJBLocalHome {
    public ContractToPackage create(String contractNo, int productPackageID) throws
            CreateException;

    public ContractToPackage  create(ContractToPackageDTO dto) throws CreateException;

    public ContractToPackage findByPrimaryKey(ContractToPackagePK pk) throws
            FinderException;
    
    public Collection findByContractNo(String contractNo) throws
            FinderException;
}
