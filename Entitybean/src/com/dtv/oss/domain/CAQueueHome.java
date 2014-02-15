package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAQueueDTO;

public interface CAQueueHome extends javax.ejb.EJBLocalHome {
	public CAQueue create(Integer queueId) throws CreateException;

	public CAQueue create(CAQueueDTO dto) throws CreateException;

	public CAQueue findByPrimaryKey(Integer queueId) throws FinderException;
}