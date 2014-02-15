package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapProcessLogDTO;

public interface LdapProcessLogHome extends EJBLocalHome {
    public LdapProcessLog create(Integer seqno) throws CreateException;

    public LdapProcessLog create(LdapProcessLogDTO dto) throws CreateException;

    public LdapProcessLog findByPrimaryKey(Integer seqno) throws
            FinderException;
}
