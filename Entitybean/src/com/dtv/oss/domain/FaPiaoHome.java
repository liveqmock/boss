package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FaPiaoDTO;

public interface FaPiaoHome extends javax.ejb.EJBLocalHome {
	public FaPiao create(Integer seqNo) throws CreateException;

    public FaPiao create(FaPiaoDTO dto) throws CreateException;

    public FaPiao findByPrimaryKey(Integer seqNo) throws FinderException;
    
    public Collection findBySerialNo(java.lang.String serialNo) throws FinderException;

}
