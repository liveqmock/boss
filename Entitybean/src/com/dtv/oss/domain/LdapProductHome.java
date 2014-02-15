package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapProductDTO;

public interface LdapProductHome extends EJBLocalHome {
    public LdapProduct create(String productName) throws CreateException;

    public LdapProduct create(LdapProductDTO dto) throws CreateException;

     
    public LdapProduct findByPrimaryKey(String productName) throws
            FinderException;
}
