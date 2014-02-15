package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapEntitlementDTO;

public interface LdapEntitlementHome extends EJBLocalHome {
    public LdapEntitlement create(int deviceid, String productname) throws
            CreateException;

    public LdapEntitlement create(LdapEntitlementDTO dto) throws CreateException;

    public LdapEntitlement findByPrimaryKey(LdapEntitlementPK pk) throws
            FinderException;
}
