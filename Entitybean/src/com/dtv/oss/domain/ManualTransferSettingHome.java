package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ManualTransferSettingDTO;

public interface ManualTransferSettingHome extends EJBLocalHome {
    public ManualTransferSetting create(Integer seqNo) throws
            CreateException;

    public ManualTransferSetting create(ManualTransferSettingDTO dto) throws CreateException;

    public ManualTransferSetting findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
