package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapEventCmdMapDTO;

public interface LdapEventCmdMapHome extends EJBLocalHome {
    public LdapEventCmdMap create(Integer mapID) throws CreateException;

    public LdapEventCmdMap create(LdapEventCmdMapDTO dto) throws CreateException;

    public LdapEventCmdMap findByPrimaryKey(Integer mapID) throws
            FinderException;
}
