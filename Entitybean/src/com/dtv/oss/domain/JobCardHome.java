package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.JobCardDTO;

public interface JobCardHome extends javax.ejb.EJBLocalHome {
	public JobCard create(Integer jobCardId) throws CreateException;

	public JobCard create(JobCardDTO dto) throws CreateException;

	public JobCard findByPrimaryKey(Integer jobCardId) throws FinderException;
}