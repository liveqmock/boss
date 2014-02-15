package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CatvTermMigrationDTO;

public interface CatvTermMigrationHome extends EJBLocalHome {
    public CatvTermMigration create(String catvID) throws CreateException;

    public CatvTermMigration create(CatvTermMigrationDTO dto) throws CreateException;

    public CatvTermMigration findByPrimaryKey(String catvID) throws
            FinderException;
}
