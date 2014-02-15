package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SwapDeviceReason2StatusDTO;

public interface SwapDeviceReason2StatusHome extends EJBLocalHome {
    public SwapDeviceReason2Status create(Integer seqNo) throws
            CreateException;

    public SwapDeviceReason2Status create(SwapDeviceReason2StatusDTO dto) throws CreateException;

    public SwapDeviceReason2Status findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
