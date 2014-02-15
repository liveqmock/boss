package com.dtv.oss.domain;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BillBoardDTO;

public interface BillBoardHome extends EJBLocalHome {
    public BillBoard create(Integer seqNo) throws CreateException;

    
    public BillBoard create(BillBoardDTO dto) throws CreateException;

    public BillBoard findByPrimaryKey(Integer seqNo) throws FinderException;
}
