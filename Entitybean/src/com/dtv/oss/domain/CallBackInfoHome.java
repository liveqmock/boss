package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CallBackInfoDTO;

import java.util.Collection;

public interface CallBackInfoHome extends javax.ejb.EJBLocalHome {
	public CallBackInfo create(Integer seqNo) throws CreateException;

	public CallBackInfo create(CallBackInfoDTO dto) throws CreateException;

	public CallBackInfo findByPrimaryKey(Integer seqNo) throws FinderException;
	
	public Collection findByReferTypeAndReferSourceId(java.lang.String referSourceType,int referSourceId)
                  throws FinderException;
         
}