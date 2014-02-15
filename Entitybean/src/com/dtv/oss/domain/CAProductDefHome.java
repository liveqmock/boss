package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAProductDefDTO;

public interface CAProductDefHome extends EJBLocalHome {
    public CAProductDef create(int hostID, int opiID, String caProductID) throws CreateException;

    public CAProductDef create(CAProductDefDTO dto) throws CreateException;

    public CAProductDef findByPrimaryKey(CAProductDefPK pk) throws
            FinderException;
}
