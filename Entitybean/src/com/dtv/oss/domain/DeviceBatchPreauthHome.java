package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DeviceBatchPreauthDTO;

public interface DeviceBatchPreauthHome extends EJBLocalHome {
    public DeviceBatchPreauth create(Integer batchId) throws
            CreateException;

    public DeviceBatchPreauth create(DeviceBatchPreauthDTO dto) throws CreateException;

    public DeviceBatchPreauth findByPrimaryKey(Integer batchId) throws
            FinderException;
}
