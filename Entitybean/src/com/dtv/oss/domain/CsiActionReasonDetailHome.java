package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CsiActionReasonDetailDTO;

public interface CsiActionReasonDetailHome extends EJBLocalHome {
    public CsiActionReasonDetail create(Integer seqNo) throws
            CreateException;

    public CsiActionReasonDetail create(CsiActionReasonDetailDTO dto) throws CreateException;

    public CsiActionReasonDetail findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
