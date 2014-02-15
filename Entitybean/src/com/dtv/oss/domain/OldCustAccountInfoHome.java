package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OldCustAccountInfoDTO;

public interface OldCustAccountInfoHome extends EJBLocalHome {
    public OldCustAccountInfo create(Integer iD) throws CreateException;

    public OldCustAccountInfo create(OldCustAccountInfoDTO dto) throws CreateException;

    public OldCustAccountInfo findByPrimaryKey(Integer ID) throws
            FinderException;
}
