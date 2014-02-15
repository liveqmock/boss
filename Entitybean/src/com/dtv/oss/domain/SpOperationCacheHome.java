package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SpOperationCacheDTO;

public interface SpOperationCacheHome extends EJBLocalHome {
    public SpOperationCache create(Integer seqNo) throws CreateException;

    public SpOperationCache create(SpOperationCacheDTO dto) throws CreateException;

    public SpOperationCache findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
