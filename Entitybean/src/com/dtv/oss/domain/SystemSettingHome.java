package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SystemSettingDTO;

public interface SystemSettingHome extends EJBLocalHome {
    public SystemSetting create(String name) throws CreateException;

    public SystemSetting create(SystemSettingDTO dto) throws CreateException;

    public SystemSetting findByPrimaryKey(String name) throws FinderException;
}
