package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CsiTypeReason2DeviceDTO;

public interface CsiTypeReason2DeviceHome extends EJBLocalHome {
    public CsiTypeReason2Device create(Integer seqNo) throws
            CreateException;

    public CsiTypeReason2Device create(CsiTypeReason2DeviceDTO dto) throws CreateException;

    public CsiTypeReason2Device findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
