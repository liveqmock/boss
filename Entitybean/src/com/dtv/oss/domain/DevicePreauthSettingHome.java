package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DevicePreauthSettingDTO;

public interface DevicePreauthSettingHome extends EJBLocalHome {
    public DevicePreauthSetting create(Integer seqNo) throws
            CreateException;

    public DevicePreauthSetting create(DevicePreauthSettingDTO dto) throws CreateException;

    public DevicePreauthSetting findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
