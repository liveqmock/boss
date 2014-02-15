package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.JobCardProcessDTO;

public interface JobCardProcessHome extends javax.ejb.EJBLocalHome {
	public JobCardProcess create(Integer seqNo) throws CreateException;

	public JobCardProcess create(JobCardProcessDTO dto) throws CreateException;

	public JobCardProcess findByPrimaryKey(Integer seqNo)
			throws FinderException;
}