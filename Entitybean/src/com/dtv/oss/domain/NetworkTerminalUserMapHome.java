package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.NetworkTerminalUserMapDTO;

public interface NetworkTerminalUserMapHome extends EJBLocalHome {
    public NetworkTerminalUserMap create(Integer seqNo) throws
            CreateException;

    public NetworkTerminalUserMap create(NetworkTerminalUserMapDTO dto) throws CreateException;

    public NetworkTerminalUserMap findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
