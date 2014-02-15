package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
 
import javax.ejb.CreateException;
import javax.ejb.FinderException;

 
import com.dtv.oss.dto.OldCustomerMarketInfoDTO;

public interface OldCustomerMarketInfoHome extends EJBLocalHome {
    public OldCustomerMarketInfo create(Integer id) throws CreateException;

    public OldCustomerMarketInfo create(OldCustomerMarketInfoDTO dto) throws CreateException;

    public OldCustomerMarketInfo findByPrimaryKey(Integer id) throws
            FinderException;
}
