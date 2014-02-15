package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FapiaoModuleDTO;

public interface FapiaoModuleHome extends EJBLocalHome {
    public FapiaoModule create(Integer seqNo) throws CreateException;

    public FapiaoModule create(FapiaoModuleDTO dto) throws CreateException;

    public FapiaoModule findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
