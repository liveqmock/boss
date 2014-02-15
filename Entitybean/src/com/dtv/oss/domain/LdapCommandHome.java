package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapCommandDTO;

public interface LdapCommandHome extends EJBLocalHome {
    public LdapCommand create(String commandName) throws CreateException;

    public LdapCommand create(LdapCommandDTO dto) throws CreateException;

    public LdapCommand findByPrimaryKey(String commandName) throws
            FinderException;
}
