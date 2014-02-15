package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FiberTransmitterDTO;

public interface FiberTransmitterHome extends EJBLocalHome {
    public FiberTransmitter create(Integer id) throws CreateException;

    public FiberTransmitter create(FiberTransmitterDTO dto) throws CreateException;

    public FiberTransmitter findByPrimaryKey(Integer id) throws
            FinderException;
}
