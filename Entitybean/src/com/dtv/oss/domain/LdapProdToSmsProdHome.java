package com.dtv.oss.domain;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapProdToSmsProdDTO;

public interface LdapProdToSmsProdHome extends EJBLocalHome {
    public LdapProdToSmsProd create(Integer smsProductId) throws
            CreateException;

    public LdapProdToSmsProd create(LdapProdToSmsProdDTO dto) throws CreateException;

    public LdapProdToSmsProd findByPrimaryKey(Integer smsProductId) throws
            FinderException;
}
