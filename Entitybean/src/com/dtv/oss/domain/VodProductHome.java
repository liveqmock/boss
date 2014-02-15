package com.dtv.oss.domain;


import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.VODInterfaceProductDTO;

public interface VodProductHome extends EJBLocalHome {
    public VodProduct create(Integer smsProductID) throws CreateException;

    public VodProduct create(VODInterfaceProductDTO dto) throws CreateException;

    public VodProduct findByPrimaryKey(Integer smsProductID) throws
            FinderException;
}
