package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CsiActionReasonSettingDTO;

public interface CsiActionReasonSettingHome extends EJBLocalHome {
    public CsiActionReasonSetting create(Integer seqNo) throws
            CreateException;

    public CsiActionReasonSetting create(CsiActionReasonSettingDTO dto) throws CreateException;

    public CsiActionReasonSetting findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
