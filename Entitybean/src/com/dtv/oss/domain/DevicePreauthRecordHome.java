package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DevicePreauthRecordDTO;

public interface DevicePreauthRecordHome extends EJBLocalHome {
    public DevicePreauthRecord create(Integer seqNo) throws CreateException;

    public DevicePreauthRecord create(DevicePreauthRecordDTO dto) throws CreateException;

    public DevicePreauthRecord findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
