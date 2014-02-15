package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CasubentitlementDTO;

public interface CasubentitlementHome extends EJBLocalHome {
    public Casubentitlement create(int hostId, String cardsn,
                                   String caproductid) throws
            CreateException;

    public Casubentitlement create(CasubentitlementDTO dto) throws CreateException;

    public Casubentitlement findByPrimaryKey(CasubentitlementPK pk) throws
            FinderException;
}
