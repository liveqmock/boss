package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FiberNodeDTO;

public interface FiberNodeHome extends EJBLocalHome {
    public FiberNode create(Integer id) throws CreateException;

    public FiberNode create(FiberNodeDTO dto) throws CreateException;

    public FiberNode findByPrimaryKey(Integer id) throws FinderException;
}
