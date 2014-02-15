package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CatvTermProcessDTO;

public interface CatvTermProcessHome extends EJBLocalHome {
    public CatvTermProcess create(Integer batchId) throws CreateException;

    public CatvTermProcess create(CatvTermProcessDTO dto) throws CreateException;

    public CatvTermProcess findByPrimaryKey(Integer batchId) throws
            FinderException;
}
