package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FapiaoTransitionDTO;

public interface FapiaoTransitionHome extends EJBLocalHome {
    public FapiaoTransition create(Integer seqNo) throws CreateException;

    public FapiaoTransition create(FapiaoTransitionDTO dto) throws CreateException;
    
    public FapiaoTransition findByPrimaryKey(Integer seqNo) throws
            FinderException;

}
