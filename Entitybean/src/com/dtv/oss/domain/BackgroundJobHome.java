package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BackgroundJobDTO;

public interface BackgroundJobHome extends javax.ejb.EJBLocalHome {
	public BackgroundJob create(Integer id) throws CreateException;

	public BackgroundJob create(BackgroundJobDTO dto) throws CreateException;

	public BackgroundJob findByPrimaryKey(Integer id) throws FinderException;
}