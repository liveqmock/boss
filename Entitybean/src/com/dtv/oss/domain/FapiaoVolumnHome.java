package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FapiaoVolumnDTO;

public interface FapiaoVolumnHome extends EJBLocalHome {
    public FapiaoVolumn create(Integer seqNo) throws CreateException;

    public FapiaoVolumn create(FapiaoVolumnDTO dto) throws CreateException;

    public FapiaoVolumn findByPrimaryKey(Integer seqNo) throws
            FinderException;
    
    public Collection findByVolumnSN(java.lang.String volumnSn) throws FinderException;	
    
    public Collection findByTypeAndVolumnSN(java.lang.String type,java.lang.String volumnSn) throws FinderException;	
}
