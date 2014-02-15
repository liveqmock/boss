package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FapiaoTransitionDetailDTO;

public interface FapiaoTransitionDetailHome extends EJBLocalHome {
    public FapiaoTransitionDetail create(Integer seqNo) throws
            CreateException;
    public FapiaoTransitionDetail create(FapiaoTransitionDetailDTO dto) throws CreateException;
    public FapiaoTransitionDetail create() throws CreateException;

    public FapiaoTransitionDetail findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
