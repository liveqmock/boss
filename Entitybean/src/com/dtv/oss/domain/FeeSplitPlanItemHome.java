package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FeeSplitPlanItemDTO;

public interface FeeSplitPlanItemHome extends EJBLocalHome {
    public FeeSplitPlanItem create(Integer seqNo) throws CreateException;

    public FeeSplitPlanItem findByPrimaryKey(Integer seqNo) throws
            FinderException;
    public FeeSplitPlanItem create(FeeSplitPlanItemDTO dto) throws CreateException;
}
