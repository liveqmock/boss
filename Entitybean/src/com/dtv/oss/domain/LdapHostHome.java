package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapHostDTO;

public interface LdapHostHome extends EJBLocalHome {
    public LdapHost create(Integer hostID) throws CreateException;

    public LdapHost create(LdapHostDTO dto) throws CreateException;

    public LdapHost findByPrimaryKey(Integer hostID) throws FinderException;
}
