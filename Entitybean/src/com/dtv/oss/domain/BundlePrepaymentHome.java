package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BundlePrepaymentDTO;

public interface BundlePrepaymentHome extends EJBLocalHome {
    public BundlePrepayment create(Integer campaignId) throws
            CreateException;

    public BundlePrepayment create(BundlePrepaymentDTO dto) throws CreateException;

    public BundlePrepayment findByPrimaryKey(Integer campaignId) throws
            FinderException;
}
