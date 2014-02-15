package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapConditionDTO;

public interface LdapConditionHome extends EJBLocalHome {
    public LdapCondition create(Integer condId) throws CreateException;

    public LdapCondition create(LdapConditionDTO dto) throws CreateException;

    public LdapCondition findByPrimaryKey(Integer condId) throws
            FinderException;
    public Collection findByCondName(String condName) throws
            FinderException;
}
