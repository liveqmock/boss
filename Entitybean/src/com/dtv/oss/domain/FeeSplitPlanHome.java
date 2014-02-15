package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FeeSplitPlanDTO;

public interface FeeSplitPlanHome extends EJBLocalHome {
    public FeeSplitPlan create(Integer feeSplitPlanID) throws
            CreateException;

    public FeeSplitPlan create(FeeSplitPlanDTO dto) throws CreateException;

    public FeeSplitPlan findByPrimaryKey(Integer feeSplitPlanID) throws
            FinderException;
}
