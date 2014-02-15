package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.UserMigrationRegInfoDTO;

public interface UserMigrationRegInfoHome extends EJBLocalHome {
    public UserMigrationRegInfo create(String catvID) throws
            CreateException;

    public UserMigrationRegInfo create(UserMigrationRegInfoDTO dto) throws CreateException;

    public UserMigrationRegInfo findByPrimaryKey(String catvID) throws
            FinderException;
}
