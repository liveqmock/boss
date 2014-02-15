package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapCMSubIDDTO;

public interface LdapCMSubIDHome extends EJBLocalHome {
    public LdapCMSubID create(Integer deviceID) throws CreateException;

    public LdapCMSubID create(LdapCMSubIDDTO dto) throws CreateException;

    public LdapCMSubID findByPrimaryKey(Integer deviceID) throws
            FinderException;
}
