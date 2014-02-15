package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ConstructionBatchDTO;

public interface ConstructionBatchHome extends EJBLocalHome {
    public ConstructionBatch create(Integer batchId) throws CreateException;

    public ConstructionBatch create(ConstructionBatchDTO dto) throws CreateException;

    public ConstructionBatch findByPrimaryKey(Integer batchId) throws
            FinderException;
}
