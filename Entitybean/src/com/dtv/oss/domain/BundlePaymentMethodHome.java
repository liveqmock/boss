package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BundlePaymentMethodDTO;

public interface BundlePaymentMethodHome extends EJBLocalHome {
    public BundlePaymentMethod create(int bundleID, String rfBillingCycleFlag) throws
            CreateException;

    public BundlePaymentMethod create(BundlePaymentMethodDTO dto) throws CreateException;

    public BundlePaymentMethod findByPrimaryKey(BundlePaymentMethodPK pk) throws
            FinderException;
}
