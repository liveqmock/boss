package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CatvTermProcessItemDTO;

public interface CatvTermProcessItemHome extends EJBLocalHome {
    public CatvTermProcessItem create(Integer itemNo) throws
            CreateException;

    public CatvTermProcessItem create(CatvTermProcessItemDTO dto) throws CreateException;

    public CatvTermProcessItem findByPrimaryKey(Integer itemNo) throws
            FinderException;
}
