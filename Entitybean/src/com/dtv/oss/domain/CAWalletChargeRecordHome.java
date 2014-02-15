package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAWalletChargeRecordDTO;

public interface CAWalletChargeRecordHome extends EJBLocalHome {
    public CAWalletChargeRecord create(Integer seqNo) throws
            CreateException;

    public CAWalletChargeRecord create(CAWalletChargeRecordDTO dto) throws CreateException;

    public CAWalletChargeRecord findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
