package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
 
import javax.ejb.CreateException;
import javax.ejb.FinderException;

 

import com.dtv.oss.dto.MaterialUsageDTO;

public interface MaterialUsageHome extends EJBLocalHome {
    public MaterialUsage create(Integer seqNo) throws CreateException;

    public MaterialUsage create(MaterialUsageDTO dto) throws CreateException;

    public MaterialUsage findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
