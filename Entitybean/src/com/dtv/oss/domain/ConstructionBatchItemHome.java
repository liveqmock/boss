package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ConstructionBatchItemDTO;

public interface ConstructionBatchItemHome extends EJBLocalHome {
    public ConstructionBatchItem create(Integer itemNo) throws
            CreateException;

    public ConstructionBatchItem create(ConstructionBatchItemDTO dto) throws CreateException;

    public ConstructionBatchItem findByPrimaryKey(Integer itemNo) throws
            FinderException;
}
