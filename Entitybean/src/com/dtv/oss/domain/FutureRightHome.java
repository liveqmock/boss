package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
 
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FutureRightDTO;

public interface FutureRightHome extends EJBLocalHome {
    public FutureRight create(Integer seqNo) throws CreateException;

    public FutureRight create(FutureRightDTO dto) throws CreateException;

    public FutureRight findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
