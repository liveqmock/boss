package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OldCustomerInfoDTO;

public interface OldCustomerInfoHome extends EJBLocalHome {
    public OldCustomerInfo create(Integer id) throws CreateException;

    public OldCustomerInfo create(OldCustomerInfoDTO dto) throws CreateException;

    public OldCustomerInfo findByPrimaryKey(Integer id) throws
            FinderException;
}
