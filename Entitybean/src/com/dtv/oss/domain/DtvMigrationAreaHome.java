package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DtvMigrationAreaDTO;

public interface DtvMigrationAreaHome extends EJBLocalHome {
    public DtvMigrationArea create(Integer id) throws CreateException;

    public DtvMigrationArea create(DtvMigrationAreaDTO dto) throws CreateException;

    public DtvMigrationArea findByPrimaryKey(Integer id) throws
            FinderException;
}
