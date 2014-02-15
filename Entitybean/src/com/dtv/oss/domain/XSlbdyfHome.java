package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.XSlbdyfDTO;

public interface XSlbdyfHome extends EJBLocalHome {
    public XSlbdyf create(Integer lsh) throws CreateException;

    public XSlbdyf create(XSlbdyfDTO dto) throws CreateException;

    public XSlbdyf findByPrimaryKey(Integer lsh) throws FinderException;
}
