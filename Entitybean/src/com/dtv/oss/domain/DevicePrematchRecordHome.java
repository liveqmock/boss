package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DevicePrematchRecordDTO;

public interface DevicePrematchRecordHome extends EJBLocalHome {
    public DevicePrematchRecord create(Integer seqNo) throws
            CreateException;

    public DevicePrematchRecord create(DevicePrematchRecordDTO dto) throws CreateException;

    public DevicePrematchRecord findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
