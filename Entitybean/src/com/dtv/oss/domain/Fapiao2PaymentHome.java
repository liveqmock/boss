package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import java.math.BigDecimal;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.Fapiao2PaymentDTO;

public interface Fapiao2PaymentHome extends javax.ejb.EJBLocalHome {
    public Fapiao2Payment create(Integer seqNo) throws CreateException;

    public Fapiao2Payment create(Fapiao2PaymentDTO dto) throws CreateException;

    public Fapiao2Payment findByPrimaryKey(Integer seqNo) throws
            FinderException;    
    public Collection findByFapiaoSeqNo(int fapiaoSeqNo) throws FinderException;	
}
