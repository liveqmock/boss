package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FapiaoDetailDTO;

public interface FapiaoDetailHome extends javax.ejb.EJBLocalHome {
    public FapiaoDetail create(Integer seqNo) throws CreateException;
    
    public FapiaoDetail create(FapiaoDetailDTO dto) throws CreateException;
    
    public FapiaoDetail findByPrimaryKey(Integer seqNo) throws
            FinderException;
    
    
}
