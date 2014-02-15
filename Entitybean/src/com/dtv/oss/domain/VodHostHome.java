package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.VODInterfaceHostDTO;

public interface VodHostHome extends EJBLocalHome {
    public VodHost create(Integer hostID) throws CreateException;

    public VodHost create(VODInterfaceHostDTO dto) throws CreateException;

    

    public VodHost findByPrimaryKey(Integer hostID) throws FinderException;
}
