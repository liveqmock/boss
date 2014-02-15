package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FiberReceiverDTO;

public interface FiberReceiverHome extends EJBLocalHome {
    public FiberReceiver create(Integer id) throws CreateException;

    public FiberReceiver create(FiberReceiverDTO dto) throws CreateException;

    public FiberReceiver findByPrimaryKey(Integer id) throws
            FinderException;
}
